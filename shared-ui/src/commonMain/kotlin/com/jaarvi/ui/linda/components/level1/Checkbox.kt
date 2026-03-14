package com.jaarvi.ui.linda.components.level1

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.jaarvi.ui.linda.components.level0.icons.*
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaCheckbox — LEVEL 1
//
// Square boolean selection control: a rounded box (checked /
// unchecked) placed left of a text label. Matches the Figma spec:
//
//   Checked   — solid accentGold fill, dark checkmark centred.
//   Unchecked — surfaceGlass fill + borderGlass stroke, no mark.
//
// Standalone atom: [LindaCheckbox]
// Grouped form:    [LindaCheckboxGroup] with DSL scope
//
// Layout per item
//   ┌──────┐
//   │  ✓   │  Label text
//   └──────┘
//   24 × 24 box — radiusSm (8 dp) — gap spacing.lg (16 dp)
// ─────────────────────────────────────────────────────────────

// ── Scope interface ───────────────────────────────────────────

/** DSL scope — call [LindaCheckbox] inside [LindaCheckboxGroup]'s content lambda. */
interface LindaCheckboxGroupScope {
    /** Renders a single checkbox item inside the group. */
    @Composable
    fun LindaCheckbox(
        checked         : Boolean,
        onCheckedChange : () -> Unit,
        label           : String,
    )
}

// ── Root composable ───────────────────────────────────────────

/**
 * @param modifier Optional layout modifier.
 * @param content  DSL lambda — call [LindaCheckboxGroupScope.LindaCheckbox] inside.
 */
@Composable
fun LindaCheckboxGroup(
    modifier: Modifier = Modifier,
    content : @Composable LindaCheckboxGroupScope.() -> Unit,
) {
    val spacing = LindaTheme.spacing

    Column(
        modifier            = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.md),
    ) {
        val scope = object : LindaCheckboxGroupScope {
            @Composable
            override fun LindaCheckbox(
                checked         : Boolean,
                onCheckedChange : () -> Unit,
                label           : String,
            ) {
                // Use fully-qualified name to resolve the top-level function,
                // not this scope override — avoids infinite recursion.
                com.jaarvi.ui.linda.components.level1.LindaCheckbox(
                    checked         = checked,
                    onCheckedChange = onCheckedChange,
                    label           = label,
                )
            }
        }
        scope.content()
    }
}

// ── Standalone atom ───────────────────────────────────────────

/**
 * Standalone square checkbox atom — box + label in a Row.
 *
 * Prefer [LindaCheckboxGroup] when rendering a vertical list of items;
 * use this directly when a single, isolated checkbox is needed.
 *
 * @param checked         Current selection state.
 * @param onCheckedChange Called when the user taps the control.
 * @param label           Text label rendered to the right of the box.
 * @param modifier        Optional layout modifier.
 */
@Composable
fun LindaCheckbox(
    checked         : Boolean,
    onCheckedChange : () -> Unit,
    label           : String,
    modifier        : Modifier = Modifier,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val sizes   = LindaTheme.sizes
    val typo    = LindaTheme.typography

    val boxShape = RoundedCornerShape(borders.radiusSm)   // 8 dp — matches Figma

    // Smooth fill transition between unchecked (glass) and checked (gold).
    val bgColor by animateColorAsState(
        targetValue   = if (checked) colors.accentGold else colors.surfaceGlass,
        animationSpec = tween(durationMillis = 150),
        label         = "checkboxBg",
    )

    Row(
        modifier          = modifier
            .clickable(onClick = onCheckedChange),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ── Selectable box ────────────────────────────────────
        Box(
            modifier = Modifier
                .size(sizes.iconLg)                        // 24 × 24 dp
                .clip(boxShape)
                .background(bgColor)
                .then(
                    if (!checked) Modifier.border(
                        width = borders.widthThin,
                        color = colors.borderGlass,
                        shape = boxShape,
                    ) else Modifier,
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) {
                Icon(
                    imageVector        = LindaIcons.Default.Check,
                    contentDescription = null,
                    tint               = colors.background, // dark mark on gold bg
                    modifier           = Modifier.size(sizes.iconSm),  // 16 dp
                )
            }
        }

        Spacer(modifier = Modifier.width(spacing.lg))     // 16 dp gap

        // ── Label ─────────────────────────────────────────────
        Text(
            text  = label,
            style = typo.bodySmall.copy(color = colors.textPrimary),
        )
    }
}