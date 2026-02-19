package com.jaarvi.shared.network.api

import com.jaarvi.shared.network.ApiConfig
import com.jaarvi.shared.network.HttpClientFactory
import com.jaarvi.shared.network.dto.HealthResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * API client for Jaarvi backend endpoints.
 *
 * Provides methods to interact with the backend REST API.
 *
 * @property httpClient Ktor HTTP client instance
 */
class JaarviApiClient(
    private val httpClient: HttpClient = HttpClientFactory.create()
) {
    /**
     * Fetches health status from the backend /api/health endpoint.
     *
     * @return HealthResponseDto containing server status information
     * @throws Exception if the request fails
     */
    suspend fun getHealth(): HealthResponseDto {
        return httpClient.get("${ApiConfig.baseUrl}/health").body()
    }
}
