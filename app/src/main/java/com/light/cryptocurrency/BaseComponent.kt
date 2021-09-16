package com.light.cryptocurrency

import android.content.Context
import com.light.cryptocurrency.data.CoinsRepo
import com.light.cryptocurrency.data.CurrencyRepo

interface BaseComponent {

    fun context(): Context
    fun coinsRepo(): CoinsRepo
    fun currencyRepo(): CurrencyRepo
}