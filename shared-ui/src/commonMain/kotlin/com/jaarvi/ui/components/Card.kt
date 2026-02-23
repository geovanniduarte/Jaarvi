package com.jaarvi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.theme.ColorBgGlass
import com.jaarvi.ui.theme.ColorBgGlassLight
import com.jaarvi.ui.theme.ColorBorderGlass
import com.jaarvi.ui.theme.Radius
import com.jaarvi.ui.theme.Spacing

enum class CardVariant {
    GLASS, GLASS_LIGHT
}

@Composable
fun Card(
    modifier: Modifier = Modifier,
    variant: CardVariant = CardVariant.GLASS,
    content: @Composable () -> Unit
) {
    val backgroundColor = when (variant) {
        CardVariant.GLASS -> ColorBgGlass
        CardVariant.GLASS_LIGHT -> ColorBgGlassLight
    }
    Box(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(Radius.lg),
                ambientColor = Color.Black.copy(alpha = 0.37f),
                spotColor = Color.Black.copy(alpha = 0.37f)
            )
            .clip(RoundedCornerShape(Radius.lg))
            .background(color = backgroundColor, shape = RoundedCornerShape(Radius.lg))
            .border(
                width = 1.dp,
                color = ColorBorderGlass,
                shape = RoundedCornerShape(Radius.lg)
            )
    ) {
        Box(modifier = Modifier.padding(Spacing.xxl)) {
            content()
        }
    }
}
