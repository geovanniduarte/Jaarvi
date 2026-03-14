package com.jaarvi.ui.linda.components.level1

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaTextField — LEVEL 1
//
// Single-line (or multi-line) text input with a glassmorphic
// surface, floating label, and optional character counter.
//
//   Normal  → surfaceGlass bg + borderGlassStrong border
//   Focused → border switches to accentLime + thin lime glow
//   Error   → border + counter in statusError
// ─────────────────────────────────────────────────────────────

/**
 * @param value         Current text value.
 * @param onValueChange Callback on text change.
 * @param label         Floating label / field name (UPPERCASE).
 * @param modifier      Optional layout modifier.
 * @param placeholder   Hint text shown when [value] is empty.
 * @param maxLength     When non-null, shows an "X/max" counter.
 * @param singleLine    True = single-line; false = multi-line.
 * @param isError       When true, borders and counter turn red.
 * @param errorMessage  Optional error message shown below the field.
 * @param enabled       When false the field is non-interactive.
 */
@Composable
fun LindaTextField(
    value        : String,
    onValueChange: (String) -> Unit,
    label        : String,
    modifier     : Modifier = Modifier,
    placeholder  : String   = "",
    maxLength    : Int?     = null,
    singleLine   : Boolean  = true,
    isError      : Boolean  = false,
    errorMessage : String?  = null,
    enabled      : Boolean  = true,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography
    val shadow  = LindaTheme.shadow

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = when {
            isError   -> colors.statusError
            isFocused -> colors.accentLime
            else      -> colors.borderGlassStrong
        },
        animationSpec = tween(durationMillis = 200),
        label         = "linda_text_field_border",
    )

    val shape = RoundedCornerShape(borders.radiusMd)

    // Counter color — gold when close to limit, red on error
    val counterColor = when {
        isError                                              -> colors.statusError
        maxLength != null && value.length >= maxLength * 0.9 -> colors.accentGold
        else                                                 -> colors.textMuted
    }

    Column(modifier = modifier) {

        // ── Glassmorphic field container ──────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(color = colors.surfaceGlass)
                .border(width = borders.widthThin, color = borderColor, shape = shape)
                .then(
                    if (isFocused && !isError) Modifier.shadow(
                        elevation    = shadow.glowElevation,
                        shape        = shape,
                        ambientColor = colors.glowLime,
                        spotColor    = colors.glowLime,
                    ) else Modifier
                )
                .padding(horizontal = spacing.lg, vertical = spacing.md),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                // ── Header row: label + counter ───────────────
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text     = label.uppercase(),
                        style    = typo.labelSmall.copy(color = colors.accentGold),
                        modifier = Modifier.weight(1f),
                    )
                    if (maxLength != null) {
                        Text(
                            text  = "${value.length}/$maxLength",
                            style = typo.labelSmall.copy(color = counterColor),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(spacing.xs))

                // ── Text input ────────────────────────────────
                BasicTextField(
                    value             = value,
                    onValueChange     = { if (maxLength == null || it.length <= maxLength) onValueChange(it) },
                    enabled           = enabled,
                    singleLine        = singleLine,
                    textStyle         = typo.bodyMedium.copy(color = colors.textPrimary),
                    interactionSource = interactionSource,
                    keyboardOptions   = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    visualTransformation = VisualTransformation.None,
                    modifier          = Modifier.fillMaxWidth(),
                    decorationBox     = { inner ->
                        Box {
                            if (value.isEmpty()) {
                                Text(
                                    text  = placeholder,
                                    style = typo.bodyMedium.copy(color = colors.textMuted),
                                )
                            }
                            inner()
                        }
                    },
                )
            }
        }

        // ── Error message ─────────────────────────────────────
        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(spacing.xs))
            Text(
                text     = errorMessage,
                style    = typo.labelSmall.copy(color = colors.statusError),
                modifier = Modifier.padding(horizontal = spacing.xs),
            )
        }
    }
}