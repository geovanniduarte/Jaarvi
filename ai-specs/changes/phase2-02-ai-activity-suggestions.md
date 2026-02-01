# User Story: AI Activity Suggestions

## Story
**As a** traveler  
**I want to** get AI suggestions to fill gaps in my manually created itinerary  
**So that** I can enhance my trip with relevant activities without starting from scratch

## Business Value
- Hybrid manual + AI approach
- Helps users discover activities they might miss
- Increases engagement with manual itineraries

## Acceptance Criteria
- [ ] User can click "Suggest activities" for any day with available time
- [ ] AI suggests 3-5 activities with rationale (why they fit)
- [ ] Suggestions consider: available time blocks, budget remaining, user preferences, nearby locations
- [ ] User can preview activity details before adding
- [ ] User can add suggested activity with one tap
- [ ] User can dismiss suggestions individually
- [ ] System tracks accepted/rejected suggestions (for learning)
- [ ] Suggestions refresh when day context changes (activities added/removed)
- [ ] User sees confidence indicator per suggestion (high/medium/low)

## Technical Notes
- Use same AI orchestrator as full generation
- Pass day context: existing activities, available time, location, budget spent
- Implement suggestion ranking based on user preferences
- Store suggestion metadata for analytics
- Limit to 5 suggestions per request to control costs
- Use async processing (SQS) for suggestion generation

## Estimated Effort
**8 Story Points** (Large)

## Priority
**High** - Enhances manual planning

## Dependencies
- phase2-01-ai-itinerary-generation
- mvp-05-build-manual-itinerary

## Mapped Use Cases
- [UC-4.3: Request AI Activity Suggestions for Day](../../product-discovery/8-UserCases.md#uc-43-request-ai-activity-suggestions-for-day)

## Definition of Done
- [ ] Unit tests for suggestion logic
- [ ] Integration tests with AI service
- [ ] API documentation updated
- [ ] Mobile UI with suggestion cards
- [ ] One-tap add functionality working
- [ ] Confidence indicators displayed
- [ ] Performance: Suggestions < 10 seconds
- [ ] Analytics tracking implemented
