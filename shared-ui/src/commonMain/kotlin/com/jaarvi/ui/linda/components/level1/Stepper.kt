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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import com.jaarvi.ui.linda.components.level0.icons.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaStepper — LEVEL 1
//
// Stepper with "−" and "+" icon buttons flanking a fully caller-
// defined value slot. The component owns only the chrome (button
// layout, disabled styling, size tokens); all value logic lives in
// the caller. Used for Days Count per destination in Step 2.
//
// The component calls onClick(true)  → "+" pressed
//                    onClick(false) → "−" pressed
// The caller decides how to clamp / change the value.
// ─────────────────────────────────────────────────────────────

// ── Size preset ───────────────────────────────────────────────

/** Controls icon-button size and the minimum width of the value area. */
class LindaStepperSize private constructor(
    internal val buttonSize    : Dp,
    internal val centerMinWidth: Dp,
) {
    companion object {
        @Composable fun small()  = LindaStepperSize(
            buttonSize     = LindaTheme.spacing.xl,
            centerMinWidth = LindaTheme.spacing.xxl,
        )
        @Composable fun medium() = LindaStepperSize(
            buttonSize     = LindaTheme.spacing.xxl,
            centerMinWidth = LindaTheme.spacing.xxxl,
        )
        @Composable fun large()  = LindaStepperSize(
            buttonSize     = LindaTheme.spacing.xxxl,
            centerMinWidth = LindaTheme.spacing.xxxxl,
        )
    }
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param onClick            Called with `true` for "+" and `false` for "−".
 * @param valueContent       Slot — the caller renders the current value here.
 * @param modifier           Optional layout modifier.
 * @param size               Button / center-area size preset.
 * @param incrementEnabled   When false the "+" button is dimmed and non-interactive.
 * @param decrementEnabled   When false the "−" button is dimmed and non-interactive.
 * @param accessibilityValue Announced by screen readers for the center area.
 * @param label              Optional label slot above the stepper row.
 * @param helper             Optional helper slot below the stepper row.
 */
@Composable
fun LindaStepper(
    onClick           : (isIncrement: Boolean) -> Unit,
    valueContent      : @Composable () -> Unit,
    modifier          : Modifier         = Modifier,
    size              : LindaStepperSize = LindaStepperSize.medium(),
    incrementEnabled  : Boolean          = true,
    decrementEnabled  : Boolean          = true,
    accessibilityValue: String           = "",
    label             : @Composable (() -> Unit)? = null,
    helper            : @Composable (() -> Unit)? = null,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing

    Column(
        modifier            = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.xs),
    ) {

        // ── Optional label ────────────────────────────────────
        label?.invoke()

        // ── Main stepper row ──────────────────────────────────
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            // ── Decrement button ──────────────────────────────
            StepperIconButton(
                enabled            = decrementEnabled,
                size               = size.buttonSize,
                onClick            = { onClick(false) },
                contentDescription = "Decrement",
            ) {
                Box(
                    modifier = Modifier
                        .size(size.buttonSize * 0.45f, 2.dp)
                        .background(
                            if (decrementEnabled) colors.textLabel else colors.textMuted,
                            RoundedCornerShape(1.dp),
                        ),
                )
            }

            // ── Value slot ────────────────────────────────────
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .semantics { contentDescription = accessibilityValue },
            ) {
                valueContent()
            }

            // ── Increment button ──────────────────────────────
            StepperIconButton(
                enabled            = incrementEnabled,
                size               = size.buttonSize,
                onClick            = { onClick(true) },
                contentDescription = "Increment",
            ) {
                Icon(
                    imageVector        = LindaIcons.Default.Add,
                    contentDescription = null,
                    tint               = if (incrementEnabled) colors.textLabel else colors.textMuted,
                    modifier           = Modifier.size(size.buttonSize * 0.5f),
                )
            }
        }

        // ── Optional helper ───────────────────────────────────
        helper?.invoke()
    }
}

// ── Private: GLASS icon button ────────────────────────────────
@Composable
private fun StepperIconButton(
    enabled           : Boolean,
    size              : Dp,
    onClick           : () -> Unit,
    contentDescription: String,
    icon              : @Composable () -> Unit,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val shape   = RoundedCornerShape(borders.radiusMd)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .alpha(if (enabled) 1f else 0.35f)
            .clip(shape)
            .background(colors.surfaceGlass)
            .border(borders.widthThin, colors.borderGlassStrong, shape)
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
            .semantics { this.contentDescription = contentDescription },
    ) {
        icon()
    }
}