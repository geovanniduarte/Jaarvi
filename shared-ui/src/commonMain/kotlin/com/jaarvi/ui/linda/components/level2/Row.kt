package com.jaarvi.ui.linda.components.level2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import com.jaarvi.ui.linda.components.level0.icons.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.level1.LindaCard
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaRow — LEVEL 2
//
// Glassmorphic list row for trip items: destinations, options,
// or preferences. Composes [LindaCard] as the surface.
//
// Leading slot (pick one, mutually exclusive):
//   ordinal != null → gold rounded-square badge with the order number
//   checked != null → gold checkmark box (checked) or glass circle (unchecked)
//
// Trailing slot (pick one or both, both can be null):
//   showDragHandle = true → drag handle icon (muted)
//   onRemove != null      → ✕ close icon (muted)
//
// Usage examples:
//   // Ordered destination row with drag + remove
//   LindaRow(label = "Florence, Italy", ordinal = 1,
//       showDragHandle = true, onRemove = { ... })
//
//   // Toggle-on option row
//   LindaRow(label = "Include Local Expert Guide",
//       checked = true, onToggle = { ... })
//
//   // Toggle-off option row (no ordinal, no remove)
//   LindaRow(label = "Standard Economy Flight",
//       checked = false, onToggle = { ... })
// ─────────────────────────────────────────────────────────────

/**
 * @param label          Row content label.
 * @param modifier       Optional layout modifier.
 * @param ordinal        When non-null, renders a numbered gold badge on the left.
 * @param checked        When non-null, renders a toggle indicator (checked/unchecked) on the right.
 * @param onToggle       Click handler for the toggle indicator; ignored when [checked] is null.
 * @param onRemove       When non-null, renders a ✕ icon on the right.
 * @param showDragHandle When true, renders a drag-handle icon on the right (before remove).
 */
@Composable
fun LindaRow(
    label         : String,
    modifier      : Modifier    = Modifier,
    ordinal       : Int?        = null,
    checked       : Boolean?    = null,
    onToggle      : (() -> Unit)? = null,
    onRemove      : (() -> Unit)? = null,
    showDragHandle: Boolean       = false,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography
    val shadow  = LindaTheme.shadow
    val sizes   = LindaTheme.sizes

    LindaCard(
        modifier       = modifier.fillMaxWidth(),
        glowColor      = androidx.compose.ui.graphics.Color.Transparent,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = spacing.lg,
            vertical   = spacing.md,
        ),
    ) {
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // ── Leading: ordinal badge ────────────────────────
            if (ordinal != null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .shadow(
                            elevation    = shadow.glowElevation,
                            shape        = RoundedCornerShape(borders.radiusSm),
                            ambientColor = colors.glowGold,
                            spotColor    = colors.glowGold,
                        )
                        .clip(RoundedCornerShape(borders.radiusSm))
                        .background(colors.accentGold),
                ) {
                    Text(
                        text  = "$ordinal",
                        style = typo.labelLarge.copy(color = colors.onAccent),
                    )
                }
                Spacer(Modifier.width(spacing.lg))
            }

            // ── Label ─────────────────────────────────────────
            Text(
                text     = label,
                style    = typo.bodyMedium.copy(color = colors.textPrimary),
                modifier = Modifier.weight(1f),
            )

            // ── Trailing: drag handle ─────────────────────────
            if (showDragHandle) {
                Spacer(Modifier.width(spacing.md))
                Icon(
                    imageVector        = LindaIcons.Default.DragHandle,
                    contentDescription = "Drag to reorder",
                    tint               = colors.textMuted,
                    modifier           = Modifier.size(sizes.iconMd),
                )
            }

            // ── Trailing: remove button ───────────────────────
            if (onRemove != null) {
                Spacer(Modifier.width(spacing.sm))
                Icon(
                    imageVector        = LindaIcons.Default.Close,
                    contentDescription = "Remove",
                    tint               = colors.textMuted,
                    modifier           = Modifier
                        .size(sizes.iconSm)
                        .clickable(onClick = onRemove),
                )
            }

            // ── Trailing: toggle indicator ────────────────────
            if (checked != null) {
                Spacer(Modifier.width(spacing.md))
                if (checked) {
                    // Checked: gold rounded square with checkmark
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(borders.radiusSm))
                            .background(colors.accentGold)
                            .then(if (onToggle != null) Modifier.clickable(onClick = onToggle) else Modifier),
                    ) {
                        // Checkmark drawn as two lines approximated by a text glyph
                        Text(
                            text  = "✓",
                            style = typo.labelSmall.copy(color = colors.onAccent),
                        )
                    }
                } else {
                    // Unchecked: glass circle
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(borders.radiusFull))
                            .background(colors.surfaceGlass)
                            .border(
                                width = borders.widthThin,
                                color = colors.borderGlassStrong,
                                shape = RoundedCornerShape(borders.radiusFull),
                            )
                            .then(if (onToggle != null) Modifier.clickable(onClick = onToggle) else Modifier),
                    )
                }
            }
        }
    }
}