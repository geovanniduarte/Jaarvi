package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────

/**
 * LindaStatCard — compact key/value display for trip statistics.
 *
 * Renders a muted [label] above a large [value] and optional [subtext]
 * suffix. Typically used in rows inside a [LindaCard].
 *
 * @param label    Descriptor text (uppercased automatically).
 * @param value    Primary numeric or textual value.
 * @param subtext  Optional dimmed suffix after [value] (e.g. "/ $5k").
 */
@Composable
fun LindaStatCard(
    label   : String,
    value   : String,
    modifier: Modifier = Modifier,
    subtext : String?  = null,
) {
    val colors  = LindaTheme.colors
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography

    Column(
        modifier            = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.xs),
    ) {
        Text(
            text  = label.uppercase(),
            style = typo.bodySmall.copy(color = colors.textMuted),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.xs),
            verticalAlignment     = Alignment.Bottom,
        ) {
            Text(
                text  = value,
                style = typo.displaySmall.copy(color = colors.textPrimary),
            )
            if (subtext != null) {
                Text(
                    text  = subtext,
                    style = typo.bodyMedium.copy(color = colors.textTertiary),
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────

/**
 * LindaStatsRow — evenly-distributed row wrapper for [LindaStatCard] items.
 * Provide each stat as a [LindaStatCard] with `Modifier.weight(1f)`.
 */
@Composable
fun LindaStatsRow(
    modifier: Modifier = Modifier,
    content : @Composable RowScope.() -> Unit,
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(LindaTheme.spacing.lg),
        content               = content,
    )
}

