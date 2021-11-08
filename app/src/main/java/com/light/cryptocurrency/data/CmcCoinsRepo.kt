package com.light.cryptocurrency.data

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.util.*
import java.util.concurrent.ExecutorService
import javax.inject.Singleton
import javax.inject.Inject
import kotlin.collections.ArrayList as ArrayList

@Singleton
class CmcCoinsRepo @Inject constructor(
    private val api: CmcApi?,
    private val db: LoftDatabase,
    private val executor: ExecutorService,
) :
    CoinsRepo {


    @Nullable
    @Throws(IOException::class)
    override fun listings(currency: String?): List<Coin?> {
        val response: Response<Listings?> = api?.listings(currency)!!.execute()
        if (response.isSuccessful) {
            val listings: Listings? = response.body()
            if (listings != null) {
                return listings.data
            }
        } else {
            val responseBody: ResponseBody? = response.errorBody()
            if (responseBody != null) {
                throw IOException(responseBody.string())
            }
        }
        return Collections.emptyList()
    }

    override fun listings(query: CoinsRepo.Query?): LiveData<List<Coin>> {
        val refresh = MutableLiveData<Boolean>()
        this.executor.submit {
            refresh.postValue(query!!.forceUpdate() || db.coins().coinsCount() == 0)
        }
        //t/f ->
        return switchMap(refresh) { r ->
            ((if (r) return@switchMap fetchFromNetwork(query);
            else return@switchMap fetchFromDb(query!!)))
        }
    }

    private fun fetchFromDb(query: CoinsRepo.Query): LiveData<List<Coin>> {
        return map(db.coins().fetchAll()) { coins -> ArrayList(coins) }
    }

    //
    private fun fetchFromNetwork(query: CoinsRepo.Query?): MutableLiveData<List<Coin>> {
        val liveData = MutableLiveData<List<Coin>>()
        executor.submit {
            try {
                val response: Response<Listings?> = api?.listings(query?.currency())!!.execute()
                if (response.isSuccessful) {
                    val listings: Listings? = response.body()
                    if (listings != null) {
                        val cmcCoins: List<CmcCoin> = listings.data
                        saveCoinsIntoDb(cmcCoins)
                        liveData.postValue(ArrayList(cmcCoins))
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
        return liveData
    }

    private fun saveCoinsIntoDb(coins: List<Coin?>) {
        val roomCoins: MutableList<RoomCoin> = ArrayList(coins.size)
        for (coin in coins) {
            roomCoins.add(
                RoomCoin(
                    coin?.id!!,
                    coin.name,
                    coin.symbol,
                    coin.rank,
//                coin.price(),
//                coin.change24h()
                )
            )
        }
        db.coins().insert(roomCoins)
    }


}