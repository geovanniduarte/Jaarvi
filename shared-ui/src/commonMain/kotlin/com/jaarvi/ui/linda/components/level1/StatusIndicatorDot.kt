package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaStatusIndicatorDot — LEVEL 1
//
// Small circular status badge. Colour and glow are driven by
// [DestinationStatus]:
//   ACTIVE    → statusActive (lime) + lime glow
//   COMPLETED → statusCompleted (gold) + gold glow
//   PLANNED   → statusPlanned (white 60%), no glow
// ─────────────────────────────────────────────────────────────

// ── Status enum ───────────────────────────────────────────────

enum class DestinationStatus {
    /** Currently active / in-progress leg of the trip. */
    ACTIVE,
    /** Leg already completed. */
    COMPLETED,
    /** Upcoming planned leg. */
    PLANNED,
}

// ── Size preset ───────────────────────────────────────────────

enum class StatusDotSize {
    SM,  //  6 dp
    MD,  //  8 dp
    LG,  // 12 dp
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param status   Trip-leg status that determines dot colour and glow.
 * @param modifier Optional layout modifier.
 * @param size     Diameter preset.
 */
@Composable
fun LindaStatusIndicatorDot(
    status  : DestinationStatus = DestinationStatus.ACTIVE,
    modifier: Modifier          = Modifier,
    size    : StatusDotSize     = StatusDotSize.MD,
) {
    val colors = LindaTheme.colors
    val shadow = LindaTheme.shadow
    val sizes  = LindaTheme.sizes

    val diameter = when (size) {
        StatusDotSize.SM -> sizes.statusDotSm
        StatusDotSize.MD -> sizes.statusDotMd
        StatusDotSize.LG -> sizes.statusDotLg
    }

    val color = when (status) {
        DestinationStatus.ACTIVE    -> colors.statusActive
        DestinationStatus.COMPLETED -> colors.statusCompleted
        DestinationStatus.PLANNED   -> colors.statusPlanned
    }

    val glowColor = when (status) {
        DestinationStatus.ACTIVE    -> colors.glowLime
        DestinationStatus.COMPLETED -> colors.glowGold
        DestinationStatus.PLANNED   -> Color.Transparent
    }

    val shouldGlow = status != DestinationStatus.PLANNED

    Box(
        modifier = modifier
            .size(diameter)
            .then(
                if (shouldGlow) Modifier.shadow(
                    elevation    = shadow.glowElevation,
                    shape        = CircleShape,
                    ambientColor = glowColor,
                    spotColor    = glowColor,
                ) else Modifier
            )
            .clip(CircleShape)
            .background(color = color, shape = CircleShape),
    )
}
