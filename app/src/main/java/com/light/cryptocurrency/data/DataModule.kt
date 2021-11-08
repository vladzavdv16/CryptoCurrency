package com.light.cryptocurrency.data

import android.content.Context
import androidx.room.Room
import com.light.cryptocurrency.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.ExecutorService
import javax.inject.Singleton


@Module
abstract class DataModule {

    @Module
    companion object {

        @JvmStatic
        @Singleton
        @Provides
        fun httpClient(executor: ExecutorService): OkHttpClient {
            val builder = OkHttpClient.Builder()
            builder.dispatcher(Dispatcher(executor))
            builder.addInterceptor(Interceptor { chain ->
                val request: Request = chain.request()
                chain.proceed(request.newBuilder()
                    .addHeader(CmcApi.API_KEY, BuildConfig.API_KEY)
                    .build())
            })
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
                interceptor.redactHeader(CmcApi.API_KEY)
                builder.addInterceptor(interceptor)
            }
            return builder.build()
        }


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
            builder.client(httpClient)
            builder.baseUrl(BuildConfig.API_ENDPOINT)
            builder.addConverterFactory(MoshiConverterFactory.create(moshi))
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
        fun loftDatabase(context: Context): LoftDatabase {
            return if (BuildConfig.DEBUG) {
                Room.inMemoryDatabaseBuilder(context, LoftDatabase::class.java).build()
            } else {
                return Room.databaseBuilder(context, LoftDatabase::class.java, "loft.db").build()
            }

        }
    }

    @Binds
    abstract fun coinsRepo(impl: CmcCoinsRepo?): CoinsRepo?


    @Binds
    abstract fun currencyRepo(impl: CurrencyRepoImpl): CurrencyRepo
}