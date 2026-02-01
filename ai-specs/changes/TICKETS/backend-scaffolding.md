# Backend Scaffolding - Jaarvi

## [Original]

> Prompt optimizado para crear el scaffolding del backend de Jaarvi siguiendo los estándares definidos en `backend-standards.mdc`.

---

### Role

You are an expert Node.js/TypeScript backend developer specialized in building enterprise-grade applications following Domain-Driven Design (DDD), SOLID principles, and layered architecture patterns.

### Context

You are building the backend scaffolding for **Jaarvi**, a travel planning assistant application. The project must follow the standards defined in `ai-specs/specs/backend-standards.mdc` which mandates:

- **Stack**: Node.js + TypeScript + Express.js + Prisma ORM + PostgreSQL
- **Architecture**: Layered DDD (Presentation → Application → Domain → Infrastructure)
- **Testing**: Jest with 90% coverage threshold
- **Deployment**: Serverless Framework (AWS Lambda compatible)
- **Code Quality**: ESLint, strict TypeScript mode

### Objective

Create the complete scaffolding for the backend application `backend`.

---

## [Enhanced]

### User Story

**As a** development team member  
**I want** a fully configured backend scaffolding for Jaarvi  
**So that** I can immediately start developing features following established standards and best practices

### Description

This task creates the foundational backend infrastructure for the Jaarvi travel planning application. The scaffolding includes:

- Complete project structure following layered DDD architecture
- All configuration files (TypeScript, Jest, ESLint, Serverless)
- Prisma ORM setup with complete database schema
- Seed data for development and testing
- Health check endpoint to verify the setup
- Docker configuration for local PostgreSQL

---

## API Endpoints

### Health Check

| Method | Endpoint      | Description                    | Auth Required |
|--------|---------------|--------------------------------|---------------|
| GET    | `/api/health` | Returns server health status   | No            |

#### Request

```http
GET /api/health HTTP/1.1
Host: localhost:3000
```

#### Response (200 OK)

```json
{
  "success": true,
  "message": "Hola, soy Jaarvi",
  "timestamp": "2026-01-31T12:00:00.000Z",
  "version": "1.0.0",
  "environment": "development"
}
```

#### Response (500 Internal Server Error)

```json
{
  "success": false,
  "error": {
    "message": "Health check failed",
    "code": "HEALTH_CHECK_ERROR"
  }
}
```

---

## Files to Create

### Project Root (`backend/`)

| File | Purpose |
|------|---------|
| `package.json` | Dependencies and npm scripts |
| `package-lock.json` | Lock file (generated) |
| `tsconfig.json` | TypeScript configuration (strict mode) |
| `jest.config.js` | Jest testing configuration |
| `.eslintrc.js` | ESLint rules and configuration |
| `.prettierrc` | Code formatting rules |
| `serverless.yml` | AWS Lambda deployment config |
| `.env.example` | Environment variables template |
| `.gitignore` | Git ignore patterns |
| `docker-compose.yml` | Local PostgreSQL container |
| `README.md` | Project documentation |

### Source Code (`backend/src/`)

#### Entry Points

| File | Purpose |
|------|---------|
| `src/index.ts` | Express application entry point |
| `src/lambda.ts` | AWS Lambda handler wrapper |

#### Infrastructure Layer (`src/infrastructure/`)

| File | Purpose |
|------|---------|
| `src/infrastructure/prismaClient.ts` | Prisma client singleton |
| `src/infrastructure/logger.ts` | Winston logger configuration |
| `src/infrastructure/config.ts` | Typed configuration accessor |
| `src/infrastructure/env.ts` | Environment validation at startup |

#### Presentation Layer (`src/presentation/`)

| File | Purpose |
|------|---------|
| `src/presentation/controllers/healthController.ts` | Health check controller |

#### Application Layer (`src/application/`)

| File | Purpose |
|------|---------|
| `src/application/services/healthService.ts` | Health check service |
| `src/application/validator.ts` | Input validation utilities |

#### Domain Layer (`src/domain/`)

| File | Purpose |
|------|---------|
| `src/domain/models/index.ts` | Domain models barrel export |
| `src/domain/repositories/index.ts` | Repository interfaces barrel export |
| `src/domain/errors/index.ts` | Custom error classes |

#### Routes (`src/routes/`)

| File | Purpose |
|------|---------|
| `src/routes/index.ts` | Main router aggregator |
| `src/routes/healthRoutes.ts` | Health endpoint routes |

#### Middleware (`src/middleware/`)

