package com.jaarvi.ui.linda.components.level1

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.level2.NavItem
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaIconButton variants
// ─────────────────────────────────────────────────────────────

enum class IconButtonVariant {
    /** Transparent background — icon only. */
    GHOST,
    /** Glass surface with border. */
    GLASS,
    /** Glass with rounded-square shape instead of circle. */
    GLASS_SQUARE,
}

enum class IconButtonSize {
    SM,  // 32 dp hit target
    MD,  // 40 dp hit target
    LG,  // 48 dp hit target
}

// ─────────────────────────────────────────────────────────────

/**
 * LindaIconButton — circular (or rounded-square) tappable icon container.
 *
 * @param onClick  Click handler.
 * @param variant  [IconButtonVariant.GHOST], [IconButtonVariant.GLASS], or
 *                 [IconButtonVariant.GLASS_SQUARE].
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

    val padding = when (size) {
        IconButtonSize.SM -> spacing.sm          // 8 dp
        IconButtonSize.MD -> spacing.sm + 2.dp   // 10 dp
        IconButtonSize.LG -> spacing.md          // 12 dp
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
        IconButtonVariant.GHOST        -> Color.Transparent
        IconButtonVariant.GLASS,
        IconButtonVariant.GLASS_SQUARE -> colors.surfaceGlass
    }

    Box(
        modifier = modifier
            .size(hitSize)
            .clip(shape)
            .background(bgColor)
            .then(
                if (variant != IconButtonVariant.GHOST) {
                    Modifier.border(
                        width = borders.widthThin,
                        color = colors.borderGlassStrong,
                        shape = shape,
                    )
                } else Modifier,
            )
            .clickable(onClick = onClick)
            .padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

enum class ProgressVariant {
    /** Gold→Lime gradient fill. */
    GRADIENT,
    /** Solid gold fill. */
    GOLD,
    /** Solid lime fill. */
    LIME,
}

enum class ProgressSize {
    SM,  // 6 dp track height
    MD,  // 10 dp track height
}

/**
 * LindaProgressBar — animated horizontal progress indicator.
 *
 * @param value      Current progress value (0–[max]).
 * @param max        Maximum value; defaults to 100.
 * @param variant    Fill colour variant.
 * @param showLabel  Whether to show the label/percentage row above the track.
 * @param label      Left-hand label text (uppercased automatically).
 * @param size       Track height preset.
 */
@Composable
fun LindaProgressBar(
    value    : Float,
    modifier : Modifier        = Modifier,
    max      : Float           = 100f,
    variant  : ProgressVariant = ProgressVariant.GRADIENT,
    showLabel: Boolean         = true,
    label    : String          = "",
    size     : ProgressSize    = ProgressSize.MD,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val shadow  = LindaTheme.shadow
    val typo    = LindaTheme.typography

    val percentage = (value / max * 100f).coerceIn(0f, 100f)
    val animatedFraction by animateFloatAsState(
        targetValue = percentage / 100f,
        animationSpec = tween(durationMillis = 500),
        label = "linda_progress",
    )

    val trackHeight = when (size) {
        ProgressSize.SM -> 6.dp
        ProgressSize.MD -> 10.dp
    }

    val fillBrush = when (variant) {
        ProgressVariant.GRADIENT -> Brush.Companion.horizontalGradient(
            colors = listOf(colors.accentGold, colors.accentLime),
        )
        ProgressVariant.GOLD     -> Brush.Companion.horizontalGradient(
            colors = listOf(colors.accentGold, colors.accentGold),
        )
        ProgressVariant.LIME     -> Brush.Companion.horizontalGradient(
            colors = listOf(colors.accentLime, colors.accentLime),
        )
    }

    val fillGlow = when (variant) {
        ProgressVariant.GRADIENT, ProgressVariant.LIME -> colors.glowLime
        ProgressVariant.GOLD                           -> colors.glowGold
    }

    val trackShape = RoundedCornerShape(borders.radiusFull)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {
        if (showLabel) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = label.uppercase(),
                    style = typo.labelMedium.copy(color = colors.textLabel),
                )
                Text(
                    text = "${percentage.toInt()}%",
                    style = typo.labelMedium.copy(color = colors.accentLime),
                    modifier = Modifier.Companion.shadow(
                        elevation = shadow.glowElevation,
                        ambientColor = colors.glowLime,
                        spotColor = colors.glowLime,
                    ),
                )
            }
        }

        // Track container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(trackHeight)
                .clip(trackShape)
                .background(Color.Black.copy(alpha = 0.30f))
                .border(
                    width = borders.widthThin,
                    color = Color.White.copy(alpha = 0.05f),
                    shape = trackShape,
                ),
        ) {
            // Animated fill
            Box(
                modifier = Modifier.Companion
                    .fillMaxHeight()
                    .fillMaxWidth(animatedFraction)
                    .clip(trackShape)
                    .background(fillBrush)
                    .shadow(
                        elevation = shadow.md,
                        ambientColor = fillGlow,
                        spotColor = fillGlow,
                    ),
            )
        }
    }
}

/**
 * LindaNavigationItem — single nav tab: icon + uppercased label.
 *
 * When [item.isActive] is true the icon and label are rendered in
 * [LindaColorScheme.accentGold] with a gold glow shadow behind them;
 * otherwise they use [LindaColorScheme.textMuted].
 *
 * @param item    [com.jaarvi.ui.linda.components.level2.NavItem] supplying icon, label, and active state.
 * @param onClick Click handler.
 */
@Composable
fun LindaNavigationItem(
    item    : NavItem,
    modifier: Modifier   = Modifier,
    onClick : () -> Unit = {},
) {
    val colors = LindaTheme.colors
    val shadow = LindaTheme.shadow
    val sizes  = LindaTheme.sizes
    val typo   = LindaTheme.typography
    val spacing = LindaTheme.spacing

    val contentColor = if (item.isActive) colors.accentGold else colors.textMuted
    val glowModifier = if (item.isActive) {
        Modifier.shadow(
            elevation    = shadow.glowElevation,
            shape        = CircleShape,
            ambientColor = colors.glowGold,
            spotColor    = colors.glowGold,
        )
    } else Modifier

    Column(
        modifier            = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.xs),
    ) {
        // Icon with optional glow halo
        Box(
            contentAlignment = Alignment.Center,
            modifier         = glowModifier,
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = contentColor,
                modifier = Modifier.size(sizes.navBarIconSize),
            )
        }

        // Label
        Text(
            text     = item.label.uppercase(),
            style    = typo.labelSmall.copy(color = contentColor),
            modifier = if (item.isActive) {
                Modifier.shadow(
                    elevation    = shadow.glowElevation,
                    ambientColor = colors.glowGold,
                    spotColor    = colors.glowGold,
                )
            } else Modifier,
        )
    }
}