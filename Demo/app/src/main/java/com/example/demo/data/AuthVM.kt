package com.example.demo.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthVM (val app: Application) : AndroidViewModel(app) {

    private val USERS = Firebase.firestore.collection("users")
    private val userLD = MutableLiveData<User?>()
    private var listener: ListenerRegistration? = null

    init {
        userLD.value = null
    }

    // ---------------------------------------------------------------------------------------------

    fun init() = Unit

    fun getUserLD() = userLD

    fun getUser() = userLD.value

    // TODO(1): Login
    fun login(email: String, password: String, remember: Boolean = false) : Boolean {
        // TODO(1A): Get the user record with matching email + password
        //           Return false is no matching found


        // TODO(1B): Setup snapshot listener
        //           Update live data -> user


        // TODO(6A): Handle remember-me -> add shared preferences


        return true
    }

    // TODO(2): Logout
    fun logout() {
        // TODO(2A): Remove snapshot listener
        //           Update live data -> null


        // TODO(6B): Handle remember-me -> clear shared preferences


    }

    // TODO(6): Get shared preferences
    private fun getPreferences() = 0

    // TODO(7): Auto login from shared preferences
    fun loginFromPreferences() {
        
    }

}