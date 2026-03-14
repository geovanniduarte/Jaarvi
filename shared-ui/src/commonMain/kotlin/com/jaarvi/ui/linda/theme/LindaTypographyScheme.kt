package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ─────────────────────────────────────────────────────────────
// Plus Jakarta Sans — place TTF files in res/font/:
//   plusjakartasans_regular.ttf   (weight 400)
//   plusjakartasans_medium.ttf    (weight 500)
//   plusjakartasans_semibold.ttf  (weight 600)
//   plusjakartasans_bold.ttf      (weight 700)
//   plusjakartasans_extrabold.ttf (weight 800)
//
// Falls back to FontFamily.Default until font resources are loaded.
// ─────────────────────────────────────────────────────────────
val LindaPlusJakartaSans = FontFamily.Default

// ─────────────────────────────────────────────────────────────
// LindaTypographyScheme — all text style tokens.
// ─────────────────────────────────────────────────────────────

@Immutable
data class LindaTypographyScheme(

    // ── Primary design-system hierarchy ──────────────────────
    val h1        : TextStyle,   // 24 sp / ExtraBold / ls −0.5
    val h2        : TextStyle,   // 20 sp / Bold
    val h3        : TextStyle,   // 18 sp / Bold / ls −0.45
    val body      : TextStyle,   // 16 sp / Regular
    val bodyMedium: TextStyle,   // 14 sp / Medium
    val small     : TextStyle,   // 14 sp / Regular
    val micro     : TextStyle,   // 12 sp / Regular
    val label     : TextStyle,   // 10 sp / Bold / UPPERCASE / ls +1

    // ── Extended / alias set (Material3-style names) ─────────
    val displayLarge  : TextStyle,   // 32 sp / Bold
    val displayMedium : TextStyle,   // 24 sp / Bold  (≡ h1)
    val displaySmall  : TextStyle,   // 20 sp / Bold  (≡ h2)
    val headlineLarge : TextStyle,   // 18 sp / Bold  (≡ h3)
    val headlineMedium: TextStyle,   // 16 sp / Bold
    val bodyLarge     : TextStyle,   // 16 sp / Regular (≡ body)
    val bodySmall     : TextStyle,   // 12 sp / Regular (≡ micro)
    val labelLarge    : TextStyle,   // 14 sp / Bold / UPPERCASE
    val labelMedium   : TextStyle,   // 12 sp / Bold / UPPERCASE / ls +1
    val labelSmall    : TextStyle,   // 10 sp / Bold / UPPERCASE / ls +1 (≡ label)
)

// ── Composition Local ─────────────────────────────────────────
val LocalLindaTypographyScheme = compositionLocalOf<LindaTypographyScheme> { LindaDefaultTypographyScheme }

// ── Helper ────────────────────────────────────────────────────
private fun base(
    size             : Float,
    weight           : FontWeight = FontWeight.Normal,
    lineHeightFactor : Float      = 1.4f,
    letterSpacingSp  : Float      = 0f,
) = TextStyle(
    fontFamily    = LindaPlusJakartaSans,
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
    displayLarge   = base(32f, FontWeight.Bold,     1.25f, -0.5f),
    displayMedium  = base(24f, FontWeight.Bold,     1.33f),
    displaySmall   = base(20f, FontWeight.Bold,     1.40f),
    headlineLarge  = base(18f, FontWeight.Bold,     1.56f, -0.45f),
    headlineMedium = base(16f, FontWeight.Bold,     1.50f),
    bodyLarge      = base(16f, FontWeight.Normal,   1.50f),
    bodySmall      = base(12f, FontWeight.Normal,   1.33f),
    labelLarge     = base(14f, FontWeight.Bold,     1.43f),
    labelMedium    = base(12f, FontWeight.Bold,     1.33f, 1f),
    labelSmall     = base(10f, FontWeight.Bold,     1.50f, 1f),
)
