# User Story: Operational Step Guidance and Playbooks

## Story
**As a** traveler  
**I want to** get step-by-step instructions for complex tasks  
**So that** I can navigate confusing situations like ticket machines, airport transfers, and transit passes

## Business Value
- High-impact execution support
- Reduces travel mistakes and stress
- Differentiates from generic navigation apps

## Acceptance Criteria
- [ ] User can access step-by-step guidance for supported activity types
- [ ] Guidance includes: landmark-based navigation, procedural instructions, checkpoints
- [ ] User can check off steps as completed
- [ ] Guidance types: airport→hotel transfer, ticket machines, transit passes, market navigation
- [ ] User can view photos/diagrams when available (future enhancement)
- [ ] System provides problem resolution playbooks for incidents (missed connections, lost items, safety)
- [ ] User can report issues with guidance (feedback)
- [ ] Guidance is available offline (cached locally)
- [ ] System highlights time-critical steps (e.g., "must do before 5pm")

## Technical Notes
- Create `StepGuide` entity with ordered steps and checkpoints
- Seed playbooks for high-impact scenarios (airport transfers, transit)
- Use AI to generate contextual playbooks based on destination
- Store playbooks in `LocalAdvicePack` per destination
- Support markdown format for step descriptions
- Implement progress tracking per step guide
- Cache guides locally for offline access

## Estimated Effort
**13 Story Points** (Extra Large)

## Priority
**High** - Key differentiator

## Dependencies
- phase2-05-dynamic-daily-planning (shares playbook infrastructure)
- mvp-08-execute-daily-activities

## Mapped Use Cases
- [UC-6.3: View Landmark-Based Instructions](../../product-discovery/8-UserCases.md#uc-63-view-landmark-based-instructions)
- [UC-6.4: View Operational Step Guidance](../../product-discovery/8-UserCases.md#uc-64-view-operational-step-guidance)
- [UC-6.9: Report Activity Issue](../../product-discovery/8-UserCases.md#uc-69-report-activity-issue)
- [UC-6.10: Access Problem Resolution Playbook](../../product-discovery/8-UserCases.md#uc-610-access-problem-resolution-playbook)

## Definition of Done
- [ ] Unit tests for step guide logic
- [ ] API documentation with playbook format
- [ ] Mobile UI with step-by-step checklist
- [ ] Playbooks seeded for 5+ high-impact scenarios
- [ ] Issue reporting functionality working
- [ ] Offline access tested
- [ ] Performance: Step guide loads < 1 second
- [ ] User feedback mechanism implemented
