package com.financetracker.app.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "finance_tracker_prefs",
        Context.MODE_PRIVATE
    )
    
    var defaultCurrency: String
        get() = prefs.getString("default_currency", "RUB") ?: "RUB"
        set(value) = prefs.edit().putString("default_currency", value).apply()
    
    var isBiometricEnabled: Boolean
        get() = prefs.getBoolean("biometric_enabled", false)
        set(value) = prefs.edit().putBoolean("biometric_enabled", value).apply()
    
    var isDarkTheme: Boolean
        get() = prefs.getBoolean("dark_theme", false)
        set(value) = prefs.edit().putBoolean("dark_theme", value).apply()
    
    var isNotificationsEnabled: Boolean
        get() = prefs.getBoolean("notifications_enabled", true)
        set(value) = prefs.edit().putBoolean("notifications_enabled", value).apply()
    
    var budgetWarningThreshold: Int
        get() = prefs.getInt("budget_warning_threshold", 80)
        set(value) = prefs.edit().putInt("budget_warning_threshold", value).apply()
    
    fun clearAll() {
        prefs.edit().clear().apply()
    }
} 