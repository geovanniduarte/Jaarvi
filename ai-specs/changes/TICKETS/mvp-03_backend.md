# Backend Implementation Plan: mvp-03 — Create Trip with Destinations

## Overview

This plan implements the full backend for the 3-step trip creation wizard, including the city catalog lookup endpoints required by Step 2. It covers:

- `POST /api/trips` — create a trip in `draft` status  
- `GET /api/trips` — list trips for the authenticated user  
- `GET /api/trips/:id` — get trip detail with destinations  
- `POST /api/trips/:id/destinations` — add a destination to a trip  
- `POST /api/trips/:id/planning-context` — store preferences snapshot  
- `GET /api/destinations/countries` — city picker: list all countries  
- `GET /api/destinations/cities` — city picker: list cities (filtered by `countryId`)  

**Architecture**: DDD layered architecture — Presentation → Application → Domain → Infrastructure (Prisma). Domain models own persistence logic via `save()` and static factory methods backed by `getPrismaClient()`. Services orchestrate domain models. Controllers are thin HTTP handlers that delegate to services.

**No Prisma migration needed**: `Trip`, `TripDestination`, and `TripPlanningContext` models are already defined in `schema.prisma` (initial migration `20260208223511_init`). `Country`, `City`, and `CityCoverage` are also already present.

**Auth dependency**: This ticket depends on `mvp-02` which provides the `authenticateJWT` middleware at `backend/src/middleware/authMiddleware.ts`. That middleware must attach `req.user = { id: string; email: string }` to the request. All trip and destination routes require this middleware. If `mvp-02` is not yet merged, stub the middleware (see Step 0 notes).

---

## Architecture Context

| Layer | Components |
|---|---|
| **Presentation** | `tripController.ts`, `destinationController.ts`, `tripRoutes.ts`, `destinationRoutes.ts` |
| **Application** | `tripService.ts`, `destinationService.ts`, additions to `validator.ts` |
| **Domain** | `Trip.ts`, `TripDestination.ts`, `TripPlanningContext.ts`, `Country.ts`, `City.ts`, `ITripRepository.ts`, `IDestinationRepository.ts` |
| **Infrastructure** | Prisma (`getPrismaClient()`) accessed from domain model methods |

---

## Implementation Steps

---

### Step 0: Create Feature Branch

- **Action**: Create and switch to a dedicated backend branch.
- **Branch name**: `feature/mvp-03-backend`
- **Implementation Steps**:
  1. Ensure you are on `main` (or `develop`) and up to date: `git checkout main && git pull origin main`
  2. Create and switch: `git checkout -b feature/mvp-03-backend`
  3. Verify: `git branch`
- **Notes**:
  - Do NOT work directly on `main` or on the generic `mvp-03` branch if it exists.
  - If `mvp-02` (auth middleware) is not yet merged, create a stub auth middleware locally:
    ```typescript
    // backend/src/middleware/authMiddleware.ts  (STUB — will be replaced by mvp-02)
    export function authenticateJWT(req, _res, next) {
      // STUB: hardcode a test user for local development only
      req.user = { id: 'stub-user-id', email: 'stub@jaarvi.app' };
      next();
    }
    ```
    Ensure this stub is never merged to `main`.

---

### Step 1: Extend Express Request Type for Authenticated User

- **File**: `backend/src/types/express.d.ts` *(new file)*
- **Action**: Augment the Express `Request` interface to add the `user` property populated by `authenticateJWT`.
- **Implementation Steps**:
  1. Create the file `backend/src/types/express.d.ts`:
    ```typescript
    import 'express-serve-static-core';

    declare module 'express-serve-static-core' {
      interface Request {
        user?: {
          id: string;
          email: string;
        };
      }
    }
    ```
  2. Ensure `tsconfig.json` includes `"typeRoots": ["./src/types", "./node_modules/@types"]` or that the file is within the `include` paths (it should be by default with `src/**/*`).
- **Notes**: `req.prisma` is already declared in `prismaMiddleware.ts`; do not duplicate it here. Only add `user`.

---

### Step 2: Add Domain Model — `Trip`

- **File**: `backend/src/domain/models/Trip.ts` *(new file)*
- **Action**: Implement the `Trip` domain entity with static factory methods for retrieval and a `save()` method for persistence.
- **Function Signatures**:
  ```typescript
  class Trip {
    id?: string;
    ownerId: string;
    name: string | null;
    startDate: Date;
    endDate: Date;
    status: string;
    createdAt?: Date;
    updatedAt?: Date;

    constructor(data: TripData)
    async save(): Promise<Trip>
    static async findById(id: string): Promise<Trip | null>
    static async findByOwnerId(ownerId: string, status?: string): Promise<Trip[]>
  }
  ```
