# Jaarvi

## 1) Short product description

**Jaarvi is a mobile travel assistant that helps travelers plan and execute trips end-to-end, providing in-destination, real-time guidance as if you were traveling with a highly experienced local companion.** It supports two key moments: **pre-trip planning** (build an editable, realistic itinerary) and **in-trip execution** (step-by-step guidance, time management, and practical decisions while the trip is happening).

To make the product viable, Jaarvi should ship with **phased destination coverage** (starting with a limited set of cities/destinations and expanding over time), prioritizing recommendation quality and trust over breadth.

Jaarvi is designed for travelers who want to:
- Reduce the effort and uncertainty of trip planning
- Avoid common mistakes in unfamiliar places (costly decisions, unsafe routes, misunderstandings)
- Make better on-the-ground choices based on local dynamics (culture, pricing, negotiation norms, transportation patterns)
- Keep all travel documents and trip artifacts organized in one place

## 2) Added value and competitive advantages

### Added value
- **Planning friction reduction**: Converts high-level trip intent (cities + days + preferences + budget) into a usable, editable itinerary with minimal user effort.
- **Context-aware execution support**: Helps users *during* the trip, not just before it—providing guidance, alerts, and “what to do next” actions tied to the current day and activity.
- **Local-dynamics guidance**: Offers travel advice that goes beyond listing places (e.g., when to buy on-site vs in advance, what to negotiate, which currency/payment method is advantageous).
- **Safety and efficiency**: Promotes safer and more efficient decisions with clear, actionable instructions in unfamiliar environments.
- **Single source of truth for travel artifacts**: Stores documents (tickets, reservations, permits, confirmations) linked to the itinerary so they’re accessible at the moment they’re needed.
- **Trust through transparency and control**: Explains “why” behind suggestions (time, distance, cost, convenience) and makes it easy to override with quick, manual edits.

### Competitive advantages (how Jaarvi can stand out)
- **“Experienced traveler” behavior model**: The core differentiation is acting like someone who has already done the same trip, anticipating friction and recommending proven actions (common pitfalls, best practices, timing, and cost-saving tactics).
- **Itinerary as a living execution pipeline**: Each day is not a static checklist; it’s a sequence of activities with location/time/cost context and real-time progress support.
- **Micro-instructions for real-world tasks**: Provides explicit “landmark-based” directions and operational steps for complex tasks (e.g., buying transit passes, navigating airport-to-hotel routes), tailored to walking navigation and high-stress moments.
- **Playbooks-first execution UX**: Ships a small set of high-impact playbooks first (airport→hotel, public transit/tickets, payments/currency, common pitfalls), then expands coverage.
- **Integrated commerce with user value**: Monetization through affiliate redirection is contextual and non-intrusive, embedded at decision points in the itinerary rather than generic ads.

## 3) Main functions (functional scope)

### A) Trip creation and preferences
- **Trip setup**: Create a trip with cities, dates, number of days per city, and traveler profile.
- **Preference capture**: Collect preferences and constraints (interests, pace, budget range, travel style, time constraints).
- **Editable recommendations**: Provide an initial plan that the user can modify quickly (swap/remove activities, reorder days, adjust timing and budget).

### B) Itinerary planning (pre-trip, multi-day, multi-city)
- **Day-by-day itinerary generation**: Propose a structured plan as a pipeline of days, each containing ordered activities.
- **Booking-aware planning**: Support planning that includes transport and lodging (flights/trains/buses, accommodations) and integrates them into the trip timeline.
- **Cost and time awareness**: Show approximate time blocks and cost ranges per activity/day and help users adjust to fit constraints.
- **Manual-first fallback**: Allow users to build and adjust the plan even when recommendations are not ideal, so the experience never blocks on “perfect AI”.

### C) On-site daily planning (in-destination, “start-of-day” mode)
- **Daily itinerary refresh**: At the start of the day, build or refine a realistic schedule based on location, opening hours, travel times, budget, and user preferences.
- **Practical local advice**: Provide guidance based on local economic/cultural dynamics (e.g., whether to buy in advance vs on-site; negotiation tips; cash vs card considerations).
- **Plan B alternatives**: Offer quick alternative options when the original plan is not viable (closures, weather, energy level, budget).

### D) Activity execution guidance (real-time assistance)
- **Route guidance with map feedback**: Provide navigation context and progress feedback for activities and transfers.
- **Landmark-based instructions**: Offer human-friendly cues (e.g., “you should see X bakery; turn right there”) suitable for walking and unfamiliar streets.
- **Step-by-step operational help**: Give procedural instructions for tasks that are typically confusing (ticket machines, airport transfers, local transit passes).
- **Problem resolution playbooks**: Provide action-oriented help for incidents and unexpected needs (missed connections, safety concerns, lost items, last-minute changes).
- **Notifications and time management**: Remind users when to leave, highlight time-critical events (closing times, next transfer), and keep the day realistic.

