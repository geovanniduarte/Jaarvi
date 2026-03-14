package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import com.jaarvi.ui.linda.components.level0.icons.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaFAB — LEVEL 1
//
// Floating Action Button. Circular, Gold→Lime gradient fill,
// dark inner-ring border, and a gold glow shadow.
// The centre icon defaults to Add (+).
// ─────────────────────────────────────────────────────────────

// ── Size preset ───────────────────────────────────────────────

enum class FabSize {
    /** Standard FAB — 64 dp (sizes.fabSize). */
    REGULAR,
    /** Mini FAB — 48 dp (sizes.fabMiniSize). */
    MINI,
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param onClick             Click handler.
 * @param modifier            Optional layout modifier.
 * @param size                [FabSize.REGULAR] (64 dp) or [FabSize.MINI] (48 dp).
 * @param icon                Icon displayed inside the FAB.
 * @param contentDescription  Accessibility label for the icon.
 */
@Composable
fun LindaFAB(
    onClick           : () -> Unit,
    modifier          : Modifier    = Modifier,
    size              : FabSize     = FabSize.REGULAR,
    icon              : ImageVector = LindaIcons.Default.Add,
    contentDescription: String      = "Action",
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val shadow  = LindaTheme.shadow
    val sizes   = LindaTheme.sizes

    val diameter = when (size) {
        FabSize.REGULAR -> sizes.fabSize
        FabSize.MINI    -> sizes.fabMiniSize
    }
    val iconSize = when (size) {
        FabSize.REGULAR -> sizes.iconLg
        FabSize.MINI    -> sizes.iconMd
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(diameter)
            .shadow(
                elevation    = shadow.fabGlowElevation,
                shape        = CircleShape,
                ambientColor = colors.glowGold,
                spotColor    = colors.glowGold,
            )
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(colors.accentGold, colors.accentLime),
                ),
            )
            .border(
                width = borders.widthThick,
                color = colors.background,
                shape = CircleShape,
            )
            .clickable(onClick = onClick),
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = contentDescription,
            tint               = colors.onAccent,
            modifier           = Modifier.size(iconSize),
        )
    }
}