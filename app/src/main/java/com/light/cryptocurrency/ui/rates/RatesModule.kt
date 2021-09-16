package com.light.cryptocurrency.ui.rates

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class RatesModule {

    @Binds
    @IntoMap
    @ClassKey(RatesViewModel::class)
    abstract fun ratesViewModel(impl: RatesViewModel): ViewModel
}