### E) Travel document vault (linked to the itinerary)
- **Document storage**: Store tickets, reservations, permits, and confirmations.
- **Contextual retrieval**: Surface the right documents at the right time (per day/activity/transfer).
- **Organization by trip/day/activity**: Keep artifacts structured around the itinerary, not as a generic file dump.

### F) Affiliate-based monetization (value-aligned commerce)
- **Contextual recommendations**: Suggest relevant products/services at decision points (tours, museum tickets, transportation, essentials).
- **Non-intrusive affiliate flow**: Redirect to partner providers and attribute conversion without degrading trust or the core planning/execution experience.

### G) Core UX principles (non-functional but product-critical)
- **Low cognitive load**: Present one “next best action” during execution to avoid overwhelming users.
- **Editability and trust**: Make recommendations transparent and easily adjustable; show why suggestions are made (cost/time/safety/local dynamics).
- **Resilience offline/low connectivity (future-friendly)**: Prioritize access to the itinerary and critical documents even when connectivity is poor (implementation detail to be defined later).



## 3) Main Functions - Functional Scope (Detailed)

This section expands the functions listed in [3) Main functions (functional scope)](#3-main-functions-functional-scope) into workflows that clarify **who** is involved, **what the system must persist**, and **what outcomes** are expected.

### A) Trip creation and preferences (detailed)

- **Primary user(s)**:
  - Traveler (account owner)
  - Optional: co-traveler (future) (TBD)
  - AI agent(s) (system actor): used to interpret inputs and prepare context for planning and recommendations

- **Persisted data (minimum)**:
  - `UserProfile`: language, units, time zone behavior (TBD), default pace, budget range
  - `PreferenceSet`: interests, tra }vel style, constraints (time windows, mobility/accessibility needs (TBD))
  - `Trip`: id, name, start/end dates, status (draft/active/completed), createdAt/updatedAt
  - `TripDestination`: city/destination id, date range or day index range, notes
  - `TripPlanningContext` (AI-ready snapshot):
    - preferences snapshot (resolved defaults + explicit user inputs)
    - time constraints and daily windows (when provided)
    - destination coverage metadata (which cities/destinations have strong playbooks vs limited coverage)
    - user-provided constraints that affect recommendations (e.g., “must see”, “avoid”, budget ceilings)

- **Workflow**:
  - User creates a new trip (name optional) and selects dates.
  - User adds destinations (one or multiple) and assigns number of days per destination.
  - User sets preferences/constraints (budget, pace, interests, time constraints).
  - System validates input (date ranges, days per destination, required fields).
  - System prepares an AI planning context snapshot (for reproducibility and auditability of “why” behind suggestions).

- **Possible outcomes**:
  - **Success**: trip is created as `draft`, ready for itinerary generation.
  - **Partial**: trip saved with incomplete preferences; user can continue later (manual-first).
  - **Failure**: invalid dates/days; user receives actionable validation errors.

### B) Itinerary planning (pre-trip, multi-day, multi-city) (detailed)

- **Primary user(s)**:
  - Traveler
  - AI agent(s) (system actor): generates itinerary drafts and recommendation rationale using available context

- **Persisted data (minimum)**:
  - `Itinerary`: tripId, version, generationSource (user/manual/system), createdAt
  - `DayPlan`: tripId, date/dayIndex, destinationId, start/end time windows (optional), status
  - `Activity`: dayPlanId, type (transfer/visit/meal/free-time/booking), title, location (optional), time block, cost estimate (optional), notes
  - Mandatory daily activities (configurable via seed/config):
    - One activity per mandatory `activityTypeKey` must exist per `DayPlan` (examples: `sleep`, others TBD)
    - readiness tracked (`pending|planned|prepared`) plus evidence via linked document OR a note (e.g., friend’s house, overnight bus/flight)
  - `BookingStub` (optional for MVP): provider, confirmation metadata, links, check-in/out times
  - `EditHistory` (optional, MVP-friendly): lastEditedAt, lastEditedBy (single-user)
  - `ItineraryGeneration` (AI generation metadata):
    - prompt inputs snapshot (trip + preferences + constraints)
    - context inputs used (when available): flight schedules/prices, local transit patterns, opening hours (TBD), weather (TBD), local dynamics/playbooks
    - model/agent metadata (agent name/version) (TBD)
    - confidence signals (high/medium/low) per day/activity (TBD)
    - citations / sources list (URLs or provider references) when applicable (TBD)
    - rationale per recommendation (“why”: time/cost/distance/convenience/local dynamics)