| File | Purpose |
|------|---------|
| `src/middleware/errorHandler.ts` | Global error handling middleware |
| `src/middleware/requestLogger.ts` | HTTP request logging middleware |
| `src/middleware/corsMiddleware.ts` | CORS configuration middleware |
| `src/middleware/prismaMiddleware.ts` | Prisma client injection |

### Prisma (`backend/prisma/`)

| File | Purpose |
|------|---------|
| `prisma/schema.prisma` | Complete database schema |
| `prisma/seed.ts` | Main seed orchestrator |
| `prisma/seeds/activityTypes.ts` | Activity type configuration seeds |
| `prisma/seeds/destinations.ts` | Countries and cities seeds |
| `prisma/seeds/users.ts` | Test user seeds (dev only) |
| `prisma/seeds/sampleTrips.ts` | Sample trip seeds (dev only) |

### Test Utilities (`backend/test-utils/`)

| File | Purpose |
|------|---------|
| `test-utils/builders/index.ts` | Test data builders |
| `test-utils/mocks/prisma.ts` | Prisma client mock |
| `test-utils/mocks/index.ts` | Mock utilities barrel export |

### Tests (`backend/__tests__/`)

| File | Purpose |
|------|---------|
| `__tests__/presentation/controllers/healthController.test.ts` | Health controller tests |
| `__tests__/application/services/healthService.test.ts` | Health service tests |
| `__tests__/infrastructure/env.test.ts` | Environment validation tests |

---

## Prisma Schema Models

Complete list of models to implement in `schema.prisma`:

### Authentication & Identity
- `User`
- `UserCredential`
- `Session`
- `RefreshToken`
- `PasswordResetToken`
- `EmailVerificationToken`
- `OAuthAccount`

### User Preferences
- `PreferenceSet`

### Trip Management
- `Trip`
- `TripDestination`
- `TripPlanningContext`

### Geography
- `Country`
- `City`
- `CityCoverage`

### Itinerary
- `ItineraryVersion`
- `ItineraryGeneration`
- `DayPlan`
- `DailyContext`

### Activities
- `ActivityTypeConfig`
- `Activity`
- `ActivityProgress`

### Execution
- `StepGuide`
- `StepGuideStep`
- `Reminder`
- `Incident`

### Documents
- `Document`
- `DocumentLink`

### Playbooks
- `PlaybookPack`
- `PlaybookEntry`
- `LocalAdvicePack`

### Affiliates
- `Recommendation`
- `AffiliateClick`

---

## Seed Data Requirements

### 1. ActivityTypeConfig (Required - System Configuration)

| key        | displayName      | isMandatoryDaily | requiresEvidence | allowNotNeeded |
|------------|------------------|------------------|------------------|----------------|
| sleep      | Accommodation    | true             | true             | false          |
| transfer   | Transportation   | false            | false            | true           |
| visit      | Visit/Attraction | false            | false            | true           |
| meal       | Meal             | false            | false            | true           |
| free_time  | Free Time        | false            | false            | true           |

### 2. Countries & Cities with Coverage (Required - MVP Destinations)

| Country        | City          | Timezone            | Coverage Level |
|----------------|---------------|---------------------|----------------|
| France (FR)    | Paris         | Europe/Paris        | high           |
| France (FR)    | Lyon          | Europe/Paris        | medium         |
| Italy (IT)     | Rome          | Europe/Rome         | high           |
| Italy (IT)     | Florence      | Europe/Rome         | high           |
| Italy (IT)     | Venice        | Europe/Rome         | medium         |
| Spain (ES)     | Barcelona     | Europe/Madrid       | high           |
| Spain (ES)     | Madrid        | Europe/Madrid       | high           |
| Japan (JP)     | Tokyo         | Asia/Tokyo          | medium         |
| Japan (JP)     | Kyoto         | Asia/Tokyo          | medium         |
| Japan (JP)     | Osaka         | Asia/Tokyo          | medium         |
| UK (GB)        | London        | Europe/London       | high           |
| USA (US)       | New York      | America/New_York    | high           |
| USA (US)       | San Francisco | America/Los_Angeles | medium         |

### 3. Test Users (Development Only)

| Email              | Display Name | Password (plain) | Status |
|--------------------|--------------|------------------|--------|
| test@jaarvi.app    | Test User    | TestPassword123  | active |
| demo@jaarvi.app    | Demo User    | DemoPassword123  | active |
| admin@jaarvi.app   | Admin User   | AdminPassword123 | active |

### 4. Sample Trips (Development Only)

