package com.jaarvi.ui.linda.components.level2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jaarvi.ui.linda.components.level1.LindaCard
import com.jaarvi.ui.linda.components.level1.LindaSlider
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaBudgetSlider — LEVEL 2
//
// Composed budget-input panel inside a [LindaCard].
//
// Layout:
//   ┌─────────────────────────────────────────┐
//   │  Daily Budget              $250 /day    │
//   │  ▐████████████░░░░░░░░░░░░░░░░░░░░░░░░ │
//   └─────────────────────────────────────────┘
//
// "Daily Budget" label — textSecondary, left-aligned
// "$250" value — white display text, right-aligned
// "/day" unit  — accentGold, appended inline
// Slider       — [LindaSlider] (Level 1), full width
// ─────────────────────────────────────────────────────────────

/**
 * @param value           Current slider value within [valueRange].
 * @param onValueChange   Called continuously while the user drags.
 * @param modifier        Optional layout modifier.
 * @param valueRange      Min/max slider range.
 * @param label           Descriptor label shown above the slider (top-left).
 * @param currency        Currency prefix for the displayed value (e.g. "$").
 * @param unit            Unit suffix shown in accentGold (e.g. "/day").
 * @param onValueChangeFinished Called once when drag ends.
 */
@Composable
fun LindaBudgetSlider(
    value                : Float,
    onValueChange        : (Float) -> Unit,
    modifier             : Modifier = Modifier,
    valueRange           : ClosedFloatingPointRange<Float> = 0f..1000f,
    label                : String = "Daily Budget",
    currency             : String = "$",
    unit                 : String = "/day",
    onValueChangeFinished: (() -> Unit)? = null,
) {
    val colors  = LindaTheme.colors
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography

    LindaCard(
        modifier       = modifier.fillMaxWidth(),
        glowColor      = Color.Transparent,
        contentPadding = PaddingValues(spacing.xxl),
    ) {
        Column(
            modifier            = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing.xxl),
        ) {
            // ── Header row ────────────────────────────────────
            Row(
                modifier          = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // Left: descriptor label
                Text(
                    text  = label,
                    style = typo.bodyMedium.copy(color = colors.textSecondary),
                )

                // Right: currency + value + unit (unit in gold)
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text  = "$currency${value.toInt()}",
                        style = typo.displaySmall.copy(color = colors.textPrimary),
                    )
                    Text(
                        text  = unit,
                        style = typo.bodyMedium.copy(color = colors.accentGold),
                    )
                }
            }

            // ── Slider ────────────────────────────────────────
            LindaSlider(
                value                 = value,
                onValueChange         = onValueChange,
                modifier              = Modifier.fillMaxWidth(),
                valueRange            = valueRange,
                onValueChangeFinished = onValueChangeFinished,
            )
        }
    }
}