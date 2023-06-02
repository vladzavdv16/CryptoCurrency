package com.cryptocurrency.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cryptocurrency.core.data.model.Coin

@Entity
data class RoomCoin(
    @PrimaryKey
    override var id: Int,
    override var name: String,
    override var symbol: String,
    override var rank: Int,
    override val price: Double,
    override val change24h: Double,
    override val currencyCode: String,
): Coin