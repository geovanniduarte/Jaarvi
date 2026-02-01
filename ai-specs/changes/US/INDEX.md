# Jaarvi Product Backlog - Visual Index

## 📊 Backlog Overview

| Phase | Stories | Story Points | Duration (Estimate) |
|-------|---------|--------------|---------------------|
| **MVP (Phase 1)** | 10 | 72 SP | 3 months |
| **Phase 2** | 8 | 85 SP | 3 months |
| **Phase 3** | 6 | 86 SP | 3+ months |
| **Total** | **24** | **243 SP** | **9+ months** |

---

## 🎯 MVP - Phase 1 (Months 1-3)

### Authentication & Profile
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| Create User Account | [mvp-01-create-user-account.md](./mvp-01-create-user-account.md) | 🔴 Critical | 3 | None |
| Manage User Profile | [mvp-02-manage-user-profile.md](./mvp-02-manage-user-profile.md) | 🟠 High | 5 | mvp-01 |

### Trip Management
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| Create Trip with Destinations | [mvp-03-create-trip-with-destinations.md](./mvp-03-create-trip-with-destinations.md) | 🔴 Critical | 8 | mvp-02 |
| Manage Trip Lifecycle | [mvp-04-manage-trip-lifecycle.md](./mvp-04-manage-trip-lifecycle.md) | 🟠 High | 5 | mvp-03 |

### Itinerary Planning
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| Build Manual Itinerary | [mvp-05-build-manual-itinerary.md](./mvp-05-build-manual-itinerary.md) | 🔴 Critical | 13 | mvp-03 |
| View Itinerary | [mvp-06-view-itinerary.md](./mvp-06-view-itinerary.md) | 🟠 High | 5 | mvp-05 |

### Document Vault
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| Manage Travel Documents | [mvp-07-manage-travel-documents.md](./mvp-07-manage-travel-documents.md) | 🟠 High | 8 | mvp-03, mvp-05 |

### Trip Execution
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| Execute Daily Activities | [mvp-08-execute-daily-activities.md](./mvp-08-execute-daily-activities.md) | 🔴 Critical | 8 | mvp-05, mvp-06 |
| Time Management Notifications | [mvp-09-time-management-notifications.md](./mvp-09-time-management-notifications.md) | 🟡 Medium | 5 | mvp-08 |

### System Infrastructure
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| System Sync and Backup | [mvp-10-system-sync-and-backup.md](./mvp-10-system-sync-and-backup.md) | 🟠 High | 8 | mvp-01, All MVP |

**MVP Subtotal: 72 Story Points**

---

## 🤖 Phase 2 - AI-Assisted (Months 4-6)

### AI Itinerary Features
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| AI-Powered Itinerary Generation | [phase2-01-ai-itinerary-generation.md](./phase2-01-ai-itinerary-generation.md) | 🔴 Critical | 13 | mvp-03, mvp-05 |
| AI Activity Suggestions | [phase2-02-ai-activity-suggestions.md](./phase2-02-ai-activity-suggestions.md) | 🟠 High | 8 | phase2-01, mvp-05 |
| AI Itinerary Optimization | [phase2-03-ai-itinerary-optimization.md](./phase2-03-ai-itinerary-optimization.md) | 🟡 Medium | 13 | phase2-01, mvp-06 |
| Plan B Alternative Activities | [phase2-04-plan-b-alternatives.md](./phase2-04-plan-b-alternatives.md) | 🟠 High | 8 | phase2-01, mvp-08 |

### AI Execution Support
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| Dynamic Daily Planning | [phase2-05-dynamic-daily-planning.md](./phase2-05-dynamic-daily-planning.md) | 🟠 High | 13 | phase2-01, mvp-08 |
| Operational Guidance & Playbooks | [phase2-06-operational-guidance.md](./phase2-06-operational-guidance.md) | 🟠 High | 13 | phase2-05, mvp-08 |

### Monetization & Search
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| Contextual Affiliate Recommendations | [phase2-07-affiliate-recommendations.md](./phase2-07-affiliate-recommendations.md) | 🟠 High | 8 | mvp-08, phase2-01 |
| Document Search | [phase2-08-document-search.md](./phase2-08-document-search.md) | 🟡 Medium | 5 | mvp-07 |

**Phase 2 Subtotal: 85 Story Points**

---

## 🚀 Phase 3 - Advanced Features (Months 7+)

### Collaboration & Social
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| Trip Collaboration | [phase3-01-trip-collaboration.md](./phase3-01-trip-collaboration.md) | 🟡 Medium | 13 | mvp-03, mvp-05, mvp-10 |
| Document Sharing | [phase3-06-document-sharing.md](./phase3-06-document-sharing.md) | ⚪ Low | 5 | mvp-07 |

### Offline & Maps
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| Full Offline Mode | [phase3-02-full-offline-mode.md](./phase3-02-full-offline-mode.md) | 🟠 High | 13 | mvp-10, All data models |
| Embedded Maps & Navigation | [phase3-03-embedded-maps.md](./phase3-03-embedded-maps.md) | 🟡 Medium | 13 | mvp-08, phase3-02 |

### Personalization & Integrations
| Story | File | Priority | SP | Dependencies |
|-------|------|----------|----|--------------| 
| Personalized Recommendations | [phase3-04-personalized-recommendations.md](./phase3-04-personalized-recommendations.md) | ⚪ Low | 13 | phase2-01, Multiple trips |
| Partner Booking Integration | [phase3-05-partner-booking-integration.md](./phase3-05-partner-booking-integration.md) | ⚪ Low | 21 | mvp-07, mvp-05, phase2-07 |

**Phase 3 Subtotal: 86 Story Points**

---

## 🎯 Critical Path (MVP)

```
mvp-01 → mvp-02 → mvp-03 → mvp-05 → mvp-08
  (3)     (5)      (8)      (13)     (8)    = 37 SP Critical Path
```

Parallel development opportunities:
- **mvp-04** (Trip Lifecycle) can start after mvp-03
- **mvp-06** (View Itinerary) can start after mvp-05
- **mvp-07** (Documents) can start after mvp-03 (parallel with mvp-05)
- **mvp-09** (Notifications) starts after mvp-08
- **mvp-10** (Sync) integrates at the end

---

## 📈 Velocity Assumptions

Assuming a team velocity of **25-30 SP per 2-week sprint**:

| Phase | Sprints | Duration |
|-------|---------|----------|
| MVP | 5-6 sprints | 10-12 weeks |
| Phase 2 | 6-7 sprints | 12-14 weeks |
| Phase 3 | 6-7 sprints | 12-14 weeks |

---

## 🔗 Quick Links

- [Product Discovery](../../product-discovery/1-JaarviGeneralSpecs.md)
- [Use Cases](../../product-discovery/8-UserCases.md)
- [Backend Standards](../specs/backend-standards.mdc)
- [API Specification](../specs/api-spec.yml)

---

## 📝 Legend

**Priority:**
- 🔴 Critical - Blocks other features
- 🟠 High - Important for user value
- 🟡 Medium - Valuable but can be deferred
- ⚪ Low - Nice-to-have

**Story Points:**
- 3 SP = 1-2 days
- 5 SP = 3-5 days
- 8 SP = 1-2 weeks
- 13 SP = 2-3 weeks
- 21 SP = 3-4 weeks

---

*Last updated: 2026-01-26*
