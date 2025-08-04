package com.financetracker.app.data.repository

import com.financetracker.app.data.remote.CurrencyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(
    private val currencyApi: CurrencyApi
) {
    
    fun getLatestRates(baseCurrency: String = "RUB"): Flow<Map<String, Double>> = flow {
        try {
            val response = currencyApi.getLatestRates(baseCurrency)
            emit(response.rates)
        } catch (e: Exception) {
            // В случае ошибки возвращаем базовые курсы
            emit(mapOf(
                "USD" to 0.011,
                "EUR" to 0.010,
                "RUB" to 1.0
            ))
        }
    }
    
    suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Double {
        return try {
            val response = currencyApi.convertCurrency(fromCurrency, toCurrency, amount)
            response.result
        } catch (e: Exception) {
            // В случае ошибки возвращаем исходную сумму
            amount
        }
    }
} 