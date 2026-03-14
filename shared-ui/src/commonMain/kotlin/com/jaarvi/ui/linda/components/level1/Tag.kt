package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import com.jaarvi.ui.linda.components.level0.icons.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaTag — LEVEL 1
//
// Wrapping multi-select group of pill-shaped tags. Slot-based DSL
// consistent with LindaSegmentedControl — each tag's content
// (icon + label, emoji + text, text-only) is fully caller-controlled.
// Uses FlowRow so items wrap naturally. Used in Step 3 for Interests.
//
//   Unselected — surfaceGlass bg, borderGlass border, textMuted content.
//   Selected   — gradient border (accentGold → accentLime),
//                accentLime content + lime glow + auto-prepended checkmark.
//
// Requires androidx.compose.foundation.layout.FlowRow
// (Compose Foundation ≥ 1.6 / Compose BOM ≥ 2024.01).
// ─────────────────────────────────────────────────────────────

// ── Scope interface ───────────────────────────────────────────

/** DSL scope — call [LindaTag] inside [LindaTagGroup]'s content lambda. */
interface LindaTagGroupScope {
    /** Renders a single pill-shaped tag inside the group. */
    @Composable
    fun LindaTag(
        checked : Boolean,
        onClick : () -> Unit,
        content : @Composable () -> Unit,
    )
}

// ── Root composable ───────────────────────────────────────────

/**
 * @param label    Optional label slot rendered above the group.
 * @param modifier Optional layout modifier.
 * @param content  DSL lambda — call [LindaTagGroupScope.LindaTag] inside.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LindaTagGroup(
    label   : @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    content : @Composable LindaTagGroupScope.() -> Unit,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val shadow  = LindaTheme.shadow

    Column(modifier = modifier) {

        // ── Optional label ────────────────────────────────────
        if (label != null) {
            label()
            Spacer(modifier = Modifier.height(spacing.sm))
        }

        // ── FlowRow of tags ───────────────────────────────────
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(spacing.sm),
            verticalArrangement   = Arrangement.spacedBy(spacing.sm),
        ) {
            val scope = object : LindaTagGroupScope {
                @Composable
                override fun LindaTag(
                    checked : Boolean,
                    onClick : () -> Unit,
                    content : @Composable () -> Unit,
                ) {
                    val itemShape = RoundedCornerShape(borders.radiusFull)

                    val itemModifier = Modifier
                        .then(
                            if (checked) Modifier.shadow(
                                elevation    = shadow.glowElevation,
                                shape        = itemShape,
                                ambientColor = colors.glowLime,
                                spotColor    = colors.glowLime,
                            ) else Modifier
                        )
                        .clip(itemShape)
                        .background(colors.surfaceGlass)
                        .border(
                            width = borders.widthThin,
                            brush = if (checked) Brush.horizontalGradient(
                                listOf(colors.accentGold, colors.accentLime),
                            ) else Brush.horizontalGradient(
                                listOf(colors.borderGlass, colors.borderGlass),
                            ),
                            shape = itemShape,
                        )
                        .clickable(onClick = onClick)
                        .padding(
                            horizontal = spacing.md,
                            vertical   = spacing.sm,
                        )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier          = itemModifier,
                    ) {
                        // Auto-prepend checkmark when checked
                        if (checked) {
                            Icon(
                                imageVector        = LindaIcons.Default.Check,
                                contentDescription = null,
                                tint               = colors.accentLime,
                                modifier           = Modifier.size(14.dp),
                            )
                            Spacer(modifier = Modifier.width(spacing.xs))
                        }

                        CompositionLocalProvider(
                            LocalContentColor provides if (checked) colors.accentLime else colors.textMuted,
                        ) {
                            content()
                        }
                    }
                }
            }

            scope.content()
        }
    }
}
