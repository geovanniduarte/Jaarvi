package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────
// LindaColorScheme — semantic color tokens for the Linda design system.
// Dark mode is the primary/default scheme; add a LindaLightColorScheme
// instance later and pass it to LindaTheme(colors = lindaLightColors) to
// support light mode without any structural changes.
// ─────────────────────────────────────────────────────────────

@Immutable
data class LindaColorScheme(
    // ── Backgrounds ──────────────────────────────────────────
    /** Primary app background. Dark: #0A0C0A */
    val background: Color,
    /** Glassmorphic surface overlay (~10% white). */
    val surfaceGlass: Color,
    /** Lighter glassmorphic surface overlay (~5% white). */
    val surfaceGlassLight: Color,

    // ── Text / Foreground ─────────────────────────────────────
    /** Highest-contrast foreground. Dark: #F1F5F9 */
    val textPrimary: Color,
    /** Secondary text. Dark: #E2E8F0 */
    val textSecondary: Color,
    /** Muted / de-emphasised text. Dark: #94A3B8 */
    val textMuted: Color,
    /** Tertiary / placeholder text. Dark: #64748B */
    val textTertiary: Color,
    /** Label / caption text. Dark: #CBD5E1 */
    val textLabel: Color,

    // ── Accent ───────────────────────────────────────────────
    /** Primary accent — gold. Dark: #F2B90D */
    val accentGold: Color,
    /** Secondary accent — lime. Dark: #A3E635 */
    val accentLime: Color,
    /** Foreground colour used ON top of accent fills (e.g. button label). */
    val onAccent: Color,

    // ── Borders ──────────────────────────────────────────────
    /** Standard glass border (~20% white). */
    val borderGlass: Color,
    /** Stronger glass border (~30% white). */
    val borderGlassStrong: Color,

    // ── Overlays ─────────────────────────────────────────────
    /** Warm gradient start for image overlays (gold, 40% opacity). */
    val overlayWarmStart: Color,
    /** Warm gradient end for image overlays (gold, 10% opacity). */
    val overlayWarmEnd: Color,
    /** Dark gradient start for image overlays (black, 80% opacity). */
    val overlayDarkStart: Color,
    /** Dark gradient end for image overlays (transparent). */
    val overlayDarkEnd: Color,

    // ── Glows ────────────────────────────────────────────────
    /** Gold glow / shadow ambient colour. */
    val glowGold: Color,
    /** Lime glow / shadow ambient colour. */
    val glowLime: Color,
    /** Emerald deep-background glow. */
    val glowEmerald: Color,
    /** Light gold deep-background glow. */
    val glowGoldLight: Color,
    /** Light emerald deep-background glow. */
    val glowEmeraldLight: Color,

    // ── Status Semantics ─────────────────────────────────────
    /** Active / in-progress status colour. */
    val statusActive: Color,
    /** Completed status colour. */
    val statusCompleted: Color,
    /** Planned / upcoming status colour. */
    val statusPlanned: Color,
)

// ── Composition Local ─────────────────────────────────────────
val LocalLindaColorScheme = compositionLocalOf {
    LindaDarkColorScheme
}

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
)
