package com.jaarvi.shared.network

/**
 * Configuration for API endpoints.
 *
 * Uses expect/actual pattern to provide different base URLs for each platform and build type:
 * - Android Debug: http://192.168.1.83:3000/api (emulator localhost alias)
 * - iOS Debug: http://localhost:3000/api (simulator shares host network)
 * - Release: https://api.jaarvi.app
 */
expect object ApiConfig {
    /**
     * Base URL for the Jaarvi backend API.
     */
    val baseUrl: String
}
