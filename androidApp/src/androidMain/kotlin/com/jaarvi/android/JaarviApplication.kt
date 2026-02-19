package com.jaarvi.android

import android.app.Application
import com.jaarvi.android.di.androidModule
import com.jaarvi.shared.di.commonModule
import com.jaarvi.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Android Application class for Jaarvi.
 * Initializes Koin dependency injection with all modules.
 */
class JaarviApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@JaarviApplication)
            modules(
                commonModule,
                uiModule,
                androidModule
            )
        }
    }
}
