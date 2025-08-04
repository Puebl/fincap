package com.financetracker.app.data.local

import com.financetracker.app.data.model.Category
import com.financetracker.app.data.model.TransactionType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultDataInitializer @Inject constructor(
    private val categoryDao: CategoryDao
) {
    
    suspend fun initializeDefaultCategories() {
        val defaultCategories = listOf(
            // Income categories
            Category(
                name = "Зарплата",
                icon = "work",
                color = "#4CAF50",
                type = TransactionType.INCOME,
                isDefault = true
            ),
            Category(
                name = "Фриланс",
                icon = "computer",
                color = "#FFC107",
                type = TransactionType.INCOME,
                isDefault = true
            ),
            Category(
                name = "Инвестиции",
                icon = "trending_up",
                color = "#00BCD4",
                type = TransactionType.INCOME,
                isDefault = true
            ),
            
            // Expense categories
            Category(
                name = "Еда",
                icon = "restaurant",
                color = "#FF5722",
                type = TransactionType.EXPENSE,
                isDefault = true
            ),
            Category(
                name = "Транспорт",
                icon = "directions_car",
                color = "#2196F3",
                type = TransactionType.EXPENSE,
                isDefault = true
            ),
            Category(
                name = "Развлечения",
                icon = "movie",
                color = "#9C27B0",
                type = TransactionType.EXPENSE,
                isDefault = true
            ),
            Category(
                name = "Покупки",
                icon = "shopping_cart",
                color = "#FF9800",
                type = TransactionType.EXPENSE,
                isDefault = true
            ),
            Category(
                name = "Здоровье",
                icon = "local_hospital",
                color = "#4CAF50",
                type = TransactionType.EXPENSE,
                isDefault = true
            ),
            Category(
                name = "Образование",
                icon = "school",
                color = "#795548",
                type = TransactionType.EXPENSE,
                isDefault = true
            ),
            Category(
                name = "Счета",
                icon = "receipt",
                color = "#607D8B",
                type = TransactionType.EXPENSE,
                isDefault = true
            ),
            Category(
                name = "Другое",
                icon = "more_horiz",
                color = "#9E9E9E",
                type = TransactionType.EXPENSE,
                isDefault = true
            )
        )
        
        defaultCategories.forEach { category ->
            categoryDao.insertCategory(category)
        }
    }
} 