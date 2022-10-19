package com.light.cryptocurrency.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cryptocurrency.core.data.model.Currency
import com.cryptocurrency.core.data.repository.CurrencyRepo
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    val currencyRepo: CurrencyRepo
): ViewModel() {

    fun allCurrency(): LiveData<List<Currency?>> = currencyRepo.availableCurrencies()

    fun updateCurrency(currency: Currency) = currencyRepo.updateCurrency(currency)
}

