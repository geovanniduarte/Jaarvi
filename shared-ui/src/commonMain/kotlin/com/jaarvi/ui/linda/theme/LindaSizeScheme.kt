package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// LindaSizeScheme — component-size and icon-size tokens.
// ─────────────────────────────────────────────────────────────

@Immutable
data class LindaSizeScheme(
    // ── Icon sizes ────────────────────────────────────────────
    val iconXs: Dp,   // 12 dp
    val iconSm: Dp,   // 16 dp
    val iconMd: Dp,   // 20 dp
    val iconLg: Dp,   // 24 dp
    val iconXl: Dp,   // 32 dp

    // ── Avatar sizes ─────────────────────────────────────────
    val avatarSm: Dp,  // 32 dp
    val avatarMd: Dp,  // 36 dp
    val avatarLg: Dp,  // 48 dp

    // ── Button heights ────────────────────────────────────────
    val buttonHeightSm: Dp,   // 32 dp
    val buttonHeightMd: Dp,   // 40 dp
    val buttonHeightLg: Dp,   // 52 dp

    // ── FAB ───────────────────────────────────────────────────
    /** Diameter of the main Floating Action Button. */
    val fabSize: Dp,          // 64 dp
    /** Diameter of a mini FAB. */
    val fabMiniSize: Dp,      // 48 dp

    // ── Nav bar ───────────────────────────────────────────────
    val navBarIconSize: Dp,   // 20 dp

    // ── Destination card image ────────────────────────────────
    val destinationImageHeight: Dp,  // 208 dp

    // ── Status indicator dot ──────────────────────────────────
    val statusDotSm: Dp,   // 6 dp
    val statusDotMd: Dp,   // 8 dp
    val statusDotLg: Dp,   // 12 dp

    // ── Divider ───────────────────────────────────────────────
    val dividerThickness: Dp,   // 1 dp
)

// ── Composition Local ─────────────────────────────────────────
val LocalLindaSizeScheme = compositionLocalOf<LindaSizeScheme> {
    LindaDefaultSizeScheme
}

// ── Defaults ─────────────────────────────────────────────────
val LindaDefaultSizeScheme = LindaSizeScheme(
    iconXs                 = 12.dp,
    iconSm                 = 16.dp,
    iconMd                 = 20.dp,
    iconLg                 = 24.dp,
    iconXl                 = 32.dp,

    avatarSm               = 32.dp,
    avatarMd               = 36.dp,
    avatarLg               = 48.dp,

    buttonHeightSm         = 32.dp,
    buttonHeightMd         = 40.dp,
    buttonHeightLg         = 52.dp,

    fabSize                = 64.dp,
    fabMiniSize            = 48.dp,

    navBarIconSize         = 20.dp,

    destinationImageHeight = 208.dp,

    statusDotSm            = 6.dp,
    statusDotMd            = 8.dp,
    statusDotLg            = 12.dp,

    dividerThickness       = 1.dp,
)
