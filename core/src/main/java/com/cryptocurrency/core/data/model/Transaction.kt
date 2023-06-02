package com.cryptocurrency.core.data.model

import com.cryptocurrency.core.data.mapper.EntityCoin
import java.util.*

data class Transaction(
    val id: String,
    val coin: EntityCoin,
    val amount: Double?,
    val date: Date?
)
