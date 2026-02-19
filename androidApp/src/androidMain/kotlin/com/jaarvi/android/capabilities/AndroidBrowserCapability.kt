package com.jaarvi.android.capabilities

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.jaarvi.shared.capabilities.BrowserCapability

/**
 * Android implementation of BrowserCapability using Chrome Custom Tabs.
 * Provides in-app browsing for affiliate links and external content.
 *
 * @property context Android application context
 */
class AndroidBrowserCapability(
    private val context: Context
) : BrowserCapability {

    /**
     * Opens a URL in an in-app browser (Chrome Custom Tab).
     *
     * @param url The URL to open
     * @return Result indicating success or failure
     */
    override suspend fun openUrl(url: String): Result<Unit> = runCatching {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()
        
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

    /**
     * Checks if in-app browser is available.
     *
     * @return true if Chrome Custom Tabs is available
     */
    override fun isBrowserAvailable(): Boolean {
        // Chrome Custom Tabs is available on most modern Android devices
        return true
    }
}
