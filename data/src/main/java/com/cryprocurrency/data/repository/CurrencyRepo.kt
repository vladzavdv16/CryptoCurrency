package com.cryprocurrency.data.repository

import androidx.lifecycle.LiveData
import com.cryprocurrency.data.model.Currency
import io.reactivex.Observable

interface CurrencyRepo {

    fun availableCurrencies(): LiveData<List<Currency?>>

    fun currency(): Observable<Currency>

    fun updateCurrency(currency: Currency)

}