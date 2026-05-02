package com.jaarvi.shared.domain.usecases

import com.jaarvi.shared.domain.models.City
import com.jaarvi.shared.domain.models.Country
import com.jaarvi.shared.domain.repositories.IDestinationRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetCountriesUseCaseTest {

    @Test
    fun `returns countries from repository on success`() = runTest {
        val countries = listOf(
            Country(id = "c1", isoCode = "FR", name = "France"),
            Country(id = "c2", isoCode = "IT", name = "Italy"),
        )
        val useCase = GetCountriesUseCase(FakeDestinationRepository(countries = countries))

        val result = useCase.invoke()

        assertTrue(result.isSuccess)
        assertEquals(countries, result.getOrNull())
    }

    @Test
    fun `propagates failure from repository`() = runTest {
        val useCase = GetCountriesUseCase(FakeDestinationRepository(shouldFail = true))

        val result = useCase.invoke()

        assertTrue(result.isFailure)
    }
}

class GetCitiesUseCaseTest {

    private val france = Country(id = "c1", isoCode = "FR", name = "France")
    private val paris  = City(id = "ct1", countryId = "c1", name = "Paris", timezone = "Europe/Paris", country = france)
    private val lyon   = City(id = "ct2", countryId = "c1", name = "Lyon",  timezone = "Europe/Paris", country = france)

    @Test
    fun `returns cities for given countryId`() = runTest {
        val useCase = GetCitiesUseCase(FakeDestinationRepository(cities = listOf(paris, lyon)))

        val result = useCase.invoke(countryId = "c1")

        assertTrue(result.isSuccess)
        assertEquals(listOf(paris, lyon), result.getOrNull())
    }

    @Test
    fun `null countryId returns all cities`() = runTest {
        val useCase = GetCitiesUseCase(FakeDestinationRepository(cities = listOf(paris, lyon)))

        val result = useCase.invoke(countryId = null)

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    @Test
    fun `propagates failure from repository`() = runTest {
        val useCase = GetCitiesUseCase(FakeDestinationRepository(shouldFail = true))

        val result = useCase.invoke(countryId = "c1")

        assertTrue(result.isFailure)
    }
}

// ── Fake repository ───────────────────────────────────────────────────────────

private class FakeDestinationRepository(
    private val countries : List<Country> = emptyList(),
    private val cities    : List<City>    = emptyList(),
    private val shouldFail: Boolean       = false,
) : IDestinationRepository {

    override suspend fun getCountries(): Result<List<Country>> =
        if (shouldFail) Result.failure(Exception("Network error"))
        else Result.success(countries)

    override suspend fun getCities(countryId: String?): Result<List<City>> =
        if (shouldFail) Result.failure(Exception("Network error"))
        else Result.success(cities)
}
