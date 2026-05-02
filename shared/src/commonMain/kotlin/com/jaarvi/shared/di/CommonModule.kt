package com.jaarvi.shared.di

import com.jaarvi.shared.data.datasources.HealthDataSource
import com.jaarvi.shared.data.datasources.RemoteDataSource
import com.jaarvi.shared.data.repositories.DestinationRepositoryImpl
import com.jaarvi.shared.data.repositories.HealthRepositoryImpl
import com.jaarvi.shared.data.repositories.TripRepositoryImpl
import com.jaarvi.shared.domain.repositories.HealthRepository
import com.jaarvi.shared.domain.repositories.IDestinationRepository
import com.jaarvi.shared.domain.repositories.ITripRepository
import com.jaarvi.shared.domain.usecases.CreateTripUseCase
import com.jaarvi.shared.domain.usecases.GetCitiesUseCase
import com.jaarvi.shared.domain.usecases.GetCountriesUseCase
import com.jaarvi.shared.network.api.JaarviApiClient
import org.koin.dsl.module

/**
 * Koin DI module for shared dependencies.
 *
 * Provides:
 * - Network layer (API client, data sources)
 * - Repository implementations
 * - Domain use cases
 *
 * Platform-specific dependencies (capabilities) are provided in platform modules.
 */
val commonModule = module {
    // Network
    single { JaarviApiClient() }
    single<HealthDataSource> { RemoteDataSource(get()) }

    // Repositories
    single<HealthRepository> { HealthRepositoryImpl(get()) }
    single<ITripRepository> { TripRepositoryImpl(get()) }
    single<IDestinationRepository> { DestinationRepositoryImpl(get()) }

    // Use cases
    factory { CreateTripUseCase(get()) }
    factory { GetCountriesUseCase(get()) }
    factory { GetCitiesUseCase(get()) }
}
