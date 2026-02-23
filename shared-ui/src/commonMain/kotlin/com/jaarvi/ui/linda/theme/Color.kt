package com.jaarvi.ui.linda.theme

import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────
// Raw color palette — internal use only.
//
// These constants are the single source of truth for hex values.
// They are consumed by LindaColorScheme's default instances and
// should NOT be referenced directly from UI components; use
// LindaTheme.colors.* instead so that the theme can be swapped.
// ─────────────────────────────────────────────────────────────

// Backgrounds
internal val ColorBgPrimary       = Color(0xFF0A0C0A)
internal val ColorBgGlass         = Color(0x1AFFFFFF)   // 10% white
internal val ColorBgGlassLight    = Color(0x0DFFFFFF)   //  5% white

// Accents
internal val ColorAccentGold      = Color(0xFFF2B90D)
internal val ColorAccentLime      = Color(0xFFA3E635)

// Text
internal val ColorTextPrimary     = Color(0xFFF1F5F9)
internal val ColorTextSecondary   = Color(0xFFE2E8F0)
internal val ColorTextMuted       = Color(0xFF94A3B8)
internal val ColorTextTertiary    = Color(0xFF64748B)
internal val ColorTextLabel       = Color(0xFFCBD5E1)

// Borders
internal val ColorBorderGlass       = Color(0x33FFFFFF)  // 20% white
internal val ColorBorderGlassStrong = Color(0x4DFFFFFF)  // 30% white

// Overlays
internal val ColorOverlayWarmStart  = Color(0x66F2B90D)
internal val ColorOverlayWarmEnd    = Color(0x1AF2B90D)
internal val ColorOverlayDarkStart  = Color(0xCC000000)
internal val ColorOverlayDarkEnd    = Color(0x00000000)

// Glows
internal val ColorGlowGold          = Color(0x66F2B90D)
internal val ColorGlowLime          = Color(0x66A3E635)
internal val ColorGlowEmerald       = Color(0x66064E3B)
internal val ColorGlowGoldLight     = Color(0x66F2B90D)
internal val ColorGlowEmeraldLight  = Color(0x4D063F2E)
