package com.jaarvi.ui.linda.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

// ─────────────────────────────────────────────────────────────
// Backward-compatibility wrapper.
//
// New code should use LindaTheme { } directly.
// This wrapper is kept so that existing call-sites (e.g. MainActivity)
// continue to compile while migrating incrementally.
//
// TravelAppTheme automatically selects the dark or — once defined —
// the light color scheme based on the system setting.
// ─────────────────────────────────────────────────────────────

@Composable
fun TravelAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // Extend here: provide LindaLightColorScheme when darkTheme == false.
    val colors = if (darkTheme) LindaDarkColorScheme else LindaDarkColorScheme

    LindaTheme(
        colors = colors,
        content = content,
    )
}
