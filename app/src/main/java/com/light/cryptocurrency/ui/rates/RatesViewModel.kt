package com.light.cryptocurrency.ui.rates

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.light.cryptocurrency.data.CmcCoinsRepo
import com.light.cryptocurrency.data.Coin
import com.light.cryptocurrency.data.CoinsRepo
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import javax.inject.Inject

class RatesViewModel @Inject constructor(val coinsRepo: CoinsRepo) : ViewModel() {

    private var isRefreshing: MutableLiveData<Boolean> = MutableLiveData()

    private var coins: MutableLiveData<List<Coin?>> = MutableLiveData()

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    private var future: Future<*>? = null

    init {
        refresh()
    }

    @NonNull
    fun coins(): LiveData<List<Coin?>> {
        return coins
    }

    @NonNull
    fun isRefreshing(): LiveData<Boolean> {
        return isRefreshing
    }

    private fun refresh() {
        isRefreshing.postValue(true)
        future = executor.submit {
            try {
                coins.postValue(coinsRepo.listings("USD"))
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