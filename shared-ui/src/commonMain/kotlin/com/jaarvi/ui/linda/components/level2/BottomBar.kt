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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.jaarvi.ui.linda.components.level1.LindaFAB
import com.jaarvi.ui.linda.components.level1.LindaNavigationItem
import com.jaarvi.ui.linda.components.level1.NavItem
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaBottomBar — LEVEL 2
//
// Floating pill-shaped bottom navigation bar with a central FAB.
// Composed from [LindaNavigationItem] (Level 1) + [LindaFAB] (Level 1).
// Uses the shared [LindaNavBarScope] DSL defined in NavBar.kt.
//
// Visual differences from [LindaNavBar] (Level 2):
//   • Fully rounded shape (radiusXl on all corners) — floats above content
//   • Darker, more minimal glass surface (rgba 20 % black)
//   • Softer border (5 % white) — lower contrast than the standard glass border
//   • Intended to be placed above the system nav bar with appropriate padding
//
// Usage:
//   LindaBottomBar(onFabClick = {}) {
//       LindaNavItem(icon = LindaIcons.Default.Home,    label = "Home",    isActive = false, onClick = {})
//       LindaNavItem(icon = LindaIcons.Default.Trips,   label = "Trips",   isActive = true,  onClick = {})
//       LindaNavItem(icon = LindaIcons.Default.Guide,   label = "Guide",   isActive = false, onClick = {})
//       LindaNavItem(icon = LindaIcons.Default.Profile, label = "Profile", isActive = false, onClick = {})
//   }
// ─────────────────────────────────────────────────────────────

/**
 * @param modifier   Optional layout modifier.
 * @param onFabClick Callback for the central FAB.
 * @param content    [LindaNavBarScope] lambda — call [LindaNavBarScope.LindaNavItem] inside.
 */
@Composable
fun LindaBottomBar(
    modifier  : Modifier      = Modifier,
    onFabClick: () -> Unit    = {},
    content   : @Composable LindaNavBarScope.() -> Unit,
) {
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val sizes   = LindaTheme.sizes

    val shape = RoundedCornerShape(borders.radiusXl)

    // Minimal dark glass — darker than surfaceGlass, softer border
    val darkGlass  = Color.Black.copy(alpha = 0.20f)
    val softBorder = Color.White.copy(alpha = 0.05f)

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        // ── Bar surface ───────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(darkGlass)
                .border(width = borders.widthThin, color = softBorder, shape = shape),
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
                            Spacer(Modifier.width(sizes.fabSize))
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
        }

        // ── Central FAB — floats above bar ────────────────────
        LindaFAB(
            onClick  = onFabClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -(sizes.fabSize / 2)),
        )
    }
}