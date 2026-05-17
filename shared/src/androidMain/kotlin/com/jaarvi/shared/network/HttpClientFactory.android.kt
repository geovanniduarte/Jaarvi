package com.jaarvi.shared.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import java.util.concurrent.TimeUnit

/**
 * Android implementation of HttpClientFactory.
 *
 * Uses OkHttp engine for Android platform.
 */
actual object HttpClientFactory {
    actual fun create(): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                config {
                    connectTimeout(15, TimeUnit.SECONDS)
                    readTimeout(30, TimeUnit.SECONDS)
                    writeTimeout(30, TimeUnit.SECONDS)
                    callTimeout(30, TimeUnit.SECONDS)
                }
            }
            applyJaarviDefaults()
        }
    }
}
