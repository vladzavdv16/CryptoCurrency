package com.light.cryptocurrency.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomCoin(
    @PrimaryKey
    override var id: Int,
    override var name: String,
    override var symbol: String,
    override var rank: Int,
) : Coin {

    override fun price(): Double {
        TODO("Not yet implemented")
    }

    override fun change24h(): Double {
        TODO("Not yet implemented")
    }


}