package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaFAB size variants
// ─────────────────────────────────────────────────────────────

enum class FabSize {
    /** Standard FAB — 64 dp. */
    REGULAR,
    /** Mini FAB — 48 dp. */
    MINI,
}

// ─────────────────────────────────────────────────────────────

/**
 * LindaFAB — Floating Action Button.
 *
 * Renders a circular button with a Gold→Lime gradient fill, a dark
 * inner ring border, and a gold glow shadow. The centre icon defaults
 * to a "+" (Add) symbol.
 *
 * @param onClick           Click handler.
 * @param size              [FabSize.REGULAR] (64 dp) or [FabSize.MINI] (48 dp).
 * @param icon              Icon to display inside the FAB.
 * @param contentDescription Accessibility label for the icon.
 */
@Composable
fun LindaFAB(
    onClick           : () -> Unit,
    modifier          : Modifier  = Modifier,
    size              : FabSize   = FabSize.REGULAR,
    icon              : ImageVector = Icons.Default.Add,
    contentDescription: String    = "Action",
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
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = contentDescription,
            tint               = colors.onAccent,
            modifier           = Modifier.size(iconSize),
        )
    }
}
