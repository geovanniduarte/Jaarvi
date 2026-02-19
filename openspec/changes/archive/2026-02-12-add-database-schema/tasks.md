# Tasks: Database Schema Implementation

## 1. Prisma Schema

- [x] 1.1 Create `prisma/schema.prisma` with generator and datasource configuration
- [x] 1.2 Add Authentication & Identity models (User, UserCredential, Session, RefreshToken, PasswordResetToken, EmailVerificationToken, OAuthAccount)
- [x] 1.3 Add User Preferences model (PreferenceSet)
- [x] 1.4 Add Geography models (Country, City, CityCoverage)
- [x] 1.5 Add Trip Management models (Trip, TripDestination, TripPlanningContext)
- [x] 1.6 Add Itinerary models (ItineraryVersion, ItineraryGeneration, DayPlan, DailyContext)
- [x] 1.7 Add Activity models (ActivityTypeConfig, Activity, ActivityProgress)
- [x] 1.8 Add Execution models (StepGuide, StepGuideStep, Reminder, Incident)
- [x] 1.9 Add Document models (Document, DocumentLink)
- [x] 1.10 Add Playbook models (PlaybookPack, PlaybookEntry, LocalAdvicePack)
- [x] 1.11 Add Affiliate models (Recommendation, AffiliateClick)
- [x] 1.12 Validate schema with `npx prisma validate`
- [x] 1.13 Format schema with `npx prisma format`

## 2. Dependencies

- [x] 2.1 Install argon2 package for password hashing: `npm install argon2`
- [x] 2.2 Install ts-node for running seed scripts: `npm install -D ts-node`

## 3. Seed Scripts

- [x] 3.1 Create `prisma/seeds/activityTypes.ts` with 5 activity type configurations
- [x] 3.2 Create `prisma/seeds/destinations.ts` with 6 countries, 13 cities, 13 coverage records
- [x] 3.3 Create `prisma/seeds/users.ts` with 3 test users (argon2id hashed passwords)
- [x] 3.4 Create `prisma/seeds/sampleTrips.ts` with 2 sample trips and 6 destinations
- [x] 3.5 Create `prisma/seed.ts` main orchestrator
- [x] 3.6 Add prisma seed configuration to `package.json`

## 4. NPM Scripts

- [x] 4.1 Add `prisma:generate` script
- [x] 4.2 Add `prisma:migrate` script
- [x] 4.3 Add `prisma:migrate:create` script
- [x] 4.4 Add `prisma:migrate:deploy` script
- [x] 4.5 Add `prisma:studio` script
- [x] 4.6 Add `prisma:format` script
- [x] 4.7 Add `seed` script
- [x] 4.8 Add individual seed scripts (seed:activity-types, seed:destinations, seed:users, seed:trips)

## 5. Verification

- [x] 5.1 Run `npx prisma generate` to create client
- [x] 5.2 Create initial migration and apply it (used `prisma migrate diff` + `prisma migrate deploy` due to port-forward instability with `migrate dev`)
- [x] 5.3 Run `npm run seed` to populate database
- [x] 5.4 Verify with `npx prisma studio` that all tables and data are present
- [x] 5.5 Execute verification queries from database-init.md

## 6. Documentation

- [x] 6.1 Update backend README with database setup instructions
- [x] 6.2 Update .env.example with DATABASE_URL format

## Dependencies

- Tasks 1.x can be parallelized
- Task 2.x must complete before 3.x
- Task 3.5 depends on 3.1-3.4
- Task 5.x requires a running PostgreSQL instance
- Task 6.x can be done in parallel with 5.x

## Notes

- Tasks 5.2-5.5 require a PostgreSQL database to be running and accessible
- Restart the dev server after running `prisma generate` to pick up the new client