| Trip Name               | Owner Email         | Destinations                          |
|-------------------------|---------------------|---------------------------------------|
| France & Italy Adventure| test@jaarvi.app     | Paris (5d) → Rome (4d) → Florence (4d)|
| Japan Discovery         | demo@jaarvi.app     | Tokyo (7d) → Kyoto (4d) → Osaka (3d)  |

---

## Environment Variables

### Required Variables (`.env.example`)

```bash
# ========================================
# Database Configuration
# ========================================
DATABASE_URL="postgresql://jaarvi_dev:jaarvi_local_dev_2024@localhost:5432/jaarvi_dev"

# ========================================
# Server Configuration
# ========================================
NODE_ENV=development
PORT=3000

# ========================================
# Authentication Configuration
# ========================================
JWT_SECRET="development-secret-min-32-chars-change-in-production"
JWT_ACCESS_EXPIRATION=15m
JWT_REFRESH_EXPIRATION=7d

# ========================================
# CORS Configuration
# ========================================
FRONTEND_URL=http://localhost:3001
ALLOWED_ORIGINS=http://localhost:3001,http://localhost:19006

# ========================================
# Logging Configuration
# ========================================
LOG_LEVEL=debug
```

---

## NPM Scripts

```json
{
  "scripts": {
    "dev": "ts-node-dev --respawn --transpile-only src/index.ts",
    "build": "tsc",
    "start": "node dist/index.js",
    "test": "jest",
    "test:watch": "jest --watch",
    "test:coverage": "jest --coverage",
    "lint": "eslint src --ext .ts",
    "lint:fix": "eslint src --ext .ts --fix",
    "prisma:generate": "prisma generate",
    "prisma:migrate": "prisma migrate dev",
    "prisma:migrate:prod": "prisma migrate deploy",
    "seed": "ts-node prisma/seed.ts",
    "seed:activity-types": "ts-node prisma/seeds/activityTypes.ts",
    "seed:destinations": "ts-node prisma/seeds/destinations.ts",
    "seed:test-users": "ts-node prisma/seeds/users.ts",
    "seed:sample-trips": "ts-node prisma/seeds/sampleTrips.ts"
  }
}
```

---

## Dependencies

### Production Dependencies

```json
{
  "express": "^4.18.2",
  "@prisma/client": "^5.x",
  "cors": "^2.8.5",
  "helmet": "^7.x",
  "winston": "^3.11.0",
  "argon2": "^0.31.x",
  "jsonwebtoken": "^9.x",
  "dotenv": "^16.x",
  "serverless-http": "^3.x"
}
```

### Development Dependencies

```json
{
  "typescript": "^5.x",
  "ts-node": "^10.x",
  "ts-node-dev": "^2.x",
  "prisma": "^5.x",
  "@types/express": "^4.x",
  "@types/cors": "^2.x",
  "@types/node": "^20.x",
  "@types/jsonwebtoken": "^9.x",
  "jest": "^29.x",
  "ts-jest": "^29.x",
  "@types/jest": "^29.x",
  "eslint": "^8.x",
  "@typescript-eslint/eslint-plugin": "^6.x",
  "@typescript-eslint/parser": "^6.x"
}
```

---

## Acceptance Criteria

### AC1: Project Structure
- [ ] Project folder `backend/` is created at workspace root
- [ ] All directories follow the layered architecture pattern
- [ ] All configuration files are present and valid

### AC2: TypeScript Configuration
- [ ] `tsconfig.json` uses strict mode
- [ ] ESLint is configured with TypeScript rules
- [ ] Build completes without errors: `npm run build`

### AC3: Express Application
- [ ] Server starts on configured PORT: `npm run dev`
- [ ] CORS is configured for allowed origins
- [ ] Request logging middleware is active
- [ ] Error handling middleware catches all errors

### AC4: Health Endpoint
- [ ] `GET /api/health` returns 200 with expected JSON structure
- [ ] Response includes `success`, `message`, `timestamp`
- [ ] Message is "Hola, soy Jaarvi"

### AC5: Prisma Setup
- [ ] `schema.prisma` contains all 27 models
- [ ] `npx prisma generate` completes successfully
- [ ] `npx prisma migrate dev --name init` creates migration

### AC6: Database Seeding
- [ ] `npm run seed` executes without errors
- [ ] ActivityTypeConfig table has 5 records
- [ ] Country table has 6 records
- [ ] City table has 13 records
- [ ] CityCoverage table has 13 records
- [ ] User table has 3 test users (in development)

### AC7: Docker Configuration
- [ ] `docker-compose.yml` defines PostgreSQL service
- [ ] `docker-compose up -d` starts database container
- [ ] Database is accessible on localhost:5432

