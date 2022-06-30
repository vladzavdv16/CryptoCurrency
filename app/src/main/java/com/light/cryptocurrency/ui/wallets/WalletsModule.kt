package com.light.cryptocurrency.ui.wallets

import androidx.lifecycle.ViewModel
import com.light.cryptocurrency.ui.rates.RatesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface WalletsModule {

    @Binds
    @IntoMap
    @ClassKey(WalletsViewModel::class)
    fun walletsViewModel(impl: WalletsViewModel): ViewModel
}