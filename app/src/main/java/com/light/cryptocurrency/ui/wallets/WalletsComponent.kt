package com.light.cryptocurrency.ui.wallets

import androidx.lifecycle.ViewModelProvider
import com.light.cryptocurrency.di.BaseComponent
import com.light.cryptocurrency.ui.rates.RatesAdapter
import com.light.cryptocurrency.util.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WalletsModule::class, ViewModelModule::class],
 dependencies = [BaseComponent::class])
interface WalletsComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    fun walletsAdapter(): WalletsAdapter
}