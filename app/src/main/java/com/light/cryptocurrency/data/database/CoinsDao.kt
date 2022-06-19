package com.light.cryptocurrency.data.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinsDao {

    @Query("SELECT * FROM RoomCoin")
    fun fetchAll(): LiveData<List<RoomCoin>>

    @Query("SELECT * FROM RoomCoin ORDER BY rank ASC")
    fun fetchAllSortByRank(): LiveData<List<RoomCoin>>

    @Query("SELECT * FROM RoomCoin ORDER BY price DESC")
    fun fetchAllSortByPrice(): LiveData<List<RoomCoin>>

    @WorkerThread
    @Query("SELECT COUNT(id) FROM RoomCoin")
    fun coinsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coins: List<RoomCoin>)
}