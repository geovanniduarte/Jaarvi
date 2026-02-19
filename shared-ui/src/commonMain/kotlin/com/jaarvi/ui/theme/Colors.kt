package com.jaarvi.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Jaarvi color palette.
 *
 * V1: Light theme only with primary brand colors.
 * V2 (future): Dark theme support.
 */
object JaarviColors {
    // Primary brand colors
    val Primary = Color(0xFF1976D2)  // Deep blue - trust, travel, sky
    val PrimaryVariant = Color(0xFF1565C0)  // Darker blue
    val Secondary = Color(0xFFFF6F00)  // Warm orange - adventure, energy
    val SecondaryVariant = Color(0xFFE65100)  // Darker orange
    
    // Backgrounds and surfaces
    val Background = Color(0xFFFAFAFA)  // Very light gray
    val Surface = Color(0xFFFFFFFF)  // White
    
    // Text colors
    val OnPrimary = Color(0xFFFFFFFF)  // White text on primary
    val OnSecondary = Color(0xFFFFFFFF)  // White text on secondary
    val OnBackground = Color(0xFF212121)  // Dark gray text on background
    val OnSurface = Color(0xFF212121)  // Dark gray text on surface
    
    // Status colors
    val Success = Color(0xFF4CAF50)  // Green
    val Error = Color(0xFFE53935)  // Red
    val Warning = Color(0xFFFFA726)  // Amber
    
    // Neutral grays
    val Gray50 = Color(0xFFFAFAFA)
    val Gray100 = Color(0xFFF5F5F5)
    val Gray200 = Color(0xFFEEEEEE)
    val Gray300 = Color(0xFFE0E0E0)
    val Gray400 = Color(0xFFBDBDBD)
    val Gray500 = Color(0xFF9E9E9E)
    val Gray600 = Color(0xFF757575)
    val Gray700 = Color(0xFF616161)
    val Gray800 = Color(0xFF424242)
    val Gray900 = Color(0xFF212121)
}
