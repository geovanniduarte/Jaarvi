package com.jaarvi.ui.linda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────

/**
 * LindaHeader — fixed top app bar with glassmorphic background.
 *
 * Renders a centred title (+ optional subtitle) with optional
 * back and share action slots flanking it.
 *
 * @param title         Primary page title.
 * @param subtitle      Optional secondary line below [title].
 * @param onBackClick   When non-null, shows a back arrow icon button.
 * @param onShareClick  When non-null, shows a share icon button.
 */
@Composable
fun LindaHeader(
    title        : String,
    modifier     : Modifier  = Modifier,
    subtitle     : String?   = null,
    onBackClick  : (() -> Unit)? = null,
    onShareClick : (() -> Unit)? = null,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val shadow  = LindaTheme.shadow
    val sizes   = LindaTheme.sizes
    val typo    = LindaTheme.typography

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
            // Leading slot (back button or placeholder)
            Box(modifier = Modifier.width(sizes.iconLg + spacing.lg)) {
                if (onBackClick != null) {
                    LindaIconButton(onClick = onBackClick, variant = IconButtonVariant.GHOST) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.textPrimary,
                            modifier = Modifier.size(sizes.iconMd),
                        )
                    }
                }
            }

            // Title / Subtitle
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

            // Trailing slot (share button or placeholder)
            Box(modifier = Modifier.width(sizes.iconLg + spacing.lg)) {
                if (onShareClick != null) {
                    LindaIconButton(onClick = onShareClick, variant = IconButtonVariant.GHOST) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = colors.textPrimary,
                            modifier = Modifier.size(sizes.iconMd),
                        )
                    }
                }
            }
        }
    }
}

// ─── Backward-compat alias ───────────────────────────────────
/** @deprecated Use [LindaHeader]. */
@Deprecated("Use LindaHeader", ReplaceWith("LindaHeader(title, modifier, subtitle, onBackClick, onShareClick)"))
@Composable
fun TravelHeader(
    title       : String,
    modifier    : Modifier       = Modifier,
    subtitle    : String?        = null,
    onBackClick : (() -> Unit)?  = null,
    onShareClick: (() -> Unit)?  = null,
) = LindaHeader(title, modifier, subtitle, onBackClick, onShareClick)