- **Workflow**:
  - User requests an initial itinerary ("generate plan" [PHASE 2]) OR starts from a blank plan (manual-first [MVP]).
  - [PHASE 2 ONLY] AI agent compiles a draft itinerary by combining:
    - user preferences and constraints,
    - trip dates and destination sequencing,
    - tourism context inputs when available (e.g., flight prices/schedules, weather, opening hours),
    - local culture/dynamics guidance and high-impact playbooks for the destination.
  - [PHASE 2 ONLY] System produces a day-by-day plan (pipeline of days) with ordered activities plus "why" rationale.
  - [MVP] User manually builds itinerary day-by-day using CRUD operations (add/edit/delete/reorder activities).
  - System ensures each `DayPlan` includes all configured mandatory activity types (seed/config):
    - default status is `pending` until the user adds evidence (e.g., reservation document) or a note
  - User reviews and edits (both MVP and Phase 2):
    - reorder activities,
    - swap/remove activities,
    - adjust time blocks and budgets,
    - insert transfers and lodging anchors (arrival/departure/hotel check-in/out).
  - System continuously recalculates feasibility signals (time conflicts, unrealistic travel time, budget overflow) (TBD: exact rules).

- **Possible outcomes**:
  - **Success [PHASE 2]**: an AI-generated itinerary version is saved (with generation metadata) and marked "ready for execution".
  - **Success [MVP]**: a manually-created itinerary is saved and ready for execution.
  - **Editable fallback [BOTH]**: user can craft/adjust plan manually at any time.
  - **Partial [PHASE 2]**: itinerary generated with limited context (e.g., missing live prices/weather); system flags affected activities and invites manual adjustment.
  - **Failure [PHASE 2 ONLY]**: generation fails (no coverage/temporary error); user falls back to manual planning.

### C) On-site daily planning (start-of-day mode) (detailed)

- **Primary user(s)**:
  - Traveler (in destination)

- **Persisted data (minimum)**:
  - `DailyContext`: date, destinationId, user constraints for the day (budget/pace/time windows), weather flag (TBD), notes
  - `DayPlan` updates: refreshedAt, change reason (start-of-day refresh)
  - `LocalAdvicePack` (playbooks per destination): destinationId, version, lastUpdatedAt

- **Workflow**:
  - User opens “Today” at the start of the day.
  - System loads the planned day and adapts it to current constraints (time available, opening hours (TBD), travel times (TBD)).
  - System surfaces practical local advice when applicable (e.g., buy on-site vs in advance; payment/currency tips), based on available destination coverage.
  - User approves the refreshed plan or performs quick edits.

- **Possible outcomes**:
  - **Success**: day plan is refreshed and confirmed, setting a “next action”.
  - **Plan B**: if key activities are not viable (closed, too far, too expensive), system proposes alternatives of similar intent.
  - **Fallback**: if data is missing (coverage gaps), system provides a minimal schedule and suggests manual edits.

### D) Activity execution guidance (Today mode) (detailed)

- **Primary user(s)**:
  - Traveler (during execution)

- **Persisted data (minimum)**:
  - `ExecutionState`: current dayPlanId, current activityId, progress status (not_started/in_progress/done/skipped)
  - `StepGuide`: activityId, ordered steps, checkpoints/landmarks (when available)
  - `NotificationSchedule`: activityId, reminders (leave time, time-critical alerts), enabled flag
  - `IncidentLog` (optional): issue type, timestamp, resolution notes (MVP can be local-only)
  - Mandatory activity readiness:
    - readiness status + evidence (document link) or note for any mandatory activity type relevant for the day

- **Workflow**:
  - User starts the day and sees the “next best action”.
  - User enters an activity and gets:
    - route/navigation context (v1: opens external maps via deep link),
    - landmark-based instructions (when available),
    - operational steps (e.g., ticket machine guidance) from playbooks.
  - User marks steps/activities as done or skipped; system advances to the next action.
  - System sends time management notifications (leave time, reminders) if enabled.

- **Possible outcomes**:
  - **Success**: user completes the activity, progress is recorded, next action is updated.
  - **Plan B**: user is blocked (closure, delay, budget); system suggests alternatives and updates the schedule.
  - **Fallback**: if step guidance is unavailable, the system provides a simplified checklist and external navigation.
  - **Critical pending**: if any mandatory activity is still pending for tonight/today, the system highlights it as a priority to solve.

### E) Travel document vault (linked to the itinerary) (detailed)

