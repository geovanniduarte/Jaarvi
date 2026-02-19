import Foundation
import MapKit
import shared

/**
 * iOS implementation of MapCapability using external navigation.
 * V1: Opens Apple Maps with MKMapItem
 * V2: Will use embedded MapKit view
 */
class IOSMapCapability: MapCapability {
    
    func openExternalNavigation(latitude: Double, longitude: Double, label: String?) async throws {
        let coordinate = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        let placemark = MKPlacemark(coordinate: coordinate)
        let mapItem = MKMapItem(placemark: placemark)
        
        if let label = label {
            mapItem.name = label
        }
        
        let launchOptions = [
            MKLaunchOptionsDirectionsModeKey: MKLaunchOptionsDirectionsModeDriving
        ]
        
        mapItem.openInMaps(launchOptions: launchOptions)
    }
    
    func isNavigationAvailable() -> Bool {
        // Apple Maps is always available on iOS devices
        return true
    }
}
