package com.light.cryptocurrency.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.jvm.Throws


interface CoinsRepo {

    @WorkerThread
    fun listings(currency: String?): List<Coin?>


    fun listings(query: Query?): LiveData<List<Coin>>?


    abstract class Query {

        companion object {
            fun builder(): Builder {
                return builder().forceUpdate(true)
            }
        }

        abstract fun currency(): String
        abstract fun forceUpdate(): Boolean


        interface Builder {
            fun currency(currency: String): Builder
            fun forceUpdate(forceUpdate: Boolean): Builder
            fun build(): Query?
        }
    }


}
