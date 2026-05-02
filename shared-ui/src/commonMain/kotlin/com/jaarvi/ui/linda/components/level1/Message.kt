package com.jaarvi.ui.linda.components.level1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.jaarvi.ui.linda.components.level0.icons.Check
import com.jaarvi.ui.linda.components.level0.icons.Info
import com.jaarvi.ui.linda.components.level0.icons.LindaIcons
import com.jaarvi.ui.linda.components.level0.icons.Warning
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaMessage — LEVEL 1
//
// Animated inline message banner that supports three semantic
// types, each with a distinct accent colour and icon:
//
//   Error       → statusError (red)    · Warning icon
//   Informative → statusInfo  (blue)   · Info icon
//   Success     → statusSuccess (lime) · Check icon
//
// The banner:
//   • is invisible when [message] is null
//   • slides in/out with a vertical slide + fade animation
//   • shows a coloured left-side accent bar for quick scanning
//   • uses surfaceGlass background to stay on-theme
//
// Usage:
//   // Error — auto-dismissed after 5 s via LaunchedEffect in the caller
//   LindaMessage(
//       message = state.step1Error,
//       type    = LindaMessageType.Error,
//       modifier = Modifier.fillMaxWidth(),
//   )
//
//   // Informative
//   LindaMessage(
//       message = "Your trip was synced.",
//       type    = LindaMessageType.Informative,
//   )
//
//   // Success
//   LindaMessage(
//       message = "Destination added successfully.",
//       type    = LindaMessageType.Success,
//   )
// ─────────────────────────────────────────────────────────────

// ── Type ──────────────────────────────────────────────────────

/**
 * Semantic type for [LindaMessage], controlling accent colour and icon.
 *
 * - [Error]       — red accent, warning icon. Use for commit-action failures.
 * - [Informative] — blue accent, info icon. Use for neutral contextual notes.
 * - [Success]     — lime accent, check icon. Use for positive confirmations.
 */
enum class LindaMessageType { Error, Informative, Success }

// ── Component ─────────────────────────────────────────────────

/**
 * Inline message banner with type-driven colour and icon.
 *
 * @param message  Text to display, or `null` to hide the banner.
 * @param type     Semantic type — controls accent colour and leading icon.
 * @param modifier Optional layout modifier applied to the animated container.
 */
@Composable
fun LindaMessage(
    message : String?,
    type    : LindaMessageType = LindaMessageType.Error,
    modifier: Modifier         = Modifier,
) {
    val colors  = LindaTheme.colors
    val spacing = LindaTheme.spacing
    val borders = LindaTheme.borders
    val typo    = LindaTheme.typography

    val accentColor: Color = when (type) {
        LindaMessageType.Error       -> colors.statusError
        LindaMessageType.Informative -> colors.statusInfo
        LindaMessageType.Success     -> colors.statusSuccess
    }
    val icon: ImageVector = when (type) {
        LindaMessageType.Error       -> LindaIcons.Default.Warning
        LindaMessageType.Informative -> LindaIcons.Default.Info
        LindaMessageType.Success     -> LindaIcons.Default.Check
    }

    val shape       = RoundedCornerShape(borders.radiusSm)
    val accentWidth = borders.widthThick

    AnimatedVisibility(
        visible  = message != null,
        enter    = slideInVertically(initialOffsetY = { -it / 2 }) + fadeIn(),
        exit     = slideOutVertically(targetOffsetY  = { -it / 2 }) + fadeOut(),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(colors.surfaceGlass, shape)
                .border(borders.widthThin, accentColor.copy(alpha = 0.4f), shape)
                .drawBehind {
                    drawRect(
                        color   = accentColor,
                        topLeft = Offset.Zero,
                        size    = size.copy(width = accentWidth.toPx()),
                    )
                }
                .padding(
                    start  = spacing.lg + accentWidth,
                    top    = spacing.md,
                    end    = spacing.md,
                    bottom = spacing.md,
                ),
        ) {
            Icon(
                imageVector        = icon,
                contentDescription = null,
                tint               = accentColor,
            )
            Spacer(modifier = Modifier.width(spacing.sm))
            Text(
                text  = message.orEmpty(),
                style = typo.bodySmall.copy(color = accentColor),
            )
        }
    }
}
