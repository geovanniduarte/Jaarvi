package com.jaarvi.ui.linda.components.level2

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.jaarvi.ui.linda.components.level0.icons.*
import com.jaarvi.ui.linda.components.level1.LindaFAB
import com.jaarvi.ui.linda.components.level1.LindaNavigationItem
import com.jaarvi.ui.linda.components.level1.NavItem
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaNavBarScope — shared DSL scope for LindaNavBar and
// LindaBottomBar (both Level 2).
//
// The scope is implemented by each nav bar. It tracks how many
// items have been placed so it can automatically insert the FAB
// spacer between items 1 and 2 (0-indexed).
//
// Usage inside any nav bar:
//   LindaNavBar(onFabClick = {}) {
//       LindaNavItem(icon = LindaIcons.Default.Home,    label = "Home",    isActive = false, onClick = {})
//       LindaNavItem(icon = LindaIcons.Default.Trips,   label = "Trips",   isActive = true,  onClick = {})
//       LindaNavItem(icon = LindaIcons.Default.Guide,   label = "Guide",   isActive = false, onClick = {})
//       LindaNavItem(icon = LindaIcons.Default.Profile, label = "Profile", isActive = false, onClick = {})
//   }
// ─────────────────────────────────────────────────────────────

// ── Shared scope ─────────────────────────────────────────────

/**
 * DSL scope shared by [LindaNavBar] and [LindaBottomBar].
 * Call [LindaNavItem] for each nav tab — up to 4 times.
 * The FAB spacer is inserted automatically between items 1 and 2.
 */
interface LindaNavBarScope {
    /**
     * Renders a single navigation item.
     *
     * @param icon     Icon vector for the tab.
     * @param label    Label text (auto-uppercased).
     * @param isActive Whether this tab is currently selected.
     * @param onClick  Tap callback for this item.
     */
    @Composable
    fun LindaNavItem(
        icon    : ImageVector,
        label   : String,
        isActive: Boolean    = false,
        onClick : () -> Unit = {},
    )
}

// ─────────────────────────────────────────────────────────────
// LindaNavBar — LEVEL 2
//
// Glassmorphic bottom navigation bar with a central floating FAB.
// Composed from [LindaNavigationItem] (Level 1) + [LindaFAB] (Level 1).
//
// Visual profile:
//   • Top-rounded corners (radiusXl on top only) — anchored to screen bottom
//   • Standard surfaceGlass surface + borderGlass border
//   • Drop shadow via cardElevation / cardShadowAlpha tokens
//
// See also [LindaBottomBar] (Level 2) for the floating pill variant.
// ─────────────────────────────────────────────────────────────

/**
 * @param modifier   Optional layout modifier.
 * @param onFabClick Callback for the central FAB.
 * @param content    [LindaNavBarScope] lambda — call [LindaNavBarScope.LindaNavItem] inside.
 */
@Composable
fun LindaNavBar(
    modifier  : Modifier      = Modifier,
    onFabClick: () -> Unit    = {},
    content   : @Composable LindaNavBarScope.() -> Unit,
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
                    start  = spacing.xxl,
                    end    = spacing.xxl,
                    top    = spacing.lg,
                    bottom = spacing.xxxl,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically,
        ) {
            var itemIndex = 0

            val scope = object : LindaNavBarScope {
                @Composable
                override fun LindaNavItem(
                    icon    : ImageVector,
                    label   : String,
                    isActive: Boolean,
                    onClick : () -> Unit,
                ) {
                    if (itemIndex == 2) {
                        Spacer(modifier = Modifier.width(sizes.fabSize))
                    }
                    LindaNavigationItem(
                        item    = NavItem(icon, label, isActive),
                        onClick = onClick,
                    )
                    itemIndex++
                }
            }

            scope.content()
        }

        // Central FAB — floats above the bar
        LindaFAB(
            onClick  = onFabClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -(sizes.fabSize / 2)),
        )
    }
}