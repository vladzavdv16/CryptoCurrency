package com.light.cryptocurrency.util

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun viewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory
}