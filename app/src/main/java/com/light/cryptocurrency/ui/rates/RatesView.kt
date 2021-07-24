package com.light.cryptocurrency.ui.rates

import androidx.annotation.NonNull
import com.light.cryptocurrency.data.Coin


interface RatesView {

    fun showCoins(@NonNull coins: List<Coin?>?)

    fun showError(@NonNull error: String?)
}