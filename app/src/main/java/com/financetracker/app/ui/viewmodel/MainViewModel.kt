package com.financetracker.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financetracker.app.data.model.Transaction
import com.financetracker.app.data.model.TransactionType
import com.financetracker.app.data.repository.TransactionRepository
import com.financetracker.app.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            combine(
                transactionRepository.getRecentTransactions(5),
                transactionRepository.getTotalByType(TransactionType.INCOME),
                transactionRepository.getTotalByType(TransactionType.EXPENSE)
            ) { recentTransactions, totalIncome, totalExpense ->
                MainUiState(
                    recentTransactions = recentTransactions,
                    totalIncome = totalIncome ?: 0.0,
                    totalExpense = totalExpense ?: 0.0,
                    balance = (totalIncome ?: 0.0) - (totalExpense ?: 0.0),
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
    
    fun refreshData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadData()
    }
}

data class MainUiState(
    val recentTransactions: List<Transaction> = emptyList(),
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val balance: Double = 0.0,
    val isLoading: Boolean = true
) 