package com.light.cryptocurrency.converter.di

import androidx.lifecycle.ViewModelProvider
import com.cryprocurrency.data.di.BaseComponent
import com.cryptocurrency.core.util.ViewModelModule
import com.light.cryptocurrency.converter.CoinsSheetAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ConverterModule::class, ViewModelModule::class],
    dependencies = [BaseComponent::class]
)
interface ConverterComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    fun coinSheetAdapter(): CoinsSheetAdapter
}