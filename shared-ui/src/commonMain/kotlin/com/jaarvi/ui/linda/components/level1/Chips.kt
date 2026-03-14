package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaChip — LEVEL 1
//
// Single filter / selection chip in full-pill shape.
//
//   selected = true  → accentGold fill, dark onAccent text
//   selected = false → surfaceGlass fill, borderGlass border, white text
//
// LindaChipRow — horizontal lazy-scrolling row of LindaChips.
//
// Slot-based: the caller supplies chip items via [LazyListScope],
// giving full control over what chips appear and in what order.
//
// Usage:
//   LindaChipRow {
//       item { LindaChip(label = "Paris",   selected = true,  onClick = { … }) }
//       item { LindaChip(label = "London",  selected = false, onClick = { … }) }
//       item { LindaChip(label = "Tokyo",   selected = false, onClick = { … }) }
//   }
// ─────────────────────────────────────────────────────────────

/**
 * @param label    Chip label text.
 * @param selected Whether this chip is the active selection.
 * @param onClick  Click handler.
 * @param modifier Optional layout modifier.
 */
@Composable
fun LindaChip(
    label   : String,
    selected: Boolean    = false,
    onClick : () -> Unit = {},
    modifier: Modifier   = Modifier,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography

    val shape    = RoundedCornerShape(borders.radiusFull)
    val bgColor  = if (selected) colors.accentGold else colors.surfaceGlass
    val txtColor = if (selected) colors.onAccent   else Color.White

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .background(bgColor)
            .then(
                if (!selected) Modifier.border(
                    width = borders.widthThin,
                    color = colors.borderGlassStrong,
                    shape = shape,
                ) else Modifier
            )
            .clickable(onClick = onClick)
            .padding(horizontal = spacing.lg, vertical = spacing.sm),
    ) {
        Text(
            text  = label,
            style = typo.labelLarge.copy(color = txtColor),
        )
    }
}

// ─────────────────────────────────────────────────────────────

/**
 * Horizontal lazy-scrolling row. The caller controls chips entirely
 * via the [LazyListScope] content lambda.
 *
 * @param modifier Optional layout modifier.
 * @param content  [LazyListScope] lambda — add chips with `item { LindaChip(…) }`.
 */
@Composable
fun LindaChipRow(
    modifier: Modifier           = Modifier,
    content : LazyListScope.() -> Unit,
) {
    val spacing = LindaTheme.spacing

    LazyRow(
        modifier              = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing.sm),
        contentPadding        = PaddingValues(horizontal = spacing.xs),
        content               = content,
    )
}