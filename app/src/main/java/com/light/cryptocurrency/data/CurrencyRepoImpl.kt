package com.light.cryptocurrency.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.light.cryptocurrency.R
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData


@Singleton
class CurrencyRepoImpl @Inject constructor(val context: Context) : CurrencyRepo {

    private val KEY_CURRENCY = "currency"

    private var availableCurrencies: HashMap<String, Currency> = HashMap()

    var prefs: SharedPreferences? = null


    init {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context)
        availableCurrencies["USD"] = Currency("$", "USD", context.getString(R.string.usd));
        availableCurrencies["EUR"] = Currency("E", "EUR", context.getString(R.string.euro));
        availableCurrencies["RUB"] = Currency("R", "RUB", context.getString(R.string.rub));
    }


    override fun availableCurrencies(): LiveData<List<Currency?>> {
        val liveData = MutableLiveData<List<Currency?>>()
        liveData.value = ArrayList(availableCurrencies.values)
        return liveData
    }

    override fun currency(): LiveData<Currency> {
        return CurrencyLiveData()
    }

    override fun updateCurrency(currency: Currency) {
    }

    inner class CurrencyLiveData : LiveData<Currency>(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onActive() {
            prefs?.registerOnSharedPreferenceChangeListener(this)
            value = availableCurrencies[prefs?.getString(KEY_CURRENCY, "USD")]
        }

        override fun onInactive() {
            prefs?.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(prefs: SharedPreferences, key: String?) {
            value = availableCurrencies[prefs.getString(key, "USD")]
        }
    }
}