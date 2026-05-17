package com.jaarvi.shared.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

/**
 * iOS implementation of HttpClientFactory.
 *
 * Uses Darwin engine for iOS platform.
 */
actual object HttpClientFactory {
    actual fun create(): HttpClient {
        return HttpClient(Darwin) {
            applyJaarviDefaults()
        }
    }
}
