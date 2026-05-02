package com.jaarvi.shared.domain.usecases

import com.jaarvi.shared.domain.models.Country
import com.jaarvi.shared.domain.repositories.IDestinationRepository

/**
 * Returns all countries from the destination catalog, ordered alphabetically by name.
 *
 * Thin wrapper around [IDestinationRepository.getCountries].
 *
 * @property repository Destination catalog repository.
 */
class GetCountriesUseCase(
    private val repository: IDestinationRepository,
) {
    /**
     * Fetches all countries.
     *
     * @return [Result] wrapping the list of [Country] or an error.
     */
    suspend fun invoke(): Result<List<Country>> = repository.getCountries()
}
