package com.cryprocurrency.data.repository

import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Zavodov on 02.01.2023
 */
interface ProfileRepo {

    suspend fun insert(onSuccess: () -> Unit)
    suspend fun delete(onSuccess: () -> Unit)

    fun isFlag(): StateFlow<Boolean>

    fun connectToDatabase(email: String, userFullName: String, onSuccess: () -> Unit, onFailure: (String) -> Unit){}

    fun signOut(){}
}