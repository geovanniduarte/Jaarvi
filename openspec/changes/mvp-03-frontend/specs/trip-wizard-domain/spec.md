## ADDED Requirements

### Requirement: Trip and destination domain models
The shared domain SHALL define immutable data classes for `Trip`, `TripDestination`, `Country`, and `City` that represent the core entities used across the wizard.

#### Scenario: Trip model carries required fields
- **WHEN** a `Trip` is created from an API response
- **THEN** it MUST include `id`, `ownerId`, `name` (nullable), `startDate` (LocalDate), `endDate` (LocalDate), `status` (TripStatus enum), and `createdAt` (Instant)

#### Scenario: TripStatus covers wizard-relevant states
- **WHEN** the `TripStatus` enum is inspected
- **THEN** it MUST include `DRAFT`, `ACTIVE`, and `COMPLETED` values

#### Scenario: City model embeds its country
- **WHEN** a `City` is constructed
- **THEN** it MUST include a nested `country: Country` object so callers never need to join separately

---

### Requirement: Repository interfaces for trip and destination operations
The domain SHALL define `ITripRepository` and `IDestinationRepository` interfaces in `commonMain`, with all methods returning `Result<T>` to surface success or failure without throwing.

#### Scenario: ITripRepository exposes createTrip
- **WHEN** `ITripRepository.createTrip(name, startDate, endDate)` is called
- **THEN** it SHALL return `Result<Trip>` containing the server-assigned ID and status

#### Scenario: ITripRepository exposes addDestination
- **WHEN** `ITripRepository.addDestination(tripId, cityId, dayOrder, daysCount)` is called
- **THEN** it SHALL return `Result<TripDestination>`

#### Scenario: ITripRepository exposes savePlanningContext
- **WHEN** `ITripRepository.savePlanningContext(tripId, travelStyle, budget, pace, interests, specialRequirements)` is called
- **THEN** it SHALL return `Result<Unit>`

#### Scenario: IDestinationRepository exposes getCountries
- **WHEN** `IDestinationRepository.getCountries()` is called
- **THEN** it SHALL return `Result<List<Country>>` with countries ordered alphabetically by name

#### Scenario: IDestinationRepository exposes getCities
- **WHEN** `IDestinationRepository.getCities(countryId)` is called with a non-null `countryId`
- **THEN** it SHALL return `Result<List<City>>` containing only cities for that country
- **WHEN** `countryId` is null
- **THEN** it SHALL return all cities

---

### Requirement: Client-side validation in CreateTripUseCase
`CreateTripUseCase` SHALL validate inputs before delegating to the repository, returning `Result.failure` with a descriptive exception for any violation.

#### Scenario: Reject end date not after start date
- **WHEN** `CreateTripUseCase.invoke` is called with `endDate <= startDate`
- **THEN** it SHALL return `Result.failure(IllegalArgumentException("endDate must be after startDate"))` without calling the repository

#### Scenario: Reject start date in the past
- **WHEN** `CreateTripUseCase.invoke` is called with `startDate` before today (device clock)
- **THEN** it SHALL return `Result.failure(IllegalArgumentException("startDate cannot be in the past"))` without calling the repository

#### Scenario: Reject name exceeding 100 characters
- **WHEN** `CreateTripUseCase.invoke` is called with a `name` longer than 100 characters
- **THEN** it SHALL return `Result.failure(IllegalArgumentException("name exceeds 100 characters"))` without calling the repository

#### Scenario: Pass through valid input to repository
- **WHEN** all validation rules pass
- **THEN** `CreateTripUseCase.invoke` SHALL delegate to `ITripRepository.createTrip` and return its result

---

### Requirement: Ktor-backed repository implementations
`TripRepositoryImpl` and `DestinationRepositoryImpl` SHALL implement the domain interfaces using `JaarviApiClient` (existing Ktor client), mapping DTOs to domain models via `toDomain()` extension functions.

#### Scenario: TripRepositoryImpl maps TripDto to Trip
- **WHEN** `POST /api/trips` returns a valid JSON body
- **THEN** `TripDto.toDomain()` converts ISO-8601 date strings to `LocalDate` and `Instant` without throwing
- **AND** the resulting `Trip` has the correct `id` and `status`

#### Scenario: Repository wraps network errors in Result.failure
- **WHEN** the Ktor client throws a network exception (e.g., no connectivity)
- **THEN** the repository catches it and returns `Result.failure(exception)` instead of propagating the throw
