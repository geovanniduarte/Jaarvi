package com.jaarvi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.jaarvi.ui.theme.ColorBgGlass
import com.jaarvi.ui.theme.ColorBgGlassLight
import com.jaarvi.ui.theme.ColorBorderGlass
import com.jaarvi.ui.theme.ColorGlowGold
import com.jaarvi.ui.theme.ColorGlowLime
import com.jaarvi.ui.theme.ColorOverlayDarkEnd
import com.jaarvi.ui.theme.ColorOverlayDarkStart
import com.jaarvi.ui.theme.ColorOverlayWarmEnd
import com.jaarvi.ui.theme.ColorOverlayWarmStart

enum class DestinationStatus {
    ACTIVE, COMPLETED, PLANNED
}

@Composable
fun DestinationCard(
    imageUrl: String,
    title: String,
    duration: String,
    theme: String,
    modifier: Modifier = Modifier,
    status: DestinationStatus = DestinationStatus.ACTIVE,
    bottomContent: @Composable (() -> Unit)? = null
) {
    val statusColor = when (status) {
        DestinationStatus.ACTIVE -> ColorAccentLime
        DestinationStatus.COMPLETED -> ColorAccentGold
        DestinationStatus.PLANNED -> Color.White.copy(alpha = 0.6f)
    }
    val statusGlow = when (status) {
        DestinationStatus.ACTIVE -> ColorGlowLime
        DestinationStatus.COMPLETED -> ColorGlowGold
        DestinationStatus.PLANNED -> Color.Transparent
    }
    val cardOpacity = when (status) {
        DestinationStatus.ACTIVE, DestinationStatus.COMPLETED -> 1f
        DestinationStatus.PLANNED -> 0.85f
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(Radius.lg),
                ambientColor = Color.Black.copy(alpha = 0.37f),
                spotColor = Color.Black.copy(alpha = 0.37f)
            )
            .clip(RoundedCornerShape(Radius.lg))
            .background(ColorBgGlass.copy(alpha = cardOpacity))
            .border(1.dp, ColorBorderGlass, RoundedCornerShape(Radius.lg))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(208.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(ColorOverlayWarmEnd, ColorOverlayWarmStart)
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    ColorOverlayDarkEnd,
                                    Color(0x33000000),
                                    ColorOverlayDarkStart
                                )
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(Spacing.lg)
                ) {
                    Text(
                        text = title,
                        style = LocalTravelTypography.current.displayMedium.copy(color = Color.White),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.then(
                            if (status != DestinationStatus.PLANNED) {
                                Modifier.shadow(
                                    elevation = 8.dp,
                                    ambientColor = statusGlow,
                                    spotColor = statusGlow
                                )
                            } else Modifier
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "$duration — $theme",
                            style = LocalTravelTypography.current.headlineMedium.copy(color = statusColor)
                        )
                    }
                }
            }
            if (bottomContent != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ColorBgGlassLight)
                        .padding(Spacing.xl)
                ) {
                    bottomContent()
                }
            }
        }
    }
}
