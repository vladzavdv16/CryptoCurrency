package com.cryprocurrency.data.repository

import com.cryprocurrency.data.BuilderCoinsRepoQuery
import com.cryprocurrency.data.SortBy
import com.cryprocurrency.data.mapper.EntityCoin
import com.cryprocurrency.data.model.Currency
import io.reactivex.Observable
import io.reactivex.Single

interface CoinsRepo {

    fun listings(query: Query): Observable<List<EntityCoin>>

    fun coin(currency: Currency, id: Long?): Single<EntityCoin>

    fun topCoins(c: Currency): Observable<List<EntityCoin>>

    abstract class Query {

        companion object {
            fun builder(): Builder {
                return BuilderCoinsRepoQuery.Companion.Builder()
                    .forceUpdate(true)
                    .sortBy(SortBy.RANK)
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
