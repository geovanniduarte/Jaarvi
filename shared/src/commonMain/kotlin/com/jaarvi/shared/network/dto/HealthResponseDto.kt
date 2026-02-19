package com.jaarvi.shared.network.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for health check API response.
 *
 * Maps to the JSON structure returned by the /api/health endpoint.
 */
@Serializable
data class HealthResponseDto(
    val message: String,
    val timestamp: String,
    val status: String? = null
)
