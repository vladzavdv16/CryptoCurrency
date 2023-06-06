package com.cryprocurrency.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Created by Zavodov on 02.01.2023
 */
class ProfileRepoImpl @Inject constructor(): ProfileRepo {

    private val authFirebase = FirebaseAuth.getInstance()

    private val _isFlag = MutableStateFlow(false)

    override fun isFlag() = _isFlag.asStateFlow()

    override suspend fun insert(onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }


    override fun connectToDatabase(email: String, userFullName: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authFirebase.signInWithEmailAndPassword(email, userFullName)
            .addOnSuccessListener {
                _isFlag.tryEmit(true)
                onSuccess()
            }
            .addOnFailureListener {
                authFirebase.createUserWithEmailAndPassword(email, userFullName)
                    .addOnSuccessListener {
                        _isFlag.tryEmit(true)
                        onSuccess()
                    }
                    .addOnFailureListener {
                        _isFlag.tryEmit(false)
                        onFailure(it.message.toString())
                    }
            }
    }

    override fun signOut() {
        authFirebase.signOut()
    }
}