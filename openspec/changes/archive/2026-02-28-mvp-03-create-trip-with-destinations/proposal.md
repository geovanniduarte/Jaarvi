## Why

The Jaarvi app needs backend support for the 3-step trip creation wizard. Without these endpoints, the mobile frontend cannot create trips, add destinations, or capture user planning preferences — blocking all MVP-03 product functionality.

## What Changes

- New REST endpoints for trip CRUD operations scoped to the authenticated user
- New REST endpoints for the city catalog (countries and cities) used by the destination picker
- New DDD domain models: `Trip`, `TripDestination`, `TripPlanningContext`, `Country`, `City`
- New application services: `TripService`, `DestinationService`
- New presentation controllers: `TripController`, `DestinationController`
- New Express routers: `tripRoutes.ts`, `destinationRoutes.ts` registered under `/api/trips` and `/api/destinations`
- New validators added to `validator.ts` for trip, destination, and planning context payloads
- Repository interfaces `ITripRepository` and `IDestinationRepository` added to the domain layer
- Unit tests for `TripService` (≥35 cases) and `TripController` (≥10 cases) with ≥90% coverage
- `api-spec.yml` updated to include `POST /api/trips/{id}/planning-context` and destination endpoints

No new npm packages. No Prisma migration (all models already exist in `20260208223511_init`).

## Capabilities

### New Capabilities

- `trip-management`: CRUD operations for authenticated user trips — create in `draft` status, list by owner, retrieve with embedded destinations and city data
- `trip-destination-management`: Add city destinations to a trip with conflict detection on `dayOrder`; fetch city coverage snapshot for AI context
- `trip-planning-context`: Save a versioned preferences snapshot (travel style, budget, pace, interests) linked to a trip, capturing city coverage at time of save
- `destination-catalog`: Read-only country and city catalog endpoints used by the city picker UI

### Modified Capabilities

_(none — no existing spec requirements are changing)_

## Impact

- **New files**: 13 source files across domain, application, and presentation layers + 2 test files
- **Modified files**: `validator.ts` (3 new functions), `routes/index.ts` (register 2 new routers), `domain/models/index.ts`, `domain/repositories/index.ts`
- **API surface**: 7 new HTTP endpoints all requiring `Authorization: Bearer <jwt>` (depends on `mvp-02` auth middleware)
- **No database changes**: schema unchanged; no migration required
- **Dependencies**: requires `mvp-02` (`authenticateJWT` middleware at `backend/src/middleware/authMiddleware.ts`)
