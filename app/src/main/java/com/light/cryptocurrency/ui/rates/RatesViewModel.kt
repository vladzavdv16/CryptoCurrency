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
import com.light.cryptocurrency.data.mapper.EntityCoin
import com.light.cryptocurrency.util.RxScheduler
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.atomic.AtomicBoolean


class RatesViewModel @Inject constructor(
    private val coinsRepo: CoinsRepo,
    private val currencyRepo: CurrencyRepo,
    private val rxScheduler: RxScheduler
) : ViewModel() {

    private val isRefreshing: Subject<Boolean> = BehaviorSubject.create()

    private val pullToRefresh: Subject<Class<*>> = BehaviorSubject.createDefault(Void.TYPE)

    private val sortBy: Subject<SortBy> = BehaviorSubject.createDefault(SortBy.RANK)

    private val forceUpdate = AtomicBoolean()

    private val coins: Observable<List<EntityCoin>>

    private var sortingIndex = 2

    // AppComponent(BaseComponent) -> MainComponent -> Fragment(BaseComponent) -> RatesComponent -> RatesViewModel()

    init {
        coins = pullToRefresh
            .map { CoinsRepo.Query.builder() }
            .switchMap { qb -> currencyRepo.currency()
                    .map { c -> qb.currency(c.code) } }
            .doOnNext{forceUpdate.set(true)}
            .doOnNext{isRefreshing.onNext(true)}
            .switchMap { qb -> sortBy.map(qb::sortBy) }
            .map { qb -> qb.forceUpdate(forceUpdate.getAndSet(false)) }
            .map(CoinsRepo.Query.Builder::build)
            .switchMap(coinsRepo::listings)
            .doOnEach{ isRefreshing.onNext(false) }
    }

    fun coins(): Observable<List<EntityCoin>> = coins
        .observeOn(rxScheduler.main())

    fun isRefreshing(): Observable<Boolean> = isRefreshing
        .observeOn(rxScheduler.main())

    fun refresh() = pullToRefresh.onNext(Void.TYPE)

    fun switchOrderSorter() = sortBy.onNext(SortBy.values()[sortingIndex++ % SortBy.values().size])
}

