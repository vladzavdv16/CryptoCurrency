package com.light.cryptocurrency.data

import androidx.annotation.NonNull
import com.light.cryptocurrency.BuildConfig
import com.squareup.moshi.Moshi
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


class CmcCoinsRepo : CoinsRepo {

    val API_KEY = "X-CMC_PRO_API_KEY"
    private lateinit var api : CmcApi

    fun CmcCoinsRepo() {
        api = createRetrofit(createHttpClient()).create(CmcApi::class.java)
    }

    @NonNull
    @Throws(IOException::class)
    override fun listings(@NonNull currency: String?): List<Coin?>? {
        val response: Response<Listings?> = api.listings(currency)!!.execute()
        if (response.isSuccessful()) {
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

    private fun createHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val request: Request = chain.request()
            chain.proceed(request.newBuilder()
                .addHeader(API_KEY, BuildConfig.API_KEY)
                .build())
        })
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            interceptor.redactHeader(API_KEY)
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    private fun createRetrofit(httpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.client(httpClient)
        builder.baseUrl(BuildConfig.API_ENDPOINT)
        val moshi = Moshi.Builder().build()
        builder.addConverterFactory(MoshiConverterFactory.create(
            moshi.newBuilder()
                .add(Coin::class.java, moshi.adapter<Any>(Coin::class.java))
                .add(Listings::class.java, moshi.adapter<Any>(Listings::class.java))
                .build()
        ))
        return builder.build()
    }
}