- **Primary user(s)**:
  - Traveler

- **Persisted data (minimum)**:
  - `Document`: id, tripId, optional activityId/dayPlanId, type (ticket/reservation/passport/permit/other), file reference, createdAt
  - `DocumentMetadata`: title, provider, date/time relevance, tags
  - `AccessRules` (TBD): local encryption, secure storage key references, offline availability flags

- **Workflow**:
  - User uploads/adds a document and links it to a trip/day/activity (or leaves it unlinked).
  - System organizes documents by trip/day/activity and supports quick retrieval from “Today”.
  - User can view/share (TBD) or delete documents.

- **Possible outcomes**:
  - **Success**: documents are stored and retrievable contextually at the right moment.
  - **Failure**: file import fails (format/storage permission); user is prompted to retry or use another source.

### F) Affiliate recommendations (value-aligned monetization) (detailed)

- **Primary user(s)**:
  - Traveler
  - External providers (affiliate partners) are not app users; they are destinations for redirects.

- **Persisted data (minimum)**:
  - `Recommendation`: context (trip/day/activity), category (tour/ticket/transport/essentials), display reason (why it fits)
  - `AffiliateClick`: recommendationId, timestamp, destination URL, attributed trip/day/activity

- **Workflow**:
  - System shows contextual recommendations at decision points (e.g., museum tickets before a visit).
  - User can open a recommendation; app redirects via in-app browser / external browser (platform capability).
  - System records the click attribution (conversion tracking is partner-dependent and may be limited) (TBD).

- **Possible outcomes**:
  - **Success**: user reaches partner site; click is recorded.
  - **Non-intrusive rule**: recommendations never block the user's itinerary execution.
  - **Failure**: partner link fails; user is notified and can retry or use a fallback link.

## 4) MVP Strategy and Phased Implementation

### Overview
Jaarvi follows a **manual-first, AI-later** approach to validate product-market fit before investing in complex AI infrastructure.

### Phase 1: MVP - Manual Itinerary Creation (Months 1-3)

#### Scope
Focus on enabling travelers to **manually create, organize, and execute trips** with a delightful mobile experience.

#### Core User Flows (MVP)
1. **Trip Setup**: Create trip → Add destinations → Set basic preferences
2. **Manual Itinerary Building**: 
   - System creates empty day plans
   - User adds activities one-by-one (title, location, time, cost)
   - Drag-and-drop reordering
   - Edit/delete activities
3. **Document Management**: Upload documents → Link to activities → Quick access in "Today" view
4. **Trip Execution**: View today's activities → Check off completed → Navigate via external maps

#### Technical Stack (MVP)
**Mobile**: 
- Kotlin Multiplatform + Compose Multiplatform
- SQLDelight for local persistence
- Ktor for API calls

**Backend**:
- Node.js + TypeScript + Express
- Domain Layer (Entities, Aggregates, Domain Services) ✅
- Application Layer (CRUD Services: TripService, ItineraryService, ActivityService) ✅
- Infrastructure Layer (Prisma + PostgreSQL + S3) ✅
- **NOT INCLUDED**: AIOrchestrator, AI Service Client, Amazon Bedrock, SQS async processing

**Infrastructure**:
- AWS Lambda + API Gateway + RDS PostgreSQL + S3
- **NOT INCLUDED**: Amazon Bedrock, SQS, advanced async processing

#### Success Metrics (MVP)
- Users can create a complete multi-day, multi-city trip in < 10 minutes
- 80% of users successfully execute a trip using the app
- Document vault used by 60%+ of active trips
- Net Promoter Score (NPS) > 40

### Phase 2: AI-Assisted Features (Months 4-6)

#### New Capabilities
- One-click itinerary generation using preferences + destination + days
- AI suggestions for activities within a day
- Automatic itinerary optimization (time, budget, routes)
- Dynamic playbooks with local advice (powered by LLM)
- Plan B generation when original plan fails

#### Technical Additions
**Backend**:
- Implement AIOrchestrator (Application Layer)
- Integrate Amazon Bedrock (Infrastructure Layer)
- Add SQS for async job processing
- Add AIGenerationLog for audit trail

**Mobile**:
- Add "Generate with AI" button in itinerary creation flow
- Add "Suggest activities" option in day planning
- Add AI confidence indicators in UI

#### Migration Strategy
- All existing manual itineraries remain functional
- Users opt-in to AI features (not forced)
- Hybrid mode: manually created itinerary + AI suggestions

### Phase 3: Advanced Features (Months 7+)
- Multi-user collaboration
- Embedded maps (v2)
- Full offline mode
- Personalized recommendations from history
- Partner integrations (booking, reservations)

