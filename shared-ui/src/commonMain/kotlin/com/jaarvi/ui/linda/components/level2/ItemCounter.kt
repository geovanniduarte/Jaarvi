package com.jaarvi.ui.linda.components.level2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.level1.LindaCard
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaItemCounter — LEVEL 2
//
// Composed list-row: descriptive label (+ optional subtitle) on
// the left, and a pill stepper control (− value +) on the right.
// The whole row is wrapped in [LindaCard].
//
// Used for "Group Size", "Number of Days", "Budget Multiplier" etc.
// ─────────────────────────────────────────────────────────────

/**
 * @param label       Primary label describing what is being counted.
 * @param value       Current numeric value shown in the stepper.
 * @param onDecrement Called when the − button is tapped.
 * @param onIncrement Called when the + button is tapped.
 * @param modifier    Optional layout modifier.
 * @param subtitle    Optional smaller description below [label].
 * @param unit        Suffix appended to the displayed value (e.g. "Days", "Pax").
 * @param min         Minimum allowed value — decrement is disabled at this floor.
 * @param max         Maximum allowed value — increment is disabled at this ceiling.
 */
@Composable
fun LindaItemCounter(
    label      : String,
    value      : Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier   : Modifier = Modifier,
    subtitle   : String?  = null,
    unit       : String   = "",
    min        : Int      = 0,
    max        : Int      = 99,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography
    val sizes   = LindaTheme.sizes

    LindaCard(
        modifier       = modifier.fillMaxWidth(),
        glowColor      = androidx.compose.ui.graphics.Color.Transparent,
        contentPadding = PaddingValues(spacing.xl),
    ) {
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // ── Label + subtitle ──────────────────────────────
            Column(
                modifier            = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing.xs),
            ) {
                Text(
                    text  = label,
                    style = typo.bodyMedium.copy(color = colors.textPrimary),
                )
                if (subtitle != null) {
                    Text(
                        text  = subtitle,
                        style = typo.bodySmall.copy(color = colors.textMuted),
                    )
                }
            }

            // ── Stepper pill ──────────────────────────────────
            val pillShape = RoundedCornerShape(borders.radiusFull)

            Row(
                modifier = Modifier
                    .height(38.dp)
                    .clip(pillShape)
                    .background(colors.surfaceGlass)
                    .border(borders.widthThin, colors.borderGlassStrong, pillShape)
                    .padding(horizontal = spacing.lg),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.md),
            ) {
                // ── Decrement ─────────────────────────────────
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(sizes.iconSm)
                        .then(
                            if (value > min) Modifier.clickable(onClick = onDecrement)
                            else Modifier
                        ),
                ) {
                    Box(
                        modifier = Modifier
                            .size(sizes.iconSm * 0.65f, 2.dp)
                            .background(
                                if (value > min) colors.textPrimary
                                else colors.textMuted.copy(alpha = 0.4f),
                                RoundedCornerShape(1.dp),
                            ),
                    )
                }

                // ── Value display ─────────────────────────────
                Text(
                    text  = if (unit.isNotEmpty()) "$value $unit" else "$value",
                    style = typo.headlineMedium.copy(color = colors.textPrimary),
                )

                // ── Increment ─────────────────────────────────
                Icon(
                    imageVector        = LindaIcons.Default.Add,
                    contentDescription = "Increase",
                    tint               = if (value < max) colors.textPrimary
                                         else colors.textMuted.copy(alpha = 0.4f),
                    modifier           = Modifier
                        .size(sizes.iconSm)
                        .then(
                            if (value < max) Modifier.clickable(onClick = onIncrement)
                            else Modifier
                        ),
                )
            }
        }
    }
}