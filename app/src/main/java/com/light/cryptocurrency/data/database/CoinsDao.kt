package com.light.cryptocurrency.data.database

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cryptocurrency.core.data.model.RoomCoin
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CoinsDao {

    @Query("SELECT * FROM RoomCoin")
    fun fetchAll(): Observable<List<RoomCoin>>

    @Query("SELECT * FROM RoomCoin ORDER BY rank ASC")
    fun fetchAllSortByRank(): Observable<List<RoomCoin>>

    @Query("SELECT * FROM RoomCoin ORDER BY price DESC")
    fun fetchAllSortByPrice(): Observable<List<RoomCoin>>

    @WorkerThread
    @Query("SELECT COUNT(id) FROM RoomCoin")
    fun coinsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coins: List<RoomCoin>)

    @Query("SELECT * FROM RoomCoin WHERE id=:id")
    fun fetchOne(id: Long): Single<RoomCoin>

    @Query("SELECT * FROM RoomCoin ORDER BY rank ASC LIMIT :limit")
    fun fetchTop(limit: Int): Observable<List<RoomCoin>>
}