package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// LindaShadowScheme — elevation and glow / shadow tokens.
// ─────────────────────────────────────────────────────────────

@Immutable
data class LindaShadowScheme(

    // ── Elevation steps ───────────────────────────────────────
    val none: Dp,   //  0 dp
    val sm  : Dp,   //  2 dp
    val md  : Dp,   //  4 dp — progress fill, small elements
    val lg  : Dp,   //  8 dp — glow halos
    val xl  : Dp,   // 16 dp — cards, headers
    val xxl : Dp,   // 32 dp — design spec: 0px 8px 32px rgba

    // ── Card shadow ───────────────────────────────────────────
    val cardElevation  : Dp,     // 16 dp (≡ xl)
    val cardShadowAlpha: Float,  // 0.37f

    // ── Glow elevation ────────────────────────────────────────
    /** Elevation used to simulate coloured glow halos on components. */
    val glowElevation   : Dp,   //  1 dp
    /** Elevation for the FAB gold glow. */
    val fabGlowElevation: Dp,   // 12 dp
)

// ── Composition Local ─────────────────────────────────────────
val LocalLindaShadowScheme = compositionLocalOf<LindaShadowScheme> { LindaDefaultShadowScheme }

// ── Defaults ─────────────────────────────────────────────────
val LindaDefaultShadowScheme = LindaShadowScheme(
    none              = 0.dp,
    sm                = 2.dp,
    md                = 4.dp,
    lg                = 8.dp,
    xl                = 16.dp,
    xxl               = 32.dp,
    cardElevation     = 16.dp,
    cardShadowAlpha   = 0.37f,
    glowElevation     = 2.dp,
    fabGlowElevation  = 12.dp,
)
