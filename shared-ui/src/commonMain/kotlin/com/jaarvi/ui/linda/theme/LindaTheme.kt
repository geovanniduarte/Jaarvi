package com.jaarvi.ui.linda.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

// ─────────────────────────────────────────────────────────────
// LindaTheme — single entry point for the Linda design system.
//
// Usage:
//   LindaTheme {
//       MyScreen()
//   }
//
// To switch to light mode once LindaLightColorScheme is defined:
//   LindaTheme(colors = LindaLightColorScheme) {
//       MyScreen()
//   }
//
// Read tokens inside any @Composable via the accessor object:
//   val colors    = LindaTheme.colors
//   val spacing   = LindaTheme.spacing
//   val borders   = LindaTheme.borders
//   val typography = LindaTheme.typography
// ─────────────────────────────────────────────────────────────

@Composable
fun LindaTheme(
    colors    : LindaColorScheme      = LindaDarkColorScheme,
    borders   : LindaBorderScheme     = LindaDefaultBorderScheme,
    sizes     : LindaSizeScheme       = LindaDefaultSizeScheme,
    spacing   : LindaSpacingScheme    = LindaDefaultSpacingScheme,
    typography: LindaTypographyScheme = LindaDefaultTypographyScheme,
    glass     : LindaGlassScheme      = LindaDefaultGlassScheme,
    shadow    : LindaShadowScheme     = LindaDefaultShadowScheme,
    content   : @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalLindaColorScheme      provides colors,
        LocalLindaBorderScheme     provides borders,
        LocalLindaSizeScheme       provides sizes,
        LocalLindaSpacingScheme    provides spacing,
        LocalLindaTypographyScheme provides typography,
        LocalLindaGlassScheme      provides glass,
        LocalLindaShadowScheme     provides shadow,
        content = content,
    )
}

// ─────────────────────────────────────────────────────────────
// LindaTheme accessor object — composable getters for the current
// scheme values. Use these inside any @Composable to read tokens
// without passing schemes around manually.
// ─────────────────────────────────────────────────────────────

object LindaTheme {

    val colors    : LindaColorScheme
        @Composable @ReadOnlyComposable get() = LocalLindaColorScheme.current

    val borders   : LindaBorderScheme
        @Composable @ReadOnlyComposable get() = LocalLindaBorderScheme.current

    val sizes     : LindaSizeScheme
        @Composable @ReadOnlyComposable get() = LocalLindaSizeScheme.current

    val spacing   : LindaSpacingScheme
        @Composable @ReadOnlyComposable get() = LocalLindaSpacingScheme.current

    val typography: LindaTypographyScheme
        @Composable @ReadOnlyComposable get() = LocalLindaTypographyScheme.current

    val glass     : LindaGlassScheme
        @Composable @ReadOnlyComposable get() = LocalLindaGlassScheme.current

    val shadow    : LindaShadowScheme
        @Composable @ReadOnlyComposable get() = LocalLindaShadowScheme.current
}
