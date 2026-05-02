package com.jaarvi.shared.network.dto

import com.jaarvi.shared.domain.models.Country
import kotlinx.serialization.Serializable

/** Network DTO for a country in the destination catalog. */
@Serializable
data class CountryDto(
    val id     : String,
    val isoCode: String,
    val name   : String,
)

/** Maps this DTO to the domain [Country]. */
fun CountryDto.toDomain() = Country(
    id      = id,
    isoCode = isoCode,
    name    = name,
)
