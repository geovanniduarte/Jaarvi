## ADDED Requirements

### Requirement: Create a trip
The system SHALL allow an authenticated user to create a trip in `draft` status by providing a date range and an optional name.

#### Scenario: Successful trip creation
- **WHEN** authenticated user sends `POST /api/trips` with valid `startDate`, `endDate`, and optional `name`
- **THEN** system creates the trip with `status: "draft"` and responds `201` with `{ success: true, data: <trip> }`

#### Scenario: Missing startDate
- **WHEN** authenticated user sends `POST /api/trips` without `startDate`
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `startDate`

#### Scenario: Missing endDate
- **WHEN** authenticated user sends `POST /api/trips` without `endDate`
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `endDate`

#### Scenario: endDate not after startDate
- **WHEN** authenticated user sends `POST /api/trips` with `endDate` equal to or before `startDate`
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `endDate`

#### Scenario: startDate in the past
- **WHEN** authenticated user sends `POST /api/trips` with `startDate` before today
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `startDate`

#### Scenario: name exceeds 100 characters
- **WHEN** authenticated user sends `POST /api/trips` with `name` longer than 100 characters
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `name`

#### Scenario: name omitted
- **WHEN** authenticated user sends `POST /api/trips` without `name`
- **THEN** system creates the trip with `name: null` and responds `201`

#### Scenario: Unauthenticated request
- **WHEN** request is sent without a valid JWT
- **THEN** system responds `401` with `UNAUTHORIZED`

---

### Requirement: List user trips
The system SHALL return all trips owned by the authenticated user, optionally filtered by status.

#### Scenario: List all trips
- **WHEN** authenticated user sends `GET /api/trips`
- **THEN** system responds `200` with `{ success: true, data: [<trip>, ...] }` containing only trips owned by the user

#### Scenario: List filtered by status
- **WHEN** authenticated user sends `GET /api/trips?status=draft`
- **THEN** system responds `200` with trips whose `status` equals `"draft"`

#### Scenario: No trips owned
- **WHEN** authenticated user has no trips
- **THEN** system responds `200` with `{ success: true, data: [] }`

#### Scenario: Trips from other users not returned
- **WHEN** authenticated user sends `GET /api/trips`
- **THEN** system MUST NOT include trips owned by other users in the response

---

### Requirement: Get trip by ID with destinations
The system SHALL return a single trip including its destinations and embedded city data when the authenticated owner requests it.

#### Scenario: Owner retrieves own trip
- **WHEN** authenticated user sends `GET /api/trips/:id` for a trip they own
- **THEN** system responds `200` with `{ success: true, data: <trip with destinations> }` where destinations include city and country data

#### Scenario: Trip not found
- **WHEN** authenticated user sends `GET /api/trips/:id` for a non-existent trip ID
- **THEN** system responds `404` with `NOT_FOUND`

#### Scenario: Non-owner access denied
- **WHEN** authenticated user sends `GET /api/trips/:id` for a trip owned by another user
- **THEN** system responds `403` with `FORBIDDEN`

#### Scenario: Invalid UUID as ID
- **WHEN** authenticated user sends `GET /api/trips/:id` with a non-UUID string
- **THEN** system responds `400` with `VALIDATION_ERROR`
