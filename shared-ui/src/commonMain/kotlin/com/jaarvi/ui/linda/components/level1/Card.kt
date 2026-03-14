package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaCard — LEVEL 1
//
// Reusable glassmorphic card container. Handles surface colour,
// border, corner shape, shadow, and content padding so callers
// never repeat these modifiers manually.
//
// Shadow behaviour:
//   glowColor == null              → black card-elevation drop shadow
//   glowColor == Color.Transparent → no shadow at all
//   glowColor == colors.glowLime   → coloured glow halo
//
// Shape defaults:
//   null   → RoundedCornerShape(borders.radiusLg)  — group/content card
//   pass a Shape explicitly to use a different radius (e.g. radiusMd for list items)
//
// Usage examples:
//   // Standard content card
//   LindaCard { Column { … } }
//
//   // Light-glass surface, no shadow
//   LindaCard(variant = CardVariant.GLASS_LIGHT, glowColor = Color.Transparent) { … }
//
//   // List-item card, strong border, lime glow when complete
//   LindaCard(
//       shape          = RoundedCornerShape(LindaTheme.borders.radiusMd),
//       glowColor      = if (done) LindaTheme.colors.glowLime else Color.Transparent,
//       borderColor    = LindaTheme.colors.borderGlassStrong,
//       contentPadding = PaddingValues(horizontal = spacing.lg, vertical = spacing.md),
//   ) { … }
// ─────────────────────────────────────────────────────────────

// ── Surface variant ───────────────────────────────────────────

/** Controls the background opacity of the card surface. */
enum class CardVariant {
    /** Standard glass surface — ~10 % white. */
    GLASS,
    /** Lighter glass surface — ~5 % white. */
    GLASS_LIGHT,
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param modifier       Modifier applied to the outer card container.
 * @param variant        Surface opacity — [CardVariant.GLASS] or [CardVariant.GLASS_LIGHT].
 * @param shape          Corner shape. `null` → `RoundedCornerShape(borders.radiusLg)`.
 * @param glowColor      Shadow colour. `null` → black card-elevation shadow.
 *                       Pass `Color.Transparent` to suppress all shadow.
 * @param borderColor    Border colour. `null` → `colors.borderGlass`.
 * @param backgroundColor Explicit background fill. `null` → uses [variant] default.
 *                        Use this to pass `colors.surfaceGlass.copy(alpha = 0.85f)` etc.
 * @param contentPadding Inner padding. `null` → `spacing.xxl` on all sides.
 * @param content        Content placed inside the card's [BoxScope].
 */
@Composable
fun LindaCard(
    modifier        : Modifier       = Modifier,
    variant         : CardVariant    = CardVariant.GLASS,
    shape           : Shape?         = null,
    glowColor       : Color?         = null,
    borderColor     : Color?         = null,
    backgroundColor : Color?         = null,
    contentPadding  : PaddingValues? = null,
    content         : @Composable BoxScope.() -> Unit,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val shadow  = LindaTheme.shadow

    val resolvedShape  : Shape         = shape          ?: RoundedCornerShape(borders.radiusLg)
    val resolvedBorder : Color         = borderColor    ?: colors.borderGlass
    val resolvedPadding: PaddingValues = contentPadding ?: PaddingValues(spacing.xxl)
    val bgColor        : Color         = backgroundColor ?: when (variant) {
        CardVariant.GLASS       -> colors.surfaceGlass
        CardVariant.GLASS_LIGHT -> colors.surfaceGlassLight
    }

    // Resolve shadow modifier
    val shadowMod: Modifier = when {
        glowColor == Color.Transparent -> Modifier
        glowColor != null -> Modifier.shadow(
            elevation    = shadow.glowElevation,
            shape        = resolvedShape,
            ambientColor = glowColor,
            spotColor    = glowColor,
        )
        else -> Modifier.shadow(
            elevation    = shadow.cardElevation,
            shape        = resolvedShape,
            ambientColor = Color.Black.copy(alpha = shadow.cardShadowAlpha),
            spotColor    = Color.Black.copy(alpha = shadow.cardShadowAlpha),
        )
    }

    Box(
        modifier = modifier
            .then(shadowMod)
            .clip(resolvedShape)
            .background(color = bgColor, shape = resolvedShape)
            .border(width = borders.widthThin, color = resolvedBorder, shape = resolvedShape)
            .padding(resolvedPadding),
        content = content,
    )
}