package com.light.cryptocurrency

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
abstract class AppComponent {

    abstract fun context(): Context

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun application(app: Application): Builder?
        abstract fun build(): AppComponent
    }
}