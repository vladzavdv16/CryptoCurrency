package com.light.cryptocurrency.data.model

import java.util.*

data class Transaction(
    val id: String,
    val coin: Coin,
    val amount: Double,
    val date: Date
)
