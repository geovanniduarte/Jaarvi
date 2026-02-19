package com.jaarvi.shared.network

import io.ktor.client.*

/**
 * Factory for creating platform-specific HTTP clients.
 *
 * Uses expect/actual pattern to provide the appropriate Ktor engine for each platform:
 * - Android: OkHttp engine
 * - iOS: Darwin engine
 */
expect object HttpClientFactory {
    /**
     * Creates a configured HttpClient instance.
     *
     * The client includes:
     * - JSON content negotiation with kotlinx.serialization
     * - Logging for debug builds
     * - Platform-specific engine
     *
     * @return Configured HttpClient
     */
    fun create(): HttpClient
}
