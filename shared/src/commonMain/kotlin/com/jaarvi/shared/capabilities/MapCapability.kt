package com.jaarvi.shared.capabilities

/**
 * Platform capability for map and navigation operations.
 *
 * V1: External navigation via deep links to Google Maps (Android) or Apple Maps (iOS)
 * V2 (future): Embedded maps with custom UI
 *
 * This abstraction allows migrating from external to embedded maps
 * without changing the shared business logic.
 */
interface MapCapability {
    /**
     * Opens external navigation app with the given coordinates.
     *
     * On Android: Deep links to Google Maps with geo: URI scheme
     * On iOS: Opens Apple Maps with MKMapItem
     *
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @param label Optional location label to display in maps app
     * @return Result wrapping Unit on success or exception on failure
     */
    suspend fun openExternalNavigation(
        latitude: Double,
        longitude: Double,
        label: String? = null
    ): Result<Unit>
    
    /**
     * Checks if navigation app is available on the device.
     *
     * @return True if navigation can be launched, false otherwise
     */
    fun isNavigationAvailable(): Boolean
}
