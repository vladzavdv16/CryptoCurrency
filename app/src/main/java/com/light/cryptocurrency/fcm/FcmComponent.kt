package com.light.cryptocurrency.fcm

import com.light.cryptocurrency.di.BaseComponent
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