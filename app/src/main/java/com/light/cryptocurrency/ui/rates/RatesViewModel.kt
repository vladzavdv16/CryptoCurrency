package com.light.cryptocurrency.ui.rates

import com.cryptocurrency.core.BaseViewModel
import javax.inject.Inject
import com.cryptocurrency.core.data.repository.CoinsRepo
import com.cryptocurrency.core.data.repository.CurrencyRepo
import com.cryptocurrency.core.data.SortBy
import com.cryptocurrency.core.data.mapper.EntityCoin
import com.cryptocurrency.core.util.RxScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.Flow
import java.util.concurrent.atomic.AtomicBoolean

class RatesViewModel @Inject constructor(
    private val coinsRepo: CoinsRepo,
    private val currencyRepo: CurrencyRepo,
    private val rxScheduler: RxScheduler
): BaseViewModel() {

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
            .switchMap { coinsRepo.listings(it) }
            .doOnEach{ isRefreshing.onNext(false) }
            .replay(1)
            .autoConnect()
    }

    fun coins(): Observable<List<EntityCoin>> = coins
        .observeOn(rxScheduler.main())

    fun isRefreshing(): Observable<Boolean> = isRefreshing
        .observeOn(rxScheduler.main())

    fun refresh() = pullToRefresh.onNext(Void.TYPE)

    fun switchOrderSorter() = sortBy.onNext(SortBy.values()[sortingIndex++ % SortBy.values().size])
}

