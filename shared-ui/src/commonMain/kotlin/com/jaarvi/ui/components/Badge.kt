package com.jaarvi.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.theme.JaarviColors.ColorAccentGold
import com.jaarvi.ui.theme.JaarviColors.ColorAccentLime
import com.jaarvi.ui.theme.JaarviColors.ColorGlowGold
import com.jaarvi.ui.theme.JaarviColors.ColorGlowLime
import com.jaarvi.ui.theme.JaarviColors.ColorTextMuted
import com.jaarvi.ui.theme.LocalTravelTypography

enum class BadgeVariant {
    GOLD, LIME, MUTED
}

@Composable
fun Badge(
    text: String,
    modifier: Modifier = Modifier,
    variant: BadgeVariant = BadgeVariant.GOLD,
    glow: Boolean = true
) {
    val textColor = when (variant) {
        BadgeVariant.GOLD -> ColorAccentGold
        BadgeVariant.LIME -> ColorAccentLime
        BadgeVariant.MUTED -> ColorTextMuted
    }
    val shadowColor = when (variant) {
        BadgeVariant.GOLD -> ColorGlowGold
        BadgeVariant.LIME -> ColorGlowLime
        BadgeVariant.MUTED -> Color.Transparent
    }
    Text(
        text = text.uppercase(),
        style = LocalTravelTypography.current.labelMedium.copy(color = textColor),
        modifier = modifier.then(
            if (glow && variant != BadgeVariant.MUTED) {
                Modifier.shadow(8.dp, ambientColor = shadowColor, spotColor = shadowColor)
            } else Modifier
        )
    )
}
