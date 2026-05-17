package com.jaarvi.shared.network

import com.jaarvi.shared.BuildConfig

/**
 * Android implementation of [ApiConfig].
 *
 * Base URL comes from `:shared` [BuildConfig.API_BASE_URL] (see `shared/build.gradle.kts`).
 *
 * - **Physical device** on the same Wi‑Fi as the server: use the Mac’s LAN IP, e.g.
 *   `http://192.168.80.86:30080/api` (port **30080** from `kubectl port-forward`, not 3000).
 * - **Emulator on the same Mac** as the server: use `http://10.0.2.2:30080/api` instead.
 */
actual object ApiConfig {
    actual val baseUrl: String = BuildConfig.API_BASE_URL
}
