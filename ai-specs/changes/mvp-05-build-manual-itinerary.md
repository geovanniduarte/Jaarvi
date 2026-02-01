# User Story: Build Manual Itinerary

## Story
**As a** traveler  
**I want to** manually add, edit, and organize activities in my day plans  
**So that** I can create a customized itinerary that fits my preferences

## Business Value
- Core MVP feature for itinerary creation
- Gives users full control over their trip planning
- Manual-first approach validates product-market fit

## Acceptance Criteria
- [ ] System creates empty day plans automatically for trip dates
- [ ] User can add activity to any day with: title, location, time block, cost estimate, activity type, notes
- [ ] User can edit activity details
- [ ] User can delete non-mandatory activities
- [ ] User can reorder activities within a day (drag-and-drop)
- [ ] User can move activity between days
- [ ] User can add transfer activities (airport→hotel, hotel→station) with transport context
- [ ] User can add lodging anchor with check-in/check-out times
- [ ] Each day has mandatory activity placeholders (sleep/accommodation)
- [ ] Activity types: visit, meal, transfer, lodging, free-time, entertainment
- [ ] System validates time blocks don't overlap
- [ ] System warns about unrealistic time gaps

## Technical Notes
- Create `DayPlan` and `Activity` entities
- Implement activity ordering (position field or timestamp-based)
- Support activity types via enum or configuration
- Validate mandatory activities per day (seed config)
- Use optimistic UI updates for better UX

## Estimated Effort
**13 Story Points** (Extra Large)

## Priority
**Critical** - Core MVP feature

## Dependencies
- mvp-03-create-trip-with-destinations

## Mapped Use Cases
- [UC-3.1: Initialize Empty Day Plans](../../product-discovery/8-UserCases.md#uc-31-initialize-empty-day-plans)
- [UC-3.2: Add Activity to Day Plan](../../product-discovery/8-UserCases.md#uc-32-add-activity-to-day-plan)
- [UC-3.3: Edit Activity Details](../../product-discovery/8-UserCases.md#uc-33-edit-activity-details)
- [UC-3.4: Delete Activity](../../product-discovery/8-UserCases.md#uc-34-delete-activity)
- [UC-3.5: Reorder Activities Within Day](../../product-discovery/8-UserCases.md#uc-35-reorder-activities-within-day)
- [UC-3.6: Move Activity Between Days](../../product-discovery/8-UserCases.md#uc-36-move-activity-between-days)
- [UC-3.7: Add Transfer Activity](../../product-discovery/8-UserCases.md#uc-37-add-transfer-activity)
- [UC-3.8: Add Lodging Anchor](../../product-discovery/8-UserCases.md#uc-38-add-lodging-anchor)

## Definition of Done
- [ ] Unit tests for activity CRUD operations
- [ ] Integration tests for day plan initialization
- [ ] API documentation with activity type examples
- [ ] Mobile UI with drag-and-drop functionality
- [ ] Form validation tested
- [ ] Time conflict detection working
- [ ] Mandatory activity enforcement tested
- [ ] Performance: Add activity < 500ms
