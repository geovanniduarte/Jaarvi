package com.jaarvi.ui.linda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaDivider orientation variants
// ─────────────────────────────────────────────────────────────

enum class DividerOrientation {
    HORIZONTAL,
    VERTICAL,
}

// ─────────────────────────────────────────────────────────────

/**
 * LindaDivider — thin glass-tinted separator line.
 *
 * Uses [LindaColorScheme.borderGlass] by default, matching card and
 * nav-bar borders. Swap for [LindaColorScheme.borderGlassStrong] via
 * [strong] for higher-contrast contexts.
 *
 * @param orientation  [DividerOrientation.HORIZONTAL] (default) or
 *                     [DividerOrientation.VERTICAL].
 * @param strong       When true uses the stronger glass border colour.
 */
@Composable
fun LindaDivider(
    modifier    : Modifier          = Modifier,
    orientation : DividerOrientation = DividerOrientation.HORIZONTAL,
    strong      : Boolean            = false,
) {
    val colors = LindaTheme.colors
    val sizes  = LindaTheme.sizes

    val color = if (strong) colors.borderGlassStrong else colors.borderGlass

    when (orientation) {
        DividerOrientation.HORIZONTAL -> Box(
            modifier = modifier
                .fillMaxWidth()
                .height(sizes.dividerThickness)
                .background(color),
        )
        DividerOrientation.VERTICAL -> Box(
            modifier = modifier
                .fillMaxHeight()
                .width(sizes.dividerThickness)
                .background(color),
        )
    }
}
