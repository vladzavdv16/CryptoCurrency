package com.light.cryptocurrency.di

import android.app.Application
import android.content.Context
import com.light.cryptocurrency.BuildConfig
import com.cryprocurrency.data.api.CmcApi
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
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

    @JvmStatic
    @Singleton
    @Provides
    fun httpClient(executor: ExecutorService): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.dispatcher(Dispatcher(executor))
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            interceptor.redactHeader(CmcApi.API_KEY)
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun picasso(context: Context, httpClient: OkHttpClient, executor: ExecutorService): Picasso =
        Picasso.Builder(context).downloader(OkHttp3Downloader(httpClient))
            .executor(executor)
            .build()
}