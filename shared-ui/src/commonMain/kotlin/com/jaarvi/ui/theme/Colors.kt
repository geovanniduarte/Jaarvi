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


    // Primary Colors
    val ColorBgPrimary = Color(0xFF0A0C0A)
    val ColorBgGlass = Color(0x1AFFFFFF)
    val ColorBgGlassLight = Color(0x0DFFFFFF)

    // Accent Colors
    val ColorAccentGold = Color(0xFFF2B90D)
    val ColorAccentLime = Color(0xFFA3E635)

    // Text Colors
    val ColorTextPrimary = Color(0xFFF1F5F9)
    val ColorTextSecondary = Color(0xFFE2E8F0)
    val ColorTextMuted = Color(0xFF94A3B8)
    val ColorTextTertiary = Color(0xFF64748B)
    val ColorTextLabel = Color(0xFFCBD5E1)

    // Border Colors
    val ColorBorderGlass = Color(0x33FFFFFF)
    val ColorBorderGlassStrong = Color(0x4DFFFFFF)

    // Overlay Colors
    val ColorOverlayWarmStart = Color(0x66F2B90D)
    val ColorOverlayWarmEnd = Color(0x1AF2B90D)
    val ColorOverlayDarkStart = Color(0xCC000000)
    val ColorOverlayDarkEnd = Color(0x00000000)

    // Background Glow Colors
    val ColorGlowGold = Color(0x66F2B90D)
    val ColorGlowLime = Color(0x66A3E635)
    val ColorGlowEmerald = Color(0x66064E3B)
    val ColorGlowGoldLight = Color(0x66F2B90D)
    val ColorGlowEmeraldLight = Color(0x4D063F2E)
}

// Top-level aliases for design system compatibility (used by components with import com.jaarvi.ui.theme.*)
val ColorBgPrimary = JaarviColors.ColorBgPrimary
val ColorBgGlass = JaarviColors.ColorBgGlass
val ColorBgGlassLight = JaarviColors.ColorBgGlassLight
val ColorAccentGold = JaarviColors.ColorAccentGold
val ColorAccentLime = JaarviColors.ColorAccentLime
val ColorTextPrimary = JaarviColors.ColorTextPrimary
val ColorTextLabel = JaarviColors.ColorTextLabel
val ColorTextMuted = JaarviColors.ColorTextMuted
val ColorBorderGlass = JaarviColors.ColorBorderGlass
val ColorBorderGlassStrong = JaarviColors.ColorBorderGlassStrong
val ColorOverlayWarmStart = JaarviColors.ColorOverlayWarmStart
val ColorOverlayWarmEnd = JaarviColors.ColorOverlayWarmEnd
val ColorOverlayDarkStart = JaarviColors.ColorOverlayDarkStart
val ColorOverlayDarkEnd = JaarviColors.ColorOverlayDarkEnd
val ColorGlowGold = JaarviColors.ColorGlowGold
val ColorGlowLime = JaarviColors.ColorGlowLime
val ColorGlowEmerald = JaarviColors.ColorGlowEmerald
val ColorGlowGoldLight = JaarviColors.ColorGlowGoldLight
val ColorGlowEmeraldLight = JaarviColors.ColorGlowEmeraldLight
val ColorTextSecondary = JaarviColors.ColorTextSecondary
val ColorTextTertiary = JaarviColors.ColorTextTertiary
