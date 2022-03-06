package com.light.cryptocurrency.data

interface Coin {

    val id: Int

    val name: String

    val symbol: String

    val rank: Int

    fun price(): Double

    fun change24h(): Double
}