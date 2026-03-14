package com.jaarvi.ui.linda.components.level2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaHeader — LEVEL 2
//
// Fixed top app bar with glassmorphic background.
// Centred title (+ optional subtitle) flanked by two symmetric
// composable slots provided entirely by the caller.
//
// The caller owns the leading / trailing content and is free to
// place any composable there — icon buttons, badges, menus, etc.
// Nothing is hard-coded inside the component itself.
//
// Layout
//   ┌─────────────────────────────────────────────────────┐
//   │ [leadingContent]   title / subtitle   [trailingContent] │
//   └─────────────────────────────────────────────────────┘
//   Both slots share the same fixed width (iconLg + spacingLg)
//   so the title stays perfectly centred regardless of content.
// ─────────────────────────────────────────────────────────────

/**
 * @param title           Primary page title, centred in the bar.
 * @param modifier        Optional layout modifier applied to the outer Box.
 * @param subtitle        Optional secondary line rendered below [title].
 * @param leadingContent  Composable placed in the left slot.
 *                        Typical usage: a back-arrow [LindaIconButton].
 * @param trailingContent Composable placed in the right slot.
 *                        Typical usage: a share button, step badge, or ··· menu.
 */
@Composable
fun LindaHeader(
    title           : String,
    modifier        : Modifier                  = Modifier,
    subtitle        : String?                   = null,
    leadingContent  : @Composable (() -> Unit)? = null,
    trailingContent : @Composable (() -> Unit)? = null,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val shadow  = LindaTheme.shadow
    val sizes   = LindaTheme.sizes
    val typo    = LindaTheme.typography

    // Both slots use the same fixed width to keep the title centred.
    val slotWidth = sizes.iconLg + spacing.lg

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation    = shadow.cardElevation,
                ambientColor = Color.Black.copy(alpha = shadow.cardShadowAlpha),
                spotColor    = Color.Black.copy(alpha = shadow.cardShadowAlpha),
            )
            .background(colors.surfaceGlass)
            .border(width = borders.widthThin, color = colors.borderGlass),
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(spacing.lg),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically,
        ) {
            // ── Leading slot ─────────────────────────────────
            Box(
                modifier         = Modifier.width(slotWidth),
                contentAlignment = Alignment.CenterStart,
            ) {
                leadingContent?.invoke()
            }

            // ── Title / Subtitle ──────────────────────────────
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier            = Modifier.weight(1f),
            ) {
                Text(
                    text  = title,
                    style = typo.headlineLarge.copy(color = colors.textPrimary),
                )
                if (subtitle != null) {
                    Text(
                        text  = subtitle,
                        style = typo.bodyMedium.copy(color = colors.textMuted),
                    )
                }
            }

            // ── Trailing slot ─────────────────────────────────
            Box(
                modifier         = Modifier.width(slotWidth),
                contentAlignment = Alignment.CenterEnd,
            ) {
                trailingContent?.invoke()
            }
        }
    }
}
