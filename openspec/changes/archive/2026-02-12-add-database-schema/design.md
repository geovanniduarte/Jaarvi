# Design: Database Schema Implementation

## Context

Jaarvi is a travel planning application requiring a relational database to store user accounts, trips, itineraries, activities, documents, and AI-generated content. The database design follows the specification in `product-discovery/6-DataBase.md` which defines 32 models organized into 10 functional areas.

**Stakeholders**: Backend developers, AI team (for itinerary generation auditing), mobile team (data sync requirements)

**Constraints**:
- Must use PostgreSQL 15.x as specified
- Must use Prisma ORM as the single source of truth for schema
- Must support future offline sync requirements (Phase 3)
- Authentication must support both email/password and OAuth (Google, Apple)

## Goals / Non-Goals

### Goals
- Implement all 32 models with correct relationships and indexes
- Provide idempotent seed scripts for consistent development environments
- Enable hot queries identified in the database specification (Today Mode, Edit Itinerary, Login)
- Support AI auditability with JSONB snapshots for inputs, rationale, and confidence

### Non-Goals
- Implementing data access repositories (separate ticket)
- Implementing authentication endpoints (separate ticket)
- Setting up PostgreSQL server (handled by deployment infrastructure)
- Implementing soft delete (marked TBD in spec, deferred)

## Decisions

### Decision 1: Model Naming Convention
**What**: Use PascalCase for Prisma models and camelCase for fields, with `@@map()` for snake_case table names.
**Why**: Follows Prisma conventions and backend-standards.mdc while maintaining PostgreSQL-friendly table names.
**Alternatives**: Use snake_case everywhere (rejected: conflicts with TypeScript conventions).

### Decision 2: UUID Primary Keys
**What**: All models use UUID primary keys with `@default(uuid())`.
**Why**: Enables distributed ID generation, better for future sync/offline scenarios, prevents ID guessing attacks.
**Alternatives**: Auto-increment integers (rejected: problematic for sync, security concerns).

### Decision 3: JSONB for AI Snapshots
**What**: Use JSONB type for `inputsSnapshot`, `coverageSnapshot`, `rationale`, `confidence`, `citations` fields.
**Why**: Provides flexibility for AI agent output evolution without schema migrations, supports reproducibility requirements.
**Alternatives**: Separate normalized tables (rejected: over-engineering, schema would change frequently with AI iterations).

### Decision 4: Argon2id for Password Hashing
**What**: Use argon2id algorithm with recommended parameters (64MB memory, 3 iterations).
**Why**: Current best practice for password hashing, resistant to GPU/ASIC attacks.
**Alternatives**: bcrypt (acceptable but argon2id preferred), scrypt (acceptable).

### Decision 5: Seed Data Structure
**What**: Separate seed files per domain area, orchestrated by main `seed.ts`.
**Why**: Enables running individual seeds, easier maintenance, clear separation of concerns.
**Alternatives**: Single monolithic seed file (rejected: harder to maintain and debug).

## Risks / Trade-offs

| Risk | Mitigation |
|------|------------|
| Schema changes require migrations | Use descriptive migration names, review generated SQL before applying |
| JSONB fields lack type safety | Define TypeScript interfaces for JSONB structures, validate at application layer |
| Seed data outdated | Include seed verification queries in acceptance criteria |
| argon2 native dependencies | Document build requirements, provide fallback instructions |

## Migration Plan

### Phase 1: Schema Creation (this proposal)
1. Create `prisma/schema.prisma` with all 32 models
2. Validate schema with `npx prisma validate`
3. Generate Prisma client

### Phase 2: Initial Migration
1. Configure DATABASE_URL in environment
2. Run `npx prisma migrate dev --name init`
3. Review generated SQL

### Phase 3: Seed Data
1. Create seed scripts in `prisma/seeds/`
2. Run `npm run seed`
3. Verify with Prisma Studio

### Rollback
- Migration can be rolled back with `npx prisma migrate reset` (destroys data)
- For production: Prepare rollback SQL scripts before applying

## Open Questions

1. **Soft delete**: Should we add `deletedAt` columns to Trip, Document, ItineraryVersion? (Deferred per spec TBD)
2. **Partitioning**: When should we consider partitioning AffiliateClick table? (Monitor volume first)
3. **Token encryption**: What encryption algorithm for OAuth token storage? (TBD, not MVP blocker)
