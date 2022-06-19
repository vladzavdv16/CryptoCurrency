package com.light.cryptocurrency.util.formatter

import android.content.Context
import androidx.core.os.ConfigurationCompat
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

import kotlin.collections.HashMap

@Singleton
class PriceFormatter @Inject constructor(
    private val context: Context
) : Formatter<Double> {

    private val locales: MutableMap<String, Locale> = HashMap()

    fun format(currency: String?, value: Double): String {
        locales.put("RUB", Locale("ru", "RU"))
        locales.put("EUR", Locale.GERMANY)
        var locale = locales.get(currency)
        if (locale == null) {
            val loc = ConfigurationCompat.getLocales(context.resources.configuration)
            locale = loc.get(0)
        }
        return NumberFormat.getCurrencyInstance(locale).format(value)
    }

    override fun format(value: Double): String = NumberFormat.getCurrencyInstance().format(value)

}