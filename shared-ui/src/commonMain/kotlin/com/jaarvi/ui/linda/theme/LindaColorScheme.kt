package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────
// LindaColorScheme — semantic color tokens for the Linda design system.
// Dark mode is the primary/default scheme. Supply LindaLightColorScheme
// and pass it to LindaTheme(colors = ...) to add light mode support
// without any structural changes.
//
// All raw values live in LindaColor.kt. Never use them in UI code.
// ─────────────────────────────────────────────────────────────

@Immutable
data class LindaColorScheme(

    // ── Backgrounds ──────────────────────────────────────────
    /** Primary app background. Dark: #0A0C0A */
    val background       : Color,
    /** Glassmorphic surface overlay (~10 % white). */
    val surfaceGlass     : Color,
    /** Lighter glassmorphic surface overlay (~5 % white). */
    val surfaceGlassLight: Color,

    // ── Text / Foreground ────────────────────────────────────
    /** Highest-contrast foreground. Dark: #F1F5F9 */
    val textPrimary  : Color,
    /** Secondary text. Dark: #E2E8F0 */
    val textSecondary: Color,
    /** Muted / de-emphasised text. Dark: #94A3B8 */
    val textMuted    : Color,
    /** Tertiary / placeholder text. Dark: #64748B */
    val textTertiary : Color,
    /** Label / caption text. Dark: #CBD5E1 */
    val textLabel    : Color,

    // ── Accents ───────────────────────────────────────────────
    /** Primary accent — gold. Dark: #F2B90D */
    val accentGold: Color,
    /** Secondary accent — lime. Dark: #A3E635 */
    val accentLime: Color,
    /** Foreground colour used ON top of accent fills (e.g. button label). */
    val onAccent  : Color,

    // ── Borders ──────────────────────────────────────────────
    /** Standard glass border — 20 % white. */
    val borderGlass      : Color,
    /** Stronger glass border — 30 % white. */
    val borderGlassStrong: Color,

    // ── Overlays (image gradients) ───────────────────────────
    /** Warm gradient start — gold 40 %. */
    val overlayWarmStart: Color,
    /** Warm gradient end — gold 10 %. */
    val overlayWarmEnd  : Color,
    /** Dark gradient start — black 80 %. */
    val overlayDarkStart: Color,
    /** Dark gradient end — transparent. */
    val overlayDarkEnd  : Color,

    // ── Glows ────────────────────────────────────────────────
    val glowGold        : Color,
    val glowLime        : Color,
    val glowEmerald     : Color,
    val glowGoldLight   : Color,
    val glowEmeraldLight: Color,

    // ── Status semantics ─────────────────────────────────────
    /** Lime — active / in-progress. */
    val statusActive   : Color,
    /** Gold — completed. */
    val statusCompleted: Color,
    /** White 60 % — planned / upcoming. */
    val statusPlanned  : Color,
    /** Red — validation error. */
    val statusError    : Color,
)

// ── Composition Local ─────────────────────────────────────────
val LocalLindaColorScheme = compositionLocalOf { LindaDarkColorScheme }

// ── Dark-mode defaults ────────────────────────────────────────
val LindaDarkColorScheme = LindaColorScheme(
    background          = ColorBgPrimary,
    surfaceGlass        = ColorBgGlass,
    surfaceGlassLight   = ColorBgGlassLight,

    textPrimary         = ColorTextPrimary,
    textSecondary       = ColorTextSecondary,
    textMuted           = ColorTextMuted,
    textTertiary        = ColorTextTertiary,
    textLabel           = ColorTextLabel,

    accentGold          = ColorAccentGold,
    accentLime          = ColorAccentLime,
    onAccent            = ColorBgPrimary,

    borderGlass         = ColorBorderGlass,
    borderGlassStrong   = ColorBorderGlassStrong,

    overlayWarmStart    = ColorOverlayWarmStart,
    overlayWarmEnd      = ColorOverlayWarmEnd,
    overlayDarkStart    = ColorOverlayDarkStart,
    overlayDarkEnd      = ColorOverlayDarkEnd,

    glowGold            = ColorGlowGold,
    glowLime            = ColorGlowLime,
    glowEmerald         = ColorGlowEmerald,
    glowGoldLight       = ColorGlowGoldLight,
    glowEmeraldLight    = ColorGlowEmeraldLight,

    statusActive        = ColorStatusActive,
    statusCompleted     = ColorStatusCompleted,
    statusPlanned       = ColorStatusPlanned,
    statusError         = ColorStatusError,
)
