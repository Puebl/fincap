package com.financetracker.app.data.model

data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
    val exchangeRate: Double = 1.0
) {
    companion object {
        val RUB = Currency("RUB", "Российский рубль", "₽")
        val USD = Currency("USD", "Доллар США", "$")
        val EUR = Currency("EUR", "Евро", "€")
        
        val SUPPORTED_CURRENCIES = listOf(RUB, USD, EUR)
        
        fun getByCode(code: String): Currency? {
            return SUPPORTED_CURRENCIES.find { it.code == code }
        }
    }
} 