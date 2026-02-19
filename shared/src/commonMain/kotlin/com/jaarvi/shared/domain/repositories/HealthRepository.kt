package com.jaarvi.shared.domain.repositories

import com.jaarvi.shared.domain.models.HealthStatus

/**
 * Repository interface for health check operations.
 *
 * Provides methods to verify backend API connectivity and retrieve health status.
 */
interface HealthRepository {
    /**
     * Fetches the health status from the backend API.
     *
     * @return Result containing HealthStatus on success or exception on failure
     */
    suspend fun getHealth(): Result<HealthStatus>
}
