package com.financetracker.app.data.repository

import com.financetracker.app.data.local.TransactionDao
import com.financetracker.app.data.model.Transaction
import com.financetracker.app.data.model.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    
    fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }
    
    fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByType(type)
    }
    
    fun getTransactionsByCategory(categoryId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByCategory(categoryId)
    }
    
    fun getTransactionsByDateRange(startDate: Date, endDate: Date): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByDateRange(startDate, endDate)
    }
    
    fun getRecentTransactions(limit: Int = 10): Flow<List<Transaction>> {
        return transactionDao.getRecentTransactions(limit)
    }
    
    fun getTotalByType(type: TransactionType): Flow<Double?> {
        return transactionDao.getTotalByType(type)
    }
    
    fun getTotalByTypeAndDateRange(type: TransactionType, startDate: Date, endDate: Date): Flow<Double?> {
        return transactionDao.getTotalByTypeAndDateRange(type, startDate, endDate)
    }
    
    fun getTotalByCategoryAndDateRange(categoryId: Long, startDate: Date, endDate: Date): Flow<Double?> {
        return transactionDao.getTotalByCategoryAndDateRange(categoryId, startDate, endDate)
    }
    
    suspend fun insertTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction)
    }
    
    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }
    
    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }
    
    suspend fun deleteTransactionById(id: Long) {
        transactionDao.deleteTransactionById(id)
    }
} 