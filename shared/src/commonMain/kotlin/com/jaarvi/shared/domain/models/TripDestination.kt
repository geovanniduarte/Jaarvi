package com.jaarvi.shared.domain.models

/**
 * Domain model representing a single destination within a [Trip].
 *
 * @param id        Server-assigned UUID.
 * @param tripId    UUID of the parent trip.
 * @param cityId    UUID of the [City].
 * @param city      Embedded city with nested country — no extra joins needed.
 * @param dayOrder  1-based position of this destination in the trip itinerary.
 * @param daysCount Number of days allocated to this destination.
 * @param notes     Optional free-text notes for this destination.
 */
data class TripDestination(
    val id       : String,
    val tripId   : String,
    val cityId   : String,
    val city     : City,
    val dayOrder : Int,
    val daysCount: Int,
    val notes    : String?,
)
