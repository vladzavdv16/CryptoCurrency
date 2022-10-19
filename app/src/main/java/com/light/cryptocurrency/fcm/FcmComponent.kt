package com.light.cryptocurrency.fcm

import com.cryptocurrency.core.di.BaseComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [FcmModule::class],
    dependencies = [BaseComponent::class]
)
interface FcmComponent {

    fun inject(service: FcmService)
}