package com.light.cryptocurrency.util

import java.text.NumberFormat

class PriceFormatter<T> : Formatter<Double> {

    override fun format(value: Double): String {
        return NumberFormat.getCurrencyInstance().format(value)
    }
}