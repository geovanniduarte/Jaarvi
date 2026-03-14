package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaIconButton — LEVEL 1
//
// Circular (or rounded-square) tappable icon container.
// Three visual styles and three hit-target size tiers.
// ─────────────────────────────────────────────────────────────

// ── Enums ─────────────────────────────────────────────────────

enum class IconButtonVariant {
    /** Transparent background — icon only. */
    GHOST,
    /** Glass surface with a circular border. */
    GLASS,
    /** Glass surface with a rounded-square border. */
    GLASS_SQUARE,
}

enum class IconButtonSize {
    SM,  // 32 dp hit target
    MD,  // 40 dp hit target
    LG,  // 48 dp hit target
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param onClick  Click handler.
 * @param modifier Optional layout modifier.
 * @param variant  [IconButtonVariant.GHOST], [IconButtonVariant.GLASS], or [IconButtonVariant.GLASS_SQUARE].
 * @param size     Hit-target diameter preset.
 * @param content  Icon composable slot (typically a Material [Icon]).
 */
@Composable
fun LindaIconButton(
    onClick : () -> Unit,
    modifier: Modifier          = Modifier,
    variant : IconButtonVariant = IconButtonVariant.GHOST,
    size    : IconButtonSize    = IconButtonSize.MD,
    content : @Composable () -> Unit,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing

    val innerPadding = when (size) {
        IconButtonSize.SM -> spacing.sm           //  8 dp
        IconButtonSize.MD -> spacing.sm + 2.dp    // 10 dp
        IconButtonSize.LG -> spacing.md           // 12 dp
    }

    val hitSize = when (size) {
        IconButtonSize.SM -> 32.dp
        IconButtonSize.MD -> 40.dp
        IconButtonSize.LG -> 48.dp
    }

    val shape = when (variant) {
        IconButtonVariant.GLASS_SQUARE -> RoundedCornerShape(borders.radiusMd)
        else                           -> CircleShape
    }

    val bgColor = when (variant) {
        IconButtonVariant.GHOST                            -> Color.Transparent
        IconButtonVariant.GLASS, IconButtonVariant.GLASS_SQUARE -> colors.surfaceGlass
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(hitSize)
            .clip(shape)
            .background(bgColor)
            .then(
                if (variant != IconButtonVariant.GHOST) Modifier.border(
                    width = borders.widthThin,
                    color = colors.borderGlassStrong,
                    shape = shape,
                ) else Modifier
            )
            .clickable(onClick = onClick)
            .padding(innerPadding),
    ) {
        content()
    }
}
