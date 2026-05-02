package com.jaarvi.shared.network.dto

import com.jaarvi.shared.domain.models.TripDestination
import kotlinx.serialization.Serializable

/** Network DTO for a trip destination with embedded city and country. */
@Serializable
data class TripDestinationDto(
    val id       : String,
    val tripId   : String,
    val cityId   : String,
    val city     : CityDto,
    val dayOrder : Int,
    val daysCount: Int,
    val notes    : String? = null,
)

/** Maps this DTO to the domain [TripDestination]. */
fun TripDestinationDto.toDomain() = TripDestination(
    id        = id,
    tripId    = tripId,
    cityId    = cityId,
    city      = city.toDomain(),
    dayOrder  = dayOrder,
    daysCount = daysCount,
    notes     = notes,
)
