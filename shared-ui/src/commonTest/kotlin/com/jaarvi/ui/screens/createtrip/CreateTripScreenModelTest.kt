package com.jaarvi.ui.screens.createtrip

import com.jaarvi.shared.domain.models.City
import com.jaarvi.shared.domain.models.Country
import com.jaarvi.shared.domain.models.Trip
import com.jaarvi.shared.domain.models.TripDestination
import com.jaarvi.shared.domain.models.TripStatus
import com.jaarvi.shared.domain.repositories.IDestinationRepository
import com.jaarvi.shared.domain.repositories.ITripRepository
import com.jaarvi.shared.domain.usecases.CreateTripUseCase
import com.jaarvi.shared.domain.usecases.GetCitiesUseCase
import com.jaarvi.shared.domain.usecases.GetCountriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CreateTripScreenModelTest {

    private val dispatcher = StandardTestDispatcher()

    // ── Shared fixtures ───────────────────────────────────────────────────────

    private val today    = LocalDate(2026, 7, 1)
    private val tomorrow = LocalDate(2026, 7, 2)
    private val later    = LocalDate(2026, 7, 10)

    private val france = Country(id = "c1", isoCode = "FR", name = "France")
    private val paris  = City(id = "ct1", countryId = "c1", name = "Paris", timezone = "Europe/Paris", country = france)
    private val fakeTripId = "trip-abc"

    private fun makeModel(
        tripRepo       : ITripRepository       = FakeTripRepo(tripId = fakeTripId),
        destRepo       : IDestinationRepository = FakeDestinationRepo(cities = listOf(paris)),
    ) = CreateTripScreenModel(
        createTripUseCase   = CreateTripUseCase(tripRepo),
        getCountriesUseCase = GetCountriesUseCase(destRepo),
        getCitiesUseCase    = GetCitiesUseCase(destRepo),
        tripRepository      = tripRepo,
    )

    @BeforeTest
    fun setup() { Dispatchers.setMain(dispatcher) }

    @AfterTest
    fun teardown() { Dispatchers.resetMain() }

    // ── Tests ─────────────────────────────────────────────────────────────────

    @Test
    fun `start loads countries into state`() = runTest(dispatcher) {
        val countries = listOf(france)
        val model = makeModel(destRepo = FakeDestinationRepo(countries = countries, cities = emptyList()))
        model.start()
        advanceUntilIdle()
        assertEquals(countries, model.state.value.availableCountries)
    }

    @Test
    fun `TripNameChanged updates tripName in state`() = runTest(dispatcher) {
        val model = makeModel()
        model.onEvent(CreateTripUiEvent.TripNameChanged("My Trip"))
        assertEquals("My Trip", model.state.value.tripName)
    }

    @Test
    fun `StartDateSelected and EndDateSelected update dates and clear step1Error`() = runTest(dispatcher) {
        val model = makeModel()
        model.onEvent(CreateTripUiEvent.StartDateSelected(tomorrow))
        model.onEvent(CreateTripUiEvent.EndDateSelected(later))
        val s = model.state.value
        assertEquals(tomorrow, s.startDate)
        assertEquals(later, s.endDate)
        assertNull(s.step1Error)
    }

    @Test
    fun `NextStep on step 1 without valid dates sets step1Error`() = runTest(dispatcher) {
        val model = makeModel()
        model.onEvent(CreateTripUiEvent.NextStep)
        assertNotNull(model.state.value.step1Error)
    }

    @Test
    fun `NextStep on step 1 with valid dates calls createTrip and advances to step 2`() = runTest(dispatcher) {
        val model = makeModel()
        model.onEvent(CreateTripUiEvent.StartDateSelected(tomorrow))
        model.onEvent(CreateTripUiEvent.EndDateSelected(later))
        model.onEvent(CreateTripUiEvent.NextStep)
        advanceUntilIdle()
        assertEquals(2, model.state.value.currentStep)
        assertEquals(fakeTripId, model.state.value.createdTripId)
    }

    @Test
    fun `NextStep on step 2 without full day allocation sets step2Error`() = runTest(dispatcher) {
        val model = makeModel()
        // Advance to step 2 with a 9-day trip (tomorrow → later)
        model.onEvent(CreateTripUiEvent.StartDateSelected(tomorrow))
        model.onEvent(CreateTripUiEvent.EndDateSelected(later))
        model.onEvent(CreateTripUiEvent.NextStep)
        advanceUntilIdle()
        // Try advancing without adding any destination
        model.onEvent(CreateTripUiEvent.NextStep)
        assertNotNull(model.state.value.step2Error)
    }

    @Test
    fun `CountrySelected loads cities via GetCitiesUseCase`() = runTest(dispatcher) {
        val cities = listOf(paris)
        val model  = makeModel(destRepo = FakeDestinationRepo(cities = cities))
        model.onEvent(CreateTripUiEvent.CountrySelected(france))
        advanceUntilIdle()
        assertEquals(france, model.state.value.selectedCountry)
        assertEquals(cities, model.state.value.availableCities)
    }

    @Test
    fun `RemoveDestination removes item and reorders dayOrder`() = runTest(dispatcher) {
        val model = makeModel()
        // Manually inject two destinations into state via reflection-free approach:
        // fire a state update through two AddDestination events (need tripId first)
        model.onEvent(CreateTripUiEvent.StartDateSelected(tomorrow))
        model.onEvent(CreateTripUiEvent.EndDateSelected(later))
        model.onEvent(CreateTripUiEvent.NextStep)
        advanceUntilIdle()
        // Add destination 1
        model.onEvent(CreateTripUiEvent.CitySelected(paris))
        model.onEvent(CreateTripUiEvent.DaysCountChanged(5))
        model.onEvent(CreateTripUiEvent.AddDestination)
        advanceUntilIdle()
        // Add destination 2 (same city, remaining days)
        model.onEvent(CreateTripUiEvent.CitySelected(paris))
        model.onEvent(CreateTripUiEvent.DaysCountChanged(4))
        model.onEvent(CreateTripUiEvent.AddDestination)
        advanceUntilIdle()
        // Remove the first destination
        model.onEvent(CreateTripUiEvent.RemoveDestination(1))
        val destinations = model.state.value.destinations
        assertEquals(1, destinations.size)
        assertEquals(1, destinations.first().dayOrder)
    }

    @Test
    fun `InterestToggled adds interest when not present`() = runTest(dispatcher) {
        val model = makeModel()
        model.onEvent(CreateTripUiEvent.InterestToggled("Museums"))
        assertTrue("Museums" in model.state.value.selectedInterests)
    }

    @Test
    fun `InterestToggled removes interest when already present`() = runTest(dispatcher) {
        val model = makeModel()
        model.onEvent(CreateTripUiEvent.InterestToggled("Museums"))
        model.onEvent(CreateTripUiEvent.InterestToggled("Museums"))
        assertTrue("Museums" !in model.state.value.selectedInterests)
    }

    @Test
    fun `DismissError clears all error fields`() = runTest(dispatcher) {
        val model = makeModel()
        model.onEvent(CreateTripUiEvent.NextStep) // triggers step1Error
        model.onEvent(CreateTripUiEvent.DismissError)
        val s = model.state.value
        assertNull(s.step1Error)
        assertNull(s.step2Error)
        assertNull(s.error)
    }
}

