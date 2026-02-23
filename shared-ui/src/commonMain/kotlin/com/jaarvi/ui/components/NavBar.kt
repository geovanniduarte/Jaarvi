package com.jaarvi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
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
import com.jaarvi.ui.theme.ColorAccentGold
import com.jaarvi.ui.theme.ColorAccentLime
import com.jaarvi.ui.theme.ColorBgGlass
import com.jaarvi.ui.theme.ColorBgPrimary
import com.jaarvi.ui.theme.ColorBorderGlass
import com.jaarvi.ui.theme.ColorGlowGold
import com.jaarvi.ui.theme.ColorTextMuted
import com.jaarvi.ui.theme.IconSize
import com.jaarvi.ui.theme.LocalTravelTypography
import com.jaarvi.ui.theme.Radius
import com.jaarvi.ui.theme.Spacing
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val icon: ImageVector,
    val label: String,
    val isActive: Boolean = false
)

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    onNavItemClick: (Int) -> Unit = {},
    onFabClick: () -> Unit = {}
) {
    val navItems = listOf(
        NavItem(Icons.Default.Home, "Home", false),
        NavItem(Icons.Default.LocationOn, "Trips", true),
        NavItem(Icons.Default.Menu, "Guide", false),
        NavItem(Icons.Default.Person, "Profile", false)
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(topStart = Radius.xl, topEnd = Radius.xl),
                ambientColor = Color.Black.copy(alpha = 0.37f),
                spotColor = Color.Black.copy(alpha = 0.37f)
            )
            .clip(RoundedCornerShape(topStart = Radius.xl, topEnd = Radius.xl))
            .background(ColorBgGlass)
            .border(
                width = 1.dp,
                color = ColorBorderGlass,
                shape = RoundedCornerShape(topStart = Radius.xl, topEnd = Radius.xl)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Spacing.xxl,
                    end = Spacing.xxl,
                    top = Spacing.lg,
                    bottom = Spacing.xxxl
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEachIndexed { index, item ->
                if (index == 2) {
                    Spacer(modifier = Modifier.width(64.dp))
                }
                NavBarItem(item = item, onClick = { onNavItemClick(index) })
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-32).dp)
                .size(64.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape,
                    ambientColor = ColorGlowGold,
                    spotColor = ColorGlowGold
                )
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(ColorAccentGold, ColorAccentLime)
                    )
                )
                .border(4.dp, ColorBgPrimary, CircleShape)
                .clickable(onClick = onFabClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = ColorBgPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun NavBarItem(
    item: NavItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (item.isActive) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = CircleShape,
                            ambientColor = ColorGlowGold,
                            spotColor = ColorGlowGold
                        )
                )
            }
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = if (item.isActive) ColorAccentGold else ColorTextMuted,
                modifier = Modifier.size(IconSize.md)
            )
        }
        Text(
            text = item.label.uppercase(),
            style = LocalTravelTypography.current.labelSmall.copy(
                color = if (item.isActive) ColorAccentGold else ColorTextMuted
            ),
            modifier = Modifier.then(
                if (item.isActive) {
                    Modifier.shadow(
                        elevation = 8.dp,
                        ambientColor = ColorGlowGold,
                        spotColor = ColorGlowGold
                    )
                } else Modifier
            )
        )
    }
}
