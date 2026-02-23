package com.jaarvi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button as MaterialButton
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.theme.LocalTravelTypography
import com.jaarvi.ui.theme.Radius
import com.jaarvi.ui.theme.ColorAccentGold
import com.jaarvi.ui.theme.ColorAccentLime
import com.jaarvi.ui.theme.ColorBgGlass
import com.jaarvi.ui.theme.ColorBgPrimary
import com.jaarvi.ui.theme.ColorBorderGlassStrong
import com.jaarvi.ui.theme.ColorGlowGold
import com.jaarvi.ui.theme.ColorGlowLime
import com.jaarvi.ui.theme.ColorTextLabel
import com.jaarvi.ui.theme.ColorTextMuted

enum class ButtonVariant {
    PRIMARY, GLASS, GHOST
}

enum class ButtonSize {
    SM, MD, LG
}

enum class ButtonGlow {
    GOLD, LIME, NONE
}

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.GLASS,
    size: ButtonSize = ButtonSize.MD,
    glow: ButtonGlow = ButtonGlow.NONE,
    enabled: Boolean = true
) {
    val paddingHorizontal = when (size) {
        ButtonSize.SM -> 16.dp
        ButtonSize.MD -> 25.dp
        ButtonSize.LG -> 32.dp
    }
    val paddingVertical = when (size) {
        ButtonSize.SM -> 8.dp
        ButtonSize.MD -> 9.dp
        ButtonSize.LG -> 12.dp
    }
    val textColor = when (variant) {
        ButtonVariant.PRIMARY -> ColorBgPrimary
        ButtonVariant.GLASS -> when (glow) {
            ButtonGlow.GOLD -> ColorAccentGold
            ButtonGlow.LIME -> ColorAccentLime
            ButtonGlow.NONE -> ColorTextLabel
        }
        ButtonVariant.GHOST -> ColorTextMuted
    }
    val shadowElevation = when (glow) {
        ButtonGlow.GOLD, ButtonGlow.LIME -> 8.dp
        ButtonGlow.NONE -> 0.dp
    }
    val shadowColor = when (glow) {
        ButtonGlow.GOLD -> ColorGlowGold
        ButtonGlow.LIME -> ColorGlowLime
        ButtonGlow.NONE -> Color.Transparent
    }
    Box(
        modifier = modifier.then(
            if (glow != ButtonGlow.NONE) {
                Modifier.shadow(
                    elevation = shadowElevation,
                    shape = RoundedCornerShape(Radius.md),
                    ambientColor = shadowColor,
                    spotColor = shadowColor
                )
            } else Modifier
        )
    ) {
        MaterialButton(
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier.then(
                when (variant) {
                    ButtonVariant.PRIMARY -> Modifier
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(ColorAccentGold, ColorAccentLime)
                            ),
                            shape = RoundedCornerShape(Radius.md)
                        )
                        .border(
                            width = 4.dp,
                            color = ColorBgPrimary,
                            shape = RoundedCornerShape(Radius.md)
                        )
                    ButtonVariant.GLASS -> Modifier
                        .background(
                            color = ColorBgGlass,
                            shape = RoundedCornerShape(Radius.md)
                        )
                        .border(
                            width = 1.dp,
                            color = ColorBorderGlassStrong,
                            shape = RoundedCornerShape(Radius.md)
                        )
                    ButtonVariant.GHOST -> Modifier
                }
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = textColor,
                disabledBackgroundColor = Color.Transparent
            ),
            contentPadding = PaddingValues(
                horizontal = paddingHorizontal,
                vertical = paddingVertical
            ),
            shape = RoundedCornerShape(Radius.md)
        ) {
            Text(
                text = text,
                style = when (size) {
                    ButtonSize.SM -> LocalTravelTypography.current.labelSmall
                    ButtonSize.MD -> LocalTravelTypography.current.labelLarge
                    ButtonSize.LG -> LocalTravelTypography.current.labelLarge
                },
                textAlign = TextAlign.Center
            )
        }
    }
}
