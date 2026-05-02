package com.jaarvi.shared.network.dto

import kotlinx.serialization.Serializable

/** Request body for `POST /api/trips`. */
@Serializable
data class CreateTripRequest(
    val name     : String?,
    val startDate: String,
    val endDate  : String,
)

/** Request body for `POST /api/trips/{id}/destinations`. */
@Serializable
data class AddDestinationRequest(
    val cityId   : String,
    val dayOrder : Int,
    val daysCount: Int,
)

/** Request body for `POST /api/trips/{id}/planning-context`. */
@Serializable
data class SavePlanningContextRequest(
    val travelStyle        : String?,
    val budget             : String?,
    val pace               : String?,
    val interests          : List<String>,
    val specialRequirements: String?,
)
