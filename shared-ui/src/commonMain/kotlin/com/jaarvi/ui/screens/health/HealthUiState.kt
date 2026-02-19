package com.jaarvi.ui.screens.health

/**
 * Represents the UI state for the Health Check screen.
 *
 * @property isLoading Whether the health check is currently in progress
 * @property message Success message from the backend health endpoint
 * @property timestamp Timestamp of the health check response
 * @property error Error message if the health check failed
 */
data class HealthUiState(
    val isLoading: Boolean = true,
    val message: String? = null,
    val timestamp: String? = null,
    val error: String? = null
) {
    /**
     * Indicates whether the screen is in a success state (health check passed).
     */
    val isSuccess: Boolean
        get() = !isLoading && message != null && error == null

    /**
     * Indicates whether the screen is in an error state (health check failed).
     */
    val isError: Boolean
        get() = !isLoading && error != null
}
