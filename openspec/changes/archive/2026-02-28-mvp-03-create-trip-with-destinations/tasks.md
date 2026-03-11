## 1. Setup

- [x] 1.1 Create feature branch `feature/mvp-03-backend` from `main`
- [x] 1.2 If `mvp-02` is not merged, create stub `authenticateJWT` middleware at `backend/src/middleware/authMiddleware.ts`

## 2. Type Augmentation

- [x] 2.1 Create `backend/src/types/express.d.ts` augmenting Express `Request` with `user?: { id: string; email: string }`

## 3. Domain Models

- [x] 3.1 Create `backend/src/domain/models/Trip.ts` with constructor, `save()`, `findById()`, `findByOwnerId()`
- [x] 3.2 Create `backend/src/domain/models/TripDestination.ts` with constructor, `save()` (P2002 → ConflictError), `findByTripId()`, `findByTripIdAndOrder()`
- [x] 3.3 Create `backend/src/domain/models/TripPlanningContext.ts` with constructor, `save()`, `findByTripId()`, `getNextVersionForTrip()`
- [x] 3.4 Create `backend/src/domain/models/Country.ts` with constructor and `findAll()`
- [x] 3.5 Create `backend/src/domain/models/City.ts` with constructor, `findAll(countryId?)`, `findById()`
- [x] 3.6 Update `backend/src/domain/models/index.ts` to export all 5 new models

## 4. Repository Interfaces

- [x] 4.1 Create `backend/src/domain/repositories/ITripRepository.ts`
- [x] 4.2 Create `backend/src/domain/repositories/IDestinationRepository.ts`
- [x] 4.3 Update `backend/src/domain/repositories/index.ts` to export both interfaces

## 5. Validators

- [x] 5.1 Add `validateCreateTrip()` to `backend/src/application/validator.ts`
- [x] 5.2 Add `validateAddDestination()` to `backend/src/application/validator.ts`
- [x] 5.3 Add `validatePlanningContext()` to `backend/src/application/validator.ts`

## 6. Application Services

- [x] 6.1 Create `backend/src/application/services/tripService.ts` with `createTrip()`, `getUserTrips()`, `getTripById()`, `addDestination()`, `savePlanningContext()` and singleton factory
- [x] 6.2 Create `backend/src/application/services/destinationService.ts` with `getCountries()`, `getCities()` and singleton factory

## 7. Presentation Controllers

- [x] 7.1 Create `backend/src/presentation/controllers/tripController.ts` with `createTrip()`, `getUserTrips()`, `getTripById()`, `addDestination()`, `savePlanningContext()` and singleton factory
- [x] 7.2 Create `backend/src/presentation/controllers/destinationController.ts` with `getCountries()`, `getCities()` and singleton factory

## 8. Routes

- [x] 8.1 Create `backend/src/routes/tripRoutes.ts` with all 5 trip routes protected by `authenticateJWT`
- [x] 8.2 Create `backend/src/routes/destinationRoutes.ts` with country and city routes protected by `authenticateJWT`
- [x] 8.3 Register `tripRouter` under `/trips` and `destinationRouter` under `/destinations` in `backend/src/routes/index.ts`
- [x] 8.4 Remove the commented-out trip router placeholder from `routes/index.ts`

## 9. Unit Tests — TripService

- [x] 9.1 Create `backend/src/__tests__/application/services/tripService.test.ts`
- [x] 9.2 Implement `createTrip` tests (7 cases: success, missing startDate, missing endDate, endDate not after startDate, past startDate, name too long, null name)
- [x] 9.3 Implement `getUserTrips` tests (3 cases: returns trips, filters by status, empty array)
- [x] 9.4 Implement `getTripById` tests (4 cases: success, not found, forbidden, invalid UUID)
- [x] 9.5 Implement `addDestination` tests (9 cases: success, missing cityId, invalid UUID, bad dayOrder, bad daysCount, trip not found, forbidden, city not found, conflict)
- [x] 9.6 Implement `savePlanningContext` tests (8 cases: success, empty body, invalid travelStyle, invalid interests, specialRequirements too long, trip not found, forbidden, version increment)
- [x] 9.7 Verify coverage ≥90% on `tripService.ts`

## 10. Unit Tests — TripController

- [x] 10.1 Create `backend/src/__tests__/presentation/controllers/tripController.test.ts`
- [x] 10.2 Implement `createTrip` controller tests (2 cases: 201 success, next called on error)
- [x] 10.3 Implement `getUserTrips` controller tests (2 cases: 200 success, status query param passed)
- [x] 10.4 Implement `getTripById` controller tests (3 cases: 200 success, NotFoundError, ForbiddenError)
- [x] 10.5 Implement `addDestination` controller tests (2 cases: 201 success, ConflictError)
- [x] 10.6 Implement `savePlanningContext` controller tests (2 cases: 201 success, next called on error)
- [x] 10.7 Verify coverage ≥90% on `tripController.ts`

## 11. Build and Quality

- [x] 11.1 Run `npm run build` — ensure TypeScript compiles with 0 errors
- [x] 11.2 Run `npm run lint` — ensure ESLint passes with 0 errors
- [x] 11.3 Run `npm test` — ensure all tests pass with 0 failures
- [x] 11.4 Run `npm run test:coverage` — verify ≥90% coverage on all new files

## 12. Documentation

- [x] 12.1 Add `POST /api/trips/{id}/planning-context` to `ai-specs/specs/api-spec.yml` with request schema and `201` response schema (`TripPlanningContext`)
- [x] 12.2 Verify `GET /api/destinations/countries` and `GET /api/destinations/cities` entries in `api-spec.yml` match the implemented response structure
