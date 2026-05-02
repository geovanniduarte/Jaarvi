package com.jaarvi.shared.network.api

import com.jaarvi.shared.network.ApiConfig
import com.jaarvi.shared.network.HttpClientFactory
import com.jaarvi.shared.network.dto.AddDestinationRequest
import com.jaarvi.shared.network.dto.ApiResponse
import com.jaarvi.shared.network.dto.CityDto
import com.jaarvi.shared.network.dto.CountryDto
import com.jaarvi.shared.network.dto.CreateTripRequest
import com.jaarvi.shared.network.dto.HealthResponseDto
import com.jaarvi.shared.network.dto.SavePlanningContextRequest
import com.jaarvi.shared.network.dto.TripDestinationDto
import com.jaarvi.shared.network.dto.TripDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

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

    // ── Health ──────────────────────────────────────────────────────────────

    /**
     * Fetches health status from the backend /api/health endpoint.
     *
     * @return HealthResponseDto containing server status information
     * @throws Exception if the request fails
     */
    suspend fun getHealth(): HealthResponseDto =
        httpClient.get("${ApiConfig.baseUrl}/health").body()

    // ── Trips ───────────────────────────────────────────────────────────────

    /**
     * Creates a new trip via `POST /api/trips`.
     *
     * @param body CreateTripRequest payload.
     * @return The created [TripDto] wrapped in [ApiResponse].
     * @throws Exception if the request fails.
     */
    suspend fun createTrip(body: CreateTripRequest): ApiResponse<TripDto> =
        httpClient.post("${ApiConfig.baseUrl}/trips") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()

    /**
     * Adds a destination to an existing trip via `POST /api/trips/{tripId}/destinations`.
     *
     * @param tripId UUID of the trip.
     * @param body   AddDestinationRequest payload.
     * @return The created [TripDestinationDto] wrapped in [ApiResponse].
     * @throws Exception if the request fails.
     */
    suspend fun addDestination(
        tripId: String,
        body  : AddDestinationRequest,
    ): ApiResponse<TripDestinationDto> =
        httpClient.post("${ApiConfig.baseUrl}/trips/$tripId/destinations") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()

    /**
     * Saves planning preferences via `POST /api/trips/{tripId}/planning-context`.
     *
     * @param tripId UUID of the trip.
     * @param body   SavePlanningContextRequest payload.
     * @throws Exception if the request fails.
     */
    suspend fun savePlanningContext(
        tripId: String,
        body  : SavePlanningContextRequest,
    ): ApiResponse<Unit> =
        httpClient.post("${ApiConfig.baseUrl}/trips/$tripId/planning-context") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()

    /**
     * Fetches a trip by its ID via `GET /api/trips/{tripId}`.
     *
     * @param tripId UUID of the trip.
     * @return The [TripDto] wrapped in [ApiResponse].
     * @throws Exception if the request fails.
     */
    suspend fun getTripById(tripId: String): ApiResponse<TripDto> =
        httpClient.get("${ApiConfig.baseUrl}/trips/$tripId").body()

    // ── Destinations catalog ─────────────────────────────────────────────────

    /**
     * Fetches all countries via `GET /api/destinations/countries`.
     *
     * @return List of [CountryDto] wrapped in [ApiResponse].
     * @throws Exception if the request fails.
     */
    suspend fun getCountries(): ApiResponse<List<CountryDto>> =
        httpClient.get("${ApiConfig.baseUrl}/destinations/countries").body()

    /**
     * Fetches cities via `GET /api/destinations/cities`, optionally filtered by country.
     *
     * @param countryId Optional UUID to filter cities by country.
     * @return List of [CityDto] wrapped in [ApiResponse].
     * @throws Exception if the request fails.
     */
    suspend fun getCities(countryId: String?): ApiResponse<List<CityDto>> =
        httpClient.get("${ApiConfig.baseUrl}/destinations/cities") {
            if (countryId != null) parameter("countryId", countryId)
        }.body()
}
