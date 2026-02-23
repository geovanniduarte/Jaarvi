package com.jaarvi.shared.data.repositories

import com.jaarvi.shared.data.datasources.HealthDataSource
import com.jaarvi.shared.data.mappers.toDomain
import com.jaarvi.shared.network.dto.HealthResponseDto
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Unit tests for HealthRepositoryImpl.
 * Tests verify success, failure, and mapping scenarios.
 */
class HealthRepositoryTest {

    @Test
    fun `getHealth returns success when remote data source succeeds`() = runTest {
        // Arrange
        val mockDto = HealthResponseDto(
            message = "Backend is healthy",
            timestamp = "2024-01-01T12:00:00Z",
            status = "ok"
        )
        val mockDataSource = MockRemoteDataSource(successResponse = mockDto)
        val repository = HealthRepositoryImpl(mockDataSource)

        // Act
        val result = repository.getHealth()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals("Backend is healthy", result.getOrNull()?.message)
        assertEquals("2024-01-01T12:00:00Z", result.getOrNull()?.timestamp)
    }

    @Test
    fun `getHealth returns failure when remote data source fails`() = runTest {
        // Arrange
        val mockDataSource = MockRemoteDataSource(shouldFail = true)
        val repository = HealthRepositoryImpl(mockDataSource)

        // Act
        val result = repository.getHealth()

        // Assert
        assertTrue(result.isFailure)
    }

    @Test
    fun `DTO to domain mapping is correct`() {
        // Arrange
        val dto = HealthResponseDto(
            message = "Test message",
            timestamp = "2024-12-31T23:59:59Z",
            status = "test"
        )

        // Act
        val domain = dto.toDomain()

        // Assert
        assertEquals("Test message", domain.message)
        assertEquals("2024-12-31T23:59:59Z", domain.timestamp)
    }
}

/**
 * Mock implementation of HealthDataSource for testing.
 */
private class MockRemoteDataSource(
    private val successResponse: HealthResponseDto? = null,
    private val shouldFail: Boolean = false
) : HealthDataSource {
    override suspend fun getHealth(): HealthResponseDto {
        if (shouldFail) {
            throw Exception("Network error")
        }
        return successResponse ?: HealthResponseDto("OK", "2024-01-01T00:00:00Z", "ok")
    }
}
