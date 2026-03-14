package com.jaarvi.ui.linda.components.level0.icons

import androidx.compose.ui.graphics.vector.ImageVector

// ─────────────────────────────────────────────────────────────
// LindaIcons — LEVEL 0
//
// Root namespace for the Linda icon system. All icons are lazy
// extension properties on either [Filled] or [Outlined], built
// once and cached in a private backing variable per icon.
//
// Usage:
//   Icon(imageVector = LindaIcons.Filled.Home, contentDescription = "Home")
//   Icon(imageVector = LindaIcons.Default.Home, contentDescription = "Home")
// ─────────────────────────────────────────────────────────────

public object LindaIcons {

    /**
     * [Filled] is the default icon theme — solid, high-visibility fills.
     */
    public object Filled

    /**
     * [Outlined] makes use of a thin stroke and empty space inside for a
     * lighter appearance.
     */
    public object Outlined

    /**
     * Alias for [Filled], the baseline icon theme.
     */
    public val Default: Filled = Filled
}