- **Implementation Steps**:
  1. Import `getPrismaClient` from `../../infrastructure/prismaClient`.
  2. Define a `TripData` interface matching the Prisma `trips` table fields.
  3. **Constructor**: initialize all properties from `data`.
  4. **`save()`**: if `this.id` exists → `prisma.trip.update(...)`, else → `prisma.trip.create(...)`. Return `new Trip(result)`.
  5. **`static findById(id)`**: `prisma.trip.findUnique({ where: { id }, include: { destinations: { include: { city: { include: { country: true } } }, orderBy: { dayOrder: 'asc' } } } })`. Return `new Trip(data)` or `null`.
  6. **`static findByOwnerId(ownerId, status?)`**: `prisma.trip.findMany({ where: { ownerId, ...(status ? { status } : {}) }, orderBy: { startDate: 'desc' } })`. Return `Trip[]`.
- **Dependencies**: `import { getPrismaClient } from '../../infrastructure/prismaClient';`

---

### Step 3: Add Domain Model — `TripDestination`

- **File**: `backend/src/domain/models/TripDestination.ts` *(new file)*
- **Action**: Implement the `TripDestination` entity with persistence and retrieval.
- **Function Signatures**:
  ```typescript
  class TripDestination {
    id?: string;
    tripId: string;
    cityId: string;
    dayOrder: number;
    daysCount: number;
    notes: string | null;
    createdAt?: Date;
    updatedAt?: Date;

    constructor(data: TripDestinationData)
    async save(): Promise<TripDestination>
    static async findByTripId(tripId: string): Promise<TripDestination[]>
    static async findByTripIdAndOrder(tripId: string, dayOrder: number): Promise<TripDestination | null>
  }
  ```
- **Implementation Steps**:
  1. **Constructor**: map all Prisma record fields.
  2. **`save()`**: `prisma.tripDestination.create({ data: { tripId, cityId, dayOrder, daysCount, notes } })`. Return `new TripDestination(result)`.
     - Do not implement `update()` here; that is out of scope for this ticket.
  3. **`static findByTripId(tripId)`**: `prisma.tripDestination.findMany({ where: { tripId }, include: { city: { include: { country: true } } }, orderBy: { dayOrder: 'asc' } })`.
  4. **`static findByTripIdAndOrder(tripId, dayOrder)`**: `prisma.tripDestination.findFirst({ where: { tripId, dayOrder } })`. Used by service for duplicate-order check.
- **Prisma error handling in `save()`**: Catch Prisma error code `P2002` (unique constraint violation on `[tripId, dayOrder]`) and rethrow as `ConflictError('Destination with this day order already exists')`. Import `ConflictError` from `../../domain/errors`.

---

### Step 4: Add Domain Model — `TripPlanningContext`

- **File**: `backend/src/domain/models/TripPlanningContext.ts` *(new file)*
- **Action**: Implement the planning context entity. Note: the Prisma model uses a JSON `inputsSnapshot` to store preferences and `coverageSnapshot` for city coverage. Flat preference fields are serialized into `inputsSnapshot`.
- **`inputsSnapshot` structure** (stored as JSON):
  ```json
  {
    "preferences": {
      "travelStyle": "cultural | adventurous | relaxed | mixed | null",
      "budget": "budget | moderate | premium | null",
      "pace": "slow | medium | fast | null",
      "interests": ["museums", "food", ...],
      "specialRequirements": "string | null"
    }
  }
  ```
- **`coverageSnapshot` structure** (stored as JSON):
  ```json
  [
    { "cityId": "uuid", "cityName": "Paris", "level": "high", "notes": "..." }
  ]
  ```
- **Function Signatures**:
  ```typescript
  class TripPlanningContext {
    id?: string;
    tripId: string;
    version: number;
    inputsSnapshot: Record<string, unknown>;
    coverageSnapshot: Record<string, unknown>[];
    createdAt?: Date;

    constructor(data: TripPlanningContextData)
    async save(): Promise<TripPlanningContext>
    static async findByTripId(tripId: string): Promise<TripPlanningContext[]>
    static async getNextVersionForTrip(tripId: string): Promise<number>
  }
  ```
- **Implementation Steps**:
  1. **`save()`**: `prisma.tripPlanningContext.create({ data: { tripId, version, inputsSnapshot, coverageSnapshot } })`.
  2. **`static getNextVersionForTrip(tripId)`**: `prisma.tripPlanningContext.aggregate({ where: { tripId }, _max: { version: true } })`. Return `(result._max.version ?? 0) + 1`.
  3. **`static findByTripId(tripId)`**: returns all contexts ordered by `version desc`. Used for Phase 2.

---

### Step 5: Add Domain Models — `Country` and `City`

- **Files**: 
  - `backend/src/domain/models/Country.ts` *(new file)*
  - `backend/src/domain/models/City.ts` *(new file)*
- **Action**: Read-only domain models for the city picker endpoints.

**`Country.ts`**:
```typescript
class Country {
  id: string;
  isoCode: string;
  name: string;
  createdAt: Date;

  constructor(data: CountryData)
  static async findAll(): Promise<Country[]>
}
```
- `findAll()`: `prisma.country.findMany({ orderBy: { name: 'asc' } })`.

