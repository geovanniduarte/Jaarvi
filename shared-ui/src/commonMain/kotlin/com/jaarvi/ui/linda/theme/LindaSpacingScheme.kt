package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// LindaSpacingScheme — spacing scale tokens (padding, gaps, margins).
// ─────────────────────────────────────────────────────────────

@Immutable
data class LindaSpacingScheme(
    /** 4 dp */
    val xs: Dp,
    /** 8 dp */
    val sm: Dp,
    /** 12 dp */
    val md: Dp,
    /** 16 dp */
    val lg: Dp,
    /** 20 dp */
    val xl: Dp,
    /** 24 dp */
    val xxl: Dp,
    /** 32 dp */
    val xxxl: Dp,
    /** 48 dp */
    val xxxxl: Dp,
)

// ── Composition Local ─────────────────────────────────────────
val LocalLindaSpacingScheme = compositionLocalOf<LindaSpacingScheme> {
    LindaDefaultSpacingScheme
}

// ── Defaults ─────────────────────────────────────────────────
val LindaDefaultSpacingScheme = LindaSpacingScheme(
    xs    = 4.dp,
    sm    = 8.dp,
    md    = 12.dp,
    lg    = 16.dp,
    xl    = 20.dp,
    xxl   = 24.dp,
    xxxl  = 32.dp,
    xxxxl = 48.dp,
)
