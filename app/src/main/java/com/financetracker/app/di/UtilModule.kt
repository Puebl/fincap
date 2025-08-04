package com.financetracker.app.di

import android.content.Context
import com.financetracker.app.util.BiometricHelper
import com.financetracker.app.util.CameraHelper
import com.financetracker.app.util.ExportHelper
import com.financetracker.app.util.NotificationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    
    @Provides
    @Singleton
    fun provideBiometricHelper(): BiometricHelper {
        return BiometricHelper()
    }
    
    @Provides
    @Singleton
    fun provideCameraHelper(@ApplicationContext context: Context): CameraHelper {
        return CameraHelper(context)
    }
    
    @Provides
    @Singleton
    fun provideExportHelper(@ApplicationContext context: Context): ExportHelper {
        return ExportHelper(context)
    }
    
    @Provides
    @Singleton
    fun provideNotificationHelper(@ApplicationContext context: Context): NotificationHelper {
        return NotificationHelper(context)
    }
} 