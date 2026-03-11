## ADDED Requirements

### Requirement: Add a destination to a trip
The system SHALL allow the authenticated trip owner to add a city destination to a trip, with a day order and duration. Each `dayOrder` value MUST be unique per trip.

#### Scenario: Successful destination addition
- **WHEN** authenticated owner sends `POST /api/trips/:id/destinations` with valid `cityId`, `dayOrder`, and `daysCount`
- **THEN** system creates the destination and responds `201` with `{ success: true, data: <destination with city and country> }`

#### Scenario: Missing cityId
- **WHEN** request body omits `cityId`
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `cityId`

#### Scenario: Invalid UUID for cityId
- **WHEN** `cityId` is not a valid UUID
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `cityId`

#### Scenario: Missing dayOrder
- **WHEN** request body omits `dayOrder`
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `dayOrder`

#### Scenario: dayOrder is not a positive integer
- **WHEN** `dayOrder` is zero, negative, or a non-integer
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `dayOrder`

#### Scenario: Missing daysCount
- **WHEN** request body omits `daysCount`
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `daysCount`

#### Scenario: daysCount is not a positive integer
- **WHEN** `daysCount` is zero, negative, or a non-integer
- **THEN** system responds `400` with `VALIDATION_ERROR` and field error on `daysCount`

#### Scenario: Trip not found
- **WHEN** trip ID does not exist
- **THEN** system responds `404` with `NOT_FOUND`

#### Scenario: Non-owner adding destination
- **WHEN** authenticated user is not the owner of the trip
- **THEN** system responds `403` with `FORBIDDEN`

#### Scenario: City not found
- **WHEN** `cityId` is a valid UUID but no city with that ID exists
- **THEN** system responds `404` with `NOT_FOUND` and message indicating city not found

#### Scenario: Duplicate dayOrder conflict
- **WHEN** a destination with the same `dayOrder` already exists for the trip
- **THEN** system responds `409` with `CONFLICT`

#### Scenario: Response includes city data
- **WHEN** destination is successfully created
- **THEN** response data MUST include the `city` object with `name`, `timezone`, and nested `country` with `name` and `isoCode`
