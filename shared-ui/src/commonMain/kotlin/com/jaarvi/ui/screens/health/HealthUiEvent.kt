package com.jaarvi.ui.screens.health

/**
 * Represents user interactions on the Health Check screen.
 */
sealed interface HealthUiEvent {
    /**
     * User triggered a manual health check.
     */
    data object CheckHealth : HealthUiEvent

    /**
     * User tapped the retry button after an error.
     */
    data object Retry : HealthUiEvent
}
