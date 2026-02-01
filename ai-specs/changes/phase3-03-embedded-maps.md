# User Story: Embedded Maps and Navigation

## Story
**As a** traveler  
**I want to** view maps and navigation within the Jaarvi app  
**So that** I don't need to switch between apps during my trip

## Business Value
- Seamless user experience
- Reduces app switching friction
- Better integration with itinerary

## Acceptance Criteria
- [ ] User can view activity locations on embedded map
- [ ] User can see all activities for a day on map (with markers)
- [ ] User can view route between consecutive activities
- [ ] User can start turn-by-turn navigation within app
- [ ] Map shows estimated walking time between activities
- [ ] User can switch between map view and list view
- [ ] Map markers show activity type and time
- [ ] User can tap marker to see activity details
- [ ] Map works offline (cached tiles for trip area)
- [ ] User can search for nearby places (restaurants, ATMs, pharmacies)

## Technical Notes
- Integrate mapping SDK (Google Maps or Mapbox)
- Implement map tile caching for offline use
- Calculate routes using mapping API (walking, transit, driving)
- Display activity markers with custom icons per type
- Implement turn-by-turn navigation using SDK
- Optimize map performance (lazy loading, clustering)
- Cache map tiles for trip destinations (configurable area)
- Handle map API costs (usage limits)

## Estimated Effort
**13 Story Points** (Extra Large)

## Priority
**Medium** - Nice-to-have for Phase 3

## Dependencies
- mvp-08-execute-daily-activities
- phase3-02-full-offline-mode (for offline maps)

## Mapped Use Cases
- [UC-9.5: View Embedded Maps (v2)](../../product-discovery/8-UserCases.md#uc-95-view-embedded-maps-v2)

## Definition of Done
- [ ] Unit tests for route calculation
- [ ] Integration tests with mapping API
- [ ] API documentation updated
- [ ] Mobile UI with embedded map
- [ ] Turn-by-turn navigation working
- [ ] Offline maps tested
- [ ] Map markers displaying correctly
- [ ] Performance: Map loads < 2 seconds
- [ ] Cost monitoring for API usage
