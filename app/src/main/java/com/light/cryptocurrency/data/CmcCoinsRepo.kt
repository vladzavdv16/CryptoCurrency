package com.light.cryptocurrency.data

import androidx.annotation.NonNull
import com.light.cryptocurrency.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Component
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.*
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class CmcCoinsRepo @Inject constructor(api: CmcApi?) : CoinsRepo {

    private var api: CmcApi


    init {
        this.api = api!!
    }

    @NonNull
    @Throws(IOException::class)
    override fun listings(@NonNull currency: String?): List<Coin?>? {
        val response: Response<Listings?> = api.listings(currency)!!.execute()
        if (response.isSuccessful) {
            val listings: Listings? = response.body()
            if (listings != null) {
                return listings.data
            }
        } else {
            val responseBody: ResponseBody? = response.errorBody()
            if (responseBody != null) {
                throw IOException(responseBody.string())
            }
        }
        return Collections.emptyList()
    }
}