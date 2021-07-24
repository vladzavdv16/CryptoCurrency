package com.light.cryptocurrency.ui.rates

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import com.light.cryptocurrency.data.CmcCoinsRepo
import com.light.cryptocurrency.data.Coin
import com.light.cryptocurrency.data.CoinsRepo
import java.io.IOException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class RatesPresenter {

    private lateinit var repo: CoinsRepo

    private var coins: List<Coin?>? = Collections.emptyList()

    private lateinit var executor: ExecutorService

    private val handler = Handler(Looper.getMainLooper())

    private var view: RatesView? = null

    init {
        executor = Executors.newSingleThreadExecutor()
        repo = CmcCoinsRepo()
        refresh()
    }

    fun attach(@NonNull view: RatesView) {
        this.view = view
        if (!coins?.isEmpty()!! == true) {
            view.showCoins(coins)
        }
    }

    fun detach(@NonNull view: RatesView) {
        this.view = null
    }

    private fun onSuccess(coins: List<Coin?>?) {
        this.coins = coins
        if (view != null) {
            view!!.showCoins(coins)
        }
    }

    private fun onError(e: IOException) {}

    fun refresh() {
        executor.submit {
            try {
                val coins = repo.listings("USD")
                handler.post { onSuccess(coins) }
            } catch (e: IOException) {
                handler.post { onError(e) }
            }
        }
    }


}