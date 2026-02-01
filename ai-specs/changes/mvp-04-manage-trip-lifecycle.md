# User Story: Manage Trip Lifecycle

## Story
**As a** traveler  
**I want to** view, edit, and delete my trips  
**So that** I can keep my travel plans organized and up-to-date

## Business Value
- Complete CRUD operations for trips
- User control over trip data
- Organized trip management

## Acceptance Criteria
- [ ] User can view all trips organized by status (draft/active/completed)
- [ ] User can view trip details (destinations, dates, summary)
- [ ] User can edit trip name, dates, and destinations for draft or active trips
- [ ] System recalculates itinerary if dates are changed
- [ ] User can delete a trip with confirmation dialog
- [ ] Deleted trips remove all associated data (itinerary, documents, activities)
- [ ] User can mark trip as active (when ready to travel)
- [ ] User can mark trip as completed (after travel)
- [ ] Trip list shows basic metadata: dates, destinations, status
- [ ] Empty state displayed when no trips exist

## Technical Notes
- Implement soft delete for audit trail (optional)
- Cascade delete for all related entities (DayPlan, Activity, Document)
- Optimize trip list query (pagination for 50+ trips)
- Support status transitions: draft → active → completed
- Prevent editing completed trips (read-only)

## Estimated Effort
**5 Story Points** (Medium)

## Priority
**High** - Essential for trip management

## Dependencies
- mvp-03-create-trip-with-destinations

## Mapped Use Cases
- [UC-2.5: View Trip List](../../product-discovery/8-UserCases.md#uc-25-view-trip-list)
- [UC-2.6: Edit Trip Details](../../product-discovery/8-UserCases.md#uc-26-edit-trip-details)
- [UC-2.7: Delete Trip](../../product-discovery/8-UserCases.md#uc-27-delete-trip)

## Definition of Done
- [ ] Unit tests for trip service CRUD operations
- [ ] Integration tests for cascade delete
- [ ] API documentation updated
- [ ] Mobile UI with swipe actions for delete
- [ ] Confirmation dialogs tested
- [ ] Performance: Trip list loads < 1 second
- [ ] Empty state UI implemented
