package com.jaarvi.ui.linda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// NavItem model
// ─────────────────────────────────────────────────────────────

/**
 * Model for a single navigation bar item.
 *
 * @param icon     Icon vector.
 * @param label    Display label (uppercased inside the component).
 * @param isActive Whether this item is the currently-selected tab.
 */
data class NavItem(
    val icon    : ImageVector,
    val label   : String,
    val isActive: Boolean = false,
)

// ─────────────────────────────────────────────────────────────

/**
 * LindaNavBar — glassmorphic bottom navigation bar with a central FAB.
 *
 * The bar contains four [NavigationItem] slots. A [LindaFAB] is centred
 * above the bar and offset upward so it floats over the content.
 *
 * @param items          List of exactly four [NavItem]s.
 * @param onNavItemClick Callback with the tapped item index.
 * @param onFabClick     Callback for the central FAB.
 */
@Composable
fun LindaNavBar(
    modifier      : Modifier           = Modifier,
    items         : List<NavItem>      = defaultNavItems,
    onNavItemClick: (Int) -> Unit      = {},
    onFabClick    : () -> Unit         = {},
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val shadow  = LindaTheme.shadow
    val sizes   = LindaTheme.sizes

    val shape = RoundedCornerShape(topStart = borders.radiusXl, topEnd = borders.radiusXl)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation    = shadow.cardElevation,
                shape        = shape,
                ambientColor = Color.Black.copy(alpha = shadow.cardShadowAlpha),
                spotColor    = Color.Black.copy(alpha = shadow.cardShadowAlpha),
            )
            .clip(shape)
            .background(colors.surfaceGlass)
            .border(width = borders.widthThin, color = colors.borderGlass, shape = shape),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start   = spacing.xxl,
                    end     = spacing.xxl,
                    top     = spacing.lg,
                    bottom  = spacing.xxxl,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically,
        ) {
            items.forEachIndexed { index, item ->
                if (index == 2) {
                    // Spacer reserves space for the floating FAB
                    Spacer(modifier = Modifier.width(sizes.fabSize))
                }
                LindaNavigationItem(
                    item = item,
                    onClick = { onNavItemClick(index) },
                )
            }
        }

        // Central FAB — floats above the bar
        LindaFAB(
            onClick = onFabClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-sizes.fabSize / 2)),
        )
    }
}

// ─── Default nav items ────────────────────────────────────────
private val defaultNavItems = listOf(
    NavItem(Icons.Default.Home,       "Home",    false),
    NavItem(Icons.Default.LocationOn, "Trips",   true),
    NavItem(Icons.Default.Menu,   "Guide",   false),
    NavItem(Icons.Default.Person,     "Profile", false),
)

