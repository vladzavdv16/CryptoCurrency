package com.light.cryptocurrency.di

import android.content.Context
import com.light.cryptocurrency.data.repositories.CoinsRepo
import com.light.cryptocurrency.data.repositories.CurrencyRepo
import com.light.cryptocurrency.util.RxScheduler
import com.light.cryptocurrency.util.loader.ImageLoader

interface BaseComponent {

    fun context(): Context
    fun coinsRepo(): CoinsRepo
    fun currencyRepo(): CurrencyRepo
    fun imageLoader(): ImageLoader
    fun rxScheduler(): RxScheduler
}