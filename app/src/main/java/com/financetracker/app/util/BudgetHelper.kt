package com.financetracker.app.util

import com.financetracker.app.data.model.Budget
import com.financetracker.app.data.model.BudgetPeriod
import com.financetracker.app.data.model.Transaction
import java.util.*

object BudgetHelper {
    
    fun calculateBudgetProgress(budget: Budget, transactions: List<Transaction>): BudgetProgress {
        val now = Date()
        
        // Проверяем, активен ли бюджет
        if (now.before(budget.startDate) || now.after(budget.endDate)) {
            return BudgetProgress(0.0, budget.amount, 0.0, 0)
        }
        
        // Фильтруем транзакции по категории и периоду бюджета
        val budgetTransactions = transactions.filter { transaction ->
            transaction.categoryId == budget.categoryId &&
            transaction.date >= budget.startDate &&
            transaction.date <= budget.endDate
        }
        
        val spent = budgetTransactions.sumOf { it.amount }
        val remaining = budget.amount - spent
        val progressPercentage = ((spent / budget.amount) * 100).toInt()
        
        return BudgetProgress(spent, budget.amount, remaining, progressPercentage)
    }
    
    fun isBudgetWarning(budget: Budget, transactions: List<Transaction>, threshold: Int = 80): Boolean {
        val progress = calculateBudgetProgress(budget, transactions)
        return progress.percentage >= threshold
    }
    
    fun isBudgetExceeded(budget: Budget, transactions: List<Transaction>): Boolean {
        val progress = calculateBudgetProgress(budget, transactions)
        return progress.spent > budget.amount
    }
    
    fun getBudgetPeriodDates(period: BudgetPeriod): Pair<Date, Date> {
        val calendar = Calendar.getInstance()
        val startDate: Date
        val endDate: Date
        
        when (period) {
            BudgetPeriod.WEEKLY -> {
                // Начало недели (понедельник)
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                startDate = calendar.time
                
                // Конец недели (воскресенье)
                calendar.add(Calendar.DAY_OF_WEEK, 6)
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                endDate = calendar.time
            }
            
            BudgetPeriod.MONTHLY -> {
                // Начало месяца
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                startDate = calendar.time
                
                // Конец месяца
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                endDate = calendar.time
            }
            
            BudgetPeriod.YEARLY -> {
                // Начало года
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                startDate = calendar.time
                
                // Конец года
                calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR))
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                endDate = calendar.time
            }
        }
        
        return Pair(startDate, endDate)
    }
}

data class BudgetProgress(
    val spent: Double,
    val limit: Double,
    val remaining: Double,
    val percentage: Int
) 