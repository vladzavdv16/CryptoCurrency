package com.light.cryptocurrency.ui.rates


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import javax.inject.Inject

import com.light.cryptocurrency.data.model.Coin
import com.light.cryptocurrency.data.repositories.CoinsRepo
import com.light.cryptocurrency.data.repositories.CurrencyRepo
import com.light.cryptocurrency.data.SortBy
import java.util.concurrent.atomic.AtomicBoolean


class RatesViewModel @Inject constructor(
    val coinsRepo: CoinsRepo,
    val currencyRepo: CurrencyRepo
): ViewModel() {


    private val isRefreshing = MutableLiveData<Boolean>()

    private val forceRefresh = MutableLiveData(AtomicBoolean(false))

    private val sortBy = MutableLiveData(SortBy.RANK)

    private val coins: LiveData<List<Coin>>

    private var sortingIndex = 2

    // AppComponent(BaseComponent) -> MainComponent -> Fragment(BaseComponent) -> RatesComponent -> RatesViewModel()

    init {
        val query = switchMap(forceRefresh) { r ->
            switchMap(currencyRepo.currency()) { c ->
                r.set(true)
                isRefreshing.postValue(true)
                map(sortBy){ s ->
                    CoinsRepo.Query.builder()
                        .forceUpdate(r.getAndSet(false))
                        .currency(c.code)
                        .sortBy(s)
                        .build()
                }
            }
        }
        val coins = switchMap(query) { q ->
            coinsRepo.listings(q)
        }
        this.coins = map(coins) { c ->
            isRefreshing.postValue(false)
            c
        }
    }

    fun coins(): LiveData<List<Coin>> = coins

    fun isRefreshing(): LiveData<Boolean> = isRefreshing

    fun refresh() = forceRefresh.postValue(AtomicBoolean(true))

    fun switchOrderSorter() = sortBy.postValue(SortBy.values()[sortingIndex++ % SortBy.values().size])
}

