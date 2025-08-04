package com.financetracker.app.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.financetracker.app.R
import com.financetracker.app.ui.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    private val context: Context
) {
    
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    
    init {
        createNotificationChannels()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val budgetChannel = NotificationChannel(
                CHANNEL_BUDGET,
                "Бюджеты",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Уведомления о бюджетах"
            }
            
            val reminderChannel = NotificationChannel(
                CHANNEL_REMINDER,
                "Напоминания",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Напоминания о транзакциях"
            }
            
            notificationManager.createNotificationChannels(listOf(budgetChannel, reminderChannel))
        }
    }
    
    fun showBudgetWarning(categoryName: String, spent: Double, limit: Double) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_BUDGET)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Предупреждение о бюджете")
            .setContentText("Категория '$categoryName' близка к лимиту")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Потрачено: ${FormatUtils.formatCurrency(spent)} из ${FormatUtils.formatCurrency(limit)}"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(BUDGET_NOTIFICATION_ID, notification)
    }
    
    fun showTransactionReminder() {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_REMINDER)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Напоминание")
            .setContentText("Не забудьте записать сегодняшние траты")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(REMINDER_NOTIFICATION_ID, notification)
    }
    
    companion object {
        private const val CHANNEL_BUDGET = "budget_channel"
        private const val CHANNEL_REMINDER = "reminder_channel"
        private const val BUDGET_NOTIFICATION_ID = 1
        private const val REMINDER_NOTIFICATION_ID = 2
    }
} 