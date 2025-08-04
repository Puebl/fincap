package com.financetracker.app.util

object Constants {
    
    // Database
    const val DATABASE_NAME = "finance_tracker_database"
    const val DATABASE_VERSION = 1
    
    // Preferences
    const val PREFS_NAME = "finance_tracker_prefs"
    const val KEY_DEFAULT_CURRENCY = "default_currency"
    const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
    const val KEY_DARK_THEME = "dark_theme"
    const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
    const val KEY_BUDGET_WARNING_THRESHOLD = "budget_warning_threshold"
    
    // API
    const val CURRENCY_API_BASE_URL = "https://api.exchangerate-api.com/v4/"
    
    // Notifications
    const val CHANNEL_BUDGET = "budget_channel"
    const val CHANNEL_REMINDER = "reminder_channel"
    const val BUDGET_NOTIFICATION_ID = 1
    const val REMINDER_NOTIFICATION_ID = 2
    
    // File Provider
    const val FILE_PROVIDER_AUTHORITY = "com.financetracker.app.fileprovider"
    
    // Default values
    const val DEFAULT_CURRENCY = "RUB"
    const val DEFAULT_BUDGET_WARNING_THRESHOLD = 80
    
    // Animation durations
    const val ANIMATION_DURATION_SHORT = 200L
    const val ANIMATION_DURATION_MEDIUM = 300L
    const val ANIMATION_DURATION_LONG = 500L
    
    // UI
    const val CARD_ELEVATION = 4
    const val CARD_CORNER_RADIUS = 12
    const val CARD_MARGIN = 8
    
    // Limits
    const val MAX_TRANSACTION_AMOUNT = 999999999.99
    const val MAX_COMMENT_LENGTH = 500
    const val MAX_CATEGORY_NAME_LENGTH = 50
} 