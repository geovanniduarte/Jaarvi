package com.jaarvi.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jaarvi.ui.navigation.AppNavigation

/**
 * Main entry point for the Android application.
 * Sets up the Compose UI with Voyager navigation.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            AppNavigation()
        }
    }
}
