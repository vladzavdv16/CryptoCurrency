package com.light.cryptocurrency.converter

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface ConverterModule {

    @Binds
    @IntoMap
    @ClassKey(ConverterViewModel::class)
    fun converterViewModel(impl: ConverterViewModel): ViewModel
}