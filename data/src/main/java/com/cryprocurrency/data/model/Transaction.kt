package com.cryprocurrency.data.model

import com.cryprocurrency.data.mapper.EntityCoin
import java.util.*

data class Transaction(
    val id: String,
    val coin: EntityCoin,
    val amount: Double?,
    val date: Date?
)
