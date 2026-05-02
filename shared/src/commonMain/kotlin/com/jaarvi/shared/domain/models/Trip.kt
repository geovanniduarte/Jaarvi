package com.jaarvi.shared.domain.models

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

/**
 * Domain model representing a Jaarvi trip.
 *
 * @param id        Server-assigned UUID.
 * @param ownerId   UUID of the authenticated user who owns this trip.
 * @param name      Optional human-readable trip label (max 100 chars).
 * @param startDate First day of the trip.
 * @param endDate   Last day of the trip (always after [startDate]).
 * @param status    Lifecycle stage of the trip.
 * @param createdAt Server timestamp of creation.
 */
data class Trip(
    val id        : String,
    val ownerId   : String,
    val name      : String?,
    val startDate : LocalDate,
    val endDate   : LocalDate,
    val status    : TripStatus,
    val createdAt : Instant,
)

/** Lifecycle stage of a [Trip]. */
enum class TripStatus { DRAFT, ACTIVE, COMPLETED }
