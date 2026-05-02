package com.jaarvi.ui.screens.tripdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.jaarvi.ui.linda.theme.LindaTheme

/**
 * Stub screen for Trip Detail.
 *
 * Displayed after the Create Trip wizard completes. Full implementation
 * is tracked in a separate ticket (mvp-04).
 *
 * @param tripId UUID of the newly created trip received from the wizard.
 */
data class TripDetailScreen(val tripId: String) : Screen {

    @Composable
    override fun Content() {
        LindaTheme {
            val colors = LindaTheme.colors
            val typo   = LindaTheme.typography

            Box(
                modifier         = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text  = "Trip created! ID: $tripId",
                    style = typo.headlineLarge.copy(color = colors.accentLime),
                )
            }
        }
    }
}