**`City.ts`**:
```typescript
class City {
  id: string;
  countryId: string;
  name: string;
  timezone: string;
  country?: Country;
  coverage?: { level: string; notes: string | null };
  createdAt: Date;

  constructor(data: CityData)
  static async findAll(countryId?: string): Promise<City[]>
  static async findById(id: string): Promise<City | null>
}
```
- `findAll(countryId?)`: `prisma.city.findMany({ where: countryId ? { countryId } : {}, include: { country: true, coverage: true }, orderBy: { name: 'asc' } })`.
- `findById(id)`: `prisma.city.findUnique({ where: { id }, include: { country: true, coverage: true } })`. Returns `null` if not found.

---

### Step 6: Add Repository Interfaces

- **Files**:
  - `backend/src/domain/repositories/ITripRepository.ts` *(new file)*
  - `backend/src/domain/repositories/IDestinationRepository.ts` *(new file)*
- **Action**: Define domain-layer contracts (used for documentation and future DI; implementations are provided directly by domain model static methods).

**`ITripRepository.ts`**:
```typescript
import { Trip } from '../models/Trip';
import { TripDestination } from '../models/TripDestination';
import { TripPlanningContext } from '../models/TripPlanningContext';

export interface ITripRepository {
  findById(id: string): Promise<Trip | null>;
  findByOwnerId(ownerId: string, status?: string): Promise<Trip[]>;
  save(trip: Trip): Promise<Trip>;
  addDestination(destination: TripDestination): Promise<TripDestination>;
  savePlanningContext(context: TripPlanningContext): Promise<TripPlanningContext>;
}
```

**`IDestinationRepository.ts`**:
```typescript
import { Country } from '../models/Country';
import { City } from '../models/City';

export interface IDestinationRepository {
  findAllCountries(): Promise<Country[]>;
  findAllCities(countryId?: string): Promise<City[]>;
  findCityById(id: string): Promise<City | null>;
}
```

- **Update** `backend/src/domain/repositories/index.ts`: export both new interfaces.
- **Update** `backend/src/domain/models/index.ts`: export `Trip`, `TripDestination`, `TripPlanningContext`, `Country`, `City`.

---

### Step 7: Add Validators

- **File**: `backend/src/application/validator.ts` *(modify existing)*
- **Action**: Add 3 new exported validation functions for trip creation, destination addition, and planning context. Use the existing utility functions (`isValidISODate`, `isValidUUID`, `isPositiveInteger`, `hasMaxLength`, `isOneOf`, `addError`, `throwIfInvalid`, `createValidationResult`).

**Add `validateCreateTrip(data: unknown): void`**:
```typescript
export function validateCreateTrip(data: unknown): void {
  const body = data as Record<string, unknown>;
  const result = createValidationResult();

  // startDate: required, valid ISO date
  if (!body.startDate) {
    addError(result, 'startDate', 'startDate is required');
  } else if (!isValidISODate(body.startDate)) {
    addError(result, 'startDate', 'startDate must be a valid date (YYYY-MM-DD)');
  } else {
    const start = new Date(body.startDate as string);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    if (start < today) {
      addError(result, 'startDate', 'startDate must be today or in the future');
    }
  }

  // endDate: required, valid ISO date, after startDate
  if (!body.endDate) {
    addError(result, 'endDate', 'endDate is required');
  } else if (!isValidISODate(body.endDate)) {
    addError(result, 'endDate', 'endDate must be a valid date (YYYY-MM-DD)');
  } else if (body.startDate && isValidISODate(body.startDate)) {
    const start = new Date(body.startDate as string);
    const end = new Date(body.endDate as string);
    if (end <= start) {
      addError(result, 'endDate', 'endDate must be strictly after startDate');
    }
  }

  // name: optional, max 100 chars
  if (body.name !== undefined && body.name !== null) {
    if (typeof body.name !== 'string') {
      addError(result, 'name', 'name must be a string');
    } else if (!hasMaxLength(body.name, 100)) {
      addError(result, 'name', 'name must not exceed 100 characters');
    }
  }

  throwIfInvalid(result, 'Trip validation failed');
}
```

**Add `validateAddDestination(data: unknown): void`**:
```typescript
export function validateAddDestination(data: unknown): void {
  const body = data as Record<string, unknown>;
  const result = createValidationResult();

  if (!body.cityId || !isValidUUID(body.cityId)) {
    addError(result, 'cityId', 'cityId is required and must be a valid UUID');
  }
  if (body.dayOrder === undefined || body.dayOrder === null) {
    addError(result, 'dayOrder', 'dayOrder is required');
  } else if (!isPositiveInteger(body.dayOrder)) {
    addError(result, 'dayOrder', 'dayOrder must be a positive integer');
  }
  if (body.daysCount === undefined || body.daysCount === null) {
    addError(result, 'daysCount', 'daysCount is required');
  } else if (!isPositiveInteger(body.daysCount)) {
    addError(result, 'daysCount', 'daysCount must be a positive integer');
  }

  throwIfInvalid(result, 'Destination validation failed');
}
```

