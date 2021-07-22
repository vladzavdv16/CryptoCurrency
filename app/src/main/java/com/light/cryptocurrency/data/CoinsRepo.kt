package com.light.cryptocurrency.data


interface CoinsRepo {

    fun listings(currency: String?): List<Coin?>?
}
