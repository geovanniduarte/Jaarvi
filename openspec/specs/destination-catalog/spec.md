## ADDED Requirements

### Requirement: List all countries
The system SHALL return all countries in the catalog, ordered alphabetically by name, for use in the city picker.

#### Scenario: Successful countries list
- **WHEN** authenticated user sends `GET /api/destinations/countries`
- **THEN** system responds `200` with `{ success: true, data: [<country>, ...] }` ordered by `name` ascending

#### Scenario: Unauthenticated request
- **WHEN** request is sent without a valid JWT
- **THEN** system responds `401` with `UNAUTHORIZED`

#### Scenario: Response shape
- **WHEN** countries are returned
- **THEN** each country object MUST include `id`, `isoCode`, `name`, and `createdAt`

---

### Requirement: List cities optionally filtered by country
The system SHALL return cities from the catalog, ordered alphabetically by name, optionally filtered by `countryId`. Each city MUST include its nested country data.

#### Scenario: List all cities without filter
- **WHEN** authenticated user sends `GET /api/destinations/cities` without query params
- **THEN** system responds `200` with `{ success: true, data: [<city>, ...] }` with all cities ordered by `name` ascending

#### Scenario: List cities filtered by country
- **WHEN** authenticated user sends `GET /api/destinations/cities?countryId=<uuid>`
- **THEN** system responds `200` with only cities whose `countryId` matches the given UUID

#### Scenario: Invalid UUID for countryId
- **WHEN** `countryId` query param is provided but is not a valid UUID
- **THEN** system responds `400` with `VALIDATION_ERROR`

#### Scenario: Unauthenticated request
- **WHEN** request is sent without a valid JWT
- **THEN** system responds `401` with `UNAUTHORIZED`

#### Scenario: City response includes country and coverage
- **WHEN** cities are returned
- **THEN** each city object MUST include `id`, `name`, `timezone`, `countryId`, `createdAt`, nested `country` object, and `coverage` (which may be `null` if no coverage record exists)
