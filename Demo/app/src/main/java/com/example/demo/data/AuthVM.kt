package com.example.demo.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthVM (val app: Application) : AndroidViewModel(app) {

    private val USERS = Firebase.firestore.collection("users")
    private val userLD = MutableLiveData<User?>()
    private var listener: ListenerRegistration? = null

    init {
        //user not yet login
        userLD.value = null
    }

    // ---------------------------------------------------------------------------------------------

    fun init() = Unit

    fun getUserLD() = userLD

    fun getUser() = userLD.value

    // TODO(1): Login
    suspend fun login(email: String, password: String, remember: Boolean = false) : Boolean {
        // TODO(1A): Get the user record with matching email + password
        //           Return false is no matching found
        if (email == "" || password == "") return false

        val user = USERS
            .whereEqualTo(FieldPath.documentId(), email)
            .whereEqualTo("password",password)
            .get() //get a list of record [record1, record2,...]
            //.addOnSuccessListener {  } -> Asynchronouse
            .await()
            .toObjects<User>()// array of user
            .firstOrNull() ?: return false // take the first user , if do not have null, nullish operation if null return false

        // TODO(1B): Setup snapshot listener
        //           Update live data -> user
         listener?.remove()
        // if user USERS.addSanpshot , listen all record
        listener = USERS.document(user.email).addSnapshotListener { snap, _ ->
            userLD.value = snap?.toObject()
        }

        // TODO(6A): Handle remember-me -> add shared preferences
        if (remember) {
            getPreferences()
                .edit()
                .putString("email", email)
                .putString("password", password)
                .apply()
        }

        return true
    }

    // TODO(2): Logout
    fun logout() {
        // TODO(2A): Remove snapshot listener
        //           Update live data -> null
        listener?.remove()
        userLD.value = null

        // TODO(6B): Handle remember-me -> clear shared preferences
        getPreferences().edit().remove("email").remove("password").apply()

        //remove everything , but the file share preference still there
//        getPreferences().edit().clear().apply()

        //delete the file
//        app.deleteSharedPreferences("AUTH")

    }

    // TODO(6): Get shared preferences
    //share preference is private, only your app can acessed, other app cannot acessed
    //XML text file to store key - value pair, for simple data, like string, int double
    //EG name=Alice, age=18
    //EG background=dark
    private fun getPreferences() = app.getSharedPreferences("AUTH", Context.MODE_PRIVATE)

    // TODO(7): Auto login from shared preferences
    suspend fun loginFromPreferences() {
        val email = getPreferences().getString("email",null)
        val password = getPreferences().getString("password",null)

        if(email != null && password != null){
            login(email,password)
        }
    }

}