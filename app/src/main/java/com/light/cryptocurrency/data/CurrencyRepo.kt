package com.light.cryptocurrency.data

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface CurrencyRepo {

    @NonNull
    fun availableCurrencies(): LiveData<List<Currency?>>

    @NonNull
    fun currency(): LiveData<Currency>?

    fun updateCurrency(currency: Currency)

}