package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// LindaGlassScheme — glassmorphism effect tokens.
// Blur values document design intent. Use Modifier.blur() or
// RenderEffect (API 31+) where the platform supports it.
// ─────────────────────────────────────────────────────────────

@Immutable
data class LindaGlassScheme(

    // ── Blur radii ────────────────────────────────────────────
    val blurLight  : Dp,   //  6 dp — cards, nav items
    val blurHeavy  : Dp,   // 12 dp — modals, sheets
    val blurAmbient: Dp,   // 40 dp — ambient glow orbs

    // ── Background opacity (0f–1f) ────────────────────────────
    val bgOpacityHeavy: Float,   // 0.10 — surfaceGlass
    val bgOpacityLight: Float,   // 0.05 — surfaceGlassLight

    // ── Border opacity (0f–1f) ────────────────────────────────
    val borderOpacity      : Float,   // 0.20 — borderGlass
    val borderOpacityStrong: Float,   // 0.30 — borderGlassStrong
)

// ── Composition Local ─────────────────────────────────────────
val LocalLindaGlassScheme = compositionLocalOf<LindaGlassScheme> { LindaDefaultGlassScheme }

// ── Defaults ─────────────────────────────────────────────────
val LindaDefaultGlassScheme = LindaGlassScheme(
    blurLight           = 6.dp,
    blurHeavy           = 12.dp,
    blurAmbient         = 40.dp,
    bgOpacityHeavy      = 0.10f,
    bgOpacityLight      = 0.05f,
    borderOpacity       = 0.20f,
    borderOpacityStrong = 0.30f,
)
