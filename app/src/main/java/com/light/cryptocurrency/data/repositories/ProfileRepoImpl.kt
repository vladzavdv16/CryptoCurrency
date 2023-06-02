package com.light.cryptocurrency.data.repositories

import com.cryptocurrency.core.data.repository.ProfileRepo
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Created by Zavodov on 02.01.2023
 */
class ProfileRepoImpl @Inject constructor(): ProfileRepo {

    private val authFirebase = FirebaseAuth.getInstance()


    override suspend fun insert(onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }


    override fun connectToDatabase(email: String, userFullName: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authFirebase.signInWithEmailAndPassword(email, userFullName)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                authFirebase.createUserWithEmailAndPassword(email, userFullName)
            }
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message.toString())
            }
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}