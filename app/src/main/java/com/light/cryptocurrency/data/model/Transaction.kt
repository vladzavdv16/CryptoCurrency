package com.light.cryptocurrency.data.model

import com.light.cryptocurrency.data.mapper.EntityCoin
import java.util.*

data class Transaction(
    val id: String,
    val coin: EntityCoin,
    val amount: Double?,
    val date: Date?
)
