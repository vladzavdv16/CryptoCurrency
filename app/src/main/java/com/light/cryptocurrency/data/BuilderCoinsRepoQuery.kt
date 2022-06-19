package com.light.cryptocurrency.data

import com.light.cryptocurrency.data.repositories.CoinsRepo

class BuilderCoinsRepoQuery constructor(
    private val currency: String?,
    private val forceUpdate: Boolean,
    private val sortBy: SortBy
): CoinsRepo.Query() {

    override fun currency(): String {
        return currency!!
    }

    override fun forceUpdate(): Boolean {
        return forceUpdate
    }

    override fun sortBy(): SortBy {
        return sortBy
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BuilderCoinsRepoQuery

        if (currency != other.currency) return false
        if (forceUpdate != other.forceUpdate) return false
        if (sortBy != other.sortBy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currency?.hashCode() ?: 0
        result = 31 * result + forceUpdate.hashCode()
        result = 31 * result + sortBy.hashCode()
        return result
    }

    override fun toString(): String {
        return "AutoValue_CoinsRepo_Query(currency=$currency, forceUpdate=$forceUpdate, sortBy=$sortBy)"
    }


    companion object {

        class Builder : CoinsRepo.Query.Builder() {
            private var currency: String? = null
            private var forceUpdate: Boolean? = null
            private var sortBy: SortBy? = null

            override fun currency(currency: String?): CoinsRepo.Query.Builder {
                this.currency = currency
                return this
            }


            override fun forceUpdate(forceUpdate: Boolean): CoinsRepo.Query.Builder {
                this.forceUpdate = forceUpdate
                return this
            }

            override fun sortBy(sortBy: SortBy): CoinsRepo.Query.Builder {
                this.sortBy = sortBy
                return this
            }


            override fun build(): CoinsRepo.Query {
                var missing = ""
                if (currency == null) {
                    missing += " currency"
                }
                if (forceUpdate == null) {
                    missing += " forceUpdate"
                }
                if (sortBy == null) {
                    missing += " sortBy"
                }
                check(missing.isEmpty()) { "Missing required properties:$missing" }
                return BuilderCoinsRepoQuery(
                    currency,
                    forceUpdate!!,
                    sortBy!!
                )
            }
        }
    }

}