// ── Fake dependencies ─────────────────────────────────────────────────────────

private class FakeTripRepo(
    private val tripId    : String  = "trip-fake",
    private val shouldFail: Boolean = false,
) : ITripRepository {

    private fun makeTrip(id: String, start: LocalDate, end: LocalDate) = Trip(
        id        = id,
        ownerId   = "owner-1",
        name      = null,
        startDate = start,
        endDate   = end,
        status    = TripStatus.DRAFT,
        createdAt = kotlinx.datetime.Instant.parse("2026-01-01T00:00:00Z"),
    )

    private fun makeDestination(tripId: String, cityId: String, dayOrder: Int, daysCount: Int): TripDestination {
        val city = City(
            id        = cityId,
            countryId = "c1",
            name      = "Paris",
            timezone  = "Europe/Paris",
            country   = Country(id = "c1", isoCode = "FR", name = "France"),
        )
        return TripDestination(
            id        = "dest-${dayOrder}",
            tripId    = tripId,
            cityId    = cityId,
            city      = city,
            dayOrder  = dayOrder,
            daysCount = daysCount,
            notes     = null,
        )
    }

    override suspend fun createTrip(name: String?, startDate: LocalDate, endDate: LocalDate): Result<Trip> =
        if (shouldFail) Result.failure(Exception("Network error"))
        else Result.success(makeTrip(tripId, startDate, endDate))

    override suspend fun addDestination(tripId: String, cityId: String, dayOrder: Int, daysCount: Int): Result<TripDestination> =
        if (shouldFail) Result.failure(Exception("Network error"))
        else Result.success(makeDestination(tripId, cityId, dayOrder, daysCount))

    override suspend fun savePlanningContext(
        tripId: String, travelStyle: String?, budget: String?, pace: String?,
        interests: List<String>, specialRequirements: String?,
    ): Result<Unit> = if (shouldFail) Result.failure(Exception("Network error")) else Result.success(Unit)

    override suspend fun getTripById(tripId: String): Result<Trip> =
        Result.failure(NotImplementedError())
}

private class FakeDestinationRepo(
    private val countries : List<Country> = emptyList(),
    private val cities    : List<City>    = emptyList(),
    private val shouldFail: Boolean       = false,
) : IDestinationRepository {
    override suspend fun getCountries(): Result<List<Country>> =
        if (shouldFail) Result.failure(Exception("Network error")) else Result.success(countries)

    override suspend fun getCities(countryId: String?): Result<List<City>> =
        if (shouldFail) Result.failure(Exception("Network error")) else Result.success(cities)
}
