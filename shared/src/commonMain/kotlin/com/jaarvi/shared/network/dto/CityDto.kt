package com.jaarvi.shared.network.dto

import com.jaarvi.shared.domain.models.City
import kotlinx.serialization.Serializable

/** Network DTO for a city in the destination catalog. */
@Serializable
data class CityDto(
    val id       : String,
    val countryId: String,
    val name     : String,
    val timezone : String,
    val country  : CountryDto,
)

/** Maps this DTO to the domain [City] with an embedded [com.jaarvi.shared.domain.models.Country]. */
fun CityDto.toDomain() = City(
    id        = id,
    countryId = countryId,
    name      = name,
    timezone  = timezone,
    country   = country.toDomain(),
)
