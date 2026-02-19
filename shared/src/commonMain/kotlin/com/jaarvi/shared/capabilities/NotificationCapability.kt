package com.jaarvi.shared.capabilities

/**
 * Platform capability for push notifications and alerts.
 *
 * Provides cross-platform interface for scheduling notifications,
 * used for time-based reminders (e.g., "time to leave for activity").
 */
interface NotificationCapability {
    /**
     * Schedules a notification to be delivered at a specific time.
     *
     * @param id Unique identifier for the notification
     * @param title Notification title
     * @param message Notification body text
     * @param scheduledTimeMillis Unix timestamp (milliseconds) when notification should appear
     * @return Result wrapping Unit on success or exception on failure
     */
    suspend fun scheduleNotification(
        id: String,
        title: String,
        message: String,
        scheduledTimeMillis: Long
    ): Result<Unit>
    
    /**
     * Cancels a previously scheduled notification.
     *
     * @param id Unique identifier of the notification to cancel
     * @return Result wrapping Unit on success or exception on failure
     */
    suspend fun cancelNotification(id: String): Result<Unit>
    
    /**
     * Checks if notification permission has been granted.
     *
     * @return True if permission granted, false otherwise
     */
    fun hasNotificationPermission(): Boolean
    
    /**
     * Requests notification permission from the user (if not already granted).
     *
     * @return Result wrapping true if permission granted, false if denied
     */
    suspend fun requestNotificationPermission(): Result<Boolean>
}
