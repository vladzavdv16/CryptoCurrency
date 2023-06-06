package com.light.cryptocurrency.ui.wallets

import androidx.lifecycle.ViewModelProvider
import com.cryprocurrency.data.di.BaseComponent
import com.cryptocurrency.core.util.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WalletsModule::class, ViewModelModule::class],
 dependencies = [BaseComponent::class])
interface WalletsComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    fun walletsAdapter(): WalletsAdapter

    fun transactionsAdapter(): TransactionAdapter
}