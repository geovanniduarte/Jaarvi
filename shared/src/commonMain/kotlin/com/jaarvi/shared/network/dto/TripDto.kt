package com.jaarvi.shared.network.dto

import com.jaarvi.shared.domain.models.Trip
import com.jaarvi.shared.domain.models.TripStatus
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * Network DTO for a trip returned by the backend.
 *
 * Date fields arrive as ISO-8601 strings; [toDomain] parses them to typed values.
 */
@Serializable
data class TripDto(
    val id       : String,
    val ownerId  : String,
    val name     : String?  = null,
    val startDate: String,
    val endDate  : String,
    val status   : String,
    val createdAt: String,
)

/**
 * Maps this DTO to the domain [Trip].
 *
 * Parses ISO-8601 date strings using the first 10 characters ("YYYY-MM-DD") for [LocalDate]
 * and full string for [Instant].
 */
fun TripDto.toDomain() = Trip(
    id        = id,
    ownerId   = ownerId,
    name      = name,
    startDate = LocalDate.parse(startDate.take(10)),
    endDate   = LocalDate.parse(endDate.take(10)),
    status    = TripStatus.valueOf(status.uppercase()),
    createdAt = Instant.parse(createdAt),
)

/** Generic API envelope returned by all Jaarvi backend endpoints. */
@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data   : T,
)
