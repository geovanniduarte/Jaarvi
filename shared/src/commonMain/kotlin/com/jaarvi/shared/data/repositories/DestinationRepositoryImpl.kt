package com.jaarvi.shared.data.repositories

import com.jaarvi.shared.domain.models.City
import com.jaarvi.shared.domain.models.Country
import com.jaarvi.shared.domain.repositories.IDestinationRepository
import com.jaarvi.shared.network.api.JaarviApiClient
import com.jaarvi.shared.network.dto.toDomain

/**
 * Ktor-backed implementation of [IDestinationRepository].
 *
 * All network calls are wrapped in [runCatching] so failures surface as
 * [Result.failure] rather than thrown exceptions.
 *
 * @property apiClient Jaarvi HTTP API client.
 */
class DestinationRepositoryImpl(
    private val apiClient: JaarviApiClient,
) : IDestinationRepository {

    override suspend fun getCountries(): Result<List<Country>> = runCatching {
        val response = apiClient.getCountries()
        response.data?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getCities(countryId: String?): Result<List<City>> = runCatching {
        val response = apiClient.getCities(countryId)
        response.data?.map { it.toDomain() } ?: emptyList()
    }
}
