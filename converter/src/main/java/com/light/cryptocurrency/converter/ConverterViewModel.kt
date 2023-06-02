package com.light.cryptocurrency.converter

import androidx.lifecycle.ViewModel
import com.cryptocurrency.core.data.mapper.EntityCoin
import com.cryptocurrency.core.data.repository.CoinsRepo
import com.cryptocurrency.core.data.repository.CurrencyRepo
import com.cryptocurrency.core.util.ImageLoader
import com.cryptocurrency.core.util.RxScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import java.util.*
import java.util.concurrent.TimeUnit


class ConverterViewModel @Inject constructor(
    private val coinsRepo: CoinsRepo,
    private val currencyRepo: CurrencyRepo,
    private val rxScheduler: RxScheduler,
    val imageLoader: ImageLoader
) : ViewModel() {

    private var topCoins: Observable<List<EntityCoin>>? = null

    private val fromCoin = BehaviorSubject.create<EntityCoin>()

    private val toCoin = BehaviorSubject.create<EntityCoin>()

    private val fromValue = BehaviorSubject.create<String>()

    private val toValue = BehaviorSubject.create<String>()

    private var factor: Observable<Double>? = null

    init {
        topCoins = currencyRepo.currency().switchMap { coinsRepo.topCoins(it) }
            .doOnNext {
                fromCoin.onNext(it[0])
            }
            .doOnNext {
                toCoin.onNext(it[1])
            }
            .replay(1)
            .autoConnect()


        factor = fromCoin
            .flatMap { fc ->
                toCoin.map { tc ->
                    fc.price / tc.price
                }
            }
            .replay(1)
            .autoConnect()

    }

    fun topCoins(): Observable<List<EntityCoin>> = topCoins?.observeOn(rxScheduler.main()) ?: Observable.empty()


    fun fromCoin(): Observable<EntityCoin>? {
        return fromCoin.observeOn(rxScheduler.main())
    }

    fun toCoin(): Observable<EntityCoin>? {
        return toCoin.observeOn(rxScheduler.main())
    }


    fun fromValue(): Observable<String?>? {
        return fromValue.observeOn(rxScheduler.main())
    }


    fun toValue(): Observable<String>? {
        return fromValue
            .observeOn(rxScheduler.computation())
            .map { s: String -> if (s.isEmpty()) "0.0" else s }
            .map { s: String -> s.toDouble() }
            .flatMap { value: Double ->
                factor?.map { f: Double -> value * f }
            }
            .map { v ->
                String.format(Locale.US, "%.2f", v)
            }
            .map { v -> if ("0.0" == v) "" else v }
            .observeOn(rxScheduler.main())
    }
    fun fromCoin(coin: EntityCoin) {
        fromCoin.onNext(coin)
    }

    fun toCoin(coin: EntityCoin) {
        toCoin.onNext(coin)
    }

    fun fromValue(text: CharSequence) {
        fromValue.onNext(text.toString())
    }

    fun toValue(text: CharSequence) {
        toValue.onNext(text.toString())
    }

}