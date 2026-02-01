# User Story: Dynamic Daily Planning with Local Advice

## Story
**As a** traveler  
**I want to** refresh my daily plan with AI-powered local insights  
**So that** I can make better decisions based on current conditions and local dynamics

## Business Value
- Real-time adaptation to conditions
- Local knowledge (key differentiator)
- Improves trip execution quality

## Acceptance Criteria
- [ ] User can refresh daily plan at start of day
- [ ] AI adapts plan to: current time available, opening hours, travel times, weather (when available)
- [ ] System surfaces local advice for today's activities: payment tips, negotiation norms, buy-in-advance vs on-site
- [ ] Local advice is contextual per activity type (museum, restaurant, market, transport)
- [ ] User can view "why" for each local advice tip
- [ ] User approves refreshed plan or makes manual edits
- [ ] System highlights changes from original plan (visual diff)
- [ ] Local advice is based on destination playbook coverage
- [ ] System flags activities with limited local coverage

## Technical Notes
- Create `LocalAdvicePack` entity with destination-specific playbooks
- Implement start-of-day refresh workflow
- Use AI to adapt plan based on `DailyContext` (time, weather, constraints)
- Integrate weather API (optional for MVP of Phase 2)
- Seed initial playbooks for 3-5 major destinations
- Store playbook version for auditability
- Cache playbooks locally for offline access

## Estimated Effort
**13 Story Points** (Extra Large)

## Priority
**High** - Differentiating feature

## Dependencies
- phase2-01-ai-itinerary-generation
- mvp-08-execute-daily-activities

## Mapped Use Cases
- [UC-5.2: Refresh Daily Plan](../../product-discovery/8-UserCases.md#uc-52-refresh-daily-plan)
- [UC-5.3: View Local Advice for Day](../../product-discovery/8-UserCases.md#uc-53-view-local-advice-for-day)

## Definition of Done
- [ ] Unit tests for daily refresh logic
- [ ] Integration tests with AI service
- [ ] API documentation with playbook format
- [ ] Mobile UI with local advice cards
- [ ] Visual diff of plan changes
- [ ] Playbooks seeded for 3-5 destinations
- [ ] Performance: Refresh < 10 seconds
- [ ] Offline playbook access working
