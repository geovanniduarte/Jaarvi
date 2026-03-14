package com.jaarvi.ui.linda.components.level1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import com.jaarvi.ui.linda.components.level0.icons.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaSearchBox — LEVEL 1
//
// Focused search input with an optional clear (✕) button and a
// `collapsed` state that shows only the search icon. The component
// is NOT responsible for displaying results — the caller pairs it
// with a bottom sheet or any overlay they choose.
// ─────────────────────────────────────────────────────────────

// ── Shape preset ──────────────────────────────────────────────

/** Controls the corner-radius shape of the search box. */
class LindaSearchBoxShape private constructor(internal val value: Int) {
    companion object {
        /** Fully-rounded pill shape (`radiusFull`). */
        val Rounded  : LindaSearchBoxShape = LindaSearchBoxShape(1)
        /** Moderately-rounded rectangle (`radiusMd`). */
        val Rectangle: LindaSearchBoxShape = LindaSearchBoxShape(2)
    }
}

// ── Size preset ───────────────────────────────────────────────

/** Controls height and icon / text sizing of the search box. */
class LindaSearchBoxSize private constructor(
    internal val height   : Dp,
    internal val iconSize : Dp,
    internal val textStyle: TextStyle,
) {
    companion object {
        @Composable fun small()  = LindaSearchBoxSize(
            height    = LindaTheme.spacing.xxl,
            iconSize  = LindaTheme.spacing.lg,
            textStyle = LindaTheme.typography.bodySmall,
        )
        @Composable fun medium() = LindaSearchBoxSize(
            height    = LindaTheme.spacing.xxxl,
            iconSize  = LindaTheme.spacing.xl,
            textStyle = LindaTheme.typography.bodyMedium,
        )
        @Composable fun large()  = LindaSearchBoxSize(
            height    = LindaTheme.spacing.xxxxl,
            iconSize  = LindaTheme.spacing.xxl,
            textStyle = LindaTheme.typography.bodyMedium,
        )
    }
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param value           Current text in the search field.
 * @param onValueChange   Callback on text changes.
 * @param modifier        Optional layout modifier.
 * @param shape           Corner shape — [LindaSearchBoxShape.Rounded] or [LindaSearchBoxShape.Rectangle].
 * @param size            Height / text / icon size preset.
 * @param collapsed       When true, only the search icon is shown.
 * @param enabled         When false, the field is non-interactive.
 * @param onClearClicked  When non-null, a ✕ button is shown while [value] is non-empty.
 * @param placeholderText Slot composable shown while [value] is empty.
 */
@Composable
fun LindaSearchBox(
    value          : String,
    onValueChange  : (String) -> Unit,
    modifier       : Modifier                  = Modifier,
    shape          : LindaSearchBoxShape       = LindaSearchBoxShape.Rounded,
    size           : LindaSearchBoxSize        = LindaSearchBoxSize.medium(),
    collapsed      : Boolean                   = false,
    enabled        : Boolean                   = true,
    onClearClicked : (() -> Unit)?             = null,
    placeholderText: @Composable (() -> Unit)? = null,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val cornerRadius = if (shape.value == 1) borders.radiusFull else borders.radiusMd
    val boxShape     = RoundedCornerShape(cornerRadius)
    val borderColor  = if (isFocused) colors.accentLime else colors.borderGlassStrong

    // ── Collapsed icon-only mode ──────────────────────────────
    if (collapsed) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(size.height)
                .clip(boxShape)
                .background(colors.surfaceGlass)
                .border(borders.widthThin, borderColor, boxShape),
        ) {
            Icon(
                imageVector        = LindaIcons.Default.Search,
                contentDescription = "Search",
                tint               = colors.textMuted,
                modifier           = Modifier.size(size.iconSize),
            )
        }
        return
    }

    // ── Expanded mode ─────────────────────────────────────────
    Row(
        modifier = modifier
            .height(size.height)
            .fillMaxWidth()
            .clip(boxShape)
            .background(colors.surfaceGlass)
            .border(borders.widthThin, borderColor, boxShape),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(spacing.md))
        Icon(
            imageVector        = LindaIcons.Default.Search,
            contentDescription = null,
            tint               = colors.textMuted,
            modifier           = Modifier.size(size.iconSize),
        )
        Spacer(modifier = Modifier.width(spacing.sm))

        // Text field
        Box(modifier = Modifier.weight(1f)) {
            BasicTextField(
                value             = value,
                onValueChange     = onValueChange,
                enabled           = enabled,
                singleLine        = true,
                textStyle         = size.textStyle.copy(color = colors.textPrimary),
                interactionSource = interactionSource,
                modifier          = Modifier.fillMaxWidth(),
                decorationBox     = { inner ->
                    Box {
                        if (value.isEmpty() && placeholderText != null) {
                            placeholderText()
                        }
                        inner()
                    }
                },
            )
        }

        // Animated clear button
        AnimatedVisibility(
            visible = onClearClicked != null && value.isNotEmpty(),
            enter   = fadeIn(tween(150)) + expandHorizontally(),
            exit    = fadeOut(tween(150)) + shrinkHorizontally(),
        ) {
            IconButton(onClick = { onClearClicked?.invoke() }) {
                Icon(
                    imageVector        = LindaIcons.Default.Clear,
                    contentDescription = "Clear search",
                    tint               = colors.textMuted,
                    modifier           = Modifier.size(size.iconSize),
                )
            }
        }

        Spacer(modifier = Modifier.width(spacing.xs))
    }
}