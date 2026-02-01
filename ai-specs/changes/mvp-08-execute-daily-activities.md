# User Story: Execute Daily Activities

## Story
**As a** traveler  
**I want to** view today's activities and track my progress  
**So that** I can stay on schedule and know what to do next

## Business Value
- Core in-trip execution feature
- Reduces cognitive load during travel
- Provides "next best action" guidance

## Acceptance Criteria
- [ ] User can open "Today" view to see current day's activities
- [ ] System highlights current/next activity based on time
- [ ] User can mark activity as "in progress"
- [ ] User can mark activity as "complete"
- [ ] User can skip activity
- [ ] System advances to next activity automatically after completion
- [ ] User sees progress indicator (completed/remaining activities)
- [ ] User sees time estimates for remaining activities
- [ ] Today view shows mandatory activities that need attention (pending lodging, etc.)
- [ ] User can navigate to activity location via external maps (deep link)
- [ ] System adjusts schedule when activities are skipped

## Technical Notes
- Create `ExecutionState` entity to track progress
- Calculate current activity based on time and completion status
- Implement activity status: not_started/in_progress/done/skipped
- Deep link to Google Maps or Apple Maps with destination
- Update schedule dynamically when activities change
- Cache today's plan for offline access

## Estimated Effort
**8 Story Points** (Large)

## Priority
**Critical** - Core MVP execution feature

## Dependencies
- mvp-05-build-manual-itinerary
- mvp-06-view-itinerary

## Mapped Use Cases
- [UC-5.1: Open Today View](../../product-discovery/8-UserCases.md#uc-51-open-today-view)
- [UC-5.4: Check Mandatory Activity Readiness](../../product-discovery/8-UserCases.md#uc-54-check-mandatory-activity-readiness)
- [UC-6.1: Start Activity](../../product-discovery/8-UserCases.md#uc-61-start-activity)
- [UC-6.2: Navigate to Activity Location](../../product-discovery/8-UserCases.md#uc-62-navigate-to-activity-location)
- [UC-6.5: Mark Activity as Complete](../../product-discovery/8-UserCases.md#uc-65-mark-activity-as-complete)
- [UC-6.6: Skip Activity](../../product-discovery/8-UserCases.md#uc-66-skip-activity)
- [UC-6.8: View Activity Progress](../../product-discovery/8-UserCases.md#uc-68-view-activity-progress)

## Definition of Done
- [ ] Unit tests for execution state management
- [ ] Integration tests for activity status transitions
- [ ] API documentation updated
- [ ] Mobile UI with clear "next action" display
- [ ] Deep linking to maps tested on iOS and Android
- [ ] Progress indicator working correctly
- [ ] Mandatory activity alerts tested
- [ ] Performance: Today view loads < 500ms
