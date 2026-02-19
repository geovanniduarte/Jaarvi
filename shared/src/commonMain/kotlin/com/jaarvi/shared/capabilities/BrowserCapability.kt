package com.jaarvi.shared.capabilities

/**
 * Platform capability for in-app web browsing.
 *
 * Used primarily for affiliate links (flight bookings, hotel reservations, etc.)
 * to keep users within the app while they complete purchases.
 *
 * On Android: Chrome Custom Tabs
 * On iOS: SFSafariViewController
 */
interface BrowserCapability {
    /**
     * Opens a URL in an in-app browser.
     *
     * The browser provides:
     * - Back button to return to app
     * - Share/copy URL functionality
     * - Security indicators (HTTPS padlock, etc.)
     *
     * @param url URL to open (must be valid HTTP/HTTPS)
     * @return Result wrapping Unit on success or exception on failure
     */
    suspend fun openUrl(url: String): Result<Unit>
    
    /**
     * Checks if in-app browser is available on the device.
     *
     * @return True if in-app browser can be used, false otherwise
     */
    fun isBrowserAvailable(): Boolean
}
