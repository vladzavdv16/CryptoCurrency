package com.cryptocurrency.core.data.repository

import com.cryptocurrency.core.data.model.Currency
import com.cryptocurrency.core.data.model.Transaction
import com.cryptocurrency.core.data.model.Wallet
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface WalletsRepo {

    fun wallet(currency: Currency): Observable<List<Wallet>>

    fun transaction(wallet: Wallet): Observable<List<Transaction>>
}