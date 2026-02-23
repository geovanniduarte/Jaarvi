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
// To switch to light mode (once a LindaLightColorScheme is defined):
//   LindaTheme(colors = LindaLightColorScheme) {
//       MyScreen()
//   }
//
// Components read tokens via the LindaTheme accessor object, e.g.:
//   val colors = LindaTheme.colors
//   val typography = LindaTheme.typography
// ─────────────────────────────────────────────────────────────

@Composable
fun LindaTheme(
    colors    : LindaColorScheme = LindaDarkColorScheme,
    borders   : LindaBorderScheme = LindaDefaultBorderScheme,
    sizes     : LindaSizeScheme = LindaDefaultSizeScheme,
    spacing   : LindaSpacingScheme = LindaDefaultSpacingScheme,
    typography: LindaTypographyScheme = LindaDefaultTypographyScheme,
    glass     : LindaGlassScheme = LindaDefaultGlassScheme,
    shadow    : LindaShadowScheme = LindaDefaultShadowScheme,
    content   : @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalLindaColorScheme provides colors,
        LocalLindaBorderScheme provides borders,
        LocalLindaSizeScheme provides sizes,
        LocalLindaSpacingScheme provides spacing,
        LocalLindaTypographyScheme provides typography,
        LocalLindaGlassScheme provides glass,
        LocalLindaShadowScheme provides shadow,
        content = content,
    )
}

// ─────────────────────────────────────────────────────────────
// LindaTheme accessor object — composable getters for the current
// scheme values. Use these inside any @Composable to read tokens
// without needing to pass schemes around manually.
// ─────────────────────────────────────────────────────────────

object LindaTheme {

    /** Current color scheme (dark by default). */
    val colors: LindaColorScheme
        @Composable @ReadOnlyComposable
        get() = LocalLindaColorScheme.current

    /** Current border/radius scheme. */
    val borders: LindaBorderScheme
        @Composable @ReadOnlyComposable
        get() = LocalLindaBorderScheme.current

    /** Current component-size scheme. */
    val sizes: LindaSizeScheme
        @Composable @ReadOnlyComposable
        get() = LocalLindaSizeScheme.current

    /** Current spacing scale scheme. */
    val spacing: LindaSpacingScheme
        @Composable @ReadOnlyComposable
        get() = LocalLindaSpacingScheme.current

    /** Current typography scheme. */
    val typography: LindaTypographyScheme
        @Composable @ReadOnlyComposable
        get() = LocalLindaTypographyScheme.current

    /** Current glass-effect scheme. */
    val glass: LindaGlassScheme
        @Composable @ReadOnlyComposable
        get() = LocalLindaGlassScheme.current

    /** Current shadow/elevation scheme. */
    val shadow: LindaShadowScheme
        @Composable @ReadOnlyComposable
        get() = LocalLindaShadowScheme.current
}
