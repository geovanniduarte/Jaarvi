package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// LindaGlassScheme — glassmorphism effect tokens.
// Note: Compose does not expose a true CSS-style backdrop-blur API
// on all targets; the blur values here document the design intent
// and are used where the platform supports it (e.g. RenderEffect
// on API 31+, or via Modifier.blur() for foreground blur).
// ─────────────────────────────────────────────────────────────

@Immutable
data class LindaGlassScheme(
    // ── Blur radii ────────────────────────────────────────────
    /** Light glass blur — 6 dp. Used on cards and nav items. */
    val blurLight: Dp,
    /** Heavy glass blur — 12 dp. Used on modals and sheets. */
    val blurHeavy: Dp,
    /** Background glow blur — 40 dp. Used on ambient orbs. */
    val blurAmbient: Dp,

    // ── Background opacity (0f–1f) ────────────────────────────
    /** Surface glass background opacity — 0.10 (~rgba 255,255,255,0.10). */
    val bgOpacityHeavy: Float,
    /** Light surface glass background opacity — 0.05. */
    val bgOpacityLight: Float,

    // ── Border opacity (0f–1f) ────────────────────────────────
    /** Standard glass border opacity — 0.20. */
    val borderOpacity: Float,
    /** Strong glass border opacity — 0.30. */
    val borderOpacityStrong: Float,
)

// ── Composition Local ─────────────────────────────────────────
val LocalLindaGlassScheme = compositionLocalOf<LindaGlassScheme> {
    LindaDefaultGlassScheme
}

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
