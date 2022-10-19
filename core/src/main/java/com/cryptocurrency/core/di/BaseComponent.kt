package com.cryptocurrency.core.di

import android.content.Context
import com.cryptocurrency.core.data.repository.CoinsRepo
import com.cryptocurrency.core.data.repository.CurrencyRepo
import com.cryptocurrency.core.data.repository.WalletsRepo
import com.cryptocurrency.core.util.ImageLoader
import com.cryptocurrency.core.util.Notifier
import com.cryptocurrency.core.util.RxScheduler

interface BaseComponent {

    fun context(): Context
    fun coinsRepo(): CoinsRepo
    fun currencyRepo(): CurrencyRepo
    fun walletRepo(): WalletsRepo
    fun imageLoader(): ImageLoader
    fun rxScheduler(): RxScheduler
    fun notifier(): Notifier
}