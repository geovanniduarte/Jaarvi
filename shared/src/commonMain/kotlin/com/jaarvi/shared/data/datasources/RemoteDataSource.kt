package com.jaarvi.shared.data.datasources

import com.jaarvi.shared.network.api.JaarviApiClient
import com.jaarvi.shared.network.dto.HealthResponseDto

/**
 * Remote data source for backend API operations.
 *
 * Wraps the API client to provide a clean data layer interface.
 *
 * @property apiClient Jaarvi API client instance
 */
class RemoteDataSource(
    private val apiClient: JaarviApiClient
) {
    /**
     * Fetches health status from the remote API.
     *
     * @return HealthResponseDto from the backend
     * @throws Exception if the network request fails
     */
    suspend fun getHealth(): HealthResponseDto {
        return apiClient.getHealth()
    }
}