**Add `validatePlanningContext(data: unknown): void`**:
```typescript
const VALID_TRAVEL_STYLES = ['adventurous', 'cultural', 'relaxed', 'mixed'];
const VALID_BUDGETS = ['budget', 'moderate', 'premium'];
const VALID_PACES = ['slow', 'medium', 'fast'];
const VALID_INTERESTS = ['museums', 'food', 'nature', 'nightlife', 'shopping', 'outdoor'];

export function validatePlanningContext(data: unknown): void {
  const body = data as Record<string, unknown>;
  const result = createValidationResult();

  if (body.travelStyle !== undefined && body.travelStyle !== null) {
    if (!isOneOf(body.travelStyle, VALID_TRAVEL_STYLES)) {
      addError(result, 'travelStyle', `travelStyle must be one of: ${VALID_TRAVEL_STYLES.join(', ')}`);
    }
  }
  if (body.budget !== undefined && body.budget !== null) {
    if (!isOneOf(body.budget, VALID_BUDGETS)) {
      addError(result, 'budget', `budget must be one of: ${VALID_BUDGETS.join(', ')}`);
    }
  }
  if (body.pace !== undefined && body.pace !== null) {
    if (!isOneOf(body.pace, VALID_PACES)) {
      addError(result, 'pace', `pace must be one of: ${VALID_PACES.join(', ')}`);
    }
  }
  if (body.interests !== undefined && body.interests !== null) {
    if (!Array.isArray(body.interests)) {
      addError(result, 'interests', 'interests must be an array');
    } else {
      const invalid = (body.interests as unknown[]).filter(i => !VALID_INTERESTS.includes(i as string));
      if (invalid.length > 0) {
        addError(result, 'interests', `interests contains invalid values: ${invalid.join(', ')}`);
      }
    }
  }
  if (body.specialRequirements !== undefined && body.specialRequirements !== null) {
    if (typeof body.specialRequirements !== 'string') {
      addError(result, 'specialRequirements', 'specialRequirements must be a string');
    } else if (!hasMaxLength(body.specialRequirements, 500)) {
      addError(result, 'specialRequirements', 'specialRequirements must not exceed 500 characters');
    }
  }

  throwIfInvalid(result, 'Planning context validation failed');
}
```

---

### Step 8: Add Application Service — `TripService`

- **File**: `backend/src/application/services/tripService.ts` *(new file)*
- **Action**: Orchestrate domain models for all trip operations. Each method handles one operation (SRP). Validate → domain model operation → return.
- **Class + Singleton Pattern** (matching `healthService.ts`):

```typescript
export class TripService {
  async createTrip(ownerId: string, data: CreateTripData): Promise<Trip>
  async getUserTrips(ownerId: string, status?: string): Promise<Trip[]>
  async getTripById(id: string, ownerId: string): Promise<TripWithDestinations>
  async addDestination(tripId: string, ownerId: string, data: AddDestinationData): Promise<TripDestination>
  async savePlanningContext(tripId: string, ownerId: string, preferences: PlanningContextPreferences): Promise<TripPlanningContext>
}

export function getTripService(): TripService
export function resetTripService(): void
```

**Method implementation details:**

**`createTrip(ownerId, data)`**:
1. Call `validateCreateTrip(data)` — throws `ValidationError` if invalid.
2. Create `new Trip({ ownerId, name: data.name ?? null, startDate: new Date(data.startDate), endDate: new Date(data.endDate), status: 'draft' })`.
3. Call `await trip.save()`.
4. Return the saved trip.

**`getUserTrips(ownerId, status?)`**:
1. Call `Trip.findByOwnerId(ownerId, status)`.
2. Return array.

**`getTripById(id, ownerId)`**:
1. Validate `id` is valid UUID — throw `ValidationError` if not.
2. Call `Trip.findById(id)` (includes destinations + cities).
3. If `null` → throw `NotFoundError('Trip not found')`.
4. If `trip.ownerId !== ownerId` → throw `ForbiddenError('Access denied to this trip')`.
5. Return trip.

**`addDestination(tripId, ownerId, data)`**:
1. Call `validateAddDestination(data)` — throws `ValidationError` if invalid.
2. Verify trip ownership: call `Trip.findById(tripId)`. If null → `NotFoundError`. If `ownerId !== req.user.id` → `ForbiddenError`.
3. Verify city exists: call `City.findById(data.cityId)`. If null → `NotFoundError('City not found')`.
4. Create `new TripDestination({ tripId, cityId: data.cityId, dayOrder: data.dayOrder, daysCount: data.daysCount, notes: null })`.
5. Call `await destination.save()` — the `save()` method will catch Prisma `P2002` and rethrow as `ConflictError('Destination with this day order already exists')`.
6. Return the saved destination (with city included from the re-fetch). After save, call `TripDestination.findByTripIdAndOrder(tripId, data.dayOrder)` to get the version with city relation included. Return that.

