package com.light.cryptocurrency.util.formatter

import com.light.cryptocurrency.data.model.Wallet
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject

class BalanceFormatter @Inject constructor(): Formatter<Wallet> {
    
    override fun format(value: Wallet): String {
        val format = NumberFormat.getCurrencyInstance() as DecimalFormat
        val symbols = format.decimalFormatSymbols
        symbols.currencySymbol = value.coin.symbol
        format.decimalFormatSymbols = symbols
        return format.format(value.balance)
    }
}