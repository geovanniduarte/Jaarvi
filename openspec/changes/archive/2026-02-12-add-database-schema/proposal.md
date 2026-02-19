# Change: Initialize PostgreSQL Database with Prisma Schema

## Why

The backend infrastructure is scaffolded but lacks a database schema. All feature development (authentication, trips, itineraries, documents) is blocked until the 32 Prisma models are defined and the database is initialized with seed data for development.

## What Changes

- **ADDED** Complete Prisma schema with 32 models covering:
  - Authentication & Identity (7 models): User, UserCredential, Session, RefreshToken, PasswordResetToken, EmailVerificationToken, OAuthAccount
  - User Preferences (1 model): PreferenceSet
  - Trip Management (3 models): Trip, TripDestination, TripPlanningContext
  - Geography (3 models): Country, City, CityCoverage
  - Itinerary (4 models): ItineraryVersion, ItineraryGeneration, DayPlan, DailyContext
  - Activities (3 models): ActivityTypeConfig, Activity, ActivityProgress
  - Execution (4 models): StepGuide, StepGuideStep, Reminder, Incident
  - Documents (2 models): Document, DocumentLink
  - Playbooks (3 models): PlaybookPack, PlaybookEntry, LocalAdvicePack
  - Affiliates (2 models): Recommendation, AffiliateClick

- **ADDED** Seed scripts for:
  - Activity type configurations (required system configuration)
  - Countries and cities with coverage levels (MVP destinations)
  - Test users with hashed passwords (development only)
  - Sample trips with destinations (development only)

- **ADDED** NPM scripts for database operations:
  - `prisma:generate`, `prisma:migrate`, `prisma:studio`
  - `seed` and individual seed scripts

## Impact

- **Affected specs**: Creates new `database-schema` capability
- **Affected code**: `backend/prisma/` directory
- **Dependencies**: Requires `argon2` package for password hashing in seeds
- **Blocks**: All backend feature tickets that require data persistence
