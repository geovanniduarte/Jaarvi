# Jaarvi - Pending Documentation & Tasks

> **Document Purpose**: Track documentation tasks and decisions deferred for later phases or requiring additional research/input.
> 
> **Status Legend**: 
> - 🔴 Blocking (required before development)
> - 🟡 Important (needed during Sprint 1)
> - 🟢 Nice-to-have (can be done incrementally)

---

## Critical Pending Items

### 1. User Stories & Use Cases
**Status**: 🔴 Blocking  
**Priority**: High  
**Assignee**: Product Owner  
**Target**: Before Sprint 1

**Description**: Create detailed user stories following the format:

```
As a [user type]
I want to [action]
So that [benefit]

Acceptance Criteria:
- Given [context]
- When [action]
- Then [expected result]
```

**Required Stories**:
- [ ] Trip creation flow (3-step wizard)
- [ ] Manual activity creation
- [ ] Activity reordering via drag & drop
- [ ] Document upload and linking
- [ ] Today view navigation
- [ ] External map navigation trigger
- [ ] Activity completion marking
- [ ] Trip editing and deletion

**File**: `product-discovery/5-UseCases.md`

---

### 2. MVP Scope & Sprint Planning
**Status**: 🟡 Important  
**Priority**: High  
**Assignee**: Product Owner + Tech Lead  
**Target**: Before Sprint 1

**Description**: Break down MVP Phase 1 features into:
- Story points or T-shirt sizing
- Priority matrix (Must-have vs Nice-to-have)
- Dependencies between features
- Technical spikes needed

**Deliverables**:
- [ ] Feature breakdown spreadsheet
- [ ] Sprint 1 backlog
- [ ] Risk assessment
- [ ] Timeline estimate

**File**: `product-discovery/6-MVP-Sprint-Plan.md` (to be created)

---

### 3. Detailed Test Strategy
**Status**: 🟡 Important  
**Priority**: Medium  
**Assignee**: QA Lead / Tech Lead  
**Target**: Week 1 of Sprint 1

**Description**: Define comprehensive testing approach:
- Test pyramid (unit 70%, integration 20%, E2E 10%)
- Critical user journeys to test
- Performance benchmarks (API response times, UI render times)
- Device/OS compatibility matrix
- Test data strategy

**Deliverables**:
- [ ] Test plan document
- [ ] Critical path test cases
- [ ] Performance acceptance criteria
- [ ] Device test matrix

**File**: `ai-specs/specs/testing-strategy.mdc` (to be created)

---

## Important Pending Items

### 4. CI/CD Pipeline Configuration
**Status**: 🟡 Important  
**Priority**: Medium  
**Assignee**: DevOps / Backend Lead  
**Target**: Week 2 of Sprint 1

**Description**: Set up automated deployment pipeline:
- GitHub Actions or GitLab CI configuration
- Automated testing on PR
- Deployment stages (dev → staging → production)
- Rollback procedures
- Feature flags setup

**Deferred Reason**: Not blocking initial development, but needed before first demo.

**File**: `.github/workflows/` (to be created)

---

### 5. Playbooks Content Strategy
**Status**: 🟢 Nice-to-have  
**Priority**: Low (Phase 2)  
**Assignee**: Content Team  
**Target**: Phase 2

**Description**: Define content for high-impact playbooks:
- Airport → Hotel guides per city
- Public transit & ticket machines
- Payment methods & currency tips
- Common traveler mistakes

**Deferred Reason**: MVP works without playbooks; can be added incrementally.

**File**: `content/playbooks/` (to be created in Phase 2)

---

### 6. Localization Strategy
**Status**: 🟢 Nice-to-have  
**Priority**: Low  
**Assignee**: Product Manager  
**Target**: Post-MVP

**Description**: Define internationalization approach:
- Target languages (English, Spanish, French, Japanese?)
- UI string externalization
- Date/time/currency formatting
- Right-to-left (RTL) support?

**Deferred Reason**: MVP launches in English only.

**File**: `ai-specs/specs/i18n-strategy.md` (to be created post-MVP)

---

### 7. Accessibility Audit Checklist
**Status**: 🟢 Nice-to-have  
**Priority**: Medium  
**Assignee**: Frontend Lead  
**Target**: Sprint 2-3

**Description**: Ensure app is accessible:
- Screen reader compatibility
- Color contrast ratios (WCAG AA)
- Touch target sizes (min 48dp)
- Keyboard navigation (Android)
- VoiceOver/TalkBack testing

**Deferred Reason**: Basic accessibility in standards, formal audit later.

**File**: `product-discovery/accessibility-audit.md` (to be created)

---

## Research & Decisions Needed

### 8. Performance Benchmarks
**Status**: 🟡 Important  
**Priority**: Medium  
**Target**: Week 1 of Sprint 1

**Questions to Answer**:
- What is acceptable API latency? (Target: <500ms for 95th percentile?)
- Maximum itinerary size? (Days/activities per trip)
- Document upload size limits? (Current: TBD)
- Offline mode scope for MVP? (Current: Not in MVP)

**Action**: Tech leads define and document baselines.

---

### 9. OAuth Provider Details
**Status**: 🟡 Important  
**Priority**: High  
**Target**: Before Sprint 1

**Decisions Needed**:
- Google OAuth client ID creation
- Apple Sign-In setup (requires Apple Developer account)
- Redirect URLs for mobile apps
- Scope permissions requested

**Action**: DevOps + Mobile leads configure OAuth.

---

### 10. AWS Infrastructure Details
**Status**: 🟡 Important  
**Priority**: Medium  
**Target**: Week 2 of Sprint 1

**Decisions Needed**:
- AWS account setup
- S3 bucket naming and policies
- RDS instance sizing
- Lambda memory/timeout configurations
- Cost estimates

**Action**: DevOps lead creates infrastructure plan.

---

## Low Priority / Future Considerations

### 11. Multi-User Collaboration (Phase 3)
**Status**: 🟢 Nice-to-have  
**Priority**: Low (Phase 3)  
**Target**: Post-Phase 2

**Description**: Enable multiple travelers to co-edit a trip.

**Deferred Reason**: MVP is single-user focused.

---

### 12. Embedded Maps (v2)
**Status**: 🟢 Nice-to-have  
**Priority**: Low (Phase 2-3)  
**Target**: Phase 2-3

**Description**: Replace external navigation with embedded maps.

**Deferred Reason**: MVP uses deep links to Google/Apple Maps; sufficient for Phase 1.

---

### 13. Advanced Analytics
**Status**: 🟢 Nice-to-have  
**Priority**: Low  
**Target**: Post-MVP

**Description**: User behavior tracking, conversion funnels, feature usage.

**Deferred Reason**: Focus on core features first.

---

## Update Log

| Date | Item | Status Change | Notes |
|------|------|---------------|-------|
| 2026-01-17 | Document Created | - | Initial pendings tracked |
|  |  |  |  |
