package com.light.cryptocurrency.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomCoin::class], version = 1, exportSchema = false)
abstract class LoftDatabase : RoomDatabase() {

    abstract fun coins() : CoinsDao

}