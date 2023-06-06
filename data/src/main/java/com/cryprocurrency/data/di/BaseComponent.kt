package com.cryprocurrency.data.di

import android.content.Context
import com.cryprocurrency.data.repository.CoinsRepo
import com.cryprocurrency.data.repository.CurrencyRepo
import com.cryprocurrency.data.repository.ProfileRepo
import com.cryprocurrency.data.repository.WalletsRepo
import com.cryptocurrency.core.util.ImageLoader
import com.cryptocurrency.core.util.Notifier
import com.cryptocurrency.core.util.RxScheduler

interface BaseComponent {

    fun context(): Context
    fun coinsRepo(): CoinsRepo
    fun currencyRepo(): CurrencyRepo
    fun profileRepo(): ProfileRepo
    fun walletRepo(): WalletsRepo
    fun imageLoader(): ImageLoader
    fun rxScheduler(): RxScheduler
    fun notifier(): Notifier
}