package com.jaarvi.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.jaarvi.ui.screens.splash.SplashScreen

/**
 * Root navigation component for the Jaarvi app.
 * Sets up the Voyager Navigator with the initial screen and default transitions.
 */
@Composable
fun AppNavigation() {
    Navigator(SplashScreen()) { navigator ->
        SlideTransition(navigator)
    }
}
