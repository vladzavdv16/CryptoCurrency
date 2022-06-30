package com.light.cryptocurrency.data.model

import com.light.cryptocurrency.data.mapper.EntityCoin

data class Wallet(
    val id: String,
    val coin: EntityCoin,
    val balance: Double?
)
