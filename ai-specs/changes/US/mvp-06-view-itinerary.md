# User Story: View Itinerary and Day Plans

## Story
**As a** traveler  
**I want to** view my complete itinerary and individual day plans  
**So that** I can see my trip schedule and estimated costs at a glance

## Business Value
- Provides overview of planned trip
- Displays feasibility indicators
- Shows budget tracking

## Acceptance Criteria
- [ ] User can view full multi-day itinerary with all days in sequence
- [ ] User can navigate between days (swipe or tabs)
- [ ] User can view day plan summary showing: all activities, total estimated cost, time blocks
- [ ] System displays feasibility indicators (time conflicts, budget status)
- [ ] System shows daily budget: estimated vs. target (under/over/on-budget)
- [ ] Empty day plans show call-to-action to add activities
- [ ] Itinerary shows trip progress (days completed vs. remaining)
- [ ] User can quickly jump to specific day (date picker or day list)
- [ ] Visual timeline shows activity sequence within day

## Technical Notes
- Aggregate costs and time per day
- Calculate budget status using user preferences
- Display warnings for time conflicts
- Cache itinerary locally for quick access
- Support both list and timeline views

## Estimated Effort
**5 Story Points** (Medium)

## Priority
**High** - Essential for trip planning

## Dependencies
- mvp-05-build-manual-itinerary

## Mapped Use Cases
- [UC-3.9: View Day Plan Summary](../../product-discovery/8-UserCases.md#uc-39-view-day-plan-summary)
- [UC-3.10: View Full Itinerary](../../product-discovery/8-UserCases.md#uc-310-view-full-itinerary)
- [UC-3.11: Detect Time Conflicts](../../product-discovery/8-UserCases.md#uc-311-detect-time-conflicts)
- [UC-3.12: Calculate Daily Budget](../../product-discovery/8-UserCases.md#uc-312-calculate-daily-budget)

## Definition of Done
- [ ] Unit tests for cost aggregation
- [ ] Unit tests for conflict detection
- [ ] API returns itinerary with all calculations
- [ ] Mobile UI with smooth navigation
- [ ] Visual indicators for conflicts and budget
- [ ] Performance: Itinerary loads < 1 second
- [ ] Offline caching working
