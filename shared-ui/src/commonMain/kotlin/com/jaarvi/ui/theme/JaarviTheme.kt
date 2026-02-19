package com.jaarvi.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

/**
 * Jaarvi application theme.
 *
 * Wraps Material theme with Jaarvi brand colors, typography, and spacing.
 * V1: Light theme only.
 */
@Composable
fun JaarviTheme(
    content: @Composable () -> Unit
) {
    val colors = lightColors(
        primary = JaarviColors.Primary,
        primaryVariant = JaarviColors.PrimaryVariant,
        secondary = JaarviColors.Secondary,
        secondaryVariant = JaarviColors.SecondaryVariant,
        background = JaarviColors.Background,
        surface = JaarviColors.Surface,
        error = JaarviColors.Error,
        onPrimary = JaarviColors.OnPrimary,
        onSecondary = JaarviColors.OnSecondary,
        onBackground = JaarviColors.OnBackground,
        onSurface = JaarviColors.OnSurface,
        onError = JaarviColors.OnPrimary
    )
    
    MaterialTheme(
        colors = colors,
        typography = JaarviTypography,
        content = content
    )
}
