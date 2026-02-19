package com.jaarvi.ui.navigation

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

/**
 * Creates a UIViewController for iOS that contains the Compose navigation hierarchy.
 * This function is called from Swift to embed Compose UI in SwiftUI.
 *
 * @return UIViewController wrapping the Compose navigation
 */
fun createAppNavigationViewController(): UIViewController {
    return ComposeUIViewController {
        AppNavigation()
    }
}
