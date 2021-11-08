package com.light.cryptocurrency.data

import AutoValue_CoinsRepo_Query
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
                return AutoValue_CoinsRepo_Query.Companion.Builder().forceUpdate(true)
            }
        }

        abstract fun currency(): String
        abstract fun forceUpdate(): Boolean


        abstract class Builder {
            abstract fun currency(currency: String): Builder
            abstract fun forceUpdate(forceUpdate: Boolean): Builder
            abstract fun build(): Query?
        }
    }


}
