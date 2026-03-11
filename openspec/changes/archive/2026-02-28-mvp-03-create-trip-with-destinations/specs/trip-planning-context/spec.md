## ADDED Requirements

### Requirement: Save a versioned planning context for a trip
The system SHALL allow the authenticated trip owner to save a preferences snapshot that captures travel style, budget, pace, interests, and special requirements at a point in time. Each save SHALL create a new version, incrementing from the last saved version for that trip.

#### Scenario: Successful context save with all preferences
- **WHEN** authenticated owner sends `POST /api/trips/:id/planning-context` with valid preference fields
- **THEN** system creates a planning context record with `version` incremented from the last for that trip, and responds `201` with `{ success: true, data: <context> }`

#### Scenario: Successful context save with empty body
- **WHEN** authenticated owner sends `POST /api/trips/:id/planning-context` with an empty body `{}`
- **THEN** system creates a planning context with all optional preference fields as `null` and responds `201`

#### Scenario: First context gets version 1
- **WHEN** no prior planning context exists for the trip
- **THEN** the saved context SHALL have `version: 1`

#### Scenario: Subsequent context increments version
- **WHEN** one or more planning contexts already exist for the trip
- **THEN** the new context SHALL have `version` equal to the previous maximum version plus 1

#### Scenario: Invalid travelStyle
- **WHEN** `travelStyle` is not one of `adventurous`, `cultural`, `relaxed`, `mixed`
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `travelStyle`

#### Scenario: Invalid budget
- **WHEN** `budget` is not one of `budget`, `moderate`, `premium`
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `budget`

#### Scenario: Invalid pace
- **WHEN** `pace` is not one of `slow`, `medium`, `fast`
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `pace`

#### Scenario: interests contains unknown values
- **WHEN** `interests` array contains values not in `museums`, `food`, `nature`, `nightlife`, `shopping`, `outdoor`
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `interests`

#### Scenario: interests is not an array
- **WHEN** `interests` is provided but is not an array
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `interests`

#### Scenario: specialRequirements exceeds 500 characters
- **WHEN** `specialRequirements` string exceeds 500 characters
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `specialRequirements`

#### Scenario: Trip not found
- **WHEN** trip ID does not exist
- **THEN** system responds `404` with `NOT_FOUND`

#### Scenario: Non-owner access denied
- **WHEN** authenticated user is not the owner of the trip
- **THEN** system responds `403` with `FORBIDDEN`

---

### Requirement: coverageSnapshot captures city coverage at save time
The system SHALL embed a point-in-time coverage snapshot of all trip destinations when saving a planning context. Destinations with no `CityCoverage` record SHALL be included with `level: null`.

#### Scenario: coverageSnapshot includes all trip destinations
- **WHEN** planning context is saved
- **THEN** `coverageSnapshot` MUST contain one entry per trip destination with `cityId`, `cityName`, `level`, and `notes`

#### Scenario: City with no coverage record
- **WHEN** a trip destination's city has no `CityCoverage` record
- **THEN** its entry in `coverageSnapshot` MUST have `level: null` and `notes: null` without causing a request failure
