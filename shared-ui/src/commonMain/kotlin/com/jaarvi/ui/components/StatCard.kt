package com.jaarvi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.theme.ColorTextMuted
import com.jaarvi.ui.theme.ColorTextTertiary
import com.jaarvi.ui.theme.LocalTravelTypography
import com.jaarvi.ui.theme.Spacing

@Composable
fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    subtext: String? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label.uppercase(),
            style = LocalTravelTypography.current.bodySmall.copy(color = ColorTextMuted)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = value,
                style = LocalTravelTypography.current.displaySmall.copy(color = Color.White)
            )
            if (subtext != null) {
                Text(
                    text = subtext,
                    style = LocalTravelTypography.current.bodyMedium.copy(color = ColorTextTertiary)
                )
            }
        }
    }
}

@Composable
fun StatsRow(
    modifier: Modifier = Modifier,
    content: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
        content = content
    )
}
