package com.light.cryptocurrency.ui.currency

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class CurrencyModule{

    @Binds
    @IntoMap
    @ClassKey(CurrencyViewModel::class)
    abstract fun ratesViewModel(impl: CurrencyViewModel): ViewModel
}