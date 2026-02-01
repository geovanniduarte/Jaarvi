# User Story: Contextual Affiliate Recommendations

## Story
**As a** traveler  
**I want to** see relevant product and service recommendations at decision points  
**So that** I can easily book tours, tickets, and essentials when I need them

## Business Value
- Primary monetization mechanism
- Non-intrusive, value-aligned commerce
- Revenue generation for sustainability

## Acceptance Criteria
- [ ] System shows recommendations at decision points (e.g., museum tickets before museum visit)
- [ ] Recommendations are contextual to activity type (tours, tickets, transport, essentials)
- [ ] User sees "why it fits" rationale for each recommendation
- [ ] User can click recommendation to open partner site (in-app browser or external)
- [ ] User can dismiss recommendations individually
- [ ] Recommendations never block core itinerary execution
- [ ] System logs click attribution (trip/day/activity context)
- [ ] User can provide feedback on recommendations (helpful/not helpful)
- [ ] Maximum 2 recommendations per activity (avoid spam)
- [ ] Recommendations respect user budget constraints

## Technical Notes
- Create `Recommendation` and `AffiliateClick` entities
- Integrate with affiliate networks (TBD: specific partners)
- Implement recommendation engine (rules-based for MVP)
- Use activity context to trigger relevant recommendations
- Track click attribution for analytics and commission tracking
- Implement in-app browser for seamless UX (WebView)
- Store partner URLs with UTM tracking parameters
- Handle failed partner links gracefully (retry or fallback)

## Estimated Effort
**8 Story Points** (Large)

## Priority
**High** - Monetization foundation

## Dependencies
- mvp-08-execute-daily-activities (recommendation trigger points)
- phase2-01-ai-itinerary-generation (AI can suggest relevant products)

## Mapped Use Cases
- [UC-8.1: View Contextual Recommendation](../../product-discovery/8-UserCases.md#uc-81-view-contextual-recommendation)
- [UC-8.2: Open Affiliate Link](../../product-discovery/8-UserCases.md#uc-82-open-affiliate-link)
- [UC-8.3: Dismiss Recommendation](../../product-discovery/8-UserCases.md#uc-83-dismiss-recommendation)
- [UC-8.4: Retry Failed Affiliate Link](../../product-discovery/8-UserCases.md#uc-84-retry-failed-affiliate-link)
- [UC-10.3: Track Affiliate Click Attribution](../../product-discovery/8-UserCases.md#uc-103-track-affiliate-click-attribution)

## Definition of Done
- [ ] Unit tests for recommendation engine
- [ ] Integration tests for click tracking
- [ ] API documentation with recommendation format
- [ ] Mobile UI with recommendation cards
- [ ] In-app browser implemented
- [ ] Click attribution tracking working
- [ ] Feedback mechanism implemented
- [ ] Performance: Recommendations load < 1 second
- [ ] Partner integration tested (2+ partners)
