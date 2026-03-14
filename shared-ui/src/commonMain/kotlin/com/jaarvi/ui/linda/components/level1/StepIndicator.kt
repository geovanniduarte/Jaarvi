package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaStepIndicator — LEVEL 1
//
// Horizontal 3-step (or N-step) progress stepper. Appears at the
// top of all wizard screens below the LindaHeader.
//
// Slot-based: the caller declares each step's label via
// [LindaStepScope.step]. The scope collects labels, derives
// totalSteps automatically, and drives all rendering logic.
//
// Step states:
//   Done    — gold→lime gradient circle + checkmark; connector filled.
//   Active  — lime-glow circle + step number; connector muted.
//   Pending — hollow muted circle; connector muted.
//
// Usage:
//   LindaStepIndicator(currentStep = 2) {
//       step("Destination")
//       step("Dates")
//       step("Preferences")
//   }
// ─────────────────────────────────────────────────────────────

// ── Scope ─────────────────────────────────────────────────────

/**
 * DSL scope used to declare step labels inside [LindaStepIndicator].
 * Not @Composable — it purely collects label strings before rendering.
 */
class LindaStepScope internal constructor() {
    internal val labels = mutableListOf<String>()

    /** Registers a step with the given [label]. Call once per step, in order. */
    fun step(label: String) {
        labels.add(label)
    }
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param currentStep 1-indexed position of the currently active step.
 * @param modifier    Optional layout modifier.
 * @param content     [LindaStepScope] lambda — call [LindaStepScope.step] for each step.
 */
@Composable
fun LindaStepIndicator(
    currentStep: Int,
    modifier   : Modifier = Modifier,
    content    : LindaStepScope.() -> Unit,
) {
    val scope      = LindaStepScope().apply(content)
    val stepLabels = scope.labels
    val totalSteps = stepLabels.size

    require(totalSteps >= 2) { "LindaStepIndicator requires at least 2 steps." }

    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val sizes   = LindaTheme.sizes
    val typo    = LindaTheme.typography
    val shadow  = LindaTheme.shadow

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(colors.accentGold, colors.accentLime),
    )

    Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {

        // ── Row: circles + connectors ────────────────────────
        Row(
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            for (step in 1..totalSteps) {
                val isDone    = step < currentStep
                val isActive  = step == currentStep

                // ── Step circle ──────────────────────────────
                val circleSize = if (isActive) sizes.stepCircleLg else sizes.stepCircleSm

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(circleSize)
                        .then(
                            when {
                                isDone || isActive -> Modifier.clip(CircleShape).background(
                                    brush = if (isDone) gradientBrush
                                            else Brush.radialGradient(listOf(colors.accentLime, colors.accentLime)),
                                )
                                else -> Modifier
                                    .clip(CircleShape)
                                    .border(borders.widthThin, colors.textMuted, CircleShape)
                            }
                        )
                        .then(
                            if (isActive) Modifier.shadow(
                                elevation    = shadow.glowElevation,
                                shape        = CircleShape,
                                ambientColor = colors.glowLime,
                                spotColor    = colors.glowLime,
                            ) else Modifier
                        ),
                ) {
                    when {
                        isDone   -> Icon(
                            imageVector        = LindaIcons.Default.Check,
                            contentDescription = "Step $step complete",
                            tint               = colors.onAccent,
                            modifier           = Modifier.size(14.dp),
                        )
                        isActive -> Text(
                            text  = "$step",
                            style = typo.labelLarge.copy(color = colors.onAccent),
                        )
                        else     -> Text(
                            text  = "$step",
                            style = typo.labelSmall.copy(color = colors.textMuted),
                        )
                    }
                }

                // ── Connector line (not after last step) ─────
                if (step < totalSteps) {
                    val connectorFilled = step < currentStep
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .padding(horizontal = spacing.xs)
                            .clip(RoundedCornerShape(borders.radiusFull))
                            .background(
                                if (connectorFilled) gradientBrush
                                else Brush.horizontalGradient(
                                    listOf(
                                        colors.textMuted.copy(alpha = 0.3f),
                                        colors.textMuted.copy(alpha = 0.3f),
                                    )
                                )
                            ),
                    )
                }
            }
        }

        // ── Labels row (rendered below, offset) ─────────────
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .offset(y = sizes.stepCircleLg + spacing.xs),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            stepLabels.forEachIndexed { index, label ->
                val step       = index + 1
                val isDone     = step < currentStep
                val isActive   = step == currentStep
                val labelColor = if (isDone || isActive) colors.textLabel else colors.textMuted

                Text(
                    text      = label,
                    style     = typo.labelSmall.copy(color = labelColor),
                    textAlign = when (index) {
                        0              -> TextAlign.Start
                        totalSteps - 1 -> TextAlign.End
                        else           -> TextAlign.Center
                    },
                    modifier  = Modifier.weight(1f),
                )
            }
        }
    }
}