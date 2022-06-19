package com.light.cryptocurrency.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomCoin::class], version = 1, exportSchema = false)
abstract class CoinsDatabase : RoomDatabase() {

    abstract fun coins() : CoinsDao

}