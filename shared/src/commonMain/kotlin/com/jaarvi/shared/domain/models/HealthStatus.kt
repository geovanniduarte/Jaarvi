package com.jaarvi.shared.domain.models

/**
 * Domain model representing the health status of the backend API.
 *
 * @property message Health status message from the backend
 * @property timestamp ISO 8601 timestamp when the health check was performed
 * @property status Optional status code or identifier
 */
data class HealthStatus(
    val message: String,
    val timestamp: String,
    val status: String? = null
)
