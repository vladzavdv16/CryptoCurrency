package com.light.cryptocurrency.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.light.cryptocurrency.data.api.CmcApi
import com.light.cryptocurrency.data.model.Listings
import com.light.cryptocurrency.data.SortBy
import com.light.cryptocurrency.data.database.CoinsDatabase
import com.light.cryptocurrency.data.database.RoomCoin
import com.light.cryptocurrency.data.model.Coin
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.ExecutorService
import javax.inject.Singleton
import javax.inject.Inject
import kotlin.collections.ArrayList as ArrayList

@Singleton
class CoinsRepoImpl @Inject constructor(
    private val api: CmcApi?,
    private val db: CoinsDatabase,
    private val executor: ExecutorService,
): CoinsRepo {


    override fun listings(query: CoinsRepo.Query?): LiveData<List<Coin>> {
        fetchFromNetwork(query)
        return fetchFromDb(query)
    }

    private fun fetchFromDb(query: CoinsRepo.Query?): LiveData<List<Coin>> {
        val coins: LiveData<List<RoomCoin>> =
            if (query!!.sortBy() == SortBy.PRICE) {
                db.coins().fetchAllSortByPrice()
            } else {
                db.coins().fetchAllSortByRank()
            }
        return map(coins, ::ArrayList)
    }


    private fun fetchFromNetwork(query: CoinsRepo.Query?) {
        executor.submit {
            if (query!!.forceUpdate() || db.coins().coinsCount() == 0) {
                try {
                    val response: Response<Listings?> = api?.listings(query.currency())!!.execute()
                    if (response.isSuccessful) {
                        val listings: Listings? = response.body()
                        if (listings != null) {
                            saveCoinsIntoDb(query,listings.data)
                        }
                    } else {
                        val responseBody: ResponseBody? = response.errorBody()
                        if (responseBody != null) {
                            throw IOException(responseBody.string())
                        }
                    }
                } catch (e: IOException) {
                    Timber.e(e)
                }
            }
        }
    }

    private fun saveCoinsIntoDb(query: CoinsRepo.Query, coins: List<Coin?>) {
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
        db.coins().insert(roomCoins)
    }


}