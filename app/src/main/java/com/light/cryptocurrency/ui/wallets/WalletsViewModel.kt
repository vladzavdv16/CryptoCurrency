package com.light.cryptocurrency.ui.wallets

import androidx.lifecycle.ViewModel
import com.light.cryptocurrency.data.model.Wallet
import com.light.cryptocurrency.data.repositories.CurrencyRepo
import com.light.cryptocurrency.data.repositories.WalletsRepo
import com.light.cryptocurrency.util.RxScheduler
import io.reactivex.Observable
import javax.inject.Inject

class WalletsViewModel @Inject constructor(
    private val walletsRepo: WalletsRepo,
    private val currencyRepo: CurrencyRepo,
    private val rxScheduler: RxScheduler
): ViewModel() {

    private val wallets: Observable<List<Wallet>>

    init {
        wallets = currencyRepo.currency().switchMap(walletsRepo::wallet)
    }

    fun wallets(): Observable<List<Wallet>> = wallets.observeOn(rxScheduler.main())
}