**`savePlanningContext(tripId, ownerId, preferences)`**:
1. Call `validatePlanningContext(preferences)`.
2. Verify trip ownership (same pattern as `addDestination`).
3. Build `inputsSnapshot = { preferences }`.
4. Build `coverageSnapshot`: call `TripDestination.findByTripId(tripId)`, then for each destination call `City.findById(dest.cityId)` and map to `{ cityId, cityName, level, notes }`.
5. Get next version: `const version = await TripPlanningContext.getNextVersionForTrip(tripId)`.
6. Create and save: `new TripPlanningContext({ tripId, version, inputsSnapshot, coverageSnapshot })`.
7. Return saved context.

**Imports needed**:
```typescript
import { Trip } from '../../domain/models/Trip';
import { TripDestination } from '../../domain/models/TripDestination';
import { TripPlanningContext } from '../../domain/models/TripPlanningContext';
import { City } from '../../domain/models/City';
import { NotFoundError, ForbiddenError } from '../../domain/errors';
import { validateCreateTrip, validateAddDestination, validatePlanningContext } from '../validator';
```

---

### Step 9: Add Application Service — `DestinationService`

- **File**: `backend/src/application/services/destinationService.ts` *(new file)*
- **Action**: Provide read-only access to country/city catalog.

```typescript
export class DestinationService {
  async getCountries(): Promise<Country[]>
  async getCities(countryId?: string): Promise<City[]>
}

export function getDestinationService(): DestinationService
export function resetDestinationService(): void
```

**Method details:**
- `getCountries()`: delegate to `Country.findAll()`.
- `getCities(countryId?)`: validate `countryId` is a valid UUID if provided (throw `ValidationError` if not). Delegate to `City.findAll(countryId)`.

---

### Step 10: Add Presentation Controller — `TripController`

- **File**: `backend/src/presentation/controllers/tripController.ts` *(new file)*
- **Action**: Thin HTTP handlers delegating to `TripService`. Singleton class pattern (matches `healthController.ts`).
- **AuthenticatedRequest type**:
  ```typescript
  import { Request } from 'express';
  interface AuthenticatedRequest extends Request {
    user: { id: string; email: string };
  }
  ```

**Methods**:

**`createTrip(req, res, next)`** — `POST /api/trips`:
```typescript
async createTrip(req: AuthenticatedRequest, res: Response, next: NextFunction): Promise<void> {
  try {
    const trip = await getTripService().createTrip(req.user.id, req.body);
    res.status(201).json({ success: true, data: trip });
  } catch (error) {
    next(error);
  }
}
```

**`getUserTrips(req, res, next)`** — `GET /api/trips`:
```typescript
async getUserTrips(req: AuthenticatedRequest, res: Response, next: NextFunction): Promise<void> {
  try {
    const status = typeof req.query.status === 'string' ? req.query.status : undefined;
    const trips = await getTripService().getUserTrips(req.user.id, status);
    res.status(200).json({ success: true, data: trips });
  } catch (error) {
    next(error);
  }
}
```

**`getTripById(req, res, next)`** — `GET /api/trips/:id`:
```typescript
async getTripById(req: AuthenticatedRequest, res: Response, next: NextFunction): Promise<void> {
  try {
    const trip = await getTripService().getTripById(req.params.id, req.user.id);
    res.status(200).json({ success: true, data: trip });
  } catch (error) {
    next(error);
  }
}
```

**`addDestination(req, res, next)`** — `POST /api/trips/:id/destinations`:
```typescript
async addDestination(req: AuthenticatedRequest, res: Response, next: NextFunction): Promise<void> {
  try {
    const destination = await getTripService().addDestination(req.params.id, req.user.id, req.body);
    res.status(201).json({ success: true, data: destination });
  } catch (error) {
    next(error);
  }
}
```

**`savePlanningContext(req, res, next)`** — `POST /api/trips/:id/planning-context`:
```typescript
async savePlanningContext(req: AuthenticatedRequest, res: Response, next: NextFunction): Promise<void> {
  try {
    const context = await getTripService().savePlanningContext(req.params.id, req.user.id, req.body);
    res.status(201).json({ success: true, data: context });
  } catch (error) {
    next(error);
  }
}
```

Also add:
```typescript
export function getTripController(): TripController
export function resetTripController(): void
```

---

### Step 11: Add Presentation Controller — `DestinationController`

- **File**: `backend/src/presentation/controllers/destinationController.ts` *(new file)*
- **Action**: Read-only handlers for countries and cities.

**Methods**:

**`getCountries(req, res, next)`** — `GET /api/destinations/countries`:
```typescript
async getCountries(_req: Request, res: Response, next: NextFunction): Promise<void> {
  try {
    const countries = await getDestinationService().getCountries();
    res.status(200).json({ success: true, data: countries });
  } catch (error) {
    next(error);
  }
}
```

