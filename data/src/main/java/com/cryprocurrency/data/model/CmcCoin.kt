package com.cryprocurrency.data.model

import com.squareup.moshi.Json

data class CmcCoin(
    override val id: Int,
    override val name: String,
    override val symbol: String,
    @Json(name = "cmc_rank")
    override val rank: Int,
    val quote: Map<String, Quote?>?,
    override val currencyCode: String? = null
): Coin {

    override val price: Double = price()

    override val change24h: Double = change24h()

    private fun price(): Double {
        val iterator: Iterator<Quote?> = quote?.values!!.iterator()
        return if (iterator.hasNext()) iterator.next()!!.price else 0.0
    }

    private fun change24h(): Double {
        val iterator: Iterator<Quote?> = quote?.values!!.iterator()
        return if (iterator.hasNext()) iterator.next()!!.change24h else 0.0
    }

    data class Quote(
        val price: Double,
        @Json(name = "percent_change_24h")
        val change24h: Double,
    )
}