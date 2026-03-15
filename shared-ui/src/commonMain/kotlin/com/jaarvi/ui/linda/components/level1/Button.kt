package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.utils.boxShadow
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaButton — LEVEL 1
//
// Primary interactive control. Three visual styles:
//   PRIMARY — Gold→Lime gradient fill, dark inner ring border.
//   GLASS   — Glassmorphic frosted surface.
//   GHOST   — No background, text only.
//
// Optional coloured glow halo via [ButtonGlow].
// ─────────────────────────────────────────────────────────────

// ── Enums ─────────────────────────────────────────────────────

enum class ButtonVariant {
    /** Gold→Lime gradient fill. */
    PRIMARY,
    /** Glassmorphic frosted surface. */
    GLASS,
    /** No background, text-only. */
    GHOST,
}

enum class ButtonSize {
    SM, MD, LG,
}

enum class ButtonGlow {
    GOLD, LIME, NONE,
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param text    Label displayed inside the button.
 * @param onClick Click handler.
 * @param modifier Optional layout modifier.
 * @param variant Visual style: [ButtonVariant.PRIMARY], [ButtonVariant.GLASS], or [ButtonVariant.GHOST].
 * @param size    Height / padding tier: [ButtonSize.SM], [ButtonSize.MD], or [ButtonSize.LG].
 * @param glow    Optional coloured shadow halo around the button.
 * @param enabled When false the button is non-interactive and visually dimmed.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LindaButton(
        text    : String,
        onClick : () -> Unit,
        modifier: Modifier      = Modifier,
        variant : ButtonVariant = ButtonVariant.GLASS,
        size    : ButtonSize    = ButtonSize.MD,
        glow    : ButtonGlow    = ButtonGlow.NONE,
        enabled : Boolean       = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    ) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val shadow  = LindaTheme.shadow
    val typo    = LindaTheme.typography

    val paddingH = when (size) {
        ButtonSize.SM -> spacing.lg
        ButtonSize.MD -> spacing.xxl
        ButtonSize.LG -> spacing.xxxl
    }
    val paddingV = when (size) {
        ButtonSize.SM -> spacing.sm
        ButtonSize.MD -> spacing.sm + spacing.xs   // ~12 dp
        ButtonSize.LG -> spacing.md
    }

    val textColor = when (variant) {
        ButtonVariant.PRIMARY -> colors.onAccent
        ButtonVariant.GLASS   -> when (glow) {
            ButtonGlow.GOLD -> colors.accentGold
            ButtonGlow.LIME -> colors.accentLime
            ButtonGlow.NONE -> colors.textLabel
        }
        ButtonVariant.GHOST   -> colors.textMuted
    }

    val glowColor = when (glow) {
        ButtonGlow.GOLD -> colors.glowGold
        ButtonGlow.LIME -> colors.glowLime
        ButtonGlow.NONE -> Color.Transparent
    }

    val shape = RoundedCornerShape(borders.radiusMd)

    val glowModifier =
        if (glow != ButtonGlow.NONE) Modifier.boxShadow(
            blurRadius    = shadow.glowElevation,
            shape        = shape,
            color = glowColor,
        ) else Modifier


    Surface(
        onClick = onClick,
        modifier = glowModifier.then(
            when (variant) {
                ButtonVariant.PRIMARY -> Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(colors.accentGold, colors.accentLime),
                        ),
                        shape = shape,
                    )
                    .border(
                        width = borders.widthThick,
                        color = colors.background,
                        shape = shape,
                    )
                ButtonVariant.GLASS   -> Modifier
                    .background(color = colors.surfaceGlass, shape = shape)
                    .border(width = borders.widthThin, color = colors.borderGlassStrong, shape = shape)
                ButtonVariant.GHOST   -> Modifier
            }
        ).semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = Color.Transparent,
        contentColor = textColor,
        elevation = 0.dp,
        interactionSource = interactionSource,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides textColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(PaddingValues(horizontal = paddingH, vertical = paddingV)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Text(
                            text      = text,
                            style     = when (size) {
                                ButtonSize.SM -> typo.labelSmall
                                ButtonSize.MD,
                                ButtonSize.LG -> typo.labelLarge
                            },
                            textAlign = TextAlign.Center,
                        )
                    }
                )
            }
        }
    }
}