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
    background          = Color(0xFF0A0C0A),
    surfaceGlass        = Color(0x1AFFFFFF),   // rgba(255,255,255,0.10)
    surfaceGlassLight   = Color(0x0DFFFFFF),   // rgba(255,255,255,0.05)

    textPrimary         = Color(0xFFF1F5F9),
    textSecondary       = Color(0xFFE2E8F0),
    textMuted           = Color(0xFF94A3B8),
    textTertiary        = Color(0xFF64748B),
    textLabel           = Color(0xFFCBD5E1),

    accentGold          = Color(0xFFF2B90D),
    accentLime          = Color(0xFFA3E635),
    onAccent            = Color(0xFF0A0C0A),

    borderGlass         = Color(0x33FFFFFF),   // rgba(255,255,255,0.20)
    borderGlassStrong   = Color(0x4DFFFFFF),   // rgba(255,255,255,0.30)

    overlayWarmStart    = Color(0x66F2B90D),
    overlayWarmEnd      = Color(0x1AF2B90D),
    overlayDarkStart    = Color(0xCC000000),
    overlayDarkEnd      = Color(0x00000000),

    glowGold            = Color(0x66F2B90D),
    glowLime            = Color(0x66A3E635),
    glowEmerald         = Color(0x66064E3B),
    glowGoldLight       = Color(0x66F2B90D),
    glowEmeraldLight    = Color(0x4D063F2E),

    statusActive        = Color(0xFFA3E635),   // lime
    statusCompleted     = Color(0xFFF2B90D),   // gold
    statusPlanned       = Color(0x99FFFFFF),   // white 60%
)
