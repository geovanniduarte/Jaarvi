# Database Schema Specification

## ADDED Requirements

### Requirement: Prisma Schema Definition

The database system SHALL define all 32 data models in `prisma/schema.prisma` as the single source of truth for the database structure.

#### Scenario: Schema validation passes

- **GIVEN** the complete schema.prisma file with all 32 models
- **WHEN** `npx prisma validate` is executed
- **THEN** validation passes with no errors
- **AND** all model relationships are correctly defined

#### Scenario: Schema format is consistent

- **GIVEN** the schema.prisma file
- **WHEN** `npx prisma format` is executed
- **THEN** no formatting changes are needed
- **AND** the file follows Prisma formatting conventions

---

### Requirement: Authentication Models

The database system SHALL provide models for user authentication supporting email/password and OAuth providers.

#### Scenario: User model stores identity

- **GIVEN** a new user registration
- **WHEN** the user is created
- **THEN** the User model stores:
  - `id`: UUID primary key
  - `email`: unique identifier
  - `displayName`: user's display name
  - `emailVerifiedAt`: nullable timestamp
  - `status`: active or disabled
  - `createdAt` and `updatedAt` timestamps

#### Scenario: UserCredential stores password hash

- **GIVEN** a user with email/password authentication
- **WHEN** credentials are stored
- **THEN** UserCredential contains:
  - `passwordHash`: argon2id hash (never plain text)
  - `passwordAlgo`: algorithm identifier (argon2id)
  - One-to-one relationship with User

#### Scenario: Session tracks login devices

- **GIVEN** a user logs in from a device
- **WHEN** a session is created
- **THEN** Session stores:
  - `deviceName`, `userAgent`, `ipAddress`
  - `createdAt`, `lastSeenAt`, `expiresAt`
  - `revokedAt` for logout/revocation

#### Scenario: RefreshToken supports rotation

- **GIVEN** a refresh token is issued
- **WHEN** stored in database
- **THEN** RefreshToken contains:
  - `tokenHash`: unique hashed token
  - `sessionId`: link to Session
  - `rotatedAt`, `revokedAt`: for token lifecycle

#### Scenario: OAuthAccount links external providers

- **GIVEN** a user signs in with Google or Apple
- **WHEN** the OAuth account is linked
- **THEN** OAuthAccount stores:
  - `provider`: google or apple
  - `providerAccountId`: unique ID from provider
  - Unique constraint on (provider, providerAccountId)

---

### Requirement: Geography Models

The database system SHALL provide models for countries, cities, and coverage levels to support destination selection.

#### Scenario: Country with ISO code

- **GIVEN** a country is added to the catalog
- **WHEN** stored in database
- **THEN** Country has unique `isoCode` (e.g., FR, IT, ES)
- **AND** `name` for display

#### Scenario: City with timezone

- **GIVEN** a city is added under a country
- **WHEN** stored in database
- **THEN** City has:
  - `countryId`: foreign key to Country
  - `name`: city name
  - `timezone`: IANA timezone (e.g., Europe/Paris)
  - Unique constraint on (countryId, name)

#### Scenario: CityCoverage indicates playbook availability

- **GIVEN** a city has playbook coverage
- **WHEN** coverage is defined
- **THEN** CityCoverage has:
  - `level`: high, medium, or low
  - One-to-one relationship with City

---

### Requirement: Trip Management Models

The database system SHALL provide models for trips, destinations, and planning context.

#### Scenario: Trip with owner and dates

- **GIVEN** a user creates a trip
- **WHEN** stored in database
- **THEN** Trip has:
  - `ownerId`: foreign key to User
  - `startDate` and `endDate`
  - `status`: draft, active, or completed
  - Constraint: startDate <= endDate

#### Scenario: TripDestination orders cities

- **GIVEN** a trip has multiple destinations
- **WHEN** destinations are added
- **THEN** TripDestination has:
  - `tripId`: foreign key to Trip
  - `cityId`: foreign key to City
  - `dayOrder`: sequence number
  - `daysCount`: number of days at destination
  - Unique constraint on (tripId, dayOrder)

#### Scenario: TripPlanningContext captures AI inputs

- **GIVEN** AI generates an itinerary
- **WHEN** planning context is saved
- **THEN** TripPlanningContext stores:
  - `inputsSnapshot`: JSONB with preferences/constraints
  - `coverageSnapshot`: JSONB with destination coverage levels
  - `version`: incremental version number

---

### Requirement: Itinerary Models

The database system SHALL provide models for versioned itineraries with AI generation audit trail.

#### Scenario: ItineraryVersion tracks source

- **GIVEN** an itinerary is created
- **WHEN** stored in database
- **THEN** ItineraryVersion has:
  - `source`: ai, manual, or mixed
  - `status`: draft, ready, or archived
  - `version`: incremental per trip
  - Unique constraint on (tripId, version)

#### Scenario: ItineraryGeneration provides AI auditability

