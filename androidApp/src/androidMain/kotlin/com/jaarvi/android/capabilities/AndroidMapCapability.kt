package com.jaarvi.android.capabilities

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.jaarvi.shared.capabilities.MapCapability

/**
 * Android implementation of MapCapability using external navigation.
 * V1: Opens default maps app with geo: URI (Google Maps, etc.)
 * V2: Will use embedded Google Maps SDK
 *
 * @property context Android application context
 */
class AndroidMapCapability(
    private val context: Context
) : MapCapability {

    /**
     * Opens external navigation app with the specified location.
     *
     * @param lat Latitude of the destination
     * @param lng Longitude of the destination
     * @param label Optional label for the location
     * @return Result indicating success or failure
     */
    override suspend fun openExternalNavigation(
        latitude: Double,
        longitude: Double,
        label: String?
    ): Result<Unit> = runCatching {
        val uriString = if (label != null) {
            "geo:$latitude,$longitude?q=$latitude,$longitude($label)"
        } else {
            "geo:$latitude,$longitude?q=$latitude,$longitude"
        }
        
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            throw IllegalStateException("No maps application available")
        }
    }

    /**
     * Checks if navigation is available on the device.
     *
     * @return true if a maps app is installed, false otherwise
     */
    override fun isNavigationAvailable(): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0"))
        return intent.resolveActivity(context.packageManager) != null
    }
}