**`getCities(req, res, next)`** — `GET /api/destinations/cities`:
```typescript
async getCities(req: Request, res: Response, next: NextFunction): Promise<void> {
  try {
    const countryId = typeof req.query.countryId === 'string' ? req.query.countryId : undefined;
    const cities = await getDestinationService().getCities(countryId);
    res.status(200).json({ success: true, data: cities });
  } catch (error) {
    next(error);
  }
}
```

---

### Step 12: Add Express Routes — `tripRoutes.ts`

- **File**: `backend/src/routes/tripRoutes.ts` *(new file)*
- **Action**: Define RESTful routes. All routes require `authenticateJWT`.
- **Content**:
```typescript
import { Router } from 'express';
import { authenticateJWT } from '../middleware/authMiddleware';
import { getTripController } from '../presentation/controllers/tripController';

const tripRouter = Router();

tripRouter.post('/', authenticateJWT, (req, res, next) => {
  void getTripController().createTrip(req as AuthenticatedRequest, res, next);
});

tripRouter.get('/', authenticateJWT, (req, res, next) => {
  void getTripController().getUserTrips(req as AuthenticatedRequest, res, next);
});

tripRouter.get('/:id', authenticateJWT, (req, res, next) => {
  void getTripController().getTripById(req as AuthenticatedRequest, res, next);
});

tripRouter.post('/:id/destinations', authenticateJWT, (req, res, next) => {
  void getTripController().addDestination(req as AuthenticatedRequest, res, next);
});

tripRouter.post('/:id/planning-context', authenticateJWT, (req, res, next) => {
  void getTripController().savePlanningContext(req as AuthenticatedRequest, res, next);
});

export { tripRouter };
```

---

### Step 13: Add Express Routes — `destinationRoutes.ts`

- **File**: `backend/src/routes/destinationRoutes.ts` *(new file)*
- **Action**: City catalog endpoints. Also require `authenticateJWT`.
```typescript
import { Router } from 'express';
import { authenticateJWT } from '../middleware/authMiddleware';
import { getDestinationController } from '../presentation/controllers/destinationController';

const destinationRouter = Router();

destinationRouter.get('/countries', authenticateJWT, (req, res, next) => {
  void getDestinationController().getCountries(req, res, next);
});

destinationRouter.get('/cities', authenticateJWT, (req, res, next) => {
  void getDestinationController().getCities(req, res, next);
});

export { destinationRouter };
```

---

### Step 14: Register Routes in `routes/index.ts`

- **File**: `backend/src/routes/index.ts` *(modify)*
- **Action**: Add trip and destination routers.
- **Changes**:
```typescript
import { tripRouter } from './tripRoutes';
import { destinationRouter } from './destinationRoutes';

// Add after healthRouter:
apiRouter.use('/trips', tripRouter);
apiRouter.use('/destinations', destinationRouter);
```
- **Remove the commented-out placeholder** `// apiRouter.use('/trips', tripRouter);`.

---

### Step 15: Write Unit Tests — TripService

- **File**: `backend/src/__tests__/application/services/tripService.test.ts` *(new file)*
- **Action**: Test all service methods in isolation. Mock domain model static methods and constructors.
- **Mocking approach**: `jest.mock('../../domain/models/Trip')` etc.

**Test cases to implement (AAA pattern, descriptive names):**

```
describe('TripService - createTrip', () => {
  ✅ should create and return a trip with draft status when valid data is provided
  ✅ should throw ValidationError when startDate is missing
  ✅ should throw ValidationError when endDate is missing
  ✅ should throw ValidationError when endDate is not after startDate
  ✅ should throw ValidationError when startDate is in the past
  ✅ should throw ValidationError when name exceeds 100 characters
  ✅ should create trip with null name when name is not provided
})

describe('TripService - getUserTrips', () => {
  ✅ should return trips for the given ownerId
  ✅ should filter trips by status when status is provided
  ✅ should return empty array when user has no trips
})

describe('TripService - getTripById', () => {
  ✅ should return trip with destinations when owner requests own trip
  ✅ should throw NotFoundError when trip does not exist
  ✅ should throw ForbiddenError when requesting user is not the trip owner
  ✅ should throw ValidationError when id is not a valid UUID
})

describe('TripService - addDestination', () => {
  ✅ should add destination and return it with city data
  ✅ should throw ValidationError when cityId is missing
  ✅ should throw ValidationError when cityId is not a valid UUID
  ✅ should throw ValidationError when dayOrder is not a positive integer
  ✅ should throw ValidationError when daysCount is not a positive integer
  ✅ should throw NotFoundError when trip does not exist
  ✅ should throw ForbiddenError when user does not own the trip
  ✅ should throw NotFoundError when cityId does not exist
  ✅ should throw ConflictError when dayOrder is already used (Prisma P2002)
})

describe('TripService - savePlanningContext', () => {
  ✅ should save context and return it when valid preferences provided
  ✅ should save context with null fields when no preferences provided (all optional)
  ✅ should throw ValidationError when travelStyle is invalid
  ✅ should throw ValidationError when interests contains unknown values
  ✅ should throw ValidationError when specialRequirements exceeds 500 characters
  ✅ should throw NotFoundError when trip does not exist
  ✅ should throw ForbiddenError when user does not own the trip
  ✅ should increment version on each call (version 2 if one already exists)
})
```

