package com.jaarvi.shared.domain.usecases

import com.jaarvi.shared.domain.models.City
import com.jaarvi.shared.domain.repositories.IDestinationRepository

/**
 * Returns cities from the destination catalog, optionally filtered by country.
 *
 * Thin wrapper around [IDestinationRepository.getCities].
 *
 * @property repository Destination catalog repository.
 */
class GetCitiesUseCase(
    private val repository: IDestinationRepository,
) {
    /**
     * Fetches cities, optionally filtered by country.
     *
     * @param countryId UUID of the country to filter by, or `null` for all cities.
     * @return [Result] wrapping the list of [City] (each with embedded country) or an error.
     */
    suspend fun invoke(countryId: String? = null): Result<List<City>> =
        repository.getCities(countryId)
}
