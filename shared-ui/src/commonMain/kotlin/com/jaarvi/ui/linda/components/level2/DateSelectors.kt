package com.jaarvi.ui.linda.components.level2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jaarvi.ui.linda.components.level1.LindaCard
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaDateSelectors — LEVEL 2
//
// Composed date range picker row:
//
//   [Start Date card]   [durationContent slot]   [End Date card]
//
// Each date card is a [LindaCard] showing a muted uppercase label
// and a bold date value below it. The two cards share equal width
// (weight 1f). The centre slot is caller-controlled — pass a
// [LindaBadge] (SUBTLE_GOLD_PILL is the canonical choice) or
// leave it null to collapse the gap.
// ─────────────────────────────────────────────────────────────

/**
 * @param startDate       Display value for the start date (e.g. "Oct 12").
 * @param endDate         Display value for the end date   (e.g. "Oct 26").
 * @param modifier        Optional layout modifier.
 * @param startLabel      Label above the start date value.
 * @param endLabel        Label above the end date value.
 * @param durationContent Optional composable slot rendered between the two date
 *                        cards. Canonical usage:
 *                        ```
 *                        durationContent = {
 *                            LindaBadge("14 DAYS", variant = BadgeVariant.SUBTLE_GOLD_PILL)
 *                        }
 *                        ```
 *                        Pass null (default) to render the cards edge-to-edge.
 * @param onStartClick    Optional tap handler for the start date card.
 * @param onEndClick      Optional tap handler for the end date card.
 */
@Composable
fun LindaDateSelectors(
    startDate       : String,
    endDate         : String,
    modifier        : Modifier                   = Modifier,
    startLabel      : String                     = "Start Date",
    endLabel        : String                     = "End Date",
    durationContent : (@Composable () -> Unit)?  = null,
    onStartClick    : (() -> Unit)?              = null,
    onEndClick      : (() -> Unit)?              = null,
) {
    val spacing = LindaTheme.spacing

    Row(
        modifier              = modifier.fillMaxWidth(),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.md),
    ) {
        // ── Start date card ───────────────────────────────────
        LindaCard(
            modifier       = Modifier
                .weight(1f)
                .then(if (onStartClick != null) Modifier.clickable(onClick = onStartClick) else Modifier),
            glowColor      = Color.Transparent,
            contentPadding = PaddingValues(spacing.lg),
        ) {
            DateCardContent(label = startLabel, date = startDate)
        }

        // ── Duration slot ─────────────────────────────────────
        // Caller owns the badge — pass LindaBadge(...) or any composable.
        durationContent?.invoke()

        // ── End date card ─────────────────────────────────────
        LindaCard(
            modifier       = Modifier
                .weight(1f)
                .then(if (onEndClick != null) Modifier.clickable(onClick = onEndClick) else Modifier),
            glowColor      = Color.Transparent,
            contentPadding = PaddingValues(spacing.lg),
        ) {
            DateCardContent(label = endLabel, date = endDate)
        }
    }
}

// ── Internal helper ───────────────────────────────────────────

@Composable
private fun DateCardContent(
    label: String,
    date : String,
) {
    val colors  = LindaTheme.colors
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography

    Column(
        modifier            = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.xs),
    ) {
        Text(
            text  = label.uppercase(),
            style = typo.labelSmall.copy(color = colors.textMuted),
        )
        Text(
            text  = date,
            style = typo.headlineLarge.copy(color = colors.textPrimary),
        )
    }
}
