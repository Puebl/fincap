package com.financetracker.app.data.repository

import com.financetracker.app.data.local.BudgetDao
import com.financetracker.app.data.model.Budget
import com.financetracker.app.data.model.BudgetPeriod
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao
) {
    
    fun getAllActiveBudgets(): Flow<List<Budget>> {
        return budgetDao.getAllActiveBudgets()
    }
    
    fun getBudgetsByCategory(categoryId: Long): Flow<List<Budget>> {
        return budgetDao.getBudgetsByCategory(categoryId)
    }
    
    fun getBudgetsByPeriod(period: BudgetPeriod): Flow<List<Budget>> {
        return budgetDao.getBudgetsByPeriod(period)
    }
    
    fun getActiveBudgetsByDate(date: Date): Flow<List<Budget>> {
        return budgetDao.getActiveBudgetsByDate(date)
    }
    
    suspend fun getBudgetById(id: Long): Budget? {
        return budgetDao.getBudgetById(id)
    }
    
    suspend fun insertBudget(budget: Budget): Long {
        return budgetDao.insertBudget(budget)
    }
    
    suspend fun updateBudget(budget: Budget) {
        budgetDao.updateBudget(budget)
    }
    
    suspend fun deleteBudget(budget: Budget) {
        budgetDao.deleteBudget(budget)
    }
    
    suspend fun deleteBudgetById(id: Long) {
        budgetDao.deleteBudgetById(id)
    }
} 