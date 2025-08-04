package com.financetracker.app.di

import com.financetracker.app.data.repository.AuthRepository
import com.financetracker.app.data.repository.BudgetRepository
import com.financetracker.app.data.repository.CategoryRepository
import com.financetracker.app.data.repository.CurrencyRepository
import com.financetracker.app.data.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: com.financetracker.app.data.local.TransactionDao
    ): TransactionRepository {
        return TransactionRepository(transactionDao)
    }
    
    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryDao: com.financetracker.app.data.local.CategoryDao
    ): CategoryRepository {
        return CategoryRepository(categoryDao)
    }
    
    @Provides
    @Singleton
    fun provideBudgetRepository(
        budgetDao: com.financetracker.app.data.local.BudgetDao
    ): BudgetRepository {
        return BudgetRepository(budgetDao)
    }
    
    @Provides
    @Singleton
    fun provideCurrencyRepository(
        currencyApi: com.financetracker.app.data.remote.CurrencyApi
    ): CurrencyRepository {
        return CurrencyRepository(currencyApi)
    }
    
    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: com.google.firebase.auth.FirebaseAuth
    ): AuthRepository {
        return AuthRepository(auth)
    }
} 