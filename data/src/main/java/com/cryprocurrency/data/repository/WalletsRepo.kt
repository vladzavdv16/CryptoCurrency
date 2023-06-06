package com.cryprocurrency.data.repository

import com.cryprocurrency.data.model.Currency
import com.cryprocurrency.data.model.Transaction
import com.cryprocurrency.data.model.Wallet
import io.reactivex.Observable

interface WalletsRepo {

    fun wallet(currency: Currency): Observable<List<Wallet>>

    fun transaction(wallet: Wallet): Observable<List<Transaction>>
}