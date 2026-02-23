package com.jaarvi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.theme.ColorBgGlass
import com.jaarvi.ui.theme.ColorBorderGlass
import com.jaarvi.ui.theme.ColorTextPrimary
import com.jaarvi.ui.theme.IconSize
import com.jaarvi.ui.theme.LocalTravelTypography
import com.jaarvi.ui.theme.Spacing

@Composable
fun Header(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBackClick: (() -> Unit)? = null,
    onShareClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                ambientColor = Color.Black.copy(alpha = 0.37f),
                spotColor = Color.Black.copy(alpha = 0.37f)
            )
            .background(ColorBgGlass)
            .border(1.dp, ColorBorderGlass, RoundedCornerShape(0.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.lg),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.width(40.dp)) {
                if (onBackClick != null) {
                    IconButton(onClick = onBackClick, variant = IconButtonVariant.GHOST) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = ColorTextPrimary,
                            modifier = Modifier.size(IconSize.md)
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = LocalTravelTypography.current.headlineLarge.copy(color = ColorTextPrimary)
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = LocalTravelTypography.current.headlineLarge.copy(color = ColorTextPrimary)
                    )
                }
            }
            Box(modifier = Modifier.width(40.dp)) {
                if (onShareClick != null) {
                    IconButton(onClick = onShareClick, variant = IconButtonVariant.GHOST) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = ColorTextPrimary,
                            modifier = Modifier.size(IconSize.md)
                        )
                    }
                }
            }
        }
    }
}
