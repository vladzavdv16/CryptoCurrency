package com.light.cryptocurrency.data.api

import com.light.cryptocurrency.data.model.Listings
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CmcApi {

    companion object {
        val API_KEY = "X-CMC_PRO_API_KEY"
    }

    @GET("cryptocurrency/listings/latest")
    fun listings(@Query("convert") convert: String?): Observable<Listings?>?
}