package com.jaarvi.ui.linda.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaBadge variants
// ─────────────────────────────────────────────────────────────

enum class BadgeVariant {
    /** Gold accent with optional glow. */
    GOLD,
    /** Lime accent with optional glow. */
    LIME,
    /** Muted / neutral, no glow. */
    MUTED,
}

// ─────────────────────────────────────────────────────────────

/**
 * LindaBadge — uppercase label chip, typically used for status tags,
 * category labels, or percentage indicators.
 *
 * @param text     Badge label; automatically uppercased.
 * @param variant  Colour variant: [BadgeVariant.GOLD], [BadgeVariant.LIME],
 *                 or [BadgeVariant.MUTED].
 * @param glow     Whether to render a soft coloured shadow behind the text.
 *                 Ignored when variant is [BadgeVariant.MUTED].
 */
@Composable
fun LindaBadge(
    text    : String,
    modifier: Modifier     = Modifier,
    variant : BadgeVariant = BadgeVariant.GOLD,
    glow    : Boolean      = true,
) {
    val colors = LindaTheme.colors
    val typo   = LindaTheme.typography
    val shadow = LindaTheme.shadow

    val textColor = when (variant) {
        BadgeVariant.GOLD  -> colors.accentGold
        BadgeVariant.LIME  -> colors.accentLime
        BadgeVariant.MUTED -> colors.textMuted
    }

    val glowColor = when (variant) {
        BadgeVariant.GOLD  -> colors.glowGold
        BadgeVariant.LIME  -> colors.glowLime
        BadgeVariant.MUTED -> Color.Transparent
    }

    val shouldGlow = glow && variant != BadgeVariant.MUTED

    Text(
        text  = text.uppercase(),
        style = typo.labelMedium.copy(color = textColor),
        modifier = modifier.then(
            if (shouldGlow) {
                Modifier.shadow(
                    elevation    = shadow.glowElevation,
                    ambientColor = glowColor,
                    spotColor    = glowColor,
                )
            } else Modifier,
        ),
    )
}

