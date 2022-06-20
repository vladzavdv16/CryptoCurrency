package com.light.cryptocurrency.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.light.cryptocurrency.R
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import com.light.cryptocurrency.data.model.Currency
import io.reactivex.Observable


@Singleton
class CurrencyRepoImpl @Inject constructor(val context: Context) : CurrencyRepo {

    private val KEY_CURRENCY = "currency"

    private var availableCurrencies: HashMap<String, Currency> = HashMap()

    var prefs: SharedPreferences? = null


    init {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context)
        availableCurrencies["USD"] = Currency("$", "USD", context.getString(R.string.usd))
        availableCurrencies["EUR"] = Currency("E", "EUR", context.getString(R.string.euro))
        availableCurrencies["RUB"] = Currency("R", "RUB", context.getString(R.string.rub))
        println("Currency Repo")
    }


    override fun availableCurrencies(): LiveData<List<Currency?>> {
        val liveData = MutableLiveData<List<Currency?>>()
        liveData.value = ArrayList(availableCurrencies.values)
        return liveData
    }

    override fun currency(): Observable<Currency> {
        return Observable.create { emitter ->
            val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
                if (!emitter.isDisposed) {
                    emitter.onNext(availableCurrencies[prefs.getString(key, "USD")]!!)
                }
            }
            prefs!!.registerOnSharedPreferenceChangeListener(listener)
            emitter.setCancellable { prefs!!.unregisterOnSharedPreferenceChangeListener(listener) }
            emitter.onNext(availableCurrencies[prefs!!.getString(KEY_CURRENCY, "USD")]!!)
        }
    }

    override fun updateCurrency(currency: Currency) {
        prefs?.edit()?.putString(KEY_CURRENCY, currency.code)?.apply()
    }
}