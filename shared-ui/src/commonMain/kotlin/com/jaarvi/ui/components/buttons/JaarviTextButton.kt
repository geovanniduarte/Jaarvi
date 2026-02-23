package com.jaarvi.ui.components.buttons

import com.jaarvi.ui.theme.Spacing
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Secondary text button for Jaarvi app.
 *
 * Used for less prominent actions (cancel, skip, etc.).
 * No background, just text with primary color.
 *
 * @param text Button label text
 * @param onClick Callback invoked when button is clicked
 * @param modifier Optional modifier for customization
 * @param enabled Whether the button is clickable (default: true)
 */
@Composable
fun JaarviTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = PaddingValues(
            horizontal = Spacing.medium,
            vertical = Spacing.small
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primary
        )
    }
}
