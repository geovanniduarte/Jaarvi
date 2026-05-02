package com.jaarvi.shared.domain.repositories

import com.jaarvi.shared.domain.models.City
import com.jaarvi.shared.domain.models.Country

/**
 * Repository interface for the destination catalog (countries and cities).
 *
 * All methods return [Result] — callers use [Result.onSuccess] / [Result.onFailure]
 * without needing to catch exceptions.
 */
interface IDestinationRepository {

    /**
     * Returns all countries ordered alphabetically by name.
     *
     * @return [Result] wrapping the list of [Country] or an error.
     */
    suspend fun getCountries(): Result<List<Country>>

    /**
     * Returns cities from the catalog, optionally filtered by country.
     *
     * @param countryId UUID of the country to filter by, or `null` to return all cities.
     * @return [Result] wrapping the list of [City] (each with embedded [Country]) or an error.
     */
    suspend fun getCities(countryId: String?): Result<List<City>>
}
