package com.cryptocurrency.core.data.model

import com.cryptocurrency.core.data.mapper.EntityCoin

data class Wallet(
    val id: String,
    val coin: EntityCoin,
    val balance: Double?
)
