package com.jaarvi.shared.network.dto

import com.jaarvi.shared.domain.models.TripStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class TripDtoTest {

    @Test
    fun toDomain_parsesTypicalBackendTripJson() {
        val dto = TripDto(
            id        = "550e8400-e29b-41d4-a716-446655440000",
            ownerId   = "660e8400-e29b-41d4-a716-446655440001",
            name      = "Summer trip",
            startDate = "2026-06-01T00:00:00.000Z",
            endDate   = "2026-06-10T00:00:00.000Z",
            status    = "draft",
            createdAt = "2026-05-17T14:30:00.000Z",
        )

        val trip = dto.toDomain()

        assertEquals("550e8400-e29b-41d4-a716-446655440000", trip.id)
        assertEquals(TripStatus.DRAFT, trip.status)
        assertEquals(2026, trip.startDate.year)
        assertEquals(6, trip.startDate.monthNumber)
        assertEquals(1, trip.startDate.dayOfMonth)
    }
}
