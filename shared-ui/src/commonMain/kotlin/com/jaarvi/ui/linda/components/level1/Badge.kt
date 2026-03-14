package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaBadge — LEVEL 1
//
// Uppercase label chip. Four visual variants faithfully mapped
// from Figma design system §6:
//
//   OUTLINED_ROUNDED   — rect (radiusMd), gold tint bg + border,
//                        gold text. No glow.       "1 Day Remaining"
//
//   SOLID_LIME_PILL    — pill (radiusFull), solid accentLime fill,
//                        background-coloured text, lime glow shadow.
//                        "Active Journey"
//
//   SUBTLE_GOLD_PILL   — pill (radiusFull), gold tint bg (no border),
//                        gold text + gold glow.     "14 DAYS"
//
//   OUTLINED_GOLD_PILL — pill (radiusFull), gold tint bg + gold
//                        border, gold text + gold glow. "STEP 1/3"
//
// Usage
//   LindaBadge(text = "14 Days", variant = BadgeVariant.SUBTLE_GOLD_PILL)
// ─────────────────────────────────────────────────────────────

// ── Variant enum ──────────────────────────────────────────────

enum class BadgeVariant {
    /** Rounded rect · gold tint bg + border · gold text · no glow. */
    OUTLINED_ROUNDED,
    /** Full pill · solid lime fill · background text · lime glow. */
    SOLID_LIME_PILL,
    /** Full pill · gold tint bg · no border · gold text + glow. */
    SUBTLE_GOLD_PILL,
    /** Full pill · gold tint bg + gold border · gold text + glow. */
    OUTLINED_GOLD_PILL,
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param text     Badge label; automatically uppercased.
 * @param variant  Visual style — one of the four [BadgeVariant] options.
 * @param modifier Optional layout modifier.
 */
@Composable
fun LindaBadge(
    text    : String,
    modifier: Modifier     = Modifier,
    variant : BadgeVariant = BadgeVariant.SUBTLE_GOLD_PILL,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography
    val shadow  = LindaTheme.shadow

    // ── Shape ─────────────────────────────────────────────────
    val shape = when (variant) {
        BadgeVariant.OUTLINED_ROUNDED -> RoundedCornerShape(borders.radiusMd)
        else                          -> RoundedCornerShape(borders.radiusFull)
    }

    // ── Fill ──────────────────────────────────────────────────
    val fillColor = when (variant) {
        BadgeVariant.OUTLINED_ROUNDED   -> colors.accentGold.copy(alpha = 0.10f)
        BadgeVariant.SOLID_LIME_PILL    -> colors.accentLime
        BadgeVariant.SUBTLE_GOLD_PILL,
        BadgeVariant.OUTLINED_GOLD_PILL -> colors.accentGold.copy(alpha = 0.20f)
    }

    // ── Text colour ───────────────────────────────────────────
    val textColor = when (variant) {
        BadgeVariant.SOLID_LIME_PILL -> colors.background   // dark on bright lime
        else                          -> colors.accentGold
    }

    // ── Glow — null means no glow shadow ─────────────────────
    val glowColor = when (variant) {
        BadgeVariant.SOLID_LIME_PILL                              -> colors.glowLime
        BadgeVariant.SUBTLE_GOLD_PILL, BadgeVariant.OUTLINED_GOLD_PILL -> colors.glowGold
        BadgeVariant.OUTLINED_ROUNDED                             -> null
    }

    // ── Border — only OUTLINED variants ──────────────────────
    val borderColor = when (variant) {
        BadgeVariant.OUTLINED_ROUNDED   -> colors.accentGold.copy(alpha = 0.20f)
        BadgeVariant.OUTLINED_GOLD_PILL -> colors.accentGold.copy(alpha = 0.30f)
        else                             -> null
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            // Glow shadow sits outside the clip boundary.
            .then(
                if (glowColor != null) Modifier.shadow(
                    elevation    = shadow.glowElevation,
                    shape        = shape,
                    clip         = false,
                    ambientColor = glowColor,
                    spotColor    = glowColor,
                ) else Modifier
            )
            .clip(shape)
            .background(fillColor)
            // Border rendered as an inset overlay after clip.
            .then(
                if (borderColor != null) Modifier.border(
                    width = borders.widthThin,
                    color = borderColor,
                    shape = shape,
                ) else Modifier
            )
            .padding(horizontal = spacing.md, vertical = spacing.xs),
    ) {
        Text(
            text  = text.uppercase(),
            style = typo.labelSmall.copy(color = textColor),
        )
    }
}
