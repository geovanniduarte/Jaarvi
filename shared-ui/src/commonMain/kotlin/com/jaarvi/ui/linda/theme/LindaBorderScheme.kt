package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// LindaBorderScheme — corner radius and stroke width tokens.
// ─────────────────────────────────────────────────────────────

@Immutable
data class LindaBorderScheme(
    // ── Corner Radii ─────────────────────────────────────────
    /** 8 dp — small inputs, chips */
    val radiusSm: Dp,
    /** 12 dp — buttons, badges */
    val radiusMd: Dp,
    /** 16 dp — cards, sheets */
    val radiusLg: Dp,
    /** 24 dp — bottom sheets, large panels */
    val radiusXl: Dp,
    /** 9999 dp — fully-circular / pill shapes */
    val radiusFull: Dp,

    // ── Stroke Widths ────────────────────────────────────────
    /** 1 dp — standard thin border */
    val widthThin: Dp,
    /** 2 dp — emphasis border */
    val widthMedium: Dp,
    /** 4 dp — strong border (e.g. FAB inner ring) */
    val widthThick: Dp,
)

// ── Composition Local ─────────────────────────────────────────
val LocalLindaBorderScheme = compositionLocalOf<LindaBorderScheme> {
    LindaDefaultBorderScheme
}

// ── Defaults ─────────────────────────────────────────────────
val LindaDefaultBorderScheme = LindaBorderScheme(
    radiusSm    = 8.dp,
    radiusMd    = 12.dp,
    radiusLg    = 16.dp,
    radiusXl    = 24.dp,
    radiusFull  = 9999.dp,
    widthThin   = 1.dp,
    widthMedium = 2.dp,
    widthThick  = 4.dp,
)
