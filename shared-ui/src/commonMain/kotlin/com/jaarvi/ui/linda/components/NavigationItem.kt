package com.jaarvi.ui.linda.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaNavigationItem — standalone, composable navigation icon + label.
//
// Used inside LindaNavBar but also available as a standalone component
// so custom nav layouts (e.g. rail, drawer) can share the same atom.
// ─────────────────────────────────────────────────────────────

/**
 * LindaNavigationItem — single nav tab: icon + uppercased label.
 *
 * When [item.isActive] is true the icon and label are rendered in
 * [LindaColorScheme.accentGold] with a gold glow shadow behind them;
 * otherwise they use [LindaColorScheme.textMuted].
 *
 * @param item    [NavItem] supplying icon, label, and active state.
 * @param onClick Click handler.
 */
@Composable
fun LindaNavigationItem(
    item    : NavItem,
    modifier: Modifier   = Modifier,
    onClick : () -> Unit = {},
) {
    val colors = LindaTheme.colors
    val shadow = LindaTheme.shadow
    val sizes  = LindaTheme.sizes
    val typo   = LindaTheme.typography
    val spacing = LindaTheme.spacing

    val contentColor = if (item.isActive) colors.accentGold else colors.textMuted
    val glowModifier = if (item.isActive) {
        Modifier.shadow(
            elevation    = shadow.glowElevation,
            shape        = CircleShape,
            ambientColor = colors.glowGold,
            spotColor    = colors.glowGold,
        )
    } else Modifier

    Column(
        modifier            = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.xs),
    ) {
        // Icon with optional glow halo
        Box(
            contentAlignment = Alignment.Center,
            modifier         = glowModifier,
        ) {
            Icon(
                imageVector        = item.icon,
                contentDescription = item.label,
                tint               = contentColor,
                modifier           = Modifier.size(sizes.navBarIconSize),
            )
        }

        // Label
        Text(
            text     = item.label.uppercase(),
            style    = typo.labelSmall.copy(color = contentColor),
            modifier = if (item.isActive) {
                Modifier.shadow(
                    elevation    = shadow.glowElevation,
                    ambientColor = colors.glowGold,
                    spotColor    = colors.glowGold,
                )
            } else Modifier,
        )
    }
}
