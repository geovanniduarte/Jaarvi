package com.jaarvi.ui.linda.theme

import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// Dimensions.kt — legacy compatibility shim.
//
// All tokens have been migrated into:
//   LindaSpacingScheme  → LindaTheme.spacing.*
//   LindaBorderScheme   → LindaTheme.borders.*
//   LindaShadowScheme   → LindaTheme.shadow.*
//   LindaSizeScheme     → LindaTheme.sizes.*
//   LindaGlassScheme    → LindaTheme.glass.*
//
// The object aliases below are kept only for incremental migration;
// new code and any refactored component should NOT reference them.
// ─────────────────────────────────────────────────────────────

/** @deprecated Use LindaTheme.spacing inside @Composable functions. */
@Deprecated("Use LindaTheme.spacing inside @Composable functions.")
object Spacing {
    val xs   = 4.dp
    val sm   = 8.dp
    val md   = 12.dp
    val lg   = 16.dp
    val xl   = 20.dp
    val xxl  = 24.dp
    val xxxl = 32.dp
}

/** @deprecated Use LindaTheme.borders inside @Composable functions. */
@Deprecated("Use LindaTheme.borders inside @Composable functions.")
object Radius {
    val sm   = 8.dp
    val md   = 12.dp
    val lg   = 16.dp
    val xl   = 24.dp
    val full = 9999.dp
}

/** @deprecated Use LindaTheme.shadow inside @Composable functions. */
@Deprecated("Use LindaTheme.shadow inside @Composable functions.")
object Elevation {
    val none = 0.dp
    val sm   = 2.dp
    val md   = 4.dp
    val lg   = 8.dp
    val xl   = 16.dp
}

/** @deprecated Use LindaTheme.sizes inside @Composable functions. */
@Deprecated("Use LindaTheme.sizes inside @Composable functions.")
object IconSize {
    val xs = 12.dp
    val sm = 16.dp
    val md = 20.dp
    val lg = 24.dp
    val xl = 32.dp
}

/** @deprecated Use LindaTheme.glass inside @Composable functions. */
@Deprecated("Use LindaTheme.glass inside @Composable functions.")
object Blur {
    val sm = 6.dp
    val md = 12.dp
    val lg = 40.dp
}
