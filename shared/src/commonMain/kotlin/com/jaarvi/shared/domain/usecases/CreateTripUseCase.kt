package com.jaarvi.shared.domain.usecases

import com.jaarvi.shared.domain.models.Trip
import com.jaarvi.shared.domain.repositories.ITripRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

/**
 * Validates and creates a new trip.
 *
 * Client-side validation rules (applied before any network call):
 * - [endDate] must be strictly after [startDate].
 * - [startDate] must not be in the past (compared to the device clock).
 * - [name], when provided, must not exceed 100 characters.
 *
 * On success, delegates to [ITripRepository.createTrip] and returns its result.
 *
 * Example usage:
 * ```kotlin
 * val result = createTripUseCase.invoke(
 *     name      = "Summer in Europe",
 *     startDate = LocalDate(2026, 7, 1),
 *     endDate   = LocalDate(2026, 7, 21),
 * )
 * result.onSuccess { trip -> println(trip.id) }
 * result.onFailure { e  -> showError(e.message) }
 * ```
 *
 * @property repository Trip repository used after validation passes.
 */
class CreateTripUseCase(
    private val repository: ITripRepository,
) {

    /**
     * Validates inputs then creates the trip.
     *
     * @param name      Optional trip label (max 100 chars).
     * @param startDate First day of the trip.
     * @param endDate   Last day — must be after [startDate].
     * @return [Result] wrapping the created [Trip] or a descriptive [IllegalArgumentException].
     */
    suspend fun invoke(
        name     : String?,
        startDate: LocalDate,
        endDate  : LocalDate,
    ): Result<Trip> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

        if (!endDate.isAfter(startDate)) {
            return Result.failure(IllegalArgumentException("endDate must be after startDate"))
        }
        if (startDate < today) {
            return Result.failure(IllegalArgumentException("startDate cannot be in the past"))
        }
        if (name != null && name.length > 100) {
            return Result.failure(IllegalArgumentException("name exceeds 100 characters"))
        }

        return repository.createTrip(name, startDate, endDate)
    }

    private fun LocalDate.isAfter(other: LocalDate): Boolean = this > other
}
