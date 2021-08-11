package com.light.cryptocurrency.ui.rates

import androidx.lifecycle.ViewModelProvider
import com.light.cryptocurrency.BaseComponent
import com.light.cryptocurrency.util.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RatesModule::class, ViewModelModule::class],
    dependencies = [BaseComponent::class]
)
abstract class RatesComponent {

    abstract fun viewModelFactory(): ViewModelProvider.Factory
}