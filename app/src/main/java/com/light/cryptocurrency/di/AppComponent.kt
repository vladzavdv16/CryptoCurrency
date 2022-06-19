package com.light.cryptocurrency.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        DataModule::class,
        UtilModule::class]
)
abstract class AppComponent : BaseComponent {

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun application(app: Application): Builder

        abstract fun build(): AppComponent
    }
}