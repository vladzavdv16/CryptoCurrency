import com.light.cryptocurrency.data.CoinsRepo
import java.lang.NullPointerException


class AutoValue_CoinsRepo_Query constructor(
    private val currency: String?,
    private val forceUpdate: Boolean,
) : CoinsRepo.Query() {

    override fun currency(): String {
        return currency!!
    }

    override fun forceUpdate(): Boolean {
        return forceUpdate
    }


    override fun toString(): String {
        return ("Query{"
                + "currency=" + currency + ", "
                + "forceUpdate=" + forceUpdate + ", "
                + "}")
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) {
            return true
        }
        if (o is CoinsRepo.Query) {
            val that: CoinsRepo.Query? = o as CoinsRepo.Query?
            return currency == that?.currency() && forceUpdate == that?.forceUpdate()
        }
        return false
    }

    override fun hashCode(): Int {
        var `h$` = 1
        `h$` *= 1000003
        `h$` = `h$` xor currency.hashCode()
        `h$` *= 1000003
        `h$` = `h$` xor if (forceUpdate) 1231 else 1237
        `h$` *= 1000003
        return `h$`
    }

    companion object {

        class Builder : CoinsRepo.Query.Builder() {
            private var currency: String? = null
            private var forceUpdate: Boolean? = null

            override fun currency(currency: String): CoinsRepo.Query.Builder {
                if (currency == null) {
                    throw NullPointerException("Null currency")
                }
                this.currency = currency
                return this
            }


            override fun forceUpdate(forceUpdate: Boolean): CoinsRepo.Query.Builder {
                this.forceUpdate = forceUpdate
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
                check(missing.isEmpty()) { "Missing required properties:$missing" }
                return AutoValue_CoinsRepo_Query(
                    currency,
                    forceUpdate!!,
                )
            }
        }
    }

}