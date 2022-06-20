package com.light.cryptocurrency.data.repositories


import com.light.cryptocurrency.data.api.CmcApi
import com.light.cryptocurrency.data.SortBy
import com.light.cryptocurrency.data.database.CoinsDatabase
import com.light.cryptocurrency.data.database.RoomCoin
import com.light.cryptocurrency.data.mapper.EntityCoin
import com.light.cryptocurrency.data.model.Coin
import com.light.cryptocurrency.util.RxScheduler
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton
import javax.inject.Inject
import kotlin.collections.ArrayList as ArrayList

@Singleton
class CoinsRepoImpl @Inject constructor(
    private val api: CmcApi?,
    private val db: CoinsDatabase,
    private val rxScheduler: RxScheduler
) : CoinsRepo {


    override fun listings(query: CoinsRepo.Query): Observable<List<EntityCoin>> {
        return Observable
            .fromCallable { query.forceUpdate() || db.coins().coinsCount() == 0 }
            .switchMap { api?.listings(query.currency()) ?: Observable.empty() }
            .map { listings -> mapToRoomCoins(query, listings.data) }
            .doOnNext { coins -> db.coins().insert(coins) }
            .switchMap { fetchFromDb(query) }
            .switchIfEmpty { fetchFromDb(query) }
            .map { coins -> mapToEntityCoins(query, coins)}
            .subscribeOn(rxScheduler.io())
    }

    private fun fetchFromDb(query: CoinsRepo.Query?): Observable<List<RoomCoin>> =
        if (query!!.sortBy() == SortBy.PRICE) {
            db.coins().fetchAllSortByPrice()
        } else {
            db.coins().fetchAllSortByRank()
        }

    private fun mapToEntityCoins(query: CoinsRepo.Query, coins: List<RoomCoin?>): List<EntityCoin> {
        val entityCoins: MutableList<EntityCoin> = ArrayList(coins.size)
        for (coin in coins) {
            entityCoins.add(
                EntityCoin(
                    coin?.id!!,
                    coin.name,
                    coin.symbol,
                    coin.rank,
                    coin.price,
                    coin.change24h,
                    query.currency()
                )
            )
        }
        return entityCoins
    }

    private fun mapToRoomCoins(query: CoinsRepo.Query, coins: List<Coin?>): List<RoomCoin> {
        val roomCoins: MutableList<RoomCoin> = ArrayList(coins.size)
        for (coin in coins) {
            roomCoins.add(
                RoomCoin(
                    coin?.id!!,
                    coin.name,
                    coin.symbol,
                    coin.rank,
                    coin.price,
                    coin.change24h,
                    query.currency()
                )
            )
        }
        return roomCoins
    }
}