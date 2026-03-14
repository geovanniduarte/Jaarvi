package com.jaarvi.ui.linda.components.level1

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import com.jaarvi.ui.linda.components.level0.icons.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaDatePickerField — LEVEL 1
//
// Tappable read-only field that shows a selected date. On tap it
// opens a platform DatePickerDialog (Android). The field itself is
// pure UI; date-picker delegation is handled via the platform dialog.
//
//   No date selected → "Select a date" in textMuted
//   Date selected    → "MMM dd, yyyy" in textPrimary
//   Error state      → red border + error message below
//   Calendar icon    → accentLime
//
// KMP note: For a Compose Multiplatform setup, replace the Android
// DatePickerDialog usage with an expect/actual DatePickerCapability.
// ─────────────────────────────────────────────────────────────

private val DATE_FORMATTER: DateTimeFormatter =
    DateTimeFormatter.ofPattern("MMM dd, yyyy")

/**
 * @param selectedDate   Currently selected date, or null if none.
 * @param onDateSelected Callback with the newly selected [LocalDate].
 * @param label          Field label shown above the value (UPPERCASE).
 * @param modifier       Optional layout modifier.
 * @param minDate        Earliest selectable date (inclusive).
 * @param maxDate        Latest selectable date (inclusive).
 * @param isError        When true, borders turn red.
 * @param errorMessage   Optional error text shown below the field.
 */
@Composable
fun LindaDatePickerField(
    selectedDate  : LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    label         : String,
    modifier      : Modifier   = Modifier,
    minDate       : LocalDate? = null,
    maxDate       : LocalDate? = null,
    isError       : Boolean    = false,
    errorMessage  : String?    = null,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography
    val context = LocalContext.current

    val borderColor = if (isError) colors.statusError else colors.borderGlassStrong
    val shape       = RoundedCornerShape(borders.radiusMd)

    val showPicker: () -> Unit = {
        val today = LocalDate.now()
        val init  = selectedDate ?: today
        val dialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
            },
            init.year, init.monthValue - 1, init.dayOfMonth,
        )
        minDate?.let { dialog.datePicker.minDate = it.toEpochDay() * 86_400_000L }
        maxDate?.let { dialog.datePicker.maxDate = it.toEpochDay() * 86_400_000L }
        dialog.show()
    }

    Column(modifier = modifier) {

        // ── Glassmorphic field container ──────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(color = colors.surfaceGlass)
                .border(width = borders.widthThin, color = borderColor, shape = shape)
                .clickable(onClick = showPicker)
                .padding(horizontal = spacing.lg, vertical = spacing.md),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                // ── Label ─────────────────────────────────────
                Text(
                    text  = label.uppercase(),
                    style = typo.labelSmall.copy(color = colors.accentGold),
                )

                Spacer(modifier = Modifier.height(spacing.xs))

                // ── Value row ─────────────────────────────────
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text     = selectedDate?.format(DATE_FORMATTER) ?: "Select a date",
                        style    = typo.bodyMedium.copy(
                            color = if (selectedDate != null) colors.textPrimary else colors.textMuted,
                        ),
                        modifier = Modifier.weight(1f),
                    )
                    Icon(
                        imageVector        = LindaIcons.Default.Calendar,
                        contentDescription = "Pick date",
                        tint               = colors.accentLime,
                        modifier           = Modifier.size(LindaTheme.sizes.iconMd),
                    )
                }
            }
        }

        // ── Error message ─────────────────────────────────────
        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(spacing.xs))
            Text(
                text     = errorMessage,
                style    = typo.labelSmall.copy(color = colors.statusError),
                modifier = Modifier.padding(horizontal = spacing.xs),
            )
        }
    }
}