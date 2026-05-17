package com.jaarvi.shared.data.repositories

import com.jaarvi.shared.domain.models.Trip
import com.jaarvi.shared.domain.models.TripDestination
import com.jaarvi.shared.domain.repositories.ITripRepository
import com.jaarvi.shared.network.api.JaarviApiClient
import com.jaarvi.shared.network.dto.AddDestinationRequest
import com.jaarvi.shared.network.dto.CreateTripRequest
import com.jaarvi.shared.network.dto.SavePlanningContextRequest
import com.jaarvi.shared.network.bestMessage
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
        val response = apiClient.createTrip(
            CreateTripRequest(
                name      = name,
                startDate = startDate.toString(),
                endDate   = endDate.toString(),
            ),
        )
        if (!response.success || response.data == null) {
            error(response.error?.message ?: "Create trip failed (success=${response.success})")
        }
        response.data.toDomain()
    }.mapError { it.toApiFailure() }

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
        ).let { response ->
            if (!response.success || response.data == null) {
                error(response.error?.message ?: "Add destination failed")
            }
            response.data.toDomain()
        }
    }.mapError { it.toApiFailure() }

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
        val response = apiClient.getTripById(tripId)
        if (!response.success || response.data == null) {
            error(response.error?.message ?: "Trip not found")
        }
        response.data.toDomain()
    }.mapError { it.toApiFailure() }

    private fun Throwable.toApiFailure(): Throwable =
        if (message.isNullOrBlank()) Exception(bestMessage(), this) else this
}

private fun <T> Result<T>.mapError(transform: (Throwable) -> Throwable): Result<T> =
    fold(onSuccess = { Result.success(it) }, onFailure = { Result.failure(transform(it)) })
