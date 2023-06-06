package com.light.cryptocurrency.ui.profile

import androidx.lifecycle.ViewModelProvider
import com.cryprocurrency.data.di.BaseComponent
import com.cryptocurrency.core.util.ViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Zavodov on 03.01.2023
 */

@Singleton
@Component(
    modules = [ProfileModule::class, ViewModelModule::class],
    dependencies = [BaseComponent::class]
)
abstract class ProfileComponent {

    abstract fun viewModelFactory(): ViewModelProvider.Factory

}