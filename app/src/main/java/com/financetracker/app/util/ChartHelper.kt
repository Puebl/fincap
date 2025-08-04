package com.financetracker.app.util

import com.financetracker.app.data.model.Transaction
import com.financetracker.app.data.model.TransactionType
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.util.*

object ChartHelper {
    
    fun createPieChartData(transactions: List<Transaction>, categories: Map<Long, String>): PieData {
        val entries = mutableListOf<PieEntry>()
        
        // Группируем транзакции по категориям
        val categoryTotals = mutableMapOf<String, Float>()
        
        transactions.forEach { transaction ->
            val categoryName = categories[transaction.categoryId] ?: "Неизвестно"
            val currentTotal = categoryTotals[categoryName] ?: 0f
            categoryTotals[categoryName] = currentTotal + transaction.amount.toFloat()
        }
        
        // Создаем записи для графика
        categoryTotals.forEach { (category, total) ->
            entries.add(PieEntry(total, category))
        }
        
        val dataSet = PieDataSet(entries, "Категории")
        dataSet.colors = listOf(
            android.graphics.Color.rgb(255, 87, 34),   // Оранжевый
            android.graphics.Color.rgb(33, 150, 243),  // Синий
            android.graphics.Color.rgb(156, 39, 176),  // Фиолетовый
            android.graphics.Color.rgb(255, 152, 0),   // Оранжевый
            android.graphics.Color.rgb(76, 175, 80),   // Зеленый
            android.graphics.Color.rgb(121, 85, 72),   // Коричневый
            android.graphics.Color.rgb(96, 125, 139),  // Серо-синий
            android.graphics.Color.rgb(158, 158, 158)  // Серый
        )
        
        return PieData(dataSet)
    }
    
    fun createBarChartData(transactions: List<Transaction>, days: Int = 7): BarData {
        val entries = mutableListOf<BarEntry>()
        val calendar = Calendar.getInstance()
        
        // Создаем данные для последних N дней
        for (i in 0 until days) {
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            val date = calendar.time
            
            val dayTransactions = transactions.filter { 
                FormatUtils.formatDate(it.date) == FormatUtils.formatDate(date)
            }
            
            val totalExpense = dayTransactions
                .filter { it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
                .toFloat()
            
            entries.add(BarEntry((days - i - 1).toFloat(), totalExpense))
            calendar.add(Calendar.DAY_OF_YEAR, i) // Возвращаем календарь в исходное состояние
        }
        
        val dataSet = BarDataSet(entries, "Расходы по дням")
        dataSet.color = android.graphics.Color.rgb(244, 67, 54) // Красный
        
        return BarData(dataSet)
    }
    
    fun getCategoryColors(): List<Int> {
        return listOf(
            android.graphics.Color.rgb(255, 87, 34),   // Еда
            android.graphics.Color.rgb(33, 150, 243),  // Транспорт
            android.graphics.Color.rgb(156, 39, 176),  // Развлечения
            android.graphics.Color.rgb(255, 152, 0),   // Покупки
            android.graphics.Color.rgb(76, 175, 80),   // Здоровье
            android.graphics.Color.rgb(121, 85, 72),   // Образование
            android.graphics.Color.rgb(96, 125, 139),  // Счета
            android.graphics.Color.rgb(158, 158, 158)  // Другое
        )
    }
} 