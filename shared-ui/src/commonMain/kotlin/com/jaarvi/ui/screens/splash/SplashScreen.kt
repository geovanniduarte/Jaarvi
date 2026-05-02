package com.jaarvi.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.material.CircularProgressIndicator
import com.jaarvi.ui.screens.createtrip.CreateTripScreen
import com.jaarvi.ui.screens.health.HealthScreen
import com.jaarvi.ui.theme.JaarviTheme
import com.jaarvi.ui.theme.Spacing
import kotlinx.coroutines.delay

/**
 * Splash screen displayed on app launch.
 * Shows Jaarvi branding and automatically navigates to the main screen after a brief delay.
 */
class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            delay(2000) // 2 second splash
            navigator.replace(CreateTripScreen())
        }

        JaarviTheme {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Spacing.lg)
                ) {
                    Text(
                        text = "Jaarvi",
                        style = MaterialTheme.typography.h1,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                    
                    Text(
                        text = "Your Travel Assistant",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onBackground
                    )
                    
                    Spacer(modifier = Modifier.height(Spacing.xl))
                    
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}
