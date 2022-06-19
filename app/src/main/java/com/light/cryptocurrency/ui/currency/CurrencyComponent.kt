package com.light.cryptocurrency.ui.currency

import androidx.lifecycle.ViewModelProvider
import com.light.cryptocurrency.di.BaseComponent
import com.light.cryptocurrency.util.ViewModelModule
import dagger.Component

@Component(modules = [CurrencyModule::class, ViewModelModule::class],
            dependencies = [BaseComponent::class])
abstract class CurrencyComponent {

    abstract fun viewModelFactory(): ViewModelProvider.Factory
}