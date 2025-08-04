package com.financetracker.app.util

import com.financetracker.app.data.model.Currency
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object FormatUtils {
    
    fun formatCurrency(amount: Double, currency: Currency = Currency.RUB): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
        formatter.currency = java.util.Currency.getInstance(currency.code)
        return formatter.format(amount)
    }
    
    fun formatDate(date: Date, pattern: String = "dd.MM.yyyy"): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }
    
    fun formatDateTime(date: Date): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return formatter.format(date)
    }
    
    fun formatAmount(amount: Double): String {
        return String.format("%.2f", amount)
    }
    
    fun parseAmount(amountString: String): Double? {
        return try {
            amountString.replace(",", ".").toDouble()
        } catch (e: NumberFormatException) {
            null
        }
    }
} 