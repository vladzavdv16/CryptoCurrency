package com.cryprocurrency.data.mapper

import com.cryprocurrency.data.model.RoomCoin
import com.cryprocurrency.data.model.Coin

data class EntityCoin(
    val id: Int,
    val name: String,
    var symbol: String,
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
