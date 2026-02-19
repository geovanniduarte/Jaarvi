import Foundation
import SafariServices
import UIKit
import shared

/**
 * iOS implementation of BrowserCapability using SFSafariViewController.
 * Provides in-app browsing for affiliate links and external content.
 */
class IOSBrowserCapability: BrowserCapability {
    
    func openUrl(url: String) async throws {
        guard let url = URL(string: url) else {
            throw NSError(
                domain: "IOSBrowserCapability",
                code: -1,
                userInfo: [NSLocalizedDescriptionKey: "Invalid URL"]
            )
        }
        
        await MainActor.run {
            let safariVC = SFSafariViewController(url: url)
            
            if let rootVC = UIApplication.shared.windows.first?.rootViewController {
                var topVC = rootVC
                while let presented = topVC.presentedViewController {
                    topVC = presented
                }
                topVC.present(safariVC, animated: true)
            }
        }
    }
    
    func isBrowserAvailable() -> Bool {
        // SFSafariViewController is always available on iOS 9+
        return true
    }
}
