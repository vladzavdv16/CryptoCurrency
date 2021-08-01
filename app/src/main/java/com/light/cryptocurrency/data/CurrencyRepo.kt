package com.light.cryptocurrency.data

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData

interface CurrencyRepo {

    @NonNull
    fun availableCurrencies(): LiveData<List<Currency>>

    @NonNull
    fun currency(): LiveData<Currency>?

    fun updateCurrency(currency: Currency)

}