- **GIVEN** AI generates an itinerary
- **WHEN** generation metadata is saved
- **THEN** ItineraryGeneration stores:
  - `agentName` and `agentVersion`
  - `promptInputsSnapshot`: JSONB of inputs sent to AI
  - `rationale`: JSONB explaining why decisions were made
  - `confidence`: JSONB with per-activity confidence levels
  - `citations`: JSONB with source references

#### Scenario: DayPlan organizes daily activities

- **GIVEN** an itinerary version exists
- **WHEN** day plans are created
- **THEN** DayPlan has:
  - `itineraryVersionId`: foreign key
  - `cityId`: where the day occurs
  - `dayDate`: the calendar date
  - `status`: pending, ready, in_progress, completed
  - Unique constraint on (itineraryVersionId, dayDate)

---

### Requirement: Activity Models

The database system SHALL provide models for scheduled activities with mandatory activity support.

#### Scenario: ActivityTypeConfig defines types

- **GIVEN** activity types are configured
- **WHEN** stored in database
- **THEN** ActivityTypeConfig has:
  - `key`: primary key (e.g., sleep, transfer, visit)
  - `isMandatoryDaily`: true for required activities like accommodation
  - `requiresEvidence`: true if documentation needed
  - `allowNotNeeded`: whether type can be marked not needed

#### Scenario: Activity schedules with time blocks

- **GIVEN** an activity is added to a day plan
- **WHEN** stored in database
- **THEN** Activity has:
  - `activityTypeKey`: foreign key to ActivityTypeConfig
  - `startTime` and `endTime`: nullable for flexible activities
  - `sortOrder`: display order within day
  - `isMandatory`: inherited from type configuration
  - `readinessStatus`: pending, planned, prepared, notNeeded
  - Unique constraint on (dayPlanId, sortOrder)

#### Scenario: ActivityProgress tracks execution

- **GIVEN** an activity is being executed
- **WHEN** progress is tracked
- **THEN** ActivityProgress has:
  - One-to-one relationship with Activity
  - `status`: notStarted, inProgress, done, skipped
  - `startedAt` and `completedAt` timestamps

---

### Requirement: Document Models

The database system SHALL provide models for document storage with multi-entity linking.

#### Scenario: Document stores metadata

- **GIVEN** a document is uploaded
- **WHEN** stored in database
- **THEN** Document has:
  - `type`: ticket, permit, reservation, insurance, other
  - `storageProvider` and `storageKey`: reference to object storage
  - `checksum`: for integrity verification
  - `metadataJson`: flexible JSONB for tags/properties

#### Scenario: DocumentLink enables multi-association

- **GIVEN** a document relates to multiple entities
- **WHEN** links are created
- **THEN** DocumentLink can reference:
  - `tripId`: required
  - `dayPlanId`: optional
  - `activityId`: optional
  - Enabling one document to appear in multiple contexts

---

### Requirement: Seed Data Configuration

The database system SHALL provide seed scripts for system configuration and development data.

#### Scenario: Activity types are seeded

- **GIVEN** a fresh database
- **WHEN** seed script runs
- **THEN** 5 activity types are created:
  - sleep (mandatory daily, requires evidence)
  - transfer, visit, meal, free_time (optional)
- **AND** running seed again does not create duplicates

#### Scenario: MVP destinations are seeded

- **GIVEN** a fresh database
- **WHEN** seed script runs
- **THEN** data is created:
  - 6 countries (FR, IT, ES, JP, GB, US)
  - 13 cities with correct timezones
  - 13 coverage records (high or medium)
- **AND** running seed again updates existing records

#### Scenario: Test users created in development

- **GIVEN** NODE_ENV is development
- **WHEN** seed script runs
- **THEN** 3 test users are created:
  - test@jaarvi.app, demo@jaarvi.app, admin@jaarvi.app
  - Passwords hashed with argon2id
  - emailVerifiedAt set to current time
- **AND** running in production skips test users

#### Scenario: Sample trips created in development

- **GIVEN** NODE_ENV is development and test users exist
- **WHEN** seed script runs
- **THEN** 2 sample trips are created:
  - "France & Italy Adventure" for test user
  - "Japan Discovery" for demo user
  - Each with correct destinations and day counts

---

### Requirement: Database Indexes

The database system SHALL define indexes for hot query optimization.

#### Scenario: Today Mode query is optimized

- **GIVEN** indexes are defined
- **WHEN** Today Mode query executes
- **THEN** these indexes are used:
  - Trip: (ownerId, status)
  - Trip: (ownerId, startDate)
  - DayPlan: (itineraryVersionId, dayDate)
  - DayPlan: (cityId, dayDate)
  - Activity: (dayPlanId, sortOrder)
  - Activity: (dayPlanId, startTime)

#### Scenario: Mandatory activities query is optimized

- **GIVEN** indexes are defined
- **WHEN** querying pending mandatory activities
- **THEN** index (dayPlanId, isMandatory, readinessStatus) is used

#### Scenario: Authentication queries are optimized

- **GIVEN** indexes are defined
- **WHEN** login or token refresh occurs
- **THEN** these indexes are used:
  - User: email (unique)
  - RefreshToken: tokenHash (unique)
  - Session: (userId, expiresAt desc)
