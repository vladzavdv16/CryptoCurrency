package com.light.cryptocurrency.ui.wallets

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cryptocurrency.core.data.model.Transaction
import com.cryptocurrency.core.data.model.Wallet
import com.cryptocurrency.core.data.repository.CurrencyRepo
import com.cryptocurrency.core.data.repository.WalletsRepo
import com.cryptocurrency.core.util.RxScheduler
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class WalletsViewModel @Inject constructor(
    private val walletsRepo: WalletsRepo,
    private val currencyRepo: CurrencyRepo,
    private val rxScheduler: RxScheduler
): ViewModel() {

    private val walletsPosition = BehaviorSubject.createDefault(0)

    private val wallet = currencyRepo.currency()
        .switchMap(walletsRepo::wallet)
        .doOnNext { Log.d("Zavodov", "WalletsViewModel : ${it.size}") }
        .replay(1)
        .autoConnect()

    private val transaction = wallet
        .switchMap { wallets ->
            walletsPosition.map(wallets::get)
        }.switchMap (walletsRepo::transaction)
        .doOnNext{ wallets ->
            Timber.d("%d", wallets.size) }
        .replay(1)
        .autoConnect()


    fun wallets(): Observable<List<Wallet>> = wallet.observeOn(rxScheduler.main())

    fun transactions(): Observable<List<Transaction>> =
        transaction.observeOn(rxScheduler.main())

    fun addWallet(): Completable {
        return Completable.complete()
    }

    fun changeWallet(position: Int) {
        walletsPosition.onNext(position)
    }
}