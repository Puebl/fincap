package com.financetracker.app.data.local

import androidx.room.*
import com.financetracker.app.data.model.Budget
import com.financetracker.app.data.model.BudgetPeriod
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface BudgetDao {
    
    @Query("SELECT * FROM budgets WHERE isActive = 1 ORDER BY startDate DESC")
    fun getAllActiveBudgets(): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE categoryId = :categoryId AND isActive = 1")
    fun getBudgetsByCategory(categoryId: Long): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE period = :period AND isActive = 1")
    fun getBudgetsByPeriod(period: BudgetPeriod): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE startDate <= :date AND endDate >= :date AND isActive = 1")
    fun getActiveBudgetsByDate(date: Date): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: Long): Budget?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: Budget): Long
    
    @Update
    suspend fun updateBudget(budget: Budget)
    
    @Delete
    suspend fun deleteBudget(budget: Budget)
    
    @Query("DELETE FROM budgets WHERE id = :id")
    suspend fun deleteBudgetById(id: Long)
} 