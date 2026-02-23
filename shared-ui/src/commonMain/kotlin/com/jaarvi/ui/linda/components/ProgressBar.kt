package com.jaarvi.ui.linda.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaProgressBar variants
// ─────────────────────────────────────────────────────────────

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

// ─────────────────────────────────────────────────────────────

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
        targetValue   = percentage / 100f,
        animationSpec = tween(durationMillis = 500),
        label         = "linda_progress",
    )

    val trackHeight = when (size) {
        ProgressSize.SM -> 6.dp
        ProgressSize.MD -> 10.dp
    }

    val fillBrush = when (variant) {
        ProgressVariant.GRADIENT -> Brush.horizontalGradient(
            colors = listOf(colors.accentGold, colors.accentLime),
        )
        ProgressVariant.GOLD     -> Brush.horizontalGradient(
            colors = listOf(colors.accentGold, colors.accentGold),
        )
        ProgressVariant.LIME     -> Brush.horizontalGradient(
            colors = listOf(colors.accentLime, colors.accentLime),
        )
    }

    val fillGlow = when (variant) {
        ProgressVariant.GRADIENT, ProgressVariant.LIME -> colors.glowLime
        ProgressVariant.GOLD                           -> colors.glowGold
    }

    val trackShape = RoundedCornerShape(borders.radiusFull)

    Column(
        modifier            = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {
        if (showLabel) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Text(
                    text  = label.uppercase(),
                    style = typo.labelMedium.copy(color = colors.textLabel),
                )
                Text(
                    text  = "${percentage.toInt()}%",
                    style = typo.labelMedium.copy(color = colors.accentLime),
                    modifier = Modifier.shadow(
                        elevation    = shadow.glowElevation,
                        ambientColor = colors.glowLime,
                        spotColor    = colors.glowLime,
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
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedFraction)
                    .clip(trackShape)
                    .background(fillBrush)
                    .shadow(
                        elevation    = shadow.md,
                        ambientColor = fillGlow,
                        spotColor    = fillGlow,
                    ),
            )
        }
    }
}

// ─── Backward-compat alias ───────────────────────────────────
/** @deprecated Use [LindaProgressBar]. */
@Deprecated("Use LindaProgressBar", ReplaceWith("LindaProgressBar(value, modifier, max, variant, showLabel, label, size)"))
@Composable
fun TravelProgressBar(
    value    : Float,
    modifier : Modifier        = Modifier,
    max      : Float           = 100f,
    variant  : ProgressVariant = ProgressVariant.GRADIENT,
    showLabel: Boolean         = true,
    label    : String          = "",
    size     : ProgressSize    = ProgressSize.MD,
) = LindaProgressBar(value, modifier, max, variant, showLabel, label, size)
