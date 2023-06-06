package com.cryprocurrency.data.model

import com.cryprocurrency.data.mapper.EntityCoin

data class Wallet(
    val id: String,
    val coin: EntityCoin,
    val balance: Double?
)
