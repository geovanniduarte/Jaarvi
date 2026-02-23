package com.jaarvi.ui.screens.health

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.jaarvi.ui.linda.TravelAppScreen2
import com.jaarvi.ui.theme.JaarviTheme

/**
 * Health Check screen that verifies backend connectivity.
 * Demonstrates full stack integration with loading, success, and error states.
 */
class HealthScreen : Screen {

    @Composable
    override fun Content() {

        JaarviTheme {
            val presenter: HealthPresenter = getScreenModel()
            LaunchedEffect(Unit) { presenter.start() }
            TravelAppScreen2()
        }
    }
}
