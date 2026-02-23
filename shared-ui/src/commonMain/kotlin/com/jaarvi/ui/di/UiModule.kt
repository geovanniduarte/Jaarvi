package com.jaarvi.ui.di

import com.jaarvi.shared.domain.repositories.HealthRepository
import com.jaarvi.ui.screens.health.HealthPresenter
import org.koin.dsl.module

/**
 * Koin module for shared UI dependencies.
 * Registers presenters (ScreenModels) and UI-layer dependencies.
 */
val uiModule = module {
    // Presenters
    factory { HealthPresenter(repository = get<HealthRepository>()) }
}
