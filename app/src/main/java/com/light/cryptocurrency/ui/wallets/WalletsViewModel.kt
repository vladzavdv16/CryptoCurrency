package com.light.cryptocurrency.ui.wallets

import androidx.lifecycle.ViewModel
import com.light.cryptocurrency.data.model.Transaction
import com.light.cryptocurrency.data.model.Wallet
import com.light.cryptocurrency.data.repositories.CurrencyRepo
import com.light.cryptocurrency.data.repositories.WalletsRepo
import com.light.cryptocurrency.util.RxScheduler
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject
import javax.security.auth.Subject

class WalletsViewModel @Inject constructor(
    private val walletsRepo: WalletsRepo,
    private val currencyRepo: CurrencyRepo,
    private val rxScheduler: RxScheduler
) : ViewModel() {

    private val walletsPosition = BehaviorSubject.createDefault(0)

    private val transaction = wallet()
        .switchMap { wallets ->
            walletsPosition.map(wallets::get)
        }.switchMap { walletsRepo.transaction(it) }
        .doOnNext { t ->
            Timber.d("%s", t)
        }
        .replay(1)
        .autoConnect()


    private fun wallet(): Observable<List<Wallet>> = currencyRepo.currency()
        .switchMap(walletsRepo::wallet)
        .doOnNext { wal -> Timber.d("%s", wal) }
        .replay(1)
        .autoConnect()

    fun wallets(): Observable<List<Wallet>> = wallet().observeOn(rxScheduler.main())

    fun transactions(): Observable<List<Transaction>> =
        transaction.observeOn(rxScheduler.main())

    fun addWallet(): Completable {
        return Completable.complete()
    }

    fun changeWallet(position: Int) {
        walletsPosition.onNext(position)
    }
}