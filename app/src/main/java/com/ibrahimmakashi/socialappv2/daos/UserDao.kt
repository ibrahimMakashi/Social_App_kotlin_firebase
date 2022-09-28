package com.ibrahimmakashi.socialappv2.daos

import android.annotation.SuppressLint
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.ibrahimmakashi.socialappv2.Models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    @SuppressLint("RestrictedApi")
    fun addUser(user: User) {
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                user.uid?.let { it1 -> usersCollection.document(it1).set(it) }
            }
        }
    }

    fun getUserById(uId: String): Task<DocumentSnapshot> {
        return usersCollection.document(uId).get()
    }
}