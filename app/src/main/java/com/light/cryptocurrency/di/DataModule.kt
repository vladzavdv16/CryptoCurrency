package com.light.cryptocurrency.di

import android.content.Context
import androidx.room.Room
import com.light.cryptocurrency.BuildConfig
import com.light.cryptocurrency.data.api.CmcApi
import com.light.cryptocurrency.data.database.CoinsDatabase
import com.light.cryptocurrency.data.repositories.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
abstract class DataModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun moshi(): Moshi {
            val moshi = Moshi.Builder().build()
            return moshi.newBuilder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        }


        @JvmStatic
        @Provides
        fun retrofit(httpClient: OkHttpClient, moshi: Moshi): Retrofit {
            val builder = Retrofit.Builder()
            builder.client(httpClient.newBuilder()
                .addInterceptor(Interceptor { chain ->
                    val request: Request = chain.request()
                    chain.proceed(
                        request.newBuilder()
                            .addHeader(CmcApi.API_KEY, BuildConfig.API_KEY)
                            .build())
                }).build()
            )
            builder.baseUrl(BuildConfig.API_ENDPOINT)
            builder.addConverterFactory(MoshiConverterFactory.create(moshi))
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            return builder.build()
        }


        @JvmStatic
        @Provides
        fun cmcApi(retrofit: Retrofit): CmcApi {
            return retrofit.create(CmcApi::class.java)
        }

        @JvmStatic
        @Provides
        @Singleton
        fun loftDatabase(context: Context): CoinsDatabase {
            return if (BuildConfig.DEBUG) {
                Room.inMemoryDatabaseBuilder(context, CoinsDatabase::class.java).build()
            } else {
                return Room.databaseBuilder(context, CoinsDatabase::class.java, "loft.db").build()
            }
        }
    }

    @Binds
    abstract fun coinsRepo(impl: CoinsRepoImpl?): CoinsRepo?

    @Binds
    abstract fun currencyRepo(impl: CurrencyRepoImpl): CurrencyRepo

    @Binds
    abstract fun walletsRepo(impl: WalletsRepoImpl): WalletsRepo
}