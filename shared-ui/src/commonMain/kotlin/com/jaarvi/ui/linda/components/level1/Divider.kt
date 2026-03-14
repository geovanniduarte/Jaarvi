package com.jaarvi.ui.linda.components.level1

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
// LindaDivider — LEVEL 1
//
// Thin glass-tinted separator line, horizontal or vertical.
// Uses borderGlass by default; pass strong = true for the
// higher-contrast borderGlassStrong variant.
// ─────────────────────────────────────────────────────────────

// ── Orientation ───────────────────────────────────────────────

enum class DividerOrientation {
    HORIZONTAL,
    VERTICAL,
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param modifier    Optional layout modifier.
 * @param orientation [DividerOrientation.HORIZONTAL] (default) or [DividerOrientation.VERTICAL].
 * @param strong      When true uses [colors.borderGlassStrong] instead of [colors.borderGlass].
 */
@Composable
fun LindaDivider(
    modifier    : Modifier           = Modifier,
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
