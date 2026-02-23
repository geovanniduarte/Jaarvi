package com.jaarvi.shared.data.repositories

import com.jaarvi.shared.data.datasources.HealthDataSource
import com.jaarvi.shared.data.mappers.toDomain
import com.jaarvi.shared.domain.models.HealthStatus
import com.jaarvi.shared.domain.repositories.HealthRepository

/**
 * Implementation of HealthRepository.
 *
 * Fetches health data from remote data source and maps it to domain models.
 *
 * @property remoteDataSource Remote data source for API calls
 */
class HealthRepositoryImpl(
    private val remoteDataSource: HealthDataSource
) : HealthRepository {
    /**
     * Fetches the health status from the backend API.
     *
     * Wraps the network call in a Result to handle success and failure cases.
     *
     * @return Result containing HealthStatus on success or exception on failure
     */
    override suspend fun getHealth(): Result<HealthStatus> {
        return runCatching {
            remoteDataSource.getHealth().toDomain()
        }
    }
}
