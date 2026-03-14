package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaRadioButton — LEVEL 1
//
// Custom circular radio indicator + label row. No Material
// RadioButton dependency — reads all tokens from LindaTheme.
//
// Selected   → accentGold outer ring 24 dp + dark inner dot 10 dp
// Unselected → surfaceGlass circle 24 dp + borderGlassStrong ring
//
// LindaRadioGroup — vertical container of radio items that enforces
// single-selection layout. Slot-based DSL consistent with
// LindaCheckboxGroup — the caller places LindaRadioButton children
// inside the content lambda.
//
// Usage:
//   LindaRadioGroup {
//       LindaRadioButton(label = "Economy",  selected = pace == 0, onClick = { pace = 0 })
//       LindaRadioButton(label = "Business", selected = pace == 1, onClick = { pace = 1 })
//   }
// ─────────────────────────────────────────────────────────────

/**
 * @param label    Display label next to the radio indicator.
 * @param selected Whether this option is currently selected.
 * @param onClick  Click / selection handler.
 * @param modifier Optional layout modifier.
 */
@Composable
fun LindaRadioButton(
    label   : String,
    selected: Boolean,
    onClick : () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography

    Row(
        modifier          = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.lg),
    ) {
        // ── Radio indicator ───────────────────────────────────
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    if (selected) colors.accentGold else colors.surfaceGlass
                )
                .then(
                    if (!selected) Modifier.border(
                        width = borders.widthThin,
                        color = colors.borderGlassStrong,
                        shape = CircleShape,
                    ) else Modifier
                ),
        ) {
            if (selected) {
                // Dark inner dot
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(colors.background),
                )
            }
        }

        // ── Label ─────────────────────────────────────────────
        Text(
            text  = label,
            style = typo.bodySmall.copy(
                color = if (selected) colors.textPrimary else colors.textMuted,
            ),
        )
    }
}

// ─────────────────────────────────────────────────────────────

// ── Scope interface ───────────────────────────────────────────

/** DSL scope — call [LindaRadioButton] inside [LindaRadioGroup]'s content lambda. */
interface LindaRadioGroupScope {
    /**
     * Renders a single radio button inside the group.
     *
     * @param label    Display label for this option.
     * @param selected Whether this option is currently selected.
     * @param onClick  Selection callback.
     */
    @Composable
    fun LindaRadioButton(
        label   : String,
        selected: Boolean,
        onClick : () -> Unit,
    )
}

// ── Root composable ───────────────────────────────────────────

/**
 * Vertical exclusive-selection container. The caller is responsible
 * for all option creation and selection logic.
 *
 * @param modifier Optional layout modifier.
 * @param content  DSL lambda — call [LindaRadioGroupScope.LindaRadioButton] inside.
 */
@Composable
fun LindaRadioGroup(
    modifier: Modifier = Modifier,
    content : @Composable LindaRadioGroupScope.() -> Unit,
) {
    val spacing = LindaTheme.spacing

    Column(
        modifier            = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.md),
    ) {
        val scope = object : LindaRadioGroupScope {
            @Composable
            override fun LindaRadioButton(
                label   : String,
                selected: Boolean,
                onClick : () -> Unit,
            ) {
                // Delegates to the top-level LindaRadioButton composable above.
                com.jaarvi.ui.linda.components.level1.LindaRadioButton(
                    label    = label,
                    selected = selected,
                    onClick  = onClick,
                )
            }
        }
        scope.content()
    }
}