package com.jaarvi.shared.network

/**
 * iOS implementation of ApiConfig.
 *
 * iOS simulator shares the host machine's network, so localhost works directly.
 */
actual object ApiConfig {
    actual val baseUrl: String = "http://localhost:3000/api"
}