- **Coverage target**: ≥ 90% branches, functions, lines, statements.
- **Use `jest.clearAllMocks()` in `beforeEach`**.
- **Use builder/factory helpers** for mock data consistency.

---

### Step 16: Write Unit Tests — TripController

- **File**: `backend/src/__tests__/presentation/controllers/tripController.test.ts` *(new file)*
- **Action**: Test controller HTTP layer. Mock `getTripService()` entirely.

**Test cases**:
```
describe('TripController - createTrip', () => {
  ✅ should return 201 with trip data on success
  ✅ should call next with error when service throws
})

describe('TripController - getUserTrips', () => {
  ✅ should return 200 with trips array on success
  ✅ should pass status query param to service
})

describe('TripController - getTripById', () => {
  ✅ should return 200 with trip on success
  ✅ should call next with NotFoundError when trip not found
  ✅ should call next with ForbiddenError when access denied
})

describe('TripController - addDestination', () => {
  ✅ should return 201 with destination on success
  ✅ should call next with ConflictError when dayOrder conflict
})

describe('TripController - savePlanningContext', () => {
  ✅ should return 201 with planning context on success
  ✅ should call next with error when service throws
})
```

- **Mock Express `Request`, `Response`, `NextFunction`** using jest mock factories.
- **Verify** `res.status().json()` called with correct status codes and body shapes.

---

### Step 17: Update Technical Documentation

- **Action**: Review and update documentation to reflect all backend changes.
- **Implementation Steps**:

  1. **`ai-specs/specs/api-spec.yml`**:
     - Add `POST /api/trips/{id}/planning-context` endpoint under the Trips tag.
     - Request body schema (`PlanningContextRequest`): optional fields `travelStyle`, `budget`, `pace`, `interests`, `specialRequirements`.
     - Response `201`: new schema `TripPlanningContext` with `id`, `tripId`, `version`, `inputsSnapshot`, `coverageSnapshot`, `createdAt`.
     - Add `TripPlanningContext` to `components/schemas`.
     - Ensure `GET /api/destinations/countries` and `GET /api/destinations/cities` match the implemented response structure.

  2. **No `data-model.md` changes needed**: Prisma schema already exists and was not changed.

  3. **No `backend-standards.mdc` changes needed**: implementation follows existing patterns.

---

## Implementation Order

1. Step 0 — Create feature branch `feature/mvp-03-backend`
2. Step 1 — Extend Express Request type for `req.user`
3. Step 2 — Domain model: `Trip`
4. Step 3 — Domain model: `TripDestination`
5. Step 4 — Domain model: `TripPlanningContext`
6. Step 5 — Domain models: `Country`, `City`
7. Step 6 — Repository interfaces + update barrel exports
8. Step 7 — Validators (add to `validator.ts`)
9. Step 8 — `TripService`
10. Step 9 — `DestinationService`
11. Step 10 — `TripController`
12. Step 11 — `DestinationController`
13. Step 12 — `tripRoutes.ts`
14. Step 13 — `destinationRoutes.ts`
15. Step 14 — Register routes in `routes/index.ts`
16. Step 15 — Unit tests: `TripService`
17. Step 16 — Unit tests: `TripController`
18. Step 17 — Update `api-spec.yml`

---

## Testing Checklist

- [ ] `npm test` passes with 0 failures
- [ ] `npm run test:coverage` shows ≥ 90% for all metrics in new files
- [ ] `POST /api/trips` returns `201` with correct shape
- [ ] `POST /api/trips` returns `400` with `VALIDATION_ERROR` code when dates are invalid
- [ ] `POST /api/trips/:id/destinations` returns `201` with embedded city
- [ ] `POST /api/trips/:id/destinations` returns `409` with `CONFLICT` code on duplicate `dayOrder`
- [ ] `POST /api/trips/:id/destinations` returns `403` when user doesn't own the trip
- [ ] `POST /api/trips/:id/destinations` returns `404` for unknown `cityId`
- [ ] `POST /api/trips/:id/planning-context` returns `201` with all optional fields null when body is `{}`
- [ ] `GET /api/destinations/countries` returns array of countries
- [ ] `GET /api/destinations/cities?countryId=<uuid>` returns filtered cities
- [ ] `GET /api/destinations/cities` with invalid UUID for `countryId` returns `400`
- [ ] All routes return `401` when no auth token is provided (requires `mvp-02` middleware)
- [ ] TypeScript compiles without errors: `npm run build`
- [ ] ESLint passes: `npm run lint`

---

## Error Response Format

All errors follow the existing global error handler structure (no changes needed):

