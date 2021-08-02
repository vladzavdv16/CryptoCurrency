package com.light.cryptocurrency

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides


import javax.inject.Singleton


@Module
object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    fun context(app: Application): Context {
        return app.applicationContext
    }
}