package com.cryptocurrency.core.data.repository

/**
 * Created by Zavodov on 02.01.2023
 */
interface ProfileRepo {

    suspend fun insert(onSuccess: () -> Unit)
    suspend fun delete(onSuccess: () -> Unit)

    fun connectToDatabase(email: String, userFullName: String, onSuccess: () -> Unit, onFailure: (String) -> Unit){}

    fun signOut(){}
}