package com.jaarvi.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jaarvi.ui.theme.Spacing

/**
 * Primary action button for Jaarvi app.
 *
 * Uses Material theme primary color and consistent styling.
 *
 * @param text Button label text
 * @param onClick Callback invoked when button is clicked
 * @param modifier Optional modifier for customization
 * @param enabled Whether the button is clickable (default: true)
 */
@Composable
fun JaarviButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        contentPadding = PaddingValues(
            horizontal = Spacing.medium,
            vertical = Spacing.small
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button
        )
    }
}
