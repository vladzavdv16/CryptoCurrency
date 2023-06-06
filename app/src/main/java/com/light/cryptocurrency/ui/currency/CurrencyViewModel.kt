package com.light.cryptocurrency.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cryprocurrency.data.model.Currency
import com.cryprocurrency.data.repository.CurrencyRepo
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    val currencyRepo: CurrencyRepo
): ViewModel() {

    fun allCurrency(): LiveData<List<Currency?>> = currencyRepo.availableCurrencies()

    fun updateCurrency(currency: Currency) = currencyRepo.updateCurrency(currency)
}

