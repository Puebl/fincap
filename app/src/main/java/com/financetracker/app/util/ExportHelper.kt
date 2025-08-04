package com.financetracker.app.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.financetracker.app.data.model.Transaction
import com.financetracker.app.data.model.Category
import com.financetracker.app.data.model.TransactionType
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExportHelper @Inject constructor(
    private val context: Context
) {
    
    fun exportToCSV(transactions: List<Transaction>, categories: List<Category>): Uri? {
        return try {
            val fileName = "finance_export_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.csv"
            val file = File(context.cacheDir, fileName)
            
            FileWriter(file).use { writer ->
                // Заголовок
                writer.append("Дата,Тип,Категория,Сумма,Валюта,Комментарий\n")
                
                // Данные
                transactions.forEach { transaction ->
                    val category = categories.find { it.id == transaction.categoryId }
                    val categoryName = category?.name ?: "Неизвестно"
                    
                    writer.append("${FormatUtils.formatDate(transaction.date)},")
                    writer.append("${if (transaction.type == TransactionType.INCOME) "Доход" else "Расход"},")
                    writer.append("$categoryName,")
                    writer.append("${transaction.amount},")
                    writer.append("${transaction.currency},")
                    writer.append("${transaction.comment ?: ""}\n")
                }
            }
            
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            
            uri
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun shareFile(uri: Uri, mimeType: String = "text/csv") {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Экспорт финансовых данных")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        
        val chooser = Intent.createChooser(intent, "Поделиться файлом")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }
    
    fun generateSummary(transactions: List<Transaction>, categories: List<Category>): String {
        val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
        val balance = totalIncome - totalExpense
        
        val categoryStats = categories.map { category ->
            val categoryTransactions = transactions.filter { it.categoryId == category.id }
            val categoryTotal = categoryTransactions.sumOf { it.amount }
            "${category.name}: ${FormatUtils.formatCurrency(categoryTotal)}"
        }.joinToString("\n")
        
        return """
            Сводка по финансам
            
            Общий доход: ${FormatUtils.formatCurrency(totalIncome)}
            Общий расход: ${FormatUtils.formatCurrency(totalExpense)}
            Баланс: ${FormatUtils.formatCurrency(balance)}
            
            По категориям:
            $categoryStats
        """.trimIndent()
    }
} 