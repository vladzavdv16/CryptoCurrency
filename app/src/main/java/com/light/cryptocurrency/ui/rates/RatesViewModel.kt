package com.light.cryptocurrency.ui.rates


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import javax.inject.Inject

import com.light.cryptocurrency.data.Coin
import com.light.cryptocurrency.data.CoinsRepo
import com.light.cryptocurrency.data.CurrencyRepo


class RatesViewModel @Inject constructor(
    val coinsRepo: CoinsRepo,
    val currencyRepo: CurrencyRepo) : ViewModel() {


    private val isRefreshing = MutableLiveData<Boolean>()

    private val forceRefresh = MutableLiveData(false)

    private val coins: LiveData<List<Coin>>

    // AppComponent(BaseComponent) -> MainComponent -> Fragment(BaseComponent) -> RatesComponent -> RatesViewModel()

    init {
        val query = map(forceRefresh) { r ->
            isRefreshing.postValue(true)
            CoinsRepo.Query.builder().forceUpdate(r).currency("USD").build()
        }
        val coins = switchMap(query) { q ->
            coinsRepo.listings(q)
        }
        this.coins = map(coins) { c ->
            isRefreshing.postValue(false)
            c
        }
    }

    fun coins(): LiveData<List<Coin>> {
        return coins
    }

    fun isRefreshing(): LiveData<Boolean> {
        return isRefreshing
    }

    fun refresh() {
        forceRefresh.postValue(true)
    }

    override fun onCleared() {
    }
}

