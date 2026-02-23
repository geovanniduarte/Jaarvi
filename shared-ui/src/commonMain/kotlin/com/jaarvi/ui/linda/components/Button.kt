package com.jaarvi.ui.linda.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.utils.boxShadow
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaButton variants
// ─────────────────────────────────────────────────────────────

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

// ─────────────────────────────────────────────────────────────

/**
 * LindaButton — primary interactive control.
 *
 * @param text     Label displayed inside the button.
 * @param onClick  Click handler.
 * @param variant  Visual style: [ButtonVariant.PRIMARY], [ButtonVariant.GLASS], or [ButtonVariant.GHOST].
 * @param size     Height / padding tier: [ButtonSize.SM], [ButtonSize.MD], or [ButtonSize.LG].
 * @param glow     Optional coloured shadow halo around the button.
 * @param enabled  When false the button is non-interactive and visually dimmed.
 */
@Composable
fun LindaButton(
    text   : String,
    onClick: () -> Unit,
    modifier: Modifier    = Modifier,
    variant : ButtonVariant = ButtonVariant.GLASS,
    size    : ButtonSize    = ButtonSize.MD,
    glow    : ButtonGlow    = ButtonGlow.NONE,
    enabled : Boolean       = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val colors   = LindaTheme.colors
    val borders  = LindaTheme.borders
    val spacing  = LindaTheme.spacing
    val shadow   = LindaTheme.shadow
    val typo     = LindaTheme.typography

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

    var blurElevation = shadow.glowElevation
    var blurColor = glowColor
    if (glow == ButtonGlow.NONE) {
            blurElevation = 0.dp
            blurColor = Color.Transparent
    }

    Container(
        onClick  = onClick,
        shape   = shape,
        backgroundColor = Color.Transparent,
        modifier = modifier.then(
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
            },
        ),
        border = BorderStroke(width = 0.dp, color = Color.Transparent),
        shadowColor = blurColor,
        enabled = enabled,
        blurRadius = 2.dp,
        blurElevation = blurElevation,
        onClickLabel = "${text} button",
        interactionSource = interactionSource,
        indication = null,
        paddingValues = PaddingValues(horizontal = paddingH, vertical = paddingV)
    ) {
        Text(
            text      = text,
            style     = when (size) {
                ButtonSize.SM -> typo.labelSmall.copy(color = textColor)
                ButtonSize.MD -> typo.labelLarge.copy(color = textColor)
                ButtonSize.LG -> typo.labelLarge.copy(color = textColor)
            },
            textAlign = TextAlign.Center
        )
    }

}

@Composable
internal fun Container(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke,
    shadowColor: Color,
    blurRadius: Dp,
    blurElevation:Dp,
    enabled: Boolean,
    onClickLabel: String?,
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    paddingValues: PaddingValues,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .boxShadow(
                blurRadius = blurRadius,
                spreadRadius = blurElevation,
                shape = shape,
                color = shadowColor,
                inset = false
            )
            .border(
                border = border,
                shape = shape
            )
            .background(
                color = backgroundColor
            )
            .clip(shape)
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                enabled = enabled,
                onClickLabel = onClickLabel,
                role = Role.Button,
                onClick = onClick
            ).padding(paddingValues),
        propagateMinConstraints = true,
        content = content
    )
}
