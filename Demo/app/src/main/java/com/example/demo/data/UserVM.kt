package com.example.demo.data

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.demo.R
import com.example.demo.util.toBlob
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase

class UserVM(val app: Application) : AndroidViewModel(app) {

    private val USERS = Firebase.firestore.collection("users")
    private val usersLD = MutableLiveData<List<User>>()
    private var listener: ListenerRegistration? = null

    init {
        listener = USERS.addSnapshotListener { snap, _ -> usersLD.value = snap?.toObjects() }
    }

    override fun onCleared() {
        listener?.remove()
    }

    // ---------------------------------------------------------------------------------------------

    fun init() = Unit

    fun getUsersLD() = usersLD

    fun getAll() = usersLD.value

    fun get(email: String) = usersLD.value?.find { it.email == email }

    fun set(user: User) {
        USERS.document(user.email).set(user);
    }

    fun delete(email: String) {
        USERS.document(email).delete()
    }

    fun deleteAll() {
        usersLD.value?.forEach { USERS.document(it.email).delete() }
    }

    fun restore() {
        USERS.get().addOnSuccessListener {
            // (1) DELETE users
            it.documents.forEach { it.reference.delete() }

            // (2) ADD users
            val user1 = User(
                email    = "1@gmail.com",
                password = "12345",
                name     = "Bae Suzy",
                photo    = BitmapFactory.decodeResource(app.resources, R.drawable.suzy).toBlob()
            )
            USERS.document(user1.email).set(user1)

            val user2 = User(
                email    = "2@gmail.com",
                password = "12345",
                name     = "Lee Jieun",
                photo    = BitmapFactory.decodeResource(app.resources, R.drawable.jieun).toBlob()
            )
            USERS.document(user2.email).set(user2)
        }
    }

    // ---------------------------------------------------------------------------------------------

    private fun emailExists(email: String) = usersLD.value?.any { it.email == email } ?: false

    fun validate(user: User, insert: Boolean = true): String {
        val regexEmail = Regex("""^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$""")
        var e = ""

        if (insert) {
            e += if (user.email == "") "- Email required.\n"
            else if (!user.email.matches(regexEmail)) "- Email format invalid.\n"
            else if (user.email.length > 100) "- Email too long (max 100 chars).\n"
            else if (emailExists(user.email)) "- Email duplicated.\n"
            else ""
        }

        e += if (user.password == "") "- Password required.\n"
        else if (user.password.length < 5) "- Password too short (min 5 chars).\n"
        else if (user.password.length > 100) "- Password too long (max 100 chars).\n"
        else ""

        e += if (user.name == "") "- Name required.\n"
        else if (user.name.length < 3) "- Name too short (min 3 chars).\n"
        else if (user.name.length > 100) "- Name too long (max 100 chars).\n"
        else ""

        e += if (user.photo.toBytes().isEmpty()) "- Photo required.\n"
        else ""

        return e
    }

}