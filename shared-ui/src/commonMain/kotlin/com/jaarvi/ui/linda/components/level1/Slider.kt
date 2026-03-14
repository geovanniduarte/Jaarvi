package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaSlider — LEVEL 1
//
// Fully custom horizontal range slider — no Material dependency.
//
// Visual spec (per Linda design sheet):
//   Track bg : white 10 % glass pill, 6 dp height
//   Fill     : Gold→Lime horizontal gradient with gold glow,
//              from track-start to thumb centre
//   Thumb    : gold circle 24 dp, 4 dp dark background border
//
// Interaction:
//   • Tap anywhere on the track → jump to that position
//   • Drag anywhere → follows horizontal position
// ─────────────────────────────────────────────────────────────

/**
 * @param value                  Current value, clamped to [valueRange].
 * @param onValueChange          Called continuously while the user drags.
 * @param modifier               Optional layout modifier.
 * @param enabled                When false, input is ignored.
 * @param valueRange             Min/max of the slider value.
 * @param onValueChangeFinished  Called once when drag ends.
 */
@Composable
fun LindaSlider(
    value                : Float,
    onValueChange        : (Float) -> Unit,
    modifier             : Modifier = Modifier,
    enabled              : Boolean  = true,
    valueRange           : ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChangeFinished: (() -> Unit)? = null,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val shadow  = LindaTheme.shadow

    val range    = valueRange.endInclusive - valueRange.start
    val fraction = ((value.coerceIn(valueRange) - valueRange.start) / range).coerceIn(0f, 1f)

    val trackShape = RoundedCornerShape(borders.radiusFull)
    val thumbShape = CircleShape

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(colors.accentGold, colors.accentLime),
    )

    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier
            .height(24.dp)
            .pointerInput(enabled, valueRange) {
                if (!enabled) return@pointerInput
                // Tap → jump
                detectTapGestures { offset ->
                    val newFraction = (offset.x / size.width.toFloat()).coerceIn(0f, 1f)
                    onValueChange(valueRange.start + newFraction * range)
                    onValueChangeFinished?.invoke()
                }
            }
            .pointerInput(enabled, valueRange) {
                if (!enabled) return@pointerInput
                // Drag → follow
                detectDragGestures(
                    onDrag = { change, _ ->
                        val newFraction = (change.position.x / size.width.toFloat())
                            .coerceIn(0f, 1f)
                        onValueChange(valueRange.start + newFraction * range)
                    },
                    onDragEnd   = { onValueChangeFinished?.invoke() },
                    onDragCancel = { onValueChangeFinished?.invoke() },
                )
            },
    ) {
        val trackWidthPx = constraints.maxWidth.toFloat()

        // ── Track background ──────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .align(Alignment.CenterStart)
                .clip(trackShape)
                .background(Color.White.copy(alpha = 0.10f)),
        ) {
            // ── Gradient fill ─────────────────────────────────
            if (fraction > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction)
                        .fillMaxHeight()
                        .clip(trackShape)
                        .background(gradientBrush)
                        .shadow(
                            elevation    = shadow.glowElevation,
                            shape        = trackShape,
                            ambientColor = colors.glowGold,
                            spotColor    = colors.glowGold,
                        ),
                )
            }
        }

        // ── Thumb ─────────────────────────────────────────────
        val thumbOffsetDp: Dp = with(density) {
            (trackWidthPx * fraction - 12.dp.toPx()).coerceAtLeast(0f).toDp()
        }
        Box(
            modifier = Modifier
                .offset(x = thumbOffsetDp)
                .size(24.dp)
                .align(Alignment.CenterStart)
                .shadow(
                    elevation    = shadow.glowElevation,
                    shape        = thumbShape,
                    ambientColor = colors.glowGold,
                    spotColor    = colors.glowGold,
                )
                .border(4.dp, colors.background, thumbShape)
                .clip(thumbShape)
                .background(colors.accentGold),
        )
    }
}
