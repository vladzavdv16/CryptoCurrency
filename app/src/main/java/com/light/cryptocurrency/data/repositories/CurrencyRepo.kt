package com.light.cryptocurrency.data.repositories

import androidx.lifecycle.LiveData
import com.light.cryptocurrency.data.model.Currency
import io.reactivex.Observable

interface CurrencyRepo {

    fun availableCurrencies(): LiveData<List<Currency?>>

    fun currency(): Observable<Currency>

    fun updateCurrency(currency: Currency)

}