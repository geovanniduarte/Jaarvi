package com.jaarvi.ui.linda.components.level2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jaarvi.ui.linda.components.level1.LindaChipRow
import com.jaarvi.ui.linda.components.level1.LindaSearchBox
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaSelector — LEVEL 2
//
// Composed search + chip-filter panel.
// Stacks a [LindaSearchBox] above a horizontal [LindaChipRow].
//
// Slot-based: chips are supplied via a [LazyListScope] content
// lambda forwarded directly to [LindaChipRow]. When null, the
// chip row is omitted entirely.
//
// Usage:
//   LindaSelector(query = q, onQueryChange = { q = it }) {
//       item { LindaChip(label = "Paris",   selected = chip == "Paris",   onClick = { chip = "Paris" }) }
//       item { LindaChip(label = "London",  selected = chip == "London",  onClick = { chip = "London" }) }
//       item { LindaChip(label = "Tokyo",   selected = chip == "Tokyo",   onClick = { chip = "Tokyo" }) }
//   }
//
// NOTE: [LindaSearchBox] is defined in SearchBox.kt (Level 1).
//       [LindaChipRow]  is defined in Chips.kt (Level 1).
// ─────────────────────────────────────────────────────────────

/**
 * @param query         Current value of the search input (read-only display).
 * @param onQueryChange Called when the user types in the search field.
 * @param modifier      Optional layout modifier.
 * @param placeholder   Composable slot rendered inside the search field when it is empty.
 *                      Pass null to show no placeholder.
 * @param chips         Optional [LazyListScope] lambda for the chip row. Pass null to hide chips.
 */
@Composable
fun LindaSelector(
    query        : String,
    onQueryChange: (String) -> Unit,
    modifier     : Modifier                    = Modifier,
    placeholder  : @Composable (() -> Unit)?  = null,
    chips        : (LazyListScope.() -> Unit)? = null,
) {
    val spacing = LindaTheme.spacing

    Column(
        modifier            = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.lg),
    ) {
        // ── Search field ──────────────────────────────────────
        LindaSearchBox(
            value           = query,
            onValueChange   = onQueryChange,
            placeholderText = placeholder,
            modifier        = Modifier.fillMaxWidth(),
        )

        // ── Chip row (optional) ───────────────────────────────
        if (chips != null) {
            LindaChipRow(
                modifier = Modifier.fillMaxWidth(),
                content  = chips,
            )
        }
    }
}