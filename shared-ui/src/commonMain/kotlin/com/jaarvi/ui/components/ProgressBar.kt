package com.jaarvi.ui.components

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
import com.jaarvi.ui.theme.LocalTravelTypography
import com.jaarvi.ui.theme.Radius
import com.jaarvi.ui.theme.Spacing
import com.jaarvi.ui.theme.ColorAccentGold
import com.jaarvi.ui.theme.ColorAccentLime
import com.jaarvi.ui.theme.ColorGlowGold
import com.jaarvi.ui.theme.ColorGlowLime
import com.jaarvi.ui.theme.ColorTextLabel

enum class ProgressVariant {
    GRADIENT, GOLD, LIME
}

enum class ProgressSize {
    SM, MD
}

@Composable
fun ProgressBar(
    value: Float,
    modifier: Modifier = Modifier,
    max: Float = 100f,
    variant: ProgressVariant = ProgressVariant.GRADIENT,
    showLabel: Boolean = true,
    label: String = "",
    size: ProgressSize = ProgressSize.MD
) {
    val percentage = (value / max * 100f).coerceIn(0f, 100f)
    val animatedPercentage by animateFloatAsState(
        targetValue = percentage / 100f,
        animationSpec = tween(durationMillis = 500),
        label = "progress"
    )
    val heightDp = when (size) {
        ProgressSize.SM -> 6.dp
        ProgressSize.MD -> 10.dp
    }
    val progressBrush = when (variant) {
        ProgressVariant.GRADIENT -> Brush.horizontalGradient(
            colors = listOf(ColorAccentGold, ColorAccentLime)
        )
        ProgressVariant.GOLD -> Brush.horizontalGradient(
            colors = listOf(ColorAccentGold, ColorAccentGold)
        )
        ProgressVariant.LIME -> Brush.horizontalGradient(
            colors = listOf(ColorAccentLime, ColorAccentLime)
        )
    }
    val shadowColor = when (variant) {
        ProgressVariant.GRADIENT, ProgressVariant.LIME -> ColorGlowLime
        ProgressVariant.GOLD -> ColorGlowGold
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        if (showLabel) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label.uppercase(),
                    style = LocalTravelTypography.current.labelMedium.copy(color = ColorTextLabel)
                )
                Text(
                    text = "${percentage.toInt()}%",
                    style = LocalTravelTypography.current.labelMedium.copy(color = ColorAccentLime),
                    modifier = Modifier.shadow(
                        elevation = 8.dp,
                        ambientColor = ColorGlowLime,
                        spotColor = ColorGlowLime
                    )
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(heightDp)
                .clip(RoundedCornerShape(Radius.full))
                .background(Color(0x4D000000))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(Radius.full)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedPercentage)
                    .clip(RoundedCornerShape(Radius.full))
                    .background(progressBrush)
                    .shadow(
                        elevation = 4.dp,
                        ambientColor = shadowColor,
                        spotColor = shadowColor
                    )
            )
        }
    }
}
