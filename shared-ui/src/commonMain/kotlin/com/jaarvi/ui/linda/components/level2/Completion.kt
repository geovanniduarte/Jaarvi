package com.jaarvi.ui.linda.components.level2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaCompletion — LEVEL 2
//
// Generic completion row for any event with an actual vs total status.
// Three independent composable slots — [status], [remaining], and
// [progressBar] — laid out in a fixed vertical structure. The component
// owns only the layout and spacing; all content and visual decisions
// belong to the caller. Used in Step 2 for trip day allocation, and
// reusable for budget, tasks, distance, or any completion context.
//
// Layout:
//   Row (SpaceBetween) { status (start) | remaining (end) }
//   [progressBar] (full width, below with spacing.sm gap)
//
// Token usage: spacing.sm (layout only — all colour/typography tokens
// belong to the slot content passed by the caller).
// ─────────────────────────────────────────────────────────────

/**
 * @param status      Left-aligned slot — actual vs total summary.
 * @param remaining   Right-aligned slot — remaining quantity.
 * @param progressBar Full-width slot below the status row.
 * @param modifier    Optional layout modifier.
 */
@Composable
fun LindaCompletion(
    status     : @Composable () -> Unit,
    remaining  : @Composable () -> Unit,
    progressBar: @Composable () -> Unit,
    modifier   : Modifier = Modifier,
) {
    val spacing = LindaTheme.spacing

    Column(
        modifier            = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {

        // ── Status + remaining row ────────────────────────────
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically,
        ) {
            status()
            remaining()
        }

        // ── Progress bar (full width) ─────────────────────────
        progressBar()
    }
}
