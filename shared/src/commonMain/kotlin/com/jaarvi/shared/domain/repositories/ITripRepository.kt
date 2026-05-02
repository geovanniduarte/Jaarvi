package com.jaarvi.shared.domain.repositories

import com.jaarvi.shared.domain.models.Trip
import com.jaarvi.shared.domain.models.TripDestination
import kotlinx.datetime.LocalDate

/**
 * Repository interface for trip lifecycle operations.
 *
 * All methods return [Result] — callers use [Result.onSuccess] / [Result.onFailure]
 * without needing to catch exceptions.
 */
interface ITripRepository {

    /**
     * Creates a new trip in `draft` status.
     *
     * @param name      Optional human-readable label (max 100 chars).
     * @param startDate First day of the trip.
     * @param endDate   Last day of the trip (must be after [startDate]).
     * @return [Result] wrapping the created [Trip] or an error.
     */
    suspend fun createTrip(
        name     : String?,
        startDate: LocalDate,
        endDate  : LocalDate,
    ): Result<Trip>

    /**
     * Adds a destination to an existing trip.
     *
     * @param tripId    UUID of the parent trip.
     * @param cityId    UUID of the destination city.
     * @param dayOrder  1-based position in the itinerary.
     * @param daysCount Number of days to spend here.
     * @return [Result] wrapping the created [TripDestination] or an error.
     */
    suspend fun addDestination(
        tripId   : String,
        cityId   : String,
        dayOrder : Int,
        daysCount: Int,
    ): Result<TripDestination>

    /**
     * Saves the planning preferences for a trip.
     *
     * @param tripId              UUID of the trip.
     * @param travelStyle         Optional preferred travel style.
     * @param budget              Optional budget tier.
     * @param pace                Optional travel pace.
     * @param interests           List of interest tags.
     * @param specialRequirements Optional free-text requirements.
     * @return [Result] of [Unit] or an error.
     */
    suspend fun savePlanningContext(
        tripId              : String,
        travelStyle         : String?,
        budget              : String?,
        pace                : String?,
        interests           : List<String>,
        specialRequirements : String?,
    ): Result<Unit>

    /**
     * Fetches a single trip by its ID.
     *
     * @param tripId UUID of the trip.
     * @return [Result] wrapping the [Trip] or an error.
     */
    suspend fun getTripById(tripId: String): Result<Trip>
}
