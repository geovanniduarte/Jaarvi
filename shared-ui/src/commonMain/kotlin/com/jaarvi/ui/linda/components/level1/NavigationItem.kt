package com.jaarvi.ui.linda.components.level1

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
import androidx.compose.ui.graphics.vector.ImageVector
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaNavigationItem — LEVEL 1
//
// Single nav tab: icon + uppercased label.
// Available standalone so custom nav layouts (rail, drawer, etc.)
// can reuse the same atom outside of LindaNavBar.
//
// Active state: accentGold colour + gold glow shadow.
// Inactive state: textMuted colour, no shadow.
// ─────────────────────────────────────────────────────────────

// ── Model ─────────────────────────────────────────────────────

/**
 * Data model for a single navigation item.
 *
 * @param icon     Icon vector.
 * @param label    Display label — uppercased inside the component.
 * @param isActive Whether this item is the currently-selected tab.
 */
data class NavItem(
    val icon    : ImageVector,
    val label   : String,
    val isActive: Boolean = false,
)

// ── Component ─────────────────────────────────────────────────

/**
 * @param item    [NavItem] supplying the icon, label, and active state.
 * @param modifier Optional layout modifier.
 * @param onClick Click handler.
 */
@Composable
fun LindaNavigationItem(
    item    : NavItem,
    modifier: Modifier   = Modifier,
    onClick : () -> Unit = {},
) {
    val colors  = LindaTheme.colors
    val shadow  = LindaTheme.shadow
    val sizes   = LindaTheme.sizes
    val typo    = LindaTheme.typography
    val spacing = LindaTheme.spacing

    val contentColor = if (item.isActive) colors.accentGold else colors.textMuted

    val glowMod = if (item.isActive) Modifier.shadow(
        elevation    = shadow.glowElevation,
        shape        = CircleShape,
        ambientColor = colors.glowGold,
        spotColor    = colors.glowGold,
    ) else Modifier

    Column(
        modifier            = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.xs),
    ) {
        // Icon with optional glow halo
        Box(
            contentAlignment = Alignment.Center,
            modifier         = glowMod,
        ) {
            Icon(
                imageVector        = item.icon,
                contentDescription = item.label,
                tint               = contentColor,
                modifier           = Modifier.size(sizes.navBarIconSize),
            )
        }

        // Uppercased label
        Text(
            text     = item.label.uppercase(),
            style    = typo.labelSmall.copy(color = contentColor),
            modifier = if (item.isActive) Modifier.shadow(
                elevation    = shadow.glowElevation,
                ambientColor = colors.glowGold,
                spotColor    = colors.glowGold,
            ) else Modifier,
        )
    }
}