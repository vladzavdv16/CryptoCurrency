package com.light.cryptocurrency.data.repositories

import com.light.cryptocurrency.data.BuilderCoinsRepoQuery
import com.light.cryptocurrency.data.SortBy
import com.light.cryptocurrency.data.mapper.EntityCoin
import com.light.cryptocurrency.data.model.Coin
import io.reactivex.Observable


interface CoinsRepo {

    fun listings(query: Query): Observable<List<EntityCoin>>

    abstract class Query {

        companion object {
            fun builder(): Builder {
                return BuilderCoinsRepoQuery.Companion.Builder().forceUpdate(true)
            }
        }

        abstract fun currency(): String
        abstract fun forceUpdate(): Boolean
        abstract fun sortBy(): SortBy


        abstract class Builder {
            abstract fun currency(currency: String?): Builder
            abstract fun forceUpdate(forceUpdate: Boolean): Builder
            abstract fun sortBy(sortBy: SortBy): Builder
            abstract fun build(): Query?
        }
    }


}
