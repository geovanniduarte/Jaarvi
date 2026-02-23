package com.jaarvi.ui.linda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaCard variants
// ─────────────────────────────────────────────────────────────

enum class CardVariant {
    /** Standard glass card surface (~10% white). */
    GLASS,
    /** Lighter glass card surface (~5% white). */
    GLASS_LIGHT,
}

// ─────────────────────────────────────────────────────────────

/**
 * LindaCard — glassmorphic container for grouped content.
 *
 * @param variant  [CardVariant.GLASS] or [CardVariant.GLASS_LIGHT].
 * @param content  Slot for card body.
 */
@Composable
fun LindaCard(
    modifier: Modifier    = Modifier,
    variant : CardVariant = CardVariant.GLASS,
    content : @Composable () -> Unit,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val shadow  = LindaTheme.shadow
    val spacing = LindaTheme.spacing

    val bgColor = when (variant) {
        CardVariant.GLASS       -> colors.surfaceGlass
        CardVariant.GLASS_LIGHT -> colors.surfaceGlassLight
    }
    val shape = RoundedCornerShape(borders.radiusLg)
    val shadowAlpha = shadow.cardShadowAlpha

    Box(
        modifier = modifier
            .shadow(
                elevation    = shadow.cardElevation,
                shape        = shape,
                ambientColor = Color.Black.copy(alpha = shadowAlpha),
                spotColor    = Color.Black.copy(alpha = shadowAlpha),
            )
            .clip(shape)
            .background(color = bgColor, shape = shape)
            .border(width = borders.widthThin, color = colors.borderGlass, shape = shape),
    ) {
        Box(modifier = Modifier.padding(spacing.xxl)) {
            content()
        }
    }
}

