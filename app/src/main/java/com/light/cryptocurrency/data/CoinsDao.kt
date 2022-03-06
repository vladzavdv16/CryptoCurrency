package com.light.cryptocurrency.data

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

    @WorkerThread
    @Query("SELECT COUNT(id) FROM RoomCoin")
    fun coinsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coins: List<RoomCoin>)
}