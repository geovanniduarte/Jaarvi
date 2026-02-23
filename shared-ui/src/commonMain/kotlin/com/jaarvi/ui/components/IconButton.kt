package com.jaarvi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.theme.ColorBgGlass
import com.jaarvi.ui.theme.ColorBorderGlassStrong

enum class IconButtonVariant {
    GHOST, GLASS
}

enum class IconButtonSize {
    SM, MD, LG
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: IconButtonVariant = IconButtonVariant.GHOST,
    size: IconButtonSize = IconButtonSize.MD,
    content: @Composable () -> Unit
) {
    val padding = when (size) {
        IconButtonSize.SM -> 6.dp
        IconButtonSize.MD -> 8.dp
        IconButtonSize.LG -> 12.dp
    }
    val backgroundColor = when (variant) {
        IconButtonVariant.GHOST -> Color.Transparent
        IconButtonVariant.GLASS -> ColorBgGlass
    }
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .then(
                if (variant == IconButtonVariant.GLASS) {
                    Modifier.border(1.dp, ColorBorderGlassStrong, CircleShape)
                } else Modifier
            )
            .clickable(onClick = onClick)
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
