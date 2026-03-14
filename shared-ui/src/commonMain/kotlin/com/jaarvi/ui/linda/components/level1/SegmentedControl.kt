package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaSegmentedControl — LEVEL 1
//
// Horizontal pill-group for single selection from a fixed set of
// options. Slot-based DSL — the caller places LindaSegment children
// inside the builder lambda; content can be icons, icons+text, or
// text-only. Used in Step 3 for Travel Style, Budget, and Pace.
//
//   Unselected — surfaceGlass bg, textMuted label.
//   Selected   — gold→lime gradient bg, onAccent label, lime glow.
// ─────────────────────────────────────────────────────────────

// ── Width distribution ────────────────────────────────────────

/** Controls how segment widths are distributed across the control. */
class SegmentedControlWidthBehavior private constructor(internal val value: Int) {
    companion object {
        /** Each segment width adapts to its own content. */
        val Variable    : SegmentedControlWidthBehavior = SegmentedControlWidthBehavior(1)
        /** All segments share equal proportional width. */
        val Proportional: SegmentedControlWidthBehavior = SegmentedControlWidthBehavior(2)
    }
}

// ── Size preset ───────────────────────────────────────────────

/** Controls vertical and horizontal padding inside each segment. */
class SegmentedControlSize private constructor(
    internal val paddingV: Dp,
    internal val paddingH: Dp,
) {
    companion object {
        @Composable fun small()  = SegmentedControlSize(
            paddingV = LindaTheme.spacing.xs,
            paddingH = LindaTheme.spacing.sm,
        )
        @Composable fun medium() = SegmentedControlSize(
            paddingV = LindaTheme.spacing.sm,
            paddingH = LindaTheme.spacing.md,
        )
        @Composable fun large()  = SegmentedControlSize(
            paddingV = LindaTheme.spacing.md,
            paddingH = LindaTheme.spacing.lg,
        )
    }
}

// ── Scope interface ───────────────────────────────────────────

/** DSL scope — call [LindaSegment] inside [LindaSegmentedControl]'s content lambda. */
interface LindaSegmentedControlScope {
    /** Renders a single segment inside the control. */
    @Composable
    fun LindaSegment(
        selected: Boolean,
        onClick : () -> Unit,
        content : @Composable () -> Unit,
    )
}

// ── Root composable ───────────────────────────────────────────

/**
 * @param selectedSegment Index of the currently selected segment (0-based).
 * @param modifier        Optional layout modifier.
 * @param widthBehavior   [SegmentedControlWidthBehavior.Proportional] or [SegmentedControlWidthBehavior.Variable].
 * @param size            Padding preset for segment items.
 * @param label           Optional label text shown above the control.
 * @param content         DSL lambda — call [LindaSegmentedControlScope.LindaSegment] inside.
 */
@Composable
fun LindaSegmentedControl(
    selectedSegment: Int,
    modifier       : Modifier                      = Modifier,
    widthBehavior  : SegmentedControlWidthBehavior = SegmentedControlWidthBehavior.Proportional,
    size           : SegmentedControlSize          = SegmentedControlSize.medium(),
    label          : String?                       = null,
    content        : @Composable LindaSegmentedControlScope.() -> Unit,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography
    val shadow  = LindaTheme.shadow

    val pillShape = RoundedCornerShape(borders.radiusFull)

    Column(modifier = modifier) {

        // ── Optional label ────────────────────────────────────
        if (label != null) {
            Text(
                text  = label,
                style = typo.labelSmall.copy(color = colors.textMuted),
            )
            Spacer(modifier = Modifier.height(spacing.sm))
        }

        // ── Pill container ────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(pillShape)
                .background(colors.surfaceGlass)
                .border(borders.widthThin, colors.borderGlass, pillShape)
                .padding(spacing.xs),
            horizontalArrangement = Arrangement.spacedBy(spacing.xs),
        ) {
            // The scope implementation renders directly into this Row context.
            // Modifier.weight(1f) is valid here because we're inside a RowScope.
            val rowScope = this

            val scope = object : LindaSegmentedControlScope {
                @Composable
                override fun LindaSegment(
                    selected: Boolean,
                    onClick : () -> Unit,
                    content : @Composable () -> Unit,
                ) {
                    val segmentModifier = if (widthBehavior == SegmentedControlWidthBehavior.Proportional) {
                        with(rowScope) { Modifier.weight(1f) }
                    } else {
                        Modifier
                    }

                    val segmentShape = RoundedCornerShape(borders.radiusFull)

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = segmentModifier
                            .then(
                                if (selected) Modifier.shadow(
                                    elevation    = shadow.glowElevation,
                                    shape        = segmentShape,
                                    ambientColor = colors.glowLime,
                                    spotColor    = colors.glowLime,
                                ) else Modifier
                            )
                            .clip(segmentShape)
                            .background(
                                if (selected) Brush.horizontalGradient(
                                    listOf(colors.accentGold, colors.accentLime),
                                ) else Brush.horizontalGradient(
                                    listOf(colors.surfaceGlass, colors.surfaceGlass),
                                )
                            )
                            .clickable(onClick = onClick)
                            .padding(vertical = size.paddingV, horizontal = size.paddingH),
                    ) {
                        CompositionLocalProvider(
                            LocalContentColor provides if (selected) colors.onAccent else colors.textMuted,
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