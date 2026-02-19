package com.jaarvi.shared.data.mappers

import com.jaarvi.shared.domain.models.HealthStatus
import com.jaarvi.shared.network.dto.HealthResponseDto

/**
 * Maps HealthResponseDto (network layer) to HealthStatus (domain layer).
 *
 * @return Domain model HealthStatus
 */
fun HealthResponseDto.toDomain(): HealthStatus {
    return HealthStatus(
        message = message,
        timestamp = timestamp,
        status = status
    )
}
