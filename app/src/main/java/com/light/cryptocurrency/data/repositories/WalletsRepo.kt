package com.light.cryptocurrency.data.repositories

import com.light.cryptocurrency.data.model.Currency
import com.light.cryptocurrency.data.model.Transaction
import com.light.cryptocurrency.data.model.Wallet
import io.reactivex.Observable

interface WalletsRepo {

    fun wallet(currency: Currency): Observable<List<Wallet>>

    fun transaction(wallet: Wallet): Observable<List<Transaction>>
}