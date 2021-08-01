package com.light.cryptocurrency.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.light.cryptocurrency.R
import java.util.ArrayList

class CurrencyRepoImpl(val context: Context) : CurrencyRepo {


    override fun availableCurrencies(): LiveData<List<Currency>> {
        return AllCurrenciesLiveData(context = context)
    }

    override fun currency(): LiveData<Currency>? {
        return null
    }

    override fun updateCurrency(currency: Currency) {
    }

    class AllCurrenciesLiveData(val context: Context) : LiveData<List<Currency>>() {

        override fun onActive() {
            super.onActive()

            val currencies: MutableList<Currency> = ArrayList()
            currencies.add(Currency("$", "USD", context.getString(R.string.usd)))
            currencies.add(Currency("E", "EUR", context.getString(R.string.euro)))
            currencies.add(Currency("R", "RUB", context.getString(R.string.rub)))
            value = currencies
        }
    }
}