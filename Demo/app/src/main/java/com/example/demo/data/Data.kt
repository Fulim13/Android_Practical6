package com.example.demo.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId

data class User (
    @DocumentId
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val photo: Blob = Blob.fromBytes(ByteArray(0))
)
