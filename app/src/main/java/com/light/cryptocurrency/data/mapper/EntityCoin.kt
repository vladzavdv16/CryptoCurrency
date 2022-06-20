package com.light.cryptocurrency.data.mapper

data class EntityCoin(
    val id: Int,
    val name: String,
    val symbol: String,
    val rank: Int,
    val price: Double,
    val change24h: Double,
    val currencyCode: String,
)
