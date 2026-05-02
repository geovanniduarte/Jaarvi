package com.jaarvi.shared.domain.usecases

import com.jaarvi.shared.domain.models.Trip
import com.jaarvi.shared.domain.models.TripDestination
import com.jaarvi.shared.domain.models.TripStatus
import com.jaarvi.shared.domain.repositories.ITripRepository
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CreateTripUseCaseTest {

    private val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    private val tomorrow: LocalDate = LocalDate(today.year, today.monthNumber, today.dayOfMonth + 1)
    private val dayAfterTomorrow: LocalDate = LocalDate(today.year, today.monthNumber, today.dayOfMonth + 2)
    private val yesterday: LocalDate = LocalDate(today.year, today.monthNumber, today.dayOfMonth - 1)

    @Test
    fun `valid dates and name delegates to repository and returns Trip`() = runTest {
        val fakeTripId = "trip-123"
        val fakeRepository = FakeTripRepository(successTripId = fakeTripId)
        val useCase = CreateTripUseCase(fakeRepository)

        val result = useCase.invoke(
            name      = "Summer Escape",
            startDate = tomorrow,
            endDate   = dayAfterTomorrow,
        )

        assertTrue(result.isSuccess)
        assertEquals(fakeTripId, result.getOrNull()?.id)
    }

    @Test
    fun `null name is accepted and delegates to repository`() = runTest {
        val fakeRepository = FakeTripRepository(successTripId = "trip-no-name")
        val useCase = CreateTripUseCase(fakeRepository)

        val result = useCase.invoke(name = null, startDate = tomorrow, endDate = dayAfterTomorrow)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `endDate equal to startDate returns failure without calling repository`() = runTest {
        val fakeRepository = FakeTripRepository()
        val useCase = CreateTripUseCase(fakeRepository)

        val result = useCase.invoke(name = null, startDate = tomorrow, endDate = tomorrow)

        assertTrue(result.isFailure)
        assertEquals("endDate must be after startDate", result.exceptionOrNull()?.message)
        assertFalse(fakeRepository.wasCalled)
    }

    @Test
    fun `endDate before startDate returns failure without calling repository`() = runTest {
        val fakeRepository = FakeTripRepository()
        val useCase = CreateTripUseCase(fakeRepository)

        val result = useCase.invoke(name = null, startDate = dayAfterTomorrow, endDate = tomorrow)

        assertTrue(result.isFailure)
        assertEquals("endDate must be after startDate", result.exceptionOrNull()?.message)
        assertFalse(fakeRepository.wasCalled)
    }

    @Test
    fun `startDate in the past returns failure without calling repository`() = runTest {
        val fakeRepository = FakeTripRepository()
        val useCase = CreateTripUseCase(fakeRepository)

        val result = useCase.invoke(name = null, startDate = yesterday, endDate = tomorrow)

        assertTrue(result.isFailure)
        assertEquals("startDate cannot be in the past", result.exceptionOrNull()?.message)
        assertFalse(fakeRepository.wasCalled)
    }

    @Test
    fun `name exceeding 100 characters returns failure without calling repository`() = runTest {
        val fakeRepository = FakeTripRepository()
        val useCase = CreateTripUseCase(fakeRepository)
        val longName = "A".repeat(101)

        val result = useCase.invoke(name = longName, startDate = tomorrow, endDate = dayAfterTomorrow)

        assertTrue(result.isFailure)
        assertEquals("name exceeds 100 characters", result.exceptionOrNull()?.message)
        assertFalse(fakeRepository.wasCalled)
    }

    @Test
    fun `exactly 100 character name is accepted`() = runTest {
        val fakeRepository = FakeTripRepository(successTripId = "trip-ok")
        val useCase = CreateTripUseCase(fakeRepository)
        val maxName = "A".repeat(100)

        val result = useCase.invoke(name = maxName, startDate = tomorrow, endDate = dayAfterTomorrow)

        assertTrue(result.isSuccess)
    }
}

// ── Fake repository ───────────────────────────────────────────────────────────

private class FakeTripRepository(
    private val successTripId: String? = null,
) : ITripRepository {

    var wasCalled: Boolean = false

    private fun makeFakeTrip(id: String, start: LocalDate, end: LocalDate) = Trip(
        id        = id,
        ownerId   = "owner-1",
        name      = null,
        startDate = start,
        endDate   = end,
        status    = TripStatus.DRAFT,
        createdAt = kotlinx.datetime.Instant.parse("2026-01-01T00:00:00Z"),
    )

    override suspend fun createTrip(name: String?, startDate: LocalDate, endDate: LocalDate): Result<Trip> {
        wasCalled = true
        return if (successTripId != null)
            Result.success(makeFakeTrip(successTripId, startDate, endDate))
        else
            Result.failure(Exception("Network error"))
    }

    override suspend fun addDestination(
        tripId: String, cityId: String, dayOrder: Int, daysCount: Int,
    ): Result<TripDestination> = Result.failure(NotImplementedError())

    override suspend fun savePlanningContext(
        tripId: String, travelStyle: String?, budget: String?, pace: String?,
        interests: List<String>, specialRequirements: String?,
    ): Result<Unit> = Result.failure(NotImplementedError())

    override suspend fun getTripById(tripId: String): Result<Trip> =
        Result.failure(NotImplementedError())
}
