import SwiftUI
import shared
import shared_ui

@main
struct JaarviApp: App {
    
    init() {
        // Initialize Koin for dependency injection
        IOSKoinHelper.shared.initializeKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
