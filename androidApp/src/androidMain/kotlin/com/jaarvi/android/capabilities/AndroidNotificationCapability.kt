package com.jaarvi.android.capabilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.jaarvi.shared.capabilities.NotificationCapability

/**
 * Android implementation of NotificationCapability.
 * V1: Basic notification channel setup
 * V2: Will add scheduling, push notifications, and rich content
 *
 * @property context Android application context
 */
class AndroidNotificationCapability(
    private val context: Context
) : NotificationCapability {

    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        createNotificationChannel()
    }

    override suspend fun scheduleNotification(
        id: String,
        title: String,
        message: String,
        scheduledTimeMillis: Long
    ): Result<Unit> {
        // V1: Not yet implemented
        return Result.failure(UnsupportedOperationException("Scheduling not yet implemented"))
    }

    override suspend fun cancelNotification(id: String): Result<Unit> {
        // V1: Not yet implemented
        return Result.failure(UnsupportedOperationException("Cancellation not yet implemented"))
    }
    
    override suspend fun requestNotificationPermission(): Result<Boolean> {
        // V1: Not yet implemented - would require Activity context for permission request
        return Result.failure(UnsupportedOperationException("Permission request not yet implemented"))
    }

    /**
     * Checks if notification permission is granted.
     *
     * @return true if permission is granted (Android 13+) or not required (Android 12-)
     */
    override fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationManager.areNotificationsEnabled()
        } else {
            true // Notifications don't require runtime permission before Android 13
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Jaarvi Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for travel activities and reminders"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "jaarvi_reminders"
    }
}
