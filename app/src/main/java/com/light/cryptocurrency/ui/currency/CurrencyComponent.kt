package com.light.cryptocurrency.ui.currency

import androidx.lifecycle.ViewModelProvider
import com.cryptocurrency.core.di.BaseComponent
import com.cryptocurrency.core.util.ViewModelModule
import dagger.Component

@Component(modules = [CurrencyModule::class, ViewModelModule::class],
            dependencies = [BaseComponent::class])
abstract class CurrencyComponent {

    abstract fun viewModelFactory(): ViewModelProvider.Factory
}