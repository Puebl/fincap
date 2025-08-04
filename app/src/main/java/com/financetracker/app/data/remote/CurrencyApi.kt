package com.financetracker.app.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    
    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") baseCurrency: String = "RUB"
    ): CurrencyResponse
    
    @GET("convert")
    suspend fun convertCurrency(
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String,
        @Query("amount") amount: Double
    ): ConversionResponse
}

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

data class ConversionResponse(
    val from: String,
    val to: String,
    val amount: Double,
    val result: Double
) 