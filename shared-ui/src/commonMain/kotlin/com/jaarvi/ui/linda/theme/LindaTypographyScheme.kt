package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Plus Jakarta Sans — use Default until fonts are loaded from compose resources.
val PlusJakartaSans = FontFamily.Default

// ─────────────────────────────────────────────────────────────
// LindaTypographyScheme — all text style tokens.
//
// Primary hierarchy (design system names):
//   H1 → 24 sp / Bold
//   H2 → 20 sp / Bold
//   H3 → 18 sp / Bold
//   Body → 16 sp / Regular
//   Small → 14 sp / Regular or Medium
//   Micro → 12 sp / Regular
//   Label → 10 sp / Bold / UPPERCASE / wider tracking
//
// Extended aliases match the old Material3-style names used across
// existing components so migrations can be done incrementally.
// ─────────────────────────────────────────────────────────────

@Immutable
data class LindaTypographyScheme(
    // ── Primary design-system hierarchy ──────────────────────

    /** H1 — 24 sp / ExtraBold / ls −0.5 */
    val h1: TextStyle,
    /** H2 — 20 sp / Bold */
    val h2: TextStyle,
    /** H3 — 18 sp / Bold / ls −0.45 */
    val h3: TextStyle,
    /** Body — 16 sp / Regular */
    val body: TextStyle,
    /** Body Medium — 14 sp / Medium */
    val bodyMedium: TextStyle,
    /** Small — 14 sp / Regular */
    val small: TextStyle,
    /** Micro — 12 sp / Regular */
    val micro: TextStyle,
    /** Label — 10 sp / Bold / UPPERCASE / ls +1 */
    val label: TextStyle,

    // ── Extended / alias set (Material3-like names) ───────────

    /** 32 sp / Bold */
    val displayLarge: TextStyle,
    /** 24 sp / Bold  (= h1) */
    val displayMedium: TextStyle,
    /** 20 sp / Bold  (= h2) */
    val displaySmall: TextStyle,
    /** 18 sp / Bold  (= h3) */
    val headlineLarge: TextStyle,
    /** 16 sp / Bold */
    val headlineMedium: TextStyle,
    /** 16 sp / Regular (= body) */
    val bodyLarge: TextStyle,
    /** 12 sp / Regular (= micro) */
    val bodySmall: TextStyle,
    /** 14 sp / Bold / UPPERCASE */
    val labelLarge: TextStyle,
    /** 12 sp / Bold / UPPERCASE / ls +1 */
    val labelMedium: TextStyle,
    /** 10 sp / Bold / UPPERCASE / ls +1 (= label) */
    val labelSmall: TextStyle,
)

// ── Composition Local ─────────────────────────────────────────
val LocalLindaTypographyScheme = compositionLocalOf<LindaTypographyScheme> {
    LindaDefaultTypographyScheme
}

// ── Helper: base style ────────────────────────────────────────
private fun base(
    size: Float,
    weight: FontWeight = FontWeight.Normal,
    lineHeightFactor: Float = 1.4f,
    letterSpacingSp: Float = 0f,
) = TextStyle(
    fontFamily    = PlusJakartaSans,
    fontWeight    = weight,
    fontSize      = size.sp,
    lineHeight    = (size * lineHeightFactor).sp,
    letterSpacing = letterSpacingSp.sp,
)

// ── Defaults ─────────────────────────────────────────────────
val LindaDefaultTypographyScheme = LindaTypographyScheme(
    // Primary hierarchy
    h1           = base(24f, FontWeight.ExtraBold, 1.33f, -0.5f),
    h2           = base(20f, FontWeight.Bold,      1.40f),
    h3           = base(18f, FontWeight.Bold,      1.56f, -0.45f),
    body         = base(16f, FontWeight.Normal,    1.50f),
    bodyMedium   = base(14f, FontWeight.Medium,    1.43f),
    small        = base(14f, FontWeight.Normal,    1.43f),
    micro        = base(12f, FontWeight.Normal,    1.33f),
    label        = base(10f, FontWeight.Bold,      1.50f, 1f),

    // Extended aliases
    displayLarge  = base(32f, FontWeight.Bold,     1.25f, -0.5f),
    displayMedium = base(24f, FontWeight.Bold,     1.33f),
    displaySmall  = base(20f, FontWeight.Bold,     1.40f),
    headlineLarge  = base(18f, FontWeight.Bold,    1.56f, -0.45f),
    headlineMedium = base(16f, FontWeight.Bold,    1.50f),
    bodyLarge     = base(16f, FontWeight.Normal,   1.50f),
    bodySmall     = base(12f, FontWeight.Normal,   1.33f),
    labelLarge    = base(14f, FontWeight.Bold,     1.43f),
    labelMedium   = base(12f, FontWeight.Bold,     1.33f, 1f),
    labelSmall    = base(10f, FontWeight.Bold,     1.50f, 1f),
)