```json
{
  "success": false,
  "error": {
    "message": "Human-readable description",
    "code": "ERROR_CODE",
    "details": {
      "fieldName": ["validation message"]
    }
  }
}
```

| HTTP Status | `code` | Scenario |
|---|---|---|
| `400` | `VALIDATION_ERROR` | Date invalid, name too long, invalid enum, invalid UUID in body |
| `401` | `UNAUTHORIZED` | Missing or invalid JWT |
| `403` | `FORBIDDEN` | Authenticated user is not the trip owner |
| `404` | `NOT_FOUND` | Trip or City not found |
| `409` | `CONFLICT` | Duplicate `dayOrder` on `TripDestination` (Prisma P2002) |
| `500` | `INTERNAL_ERROR` | Unexpected server error |

---

## Dependencies

| Package | Already installed? | Notes |
|---|---|---|
| `@prisma/client` | Yes | `Trip`, `TripDestination`, `TripPlanningContext` models already in schema |
| `express` | Yes | Routing and middleware |
| `jsonwebtoken` | Assumed (mvp-02) | Used by `authenticateJWT` middleware |
| No new packages required | — | All functionality uses existing stack |

---

## Notes

1. **No Prisma migration required**: `Trip`, `TripDestination`, `TripPlanningContext`, `Country`, `City`, `CityCoverage` are all already defined in the initial migration (`20260208223511_init`). Do not run `prisma migrate dev` unless the schema is changed.

2. **`TripPlanningContext` uses JSON fields** (`inputsSnapshot`, `coverageSnapshot`). Preferences are not flat columns — they live inside `inputsSnapshot.preferences`. This aligns with the Phase 2 AI context design (more inputs can be added to the snapshot without schema changes).

3. **Auth middleware (`authenticateJWT`) is a hard dependency** from `mvp-02`. If not yet available, use the stub described in Step 0. Never merge the stub to `main`.

4. **English only**: all code, error messages, comments, and logs must be in English.

5. **`req.user` is non-null** on all trip routes because `authenticateJWT` runs first and would 401 before the controller is reached. Use `!` assertion (`req.user!.id`) or the `AuthenticatedRequest` type extension in the controller.

6. **`addDestination` returns with city data**: After calling `destination.save()`, call `TripDestination.findByTripIdAndOrder(tripId, dayOrder)` to return the record with `city` + `country` included. This avoids returning a partial object.

7. **`coverageSnapshot` in `savePlanningContext`**: fetches current city coverage for all trip destinations at the time of the call. If a city has no `CityCoverage` record, include `{ cityId, cityName, level: null, notes: null }` — do not fail the request.

8. **Do not implement `DELETE /api/trips/:id`** in this ticket — it is in scope for `mvp-04`.

9. **`GET /api/trips` only returns trips owned by the authenticated user** — no admin view.

---

## Next Steps After Implementation

- `mvp-02` (if not done): merge auth middleware so the stub can be removed
- `mvp-04` (Manage Trip Lifecycle): implements trip status transitions (`draft → active → completed`), trip deletion, and destination reordering (`PUT/DELETE /api/trips/:id/destinations/:destinationId`)
- `mvp-05` (Build Manual Itinerary): creates `ItineraryVersion`, `DayPlan`, and `Activity` records — depends on `Trip` and `TripDestination` being available
- **Integration test** (post-mvp-02): end-to-end test with a real JWT to exercise auth + trip creation flow

---

## Implementation Verification

### Code Quality
- [ ] No `any` types — all function params and return values are strictly typed
- [ ] `strict` mode enabled in `tsconfig.json` (already configured)
- [ ] No direct `prisma.*` calls in services or controllers — only via domain model methods
- [ ] No business logic in controllers — all logic in services or domain models
- [ ] Singleton pattern (`get*Service()`, `get*Controller()`, `reset*()`) applied consistently

### Functionality
- [ ] `POST /api/trips` creates trip in `draft` status
- [ ] `POST /api/trips/:id/destinations` verifies city exists before creating
- [ ] `POST /api/trips/:id/destinations` verifies trip ownership before creating
- [ ] Duplicate `dayOrder` is rejected with `409 CONFLICT`
- [ ] Planning context version auto-increments correctly (1, 2, 3...)
- [ ] `coverageSnapshot` captured at time of saving context (not real-time)

### Testing
- [ ] All described test cases implemented
- [ ] `jest.clearAllMocks()` called in `beforeEach` in every test file
- [ ] No real DB connections in unit tests — all Prisma calls mocked
- [ ] AAA (Arrange-Act-Assert) pattern followed throughout
- [ ] Coverage threshold met (≥ 90%)

### Integration
- [ ] Routes registered in `routes/index.ts`
- [ ] `TypeScript` compiles: `npm run build` exits 0
- [ ] Tests pass: `npm test` exits 0

### Documentation
- [ ] `api-spec.yml` updated with `POST /api/trips/{id}/planning-context`
- [ ] Response schemas match the actual implementation
