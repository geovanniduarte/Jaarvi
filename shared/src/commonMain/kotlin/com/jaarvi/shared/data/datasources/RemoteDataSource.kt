package com.jaarvi.shared.data.datasources

import com.jaarvi.shared.network.api.JaarviApiClient
import com.jaarvi.shared.network.dto.HealthResponseDto

/**
 * Abstraction for fetching health data from a remote source.
 * Allows tests to provide a mock implementation.
 */
interface HealthDataSource {
    suspend fun getHealth(): HealthResponseDto
}

/**
 * Remote data source for backend API operations.
 *
 * Wraps the API client to provide a clean data layer interface.
 *
 * @property apiClient Jaarvi API client instance
 */
class RemoteDataSource(
    private val apiClient: JaarviApiClient
) : HealthDataSource {
    /**
     * Fetches health status from the remote API.
     *
     * @return HealthResponseDto from the backend
     * @throws Exception if the network request fails
     */
    override suspend fun getHealth(): HealthResponseDto {
        return apiClient.getHealth()
    }
}
