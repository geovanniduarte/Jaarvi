package com.jaarvi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.theme.JaarviColors.ColorBgGlass
import com.jaarvi.ui.theme.JaarviColors.ColorBorderGlassStrong

enum class AvatarSize {
    SM, MD, LG
}

@Composable
fun Avatar(
    imageUrl: String,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.MD,
    contentDescription: String? = null
) {
    val avatarSizeDp = when (size) {
        AvatarSize.SM -> 32.dp
        AvatarSize.MD -> 36.dp
        AvatarSize.LG -> 48.dp
    }
    Box(
        modifier = modifier
            .size(avatarSizeDp)
            .clip(CircleShape)
            .background(ColorBgGlass)
            .border(1.dp, ColorBorderGlassStrong, CircleShape)
            .clip(CircleShape)
            .background(Color.Gray)
    )
}

@Composable
fun AvatarGroup(
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
    max: Int = 3,
    size: AvatarSize = AvatarSize.MD
) {
    val displayUrls = imageUrls.take(max)
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-8).dp)
    ) {
        displayUrls.forEach { url ->
            Avatar(imageUrl = url, size = size)
        }
    }
}
