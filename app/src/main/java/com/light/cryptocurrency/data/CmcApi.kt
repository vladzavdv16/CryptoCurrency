package com.light.cryptocurrency.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CmcApi {

    companion object{val API_KEY = "X-CMC_PRO_API_KEY"}

    @GET("cryptocurrency/listings/latest")
    fun listings(@Query("convert") convert: String?): Call<Listings?>?
}