import Foundation
import UserNotifications
import shared

/**
 * iOS implementation of NotificationCapability.
 * V1: Basic UNUserNotificationCenter setup
 * V2: Will add scheduling, push notifications, and rich content
 */
class IOSNotificationCapability: NotificationCapability {
    
    private let notificationCenter = UNUserNotificationCenter.current()
    
    func scheduleNotification(
        id: String,
        title: String,
        message: String,
        scheduledTimeMillis: Int64
    ) async throws {
        // V1: Not yet implemented
        throw NSError(
            domain: "IOSNotificationCapability",
            code: -1,
            userInfo: [NSLocalizedDescriptionKey: "Scheduling not yet implemented"]
        )
    }
    
    func cancelNotification(id: String) async throws {
        // V1: Not yet implemented
        throw NSError(
            domain: "IOSNotificationCapability",
            code: -1,
            userInfo: [NSLocalizedDescriptionKey: "Cancellation not yet implemented"]
        )
    }
    
    func requestNotificationPermission() async throws -> Bool {
        // V1: Not yet implemented
        throw NSError(
            domain: "IOSNotificationCapability",
            code: -1,
            userInfo: [NSLocalizedDescriptionKey: "Permission request not yet implemented"]
        )
    }
    
    func hasNotificationPermission() -> Bool {
        var isAuthorized = false
        let semaphore = DispatchSemaphore(value: 0)
        
        notificationCenter.getNotificationSettings { settings in
            isAuthorized = settings.authorizationStatus == .authorized
            semaphore.signal()
        }
        
        semaphore.wait()
        return isAuthorized
    }
}
