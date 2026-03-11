## [original]

# User Story: Create Trip with Destinations

## Story
**As a** traveler  
**I want to** create a new trip with destinations and dates  
**So that** I can start building my travel itinerary

## Business Value
- Core trip creation workflow
- Foundation for itinerary planning
- Captures essential trip metadata

## Acceptance Criteria
- [ ] User can create a trip with optional name
- [ ] User must provide start and end dates (date picker)
- [ ] User can add one or multiple destinations (cities)
- [ ] User can assign number of days per destination
- [ ] User can set trip-specific preferences (override profile defaults)
- [ ] System validates date ranges (end date after start date)
- [ ] System validates destination days don't exceed total trip days
- [ ] Trip is created in "draft" status
- [ ] User sees confirmation and is redirected to trip details
- [ ] Trip appears in trip list immediately after creation

## Technical Notes
- Create `Trip`, `TripDestination`, and `TripPlanningContext` entities
- Validate dates on both client and server
- Support multi-city trips from MVP
- Store preferences snapshot for AI context (Phase 2 ready)
- Use city/destination search with autocomplete (Google Places API or similar)

## Estimated Effort
**8 Story Points** (Large)

## Priority
**Critical** - Core feature for MVP

## Dependencies
- mvp-02-manage-user-profile

## Mapped Use Cases
- [UC-2.1: Create New Trip](../../../product-discovery/8-UserCases.md#uc-21-create-new-trip)
- [UC-2.2: Add Destinations to Trip](../../../product-discovery/8-UserCases.md#uc-22-add-destinations-to-trip)
- [UC-2.3: Set Trip-Specific Preferences](../../../product-discovery/8-UserCases.md#uc-23-set-trip-specific-preferences)
- [UC-2.4: Validate Trip Configuration](../../../product-discovery/8-UserCases.md#uc-24-validate-trip-configuration)

## Definition of Done
- [ ] Unit tests for trip creation logic
- [ ] Integration tests for validation rules
- [ ] API documentation with examples
- [ ] Mobile UI with intuitive date picker and destination search
- [ ] Error handling for validation failures
- [ ] Multi-city flow tested (2+ destinations)
- [ ] Performance: Trip creation < 2 seconds

---

## [enhanced]

# User Story: Create Trip with Destinations

## Story
**As a** traveler  
**I want to** create a new trip with a name, dates, and one or more city destinations  
**So that** I can start building my travel itinerary with the right structure from day one

## Business Value
- Core trip creation workflow — gates all downstream itinerary planning
- Foundation for the destination-by-destination itinerary model
- Captures preferences snapshot needed for Phase 2 AI context
- Supports multi-city trips from day one

## Functional Description

The creation wizard is a **3-step stepper** (Screens 12 → 13 → 14 in wireframes). Each step maps to a distinct API call. The wizard can be cancelled at any point, abandoning a trip created in `draft` status — the UI must handle cleanup or the backend must support deletion of drafts.

### Step 1 — Basic Info (Screen 12)
User inputs:
- **Trip name** _(optional)_: free text, max 100 characters
- **Start date** _(required)_: date picker, must be today or future
- **End date** _(required)_: date picker, must be strictly after start date

On "Next": `POST /api/trips` → returns `Trip` with `id` in `draft` status. Trip ID is kept in local wizard state for subsequent steps.

### Step 2 — Add Destinations (Screen 13)
User inputs for each destination:
- **City**: selected from the seeded city catalog (searchable list filtered by country; uses `GET /api/destinations/countries` + `GET /api/destinations/cities?countryId={id}`)
- **Days count** _(required)_: integer ≥ 1, representing how many days are allocated to this city
- **Day order** _(auto-managed by UI)_: sequential integer reflecting the order destinations appear

User can add multiple cities. Before proceeding, the UI must:
- Show total allocated days vs. total trip days (`endDate − startDate + 1`)
- Block "Next" until **sum of all `daysCount` equals total trip days** and **at least one destination exists**

Each city addition calls `POST /api/trips/{id}/destinations`.

### Step 3 — Preferences (Screen 14)
User inputs trip-specific preferences that override profile defaults:
- **Travel style**: `adventurous | cultural | relaxed | mixed`
- **Budget**: `budget | moderate | premium`
- **Pace**: `slow | medium | fast`
- **Interests** _(multi-select)_: `["museums", "food", "nature", "nightlife", "shopping", "outdoor"]`
- **Special requirements** _(optional)_: free text, max 500 characters

Stored as a `TripPlanningContext` record (Phase 2 AI context). On "Create Trip": `POST /api/trips/{id}/planning-context`.

On success: navigate to Trip Detail screen (Screen 2) showing the newly created trip and its destinations.

## API Contracts

### `POST /api/trips` — Step 1
**Auth**: Bearer JWT required  
**Request body**:
```json
{
  "name": "France & Italy Spring Trip",
  "startDate": "2026-04-01",
  "endDate": "2026-04-14"
}
```
**Success `201`**:
```json
{
  "id": "uuid",
  "ownerId": "uuid",
  "name": "France & Italy Spring Trip",
  "startDate": "2026-04-01T00:00:00Z",
  "endDate": "2026-04-14T00:00:00Z",
  "status": "draft",
  "createdAt": "...",
  "updatedAt": "..."
}
```
**Validation errors `400`**:
| Code | Condition |
|---|---|
| `TRIP_DATE_REQUIRED` | `startDate` or `endDate` missing |
| `TRIP_DATE_INVALID` | `endDate` is not strictly after `startDate` |
| `TRIP_DATE_PAST` | `startDate` is in the past |
| `TRIP_NAME_TOO_LONG` | `name` exceeds 100 characters |

---

### `GET /api/destinations/countries` — Step 2 (city picker)
**Auth**: Bearer JWT required  
Returns list of `Country` objects: `[{ id, isoCode, name }]`

### `GET /api/destinations/cities?countryId={uuid}` — Step 2 (city picker)
**Auth**: Bearer JWT required  
Returns list of `City` objects: `[{ id, countryId, name, timezone, country }]`

---

### `POST /api/trips/{id}/destinations` — Step 2
**Auth**: Bearer JWT required. Ownership of trip must be verified.  
**Request body**:
```json
{
  "cityId": "uuid",
  "dayOrder": 1,
  "daysCount": 5
}
```
**Success `201`**:
```json
{
  "id": "uuid",
  "tripId": "uuid",
  "cityId": "uuid",
  "city": { "id": "uuid", "name": "Paris", "timezone": "Europe/Paris", "country": { "isoCode": "FR", "name": "France" } },
  "dayOrder": 1,
  "daysCount": 5,
  "notes": null,
  "createdAt": "...",
  "updatedAt": "..."
}
```
**Validation errors `400` / `404`**:
| Code | HTTP | Condition |
|---|---|---|
| `CITY_NOT_FOUND` | 404 | `cityId` does not exist in DB |
| `DESTINATION_DUPLICATE_ORDER` | 400 | `dayOrder` is already used by another destination in the same trip |
| `DAYS_COUNT_INVALID` | 400 | `daysCount` < 1 |
| `TRIP_NOT_FOUND` | 404 | Trip ID does not exist |
| `TRIP_ACCESS_DENIED` | 403 | Authenticated user is not the trip owner |

---

### `POST /api/trips/{id}/planning-context` — Step 3
**Auth**: Bearer JWT required. Ownership verified.  
**Request body**:
```json
{
  "travelStyle": "cultural",
  "budget": "moderate",
  "pace": "medium",
  "interests": ["museums", "food"],
  "specialRequirements": "Vegetarian meals required"
}
```
**Success `201`**:
```json
{
  "id": "uuid",
  "tripId": "uuid",
  "travelStyle": "cultural",
  "budget": "moderate",
  "pace": "medium",
  "interests": ["museums", "food"],
  "specialRequirements": "Vegetarian meals required",
  "createdAt": "..."
}
```
All fields except `tripId` are optional (user may skip preferences).

---

### `GET /api/trips/{id}` — Post-creation redirect
Returns `TripDetail` (Trip + destinations list) used to populate the Trip Detail screen.

## Database Changes

### New Prisma Models (`backend/prisma/schema.prisma`)

```prisma
model Trip {
  id          String    @id @default(uuid())
  ownerId     String
  name        String?   @db.VarChar(100)
  startDate   DateTime  @db.Date
  endDate     DateTime  @db.Date
  status      TripStatus @default(draft)
  createdAt   DateTime  @default(now())
  updatedAt   DateTime  @updatedAt

  owner        User              @relation(fields: [ownerId], references: [id])
  destinations TripDestination[]
  planningContext TripPlanningContext?
  itineraries  ItineraryVersion[]

  @@index([ownerId])
}

model TripDestination {
  id         String   @id @default(uuid())
  tripId     String
  cityId     String
  dayOrder   Int
  daysCount  Int
  notes      String?
  createdAt  DateTime @default(now())
  updatedAt  DateTime @updatedAt

  trip  Trip @relation(fields: [tripId], references: [id], onDelete: Cascade)
  city  City @relation(fields: [cityId], references: [id])

  @@unique([tripId, dayOrder])
  @@index([tripId])
}

model TripPlanningContext {
  id                  String   @id @default(uuid())
  tripId              String   @unique
  travelStyle         String?  @db.VarChar(20)
  budget              String?  @db.VarChar(20)
  pace                String?  @db.VarChar(10)
  interests           String[] @default([])
  specialRequirements String?  @db.VarChar(500)
  createdAt           DateTime @default(now())
  updatedAt           DateTime @updatedAt

  trip Trip @relation(fields: [tripId], references: [id], onDelete: Cascade)
}

enum TripStatus {
  draft
  active
  completed
}
```

**Migration name**: `create_trip_destination_planning_context_tables`

## Files to Create / Modify

### Backend (`backend/src/`)

| Action | File | Purpose |
|---|---|---|
| **Create** | `domain/models/Trip.ts` | Trip entity class |
| **Create** | `domain/models/TripDestination.ts` | TripDestination entity class |
| **Create** | `domain/models/TripPlanningContext.ts` | TripPlanningContext entity class |
| **Create** | `domain/repositories/ITripRepository.ts` | Repository interface |
| **Create** | `application/services/tripService.ts` | `createTrip`, `addDestination`, `savePlanningContext`, `getTripById`, `validateDaysConsistency` |
| **Modify** | `application/validator.ts` | Add `validateCreateTrip`, `validateAddDestination`, `validatePlanningContext` |
| **Create** | `presentation/controllers/tripController.ts` | `createTrip`, `addDestination`, `savePlanningContext`, `getTripById`, `getUserTrips`, `deleteTrip` |
| **Create** | `routes/tripRoutes.ts` | Wire endpoints with `authenticateJWT` middleware |
| **Modify** | `routes/index.ts` | Register `tripRoutes` |
| **Modify** | `prisma/schema.prisma` | Add `Trip`, `TripDestination`, `TripPlanningContext` models |
| **Create** | `prisma/migrations/[ts]_create_trip_tables/` | Prisma migration |

> Note: `POST /api/trips/{id}/planning-context` is not yet in `api-spec.yml` — add it under the Trips tag.

### Frontend (Kotlin Multiplatform / Compose Multiplatform)

| Action | File | Purpose |
|---|---|---|
| **Create** | `shared/src/commonMain/.../domain/Trip.kt` | Domain data class |
| **Create** | `shared/src/commonMain/.../domain/TripDestination.kt` | Domain data class |
| **Create** | `shared/src/commonMain/.../domain/TripPlanningContext.kt` | Domain data class |
| **Create** | `shared/src/commonMain/.../repository/ITripRepository.kt` | Repository interface |
| **Create** | `shared/src/commonMain/.../usecase/CreateTripUseCase.kt` | Orchestrates steps 1-3 |
| **Create** | `shared/src/commonMain/.../usecase/GetCitiesUseCase.kt` | Fetches countries + cities |
| **Create** | `shared-ui/src/commonMain/.../createtrip/CreateTripUiState.kt` | `UiState`, `UiEvent` sealed classes |
| **Create** | `shared-ui/src/commonMain/.../createtrip/CreateTripScreenModel.kt` | State holder (Presenter) exposing `StateFlow<CreateTripUiState>` |
| **Create** | `shared-ui/src/commonMain/.../createtrip/CreateTripScreen.kt` | Compose Multiplatform 3-step stepper screen |
| **Modify** | `shared-ui/src/commonMain/.../navigation/AppNavGraph.kt` | Register `CreateTripScreen` route |

### `CreateTripUiState` Structure
```kotlin
data class CreateTripUiState(
    val currentStep: Int = 1,
    val tripName: String = "",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val destinations: List<DestinationDraft> = emptyList(),
    val availableCountries: List<Country> = emptyList(),
    val availableCities: List<City> = emptyList(),
    val travelStyle: String? = null,
    val budget: String? = null,
    val pace: String? = null,
    val selectedInterests: List<String> = emptyList(),
    val specialRequirements: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val createdTripId: String? = null
)

data class DestinationDraft(
    val city: City,
    val daysCount: Int,
    val dayOrder: Int
)
```

## Validation Rules

### Client-side (Compose UI)
- "Next" on Step 1 is disabled if `startDate` or `endDate` is null, or `endDate <= startDate`
- "Next" on Step 2 is disabled if `destinations.isEmpty()` or `destinations.sumOf { daysCount } != totalTripDays`
- `totalTripDays = (endDate - startDate).days + 1`
- Day count input: integer, min 1
- Trip name input: character counter showing `X/100`

### Server-side (backend validator)
- `startDate`: required, must be ISO 8601 date format
- `endDate`: required, must be strictly after `startDate`
- `name` (if provided): max 100 characters
- `cityId`: must reference an existing `City` record
- `daysCount`: integer, minimum 1
- `dayOrder`: unique per trip (DB unique constraint `[tripId, dayOrder]`)
- `travelStyle`: enum `[adventurous, cultural, relaxed, mixed]` if provided
- `budget`: enum `[budget, moderate, premium]` if provided
- `pace`: enum `[slow, medium, fast]` if provided
- `interests`: array of strings, each matching known interest keys

### Cross-field validation (server, final trip confirmation)
- Sum of all `TripDestination.daysCount` for a trip must equal `(endDate - startDate + 1)` days. Return `TRIP_DAYS_MISMATCH` error if violated before activating the trip.

## Acceptance Criteria (Updated)

- [ ] `POST /api/trips` creates a trip in `draft` status and returns `201` with the trip object
- [ ] Trip name is optional; start date and end date are required
- [ ] Validation returns `TRIP_DATE_INVALID` when `endDate <= startDate`
- [ ] Validation returns `TRIP_DATE_PAST` when `startDate` is in the past
- [ ] `POST /api/trips/{id}/destinations` adds a city with `dayOrder` and `daysCount`
- [ ] Adding a destination with a duplicate `dayOrder` returns `DESTINATION_DUPLICATE_ORDER`
- [ ] Adding a destination with a non-existent `cityId` returns `CITY_NOT_FOUND` (404)
- [ ] Accessing another user's trip returns `403`
- [ ] `POST /api/trips/{id}/planning-context` stores preferences snapshot; all fields optional
- [ ] Step 2 UI shows running total of allocated days vs. total trip days
- [ ] "Next" on Step 2 is blocked until days are fully allocated and at least 1 destination exists
- [ ] On successful wizard completion, user is redirected to the Trip Detail screen
- [ ] Newly created trip appears in the trip list immediately (`GET /api/trips`)
- [ ] Trip creation end-to-end (3 steps) completes in < 2 seconds on stable network

## Unit & Integration Tests

### Backend (`tripService.test.ts`)
- **Happy path**: `createTrip` returns a Trip with `draft` status and correct dates
- **Validation**: `createTrip` throws `TRIP_DATE_INVALID` when `endDate <= startDate`
- **Validation**: `createTrip` throws `TRIP_DATE_PAST` when `startDate < today`
- **Happy path**: `addDestination` creates a `TripDestination` and returns it with embedded city
- **Error**: `addDestination` throws `CITY_NOT_FOUND` when `cityId` does not exist
- **Error**: `addDestination` throws `DESTINATION_DUPLICATE_ORDER` on unique constraint violation
- **Error**: `addDestination` throws 403 when userId does not match `trip.ownerId`
- **Happy path**: `savePlanningContext` stores context with partial fields (all optional)
- **Edge case**: `createTrip` with null `name` stores null without error

### Frontend (`commonTest`)
- `CreateTripScreenModel`: `onEvent(NextStep)` on Step 1 transitions to `currentStep = 2` when dates are valid
- `CreateTripScreenModel`: `onEvent(NextStep)` on Step 1 sets `error` when dates are invalid
- `CreateTripScreenModel`: `onEvent(AddDestination)` updates `destinations` list and recalculates total days
- `CreateTripScreenModel`: "Next" event on Step 2 is rejected when `sumDays != totalTripDays`
- `CreateTripScreenModel`: successful wizard completion sets `createdTripId` and triggers navigation

## Non-Functional Requirements

| Requirement | Detail |
|---|---|
| **Authentication** | All endpoints require `Authorization: Bearer {accessToken}` header |
| **Authorization** | Trip ownership must be validated on `POST /api/trips/{id}/destinations` and `POST /api/trips/{id}/planning-context` using `trip.ownerId === req.user.id` |
| **Performance** | Each individual API call must respond in < 500ms (p95); end-to-end wizard < 2s |
| **Rate limiting** | Subject to general API rate limiter: 100 req / 15 min per IP |
| **Data integrity** | `TripDestination` cascade-deletes when its parent `Trip` is deleted |
| **English only** | All code, logs, error messages, and comments must be in English |
| **Test coverage** | ≥ 90% branches, functions, lines, and statements for all new backend files |
| **Type safety** | No `any` types in TypeScript; strict mode must be enabled |

## Definition of Done

- [ ] `Trip`, `TripDestination`, `TripPlanningContext` Prisma models created and migrated
- [ ] Backend: domain entities, repository interface, service, controller, and routes implemented
- [ ] `POST /api/trips/{id}/planning-context` endpoint added to `api-spec.yml`
- [ ] All validation rules implemented server-side with correct error codes
- [ ] Backend unit tests pass with ≥ 90% coverage (all new files)
- [ ] Frontend: `CreateTripUseCase`, `CreateTripScreenModel`, and `CreateTripScreen` implemented in shared/shared-ui modules
- [ ] Frontend: Step-by-step wizard navigates correctly through all 3 steps
- [ ] Frontend: Day allocation counter visible on Step 2; "Next" blocked when days don't match
- [ ] Frontend: `commonTest` tests cover state transitions and validations
- [ ] Trip appears in `GET /api/trips` immediately after creation
- [ ] Multi-city flow tested with 2+ destinations where `sum(daysCount) === totalDays`
- [ ] Error states shown correctly in UI for each validation failure
- [ ] Performance: end-to-end wizard creation < 2 seconds
