package com.light.cryptocurrency

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.Nullable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


import javax.inject.Singleton


@Module
object AppModule {

    @Singleton
    @Provides
    @JvmStatic
    fun context(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    @JvmStatic
    fun ioExecutor(): ExecutorService {
        val poolSize = Runtime.getRuntime().availableProcessors() * 2 + 1
        return Executors.newFixedThreadPool(poolSize)
    }
}