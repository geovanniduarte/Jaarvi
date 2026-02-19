package com.jaarvi.shared.di

import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Initializes Koin for iOS platform.
 * This function is called from Swift to set up dependency injection.
 *
 * @return KoinApplication instance
 */
fun initKoinIOS() = startKoin {
    modules(
        commonModule,
        iosModule
    )
}

/**
 * iOS-specific Koin module.
 * Registers platform capability implementations for iOS.
 */
val iosModule = module {
    // Platform capabilities will be registered from Swift
    // This is a placeholder for future iOS-specific dependencies
}
