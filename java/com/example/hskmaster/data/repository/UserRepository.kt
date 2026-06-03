package com.example.hskmaster.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.hskmaster.data.model.UserProgress

class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun saveUserProgress(progress: UserProgress) {
        val uid = auth.currentUser?.uid ?: return

        db.collection("users")
            .document(uid)
            .set(progress)
    }
}