### AC8: Testing
- [ ] Jest is configured with TypeScript support
- [ ] `npm test` runs without configuration errors
- [ ] Health controller has unit tests
- [ ] Coverage threshold is set to 90%

---

## Unit Tests Required

### Health Controller Tests (`healthController.test.ts`)

```typescript
describe('HealthController', () => {
  describe('GET /api/health', () => {
    it('should return 200 with success response');
    it('should include message "Hola, soy Jaarvi"');
    it('should include valid ISO timestamp');
    it('should include version and environment');
  });
});
```

### Health Service Tests (`healthService.test.ts`)

```typescript
describe('HealthService', () => {
  describe('getHealthStatus', () => {
    it('should return health status object');
    it('should include current timestamp');
    it('should read version from package.json');
  });
});
```

### Environment Validation Tests (`env.test.ts`)

```typescript
describe('Environment Validation', () => {
  describe('validateEnvironment', () => {
    it('should pass with all required variables');
    it('should throw error when DATABASE_URL is missing');
    it('should throw error when JWT_SECRET is too short');
    it('should validate NODE_ENV values');
  });
});
```

---

## Non-Functional Requirements

### Security
- [ ] Environment variables validated at startup
- [ ] No secrets committed to repository
- [ ] Helmet middleware for security headers
- [ ] CORS restricted to allowed origins only
- [ ] Password hashing uses argon2id

### Performance
- [ ] Prisma client is singleton (not recreated per request)
- [ ] Logger uses async transport
- [ ] No blocking operations in request handlers

### Code Quality
- [ ] ESLint passes with zero errors
- [ ] TypeScript strict mode enabled
- [ ] All functions have explicit return types
- [ ] No `any` types in production code

### Documentation
- [ ] README.md includes setup instructions
- [ ] README.md includes available npm scripts
- [ ] README.md includes environment variables list
- [ ] All public functions have JSDoc comments

---

## Implementation Steps

### Phase 1: Project Initialization
1. Create `backend/` directory
2. Initialize npm: `npm init -y`
3. Install all dependencies
4. Configure TypeScript (`tsconfig.json`)
5. Configure ESLint (`.eslintrc.js`)
6. Configure Jest (`jest.config.js`)

### Phase 2: Infrastructure Setup
1. Create `src/infrastructure/env.ts` with validation
2. Create `src/infrastructure/config.ts` with typed accessors
3. Create `src/infrastructure/logger.ts` with Winston
4. Create `src/infrastructure/prismaClient.ts` singleton

### Phase 3: Express Application
1. Create `src/index.ts` with Express app
2. Create middleware (error handler, CORS, logging, Prisma)
3. Create routes structure
4. Create `src/lambda.ts` for serverless

### Phase 4: Health Endpoint
1. Create `src/application/services/healthService.ts`
2. Create `src/presentation/controllers/healthController.ts`
3. Create `src/routes/healthRoutes.ts`
4. Write unit tests for health endpoint

### Phase 5: Prisma & Database
1. Create `prisma/schema.prisma` with all models
2. Create `docker-compose.yml` for PostgreSQL
3. Run `docker-compose up -d`
4. Run `npx prisma migrate dev --name init`

### Phase 6: Seed Data
1. Create `prisma/seeds/activityTypes.ts`
2. Create `prisma/seeds/destinations.ts`
3. Create `prisma/seeds/users.ts`
4. Create `prisma/seeds/sampleTrips.ts`
5. Create `prisma/seed.ts` orchestrator
6. Run `npm run seed`

### Phase 7: Deployment Configuration
1. Create `serverless.yml`
2. Create `.env.example`
3. Update `.gitignore`
4. Create `README.md`

### Phase 8: Verification
1. Run `npm run lint`
2. Run `npm run build`
3. Run `npm test`
4. Run `npm run dev` and test `/api/health`

---

## Definition of Done

- [ ] All files listed in "Files to Create" section exist
- [ ] All acceptance criteria are met
- [ ] All unit tests pass with 90%+ coverage
- [ ] ESLint reports zero errors
- [ ] TypeScript compiles without errors
- [ ] Docker PostgreSQL container starts successfully
- [ ] Database migrations apply successfully
- [ ] Seed data populates correctly
- [ ] Health endpoint returns expected response
- [ ] README.md is complete with setup instructions
- [ ] No secrets are committed to the repository

---

## References

- `ai-specs/specs/backend-standards.mdc` - Backend development standards
- `product-discovery/6-DataBase.md` - Database schema specification
