package com.light.cryptocurrency.ui.rates

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import timber.log.Timber

import com.light.cryptocurrency.data.Coin

import com.light.cryptocurrency.data.CoinsRepo
import com.light.cryptocurrency.data.CurrencyRepo
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future


class RatesViewModel @Inject constructor(val coinsRepo: CoinsRepo, val currencyRepo: CurrencyRepo) :
    ViewModel() {

    private val coins = MutableLiveData<List<Coin?>>()

    private val isRefreshing = MutableLiveData<Boolean>()

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    private var future: Future<*>? = null

    // AppComponent(BaseComponent) -> MainComponent -> Fragment(BaseComponent) -> RatesComponent -> RatesViewModel()

    // AppComponent(BaseComponent) -> MainComponent -> Fragment(BaseComponent) -> RatesComponent -> RatesViewModel()

    init {
        refresh()
    }

    fun coins(): MutableLiveData<List<Coin?>> {
        return coins
    }

    fun isRefreshing(): LiveData<Boolean> {
        return isRefreshing
    }

    fun refresh() {
        isRefreshing.postValue(true)
        future = executor.submit {
            try {
                coins.postValue(ArrayList(coinsRepo.listings("USD")))
                isRefreshing.postValue(false)
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
    }

    override fun onCleared() {
        if (future != null) {
            future!!.cancel(true)
        }
    }
}

