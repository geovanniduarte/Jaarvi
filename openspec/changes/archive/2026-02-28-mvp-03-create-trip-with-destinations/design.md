## Context

The Jaarvi backend follows a DDD layered architecture: Presentation → Application → Domain → Infrastructure (Prisma). This pattern is already established in `mvp-01` (health) and `mvp-02` (auth). Domain models own their own persistence via `save()` and static factory methods backed by `getPrismaClient()`. Services orchestrate domain models. Controllers are thin HTTP handlers.

The Prisma schema already contains all required tables (`trips`, `trip_destinations`, `trip_planning_contexts`, `countries`, `cities`, `city_coverages`) from migration `20260208223511_init`. No schema changes are needed.

The `authenticateJWT` middleware is provided by `mvp-02`. All new routes depend on it. If `mvp-02` is not yet merged, a stub must be used locally (never merged to `main`).

## Goals / Non-Goals

**Goals:**
- Introduce 7 new REST endpoints for trip and destination management following existing architectural conventions
- Keep domain logic (ownership checks, validation, conflict detection) in the service layer, not controllers
- Return consistent JSON response shapes matching the existing `{ success, data }` and `{ success, error }` envelope
- Achieve ≥90% unit test coverage on all new service and controller files
- No new npm dependencies, no Prisma migration

**Non-Goals:**
- Trip status transitions (`draft → active → completed`) — deferred to `mvp-04`
- Trip deletion — deferred to `mvp-04`
- Destination reordering or removal — deferred to `mvp-04`
- AI-driven itinerary generation — deferred to Phase 2
- Admin or cross-user trip access
- Integration tests (blocked on `mvp-02` merge)

## Decisions

### 1. Domain models own persistence (no separate Repository classes)
**Decision**: Static factory methods (`Trip.findById`, `Trip.findByOwnerId`) and instance `save()` on domain models call Prisma directly via `getPrismaClient()`.

**Rationale**: Consistent with the existing `User` domain model pattern established in `mvp-02`. Adding a Repository class layer would require DI infrastructure not yet present and would add indirection with no benefit at this scale. Repository *interfaces* (`ITripRepository`, `IDestinationRepository`) are defined in the domain layer as documentation contracts for future DI adoption.

**Alternative considered**: Separate `TripRepository` class implementing `ITripRepository`. Rejected — premature abstraction at MVP stage.

---

### 2. `TripPlanningContext` stores preferences as JSON, not flat columns
**Decision**: Preferences (`travelStyle`, `budget`, `pace`, `interests`, `specialRequirements`) are serialized into `inputsSnapshot.preferences` (JSON column). City coverage is serialized into `coverageSnapshot` (JSON column).

**Rationale**: The Prisma schema already defines these as JSON columns. This avoids a migration and allows the AI context shape to evolve (Phase 2 will add more input types) without schema changes.

**Alternative considered**: Flat columns per preference field. Rejected — would require a migration and makes the schema rigid for Phase 2 AI inputs.

---

### 3. `addDestination` returns record with city data via re-fetch
**Decision**: After `destination.save()`, call `TripDestination.findByTripIdAndOrder(tripId, dayOrder)` to return the full record including `city` and `country` relations.

**Rationale**: `save()` only has access to the IDs — it cannot include relations without a second query. Returning a partial object (no city name) would force the client to make an extra API call. The re-fetch is cheap (PK lookup) and keeps the response complete.

---

### 4. `coverageSnapshot` is a point-in-time capture
**Decision**: When saving a planning context, the city coverage data is fetched and embedded at save time, not resolved dynamically on read.

**Rationale**: The AI planning system (Phase 2) needs a stable snapshot of what coverage existed when the user expressed preferences. Dynamic resolution would make historical contexts inconsistent.

---

### 5. Singleton pattern for services and controllers
**Decision**: `getTripService()` / `resetTripService()` and `getTripController()` / `resetTripController()` follow the same factory+singleton pattern as `healthService.ts` and `healthController.ts`.

**Rationale**: Consistent with the established pattern. `reset*()` functions enable clean test isolation without module re-imports.

---

### 6. All trip routes require authentication
**Decision**: Every route in `tripRoutes.ts` and `destinationRoutes.ts` applies `authenticateJWT` middleware before the controller handler.

**Rationale**: Trip data is per-user and must never be exposed unauthenticated. The destination catalog (countries/cities) is also gated to prevent unauthenticated data scraping.

## Risks / Trade-offs

- **[Risk] `mvp-02` not merged** → Use stub `authenticateJWT` locally. Auth integration tests cannot run until merge. Mitigation: stub is clearly documented and must not reach `main`.
- **[Risk] `coverageSnapshot` includes cities with no `CityCoverage` record** → Mitigation: if `city.coverage` is null, store `{ cityId, cityName, level: null, notes: null }` — do not fail the request.
- **[Risk] Duplicate `dayOrder` handled at DB level (Prisma P2002)** → Mitigation: `TripDestination.save()` catches P2002 and rethrows as `ConflictError`. The global error handler maps it to `409`.
- **[Trade-off] Re-fetch after `addDestination`** → Adds one extra DB query per destination add. Acceptable at MVP scale; can be eliminated in Phase 2 by using `prisma.tripDestination.create({ include: ... })` directly.

## Migration Plan

1. Create feature branch `feature/mvp-03-backend` from `main`
2. Implement all domain models, services, controllers, and routes in order (Steps 1–14 of implementation plan)
3. Write unit tests (Steps 15–16); ensure `npm test` and `npm run build` pass
4. Update `api-spec.yml` (Step 17)
5. Open PR targeting `main`; merge only after `mvp-02` is merged (or stub is confirmed removed)
6. Redeploy backend to Jaarvi cluster via `/redeploy-backend`

**Rollback**: revert the PR. No DB migrations means rollback has zero schema impact.

## Open Questions

- None blocking implementation. `mvp-02` merge timing is a deployment dependency but does not block coding or unit testing.
