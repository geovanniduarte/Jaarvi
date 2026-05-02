package com.jaarvi.shared.domain.models

/**
 * Domain model representing a city in the destination catalog.
 *
 * @param id        Server-assigned UUID.
 * @param countryId UUID of the parent [Country].
 * @param name      Display name of the city (e.g. "Paris").
 * @param timezone  IANA timezone identifier (e.g. "Europe/Paris").
 * @param country   Embedded parent country — callers never need a separate join.
 */
data class City(
    val id       : String,
    val countryId: String,
    val name     : String,
    val timezone : String,
    val country  : Country,
)
