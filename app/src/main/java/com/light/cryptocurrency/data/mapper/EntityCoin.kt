package com.light.cryptocurrency.data.mapper

import com.light.cryptocurrency.data.database.RoomCoin
import com.light.cryptocurrency.data.model.Coin

data class EntityCoin(
    val id: Int,
    val name: String,
    val symbol: String,
    val rank: Int,
    val price: Double,
    val change24h: Double,
    val currencyCode: String,
)

fun RoomCoin.toEntityCoin(): EntityCoin =
    EntityCoin(
        this.id,
        this.name,
        this.symbol,
        this.rank,
        this.change24h,
        this.price,
        this.currencyCode
    )

fun Coin.toEntityCoin(): EntityCoin =
    EntityCoin(
        this.id,
        this.name,
        this.symbol,
        this.rank,
        this.change24h,
        this.price,
        this.currencyCode!!
    )
