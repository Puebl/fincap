package com.financetracker.app

import android.app.Application
import com.financetracker.app.data.local.DefaultDataInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FinanceTrackerApp : Application() {
    
    @Inject
    lateinit var defaultDataInitializer: DefaultDataInitializer
    
    override fun onCreate() {
        super.onCreate()
        
        // Инициализация данных по умолчанию
        CoroutineScope(Dispatchers.IO).launch {
            defaultDataInitializer.initializeDefaultCategories()
        }
    }
} 