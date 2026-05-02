package com.jaarvi.shared.data.repositories

import com.jaarvi.shared.domain.models.Trip
import com.jaarvi.shared.domain.models.TripDestination
import com.jaarvi.shared.domain.repositories.ITripRepository
import com.jaarvi.shared.network.api.JaarviApiClient
import com.jaarvi.shared.network.dto.AddDestinationRequest
import com.jaarvi.shared.network.dto.CreateTripRequest
import com.jaarvi.shared.network.dto.SavePlanningContextRequest
import com.jaarvi.shared.network.dto.toDomain
import kotlinx.datetime.LocalDate

/**
 * Ktor-backed implementation of [ITripRepository].
 *
 * All network calls are wrapped in [runCatching] so failures surface as
 * [Result.failure] rather than thrown exceptions.
 *
 * @property apiClient Jaarvi HTTP API client.
 */
class TripRepositoryImpl(
    private val apiClient: JaarviApiClient,
) : ITripRepository {

    override suspend fun createTrip(
        name     : String?,
        startDate: LocalDate,
        endDate  : LocalDate,
    ): Result<Trip> = runCatching {
        apiClient.createTrip(
            CreateTripRequest(
                name      = name,
                startDate = startDate.toString(),
                endDate   = endDate.toString(),
            )
        ).data.toDomain()
    }

    override suspend fun addDestination(
        tripId   : String,
        cityId   : String,
        dayOrder : Int,
        daysCount: Int,
    ): Result<TripDestination> = runCatching {
        apiClient.addDestination(
            tripId = tripId,
            body   = AddDestinationRequest(
                cityId    = cityId,
                dayOrder  = dayOrder,
                daysCount = daysCount,
            )
        ).data.toDomain()
    }

    override suspend fun savePlanningContext(
        tripId              : String,
        travelStyle         : String?,
        budget              : String?,
        pace                : String?,
        interests           : List<String>,
        specialRequirements : String?,
    ): Result<Unit> = runCatching {
        apiClient.savePlanningContext(
            tripId = tripId,
            body   = SavePlanningContextRequest(
                travelStyle         = travelStyle,
                budget              = budget,
                pace                = pace,
                interests           = interests,
                specialRequirements = specialRequirements,
            )
        )
        Unit
    }

    override suspend fun getTripById(tripId: String): Result<Trip> = runCatching {
        apiClient.getTripById(tripId).data.toDomain()
    }
}
