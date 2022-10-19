package com.light.cryptocurrency.converter

import androidx.lifecycle.ViewModelProvider
import com.cryptocurrency.core.di.BaseComponent
import com.cryptocurrency.core.util.ViewModelModule
import dagger.Component

@Component(
    modules = [ConverterModule::class, ViewModelModule::class],
    dependencies = [BaseComponent::class]
)
interface ConverterComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    fun coinSheetAdapter(): CoinsSheetAdapter
}