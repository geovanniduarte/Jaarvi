package com.jaarvi.shared.domain.models

/**
 * Domain model representing a country in the destination catalog.
 *
 * @param id      Server-assigned UUID.
 * @param isoCode Two-letter ISO 3166-1 alpha-2 country code (e.g. "FR").
 * @param name    Full display name of the country (e.g. "France").
 */
data class Country(
    val id     : String,
    val isoCode: String,
    val name   : String,
)
