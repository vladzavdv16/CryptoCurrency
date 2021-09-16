package com.light.cryptocurrency.ui.main

import com.light.cryptocurrency.BaseComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [MainModule::class],
    dependencies = [BaseComponent::class]
)
abstract class MainComponent {

    abstract fun inject(activity: MainActivity)
}