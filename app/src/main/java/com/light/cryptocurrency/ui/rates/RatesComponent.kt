package com.light.cryptocurrency.ui.rates

import androidx.lifecycle.ViewModelProvider
import com.cryprocurrency.data.di.BaseComponent
import com.cryptocurrency.core.util.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RatesModule::class, ViewModelModule::class],
    dependencies = [BaseComponent::class]
)
abstract class RatesComponent {

    abstract fun viewModelFactory(): ViewModelProvider.Factory

    abstract fun ratesAdapter(): RatesAdapter
}