package com.light.cryptocurrency.data.repositories

import androidx.lifecycle.LiveData
import com.light.cryptocurrency.data.model.Currency

interface CurrencyRepo {

    fun availableCurrencies(): LiveData<List<Currency?>>

    fun currency(): LiveData<Currency>

    fun updateCurrency(currency: Currency)

}