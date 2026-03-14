package com.jaarvi.ui.linda.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

// ─────────────────────────────────────────────────────────────
// Theme.kt — backward-compatibility wrapper.
//
// New code must use LindaTheme { } directly.
// TravelAppTheme is kept only for existing call-sites migrating
// incrementally from the old SunTheme / Material3 setup.
//
// Extend: provide LindaLightColorScheme when darkTheme == false
// once a light palette is designed.
// ─────────────────────────────────────────────────────────────

@Composable
fun TravelAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content  : @Composable () -> Unit,
) {
    val colors = if (darkTheme) LindaDarkColorScheme else LindaDarkColorScheme
    LindaTheme(colors = colors, content = content)
}
