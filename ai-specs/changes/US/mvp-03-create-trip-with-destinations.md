# User Story: Create Trip with Destinations

## Story
**As a** traveler  
**I want to** create a new trip with destinations and dates  
**So that** I can start building my travel itinerary

## Business Value
- Core trip creation workflow
- Foundation for itinerary planning
- Captures essential trip metadata

## Acceptance Criteria
- [ ] User can create a trip with optional name
- [ ] User must provide start and end dates (date picker)
- [ ] User can add one or multiple destinations (cities)
- [ ] User can assign number of days per destination
- [ ] User can set trip-specific preferences (override profile defaults)
- [ ] System validates date ranges (end date after start date)
- [ ] System validates destination days don't exceed total trip days
- [ ] Trip is created in "draft" status
- [ ] User sees confirmation and is redirected to trip details
- [ ] Trip appears in trip list immediately after creation

## Technical Notes
- Create `Trip`, `TripDestination`, and `TripPlanningContext` entities
- Validate dates on both client and server
- Support multi-city trips from MVP
- Store preferences snapshot for AI context (Phase 2 ready)
- Use city/destination search with autocomplete (Google Places API or similar)

## Estimated Effort
**8 Story Points** (Large)

## Priority
**Critical** - Core feature for MVP

## Dependencies
- mvp-02-manage-user-profile

## Mapped Use Cases
- [UC-2.1: Create New Trip](../../product-discovery/8-UserCases.md#uc-21-create-new-trip)
- [UC-2.2: Add Destinations to Trip](../../product-discovery/8-UserCases.md#uc-22-add-destinations-to-trip)
- [UC-2.3: Set Trip-Specific Preferences](../../product-discovery/8-UserCases.md#uc-23-set-trip-specific-preferences)
- [UC-2.4: Validate Trip Configuration](../../product-discovery/8-UserCases.md#uc-24-validate-trip-configuration)

## Definition of Done
- [ ] Unit tests for trip creation logic
- [ ] Integration tests for validation rules
- [ ] API documentation with examples
- [ ] Mobile UI with intuitive date picker and destination search
- [ ] Error handling for validation failures
- [ ] Multi-city flow tested (2+ destinations)
- [ ] Performance: Trip creation < 2 seconds
