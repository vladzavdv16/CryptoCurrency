package com.light.cryptocurrency.ui.profile

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

/**
 * Created by Zavodov on 03.01.2023
 */
@Module
interface ProfileModule {

    @Binds
    @IntoMap
    @ClassKey(ProfileViewModel::class)
    fun profileViewModel(impl: ProfileViewModel): ViewModel
}