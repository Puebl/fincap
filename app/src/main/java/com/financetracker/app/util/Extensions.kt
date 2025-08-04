package com.financetracker.app.util

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.text.NumberFormat
import java.util.*

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Double.formatCurrency(locale: Locale = Locale.getDefault()): String {
    return NumberFormat.getCurrencyInstance(locale).format(this)
}

fun Double.formatAmount(): String {
    return String.format("%.2f", this)
}

fun String.parseAmount(): Double? {
    return try {
        this.replace(",", ".").toDouble()
    } catch (e: NumberFormatException) {
        null
    }
}

@Composable
fun rememberContext(): Context {
    return LocalContext.current
} 