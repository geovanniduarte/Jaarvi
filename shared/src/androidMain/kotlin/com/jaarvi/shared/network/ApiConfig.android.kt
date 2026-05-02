package com.jaarvi.shared.network

/**
 * Android implementation of ApiConfig.
 *
 * Uses 10.0.2.2 for debug builds (emulator localhost alias).
 * This will be configured via BuildConfig in the Android app module.
 * For now, defaulting to debug localhost.
 */
actual object ApiConfig {
    actual val baseUrl: String = "http://localhost:8080/api"
}
