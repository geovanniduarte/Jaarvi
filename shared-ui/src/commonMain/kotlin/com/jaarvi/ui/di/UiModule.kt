package com.jaarvi.ui.di

import com.jaarvi.shared.domain.repositories.HealthRepository
import com.jaarvi.shared.domain.repositories.ITripRepository
import com.jaarvi.shared.domain.usecases.CreateTripUseCase
import com.jaarvi.shared.domain.usecases.GetCitiesUseCase
import com.jaarvi.shared.domain.usecases.GetCountriesUseCase
import com.jaarvi.ui.screens.createtrip.CreateTripScreenModel
import com.jaarvi.ui.screens.health.HealthPresenter
import org.koin.dsl.module

/**
 * Koin module for shared UI dependencies.
 * Registers presenters (ScreenModels) and UI-layer dependencies.
 */
val uiModule = module {
    // Presenters
    factory { HealthPresenter(repository = get<HealthRepository>()) }

    // ScreenModels
    factory {
        CreateTripScreenModel(
            createTripUseCase   = get<CreateTripUseCase>(),
            getCountriesUseCase = get<GetCountriesUseCase>(),
            getCitiesUseCase    = get<GetCitiesUseCase>(),
            tripRepository      = get<ITripRepository>(),
        )
    }
}
