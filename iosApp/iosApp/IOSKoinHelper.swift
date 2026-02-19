import Foundation
import shared

/**
 * Helper class to initialize Koin dependency injection from Swift.
 * Bridges Kotlin Multiplatform Koin setup with iOS app.
 */
class IOSKoinHelper {
    static let shared = IOSKoinHelper()
    
    private var koinApplication: KoinApplication?
    
    private init() {}
    
    func initializeKoin() {
        if koinApplication == nil {
            koinApplication = KoinIOSKt.doInitKoinIOS()
        }
    }
}
