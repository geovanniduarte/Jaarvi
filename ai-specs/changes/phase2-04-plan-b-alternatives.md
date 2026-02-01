# User Story: Plan B Alternative Activities

## Story
**As a** traveler  
**I want to** get alternative activity suggestions when my original plan fails  
**So that** I can quickly adapt to unexpected changes without stress

## Business Value
- Real-time problem resolution
- Reduces travel stress during trip
- Showcases AI value during execution

## Acceptance Criteria
- [ ] User can mark activity as unavailable (closed, weather, budget issue)
- [ ] AI suggests 2-3 alternative activities with similar intent/type
- [ ] Alternatives consider: same time block, similar cost, nearby location
- [ ] User can swap original activity with alternative in one tap
- [ ] User can view alternatives without committing to change
- [ ] System explains why each alternative fits (rationale)
- [ ] User can dismiss all alternatives and manually add new activity
- [ ] Alternatives respect remaining budget for the day
- [ ] System logs incident for analytics (closure reports)

## Technical Notes
- Trigger alternative generation on activity status change (unavailable)
- Use AI with context: activity type, intent, location, time, budget
- Implement fast generation (< 5 seconds) for in-trip use
- Cache common alternatives for popular activities
- Store incident logs in `IncidentLog` entity
- Handle offline scenario (show cached alternatives or manual fallback)

## Estimated Effort
**8 Story Points** (Large)

## Priority
**High** - Critical for in-trip experience

## Dependencies
- phase2-01-ai-itinerary-generation
- mvp-08-execute-daily-activities

## Mapped Use Cases
- [UC-4.5: Generate Plan B Alternatives](../../product-discovery/8-UserCases.md#uc-45-generate-plan-b-alternatives)

## Definition of Done
- [ ] Unit tests for alternative generation logic
- [ ] Integration tests with AI service
- [ ] API documentation updated
- [ ] Mobile UI with quick swap functionality
- [ ] Rationale display per alternative
- [ ] Incident logging working
- [ ] Performance: Alternatives < 5 seconds
- [ ] Offline fallback tested
