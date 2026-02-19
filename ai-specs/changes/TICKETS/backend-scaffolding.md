# Backend Scaffolding - Jaarvi

## Ticket Info

| Field | Value |
|-------|-------|
| **Type** | Task |
| **Priority** | High |
| **Depends On** | None |
| **Blocks** | `TICKETS/database-init.md`, All backend feature tickets |

---

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
- All configuration files (TypeScript, Jest, ESLint, Prettier)
- Express application with middleware stack
- Health check endpoint to verify the setup
- Prisma client singleton (schema created in separate ticket)
- Test utilities and mocks

**Note**: Database schema, migrations, and seed data are handled in a separate ticket: `TICKETS/database-init.md`

---

## API Endpoints

### Health Check

| Method | Endpoint      | Description                    | Auth Required |
|--------|---------------|--------------------------------|---------------|
| GET    | `/api/health` | Returns server health status   | No            |

#### Request

```http
GET /api/health HTTP/1.1
Host: ${SERVER_HOST}:3000
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

## Project Structure

```
backend/
├── src/
│   ├── domain/
│   │   ├── models/
│   │   │   └── index.ts
│   │   ├── repositories/
│   │   │   └── index.ts
│   │   └── errors/
│   │       └── index.ts
│   ├── application/
│   │   ├── services/
│   │   │   └── healthService.ts
│   │   └── validator.ts
│   ├── presentation/
│   │   └── controllers/
│   │       └── healthController.ts
│   ├── infrastructure/
│   │   ├── prismaClient.ts
│   │   ├── logger.ts
│   │   ├── config.ts
│   │   └── env.ts
│   ├── routes/
│   │   ├── index.ts
│   │   └── healthRoutes.ts
│   ├── middleware/
│   │   ├── errorHandler.ts
│   │   ├── requestLogger.ts
│   │   ├── corsMiddleware.ts
│   │   └── prismaMiddleware.ts
│   └── index.ts
├── prisma/
│   └── .gitkeep              # Placeholder for schema (created in database-init)
├── test-utils/
│   ├── builders/
│   │   └── index.ts
│   └── mocks/
│       ├── prisma.ts
│       └── index.ts
├── __tests__/
│   ├── presentation/
│   │   └── controllers/
│   │       └── healthController.test.ts
│   ├── application/
│   │   └── services/
│   │       └── healthService.test.ts
│   └── infrastructure/
│       └── env.test.ts
├── package.json
├── package-lock.json
├── tsconfig.json
├── jest.config.js
├── .eslintrc.js
├── .prettierrc
├── .env.example
├── .gitignore
└── README.md
```

---

## Files to Create

### Project Root (`backend/`)

| File | Purpose |
|------|---------|
| `package.json` | Dependencies and npm scripts |
| `tsconfig.json` | TypeScript configuration (strict mode) |
| `jest.config.js` | Jest testing configuration |
| `.eslintrc.js` | ESLint rules and configuration |
| `.prettierrc` | Code formatting rules |
| `.env.example` | Environment variables template |
| `.gitignore` | Git ignore patterns |
| `README.md` | Project documentation |

### Source Code (`backend/src/`)

#### Entry Points (1 file)

| File | Purpose |
|------|---------|
| `src/index.ts` | Express application entry point |

#### Infrastructure Layer (4 files)

| File | Purpose |
|------|---------|
| `src/infrastructure/prismaClient.ts` | Prisma client singleton |
| `src/infrastructure/logger.ts` | Winston logger configuration |
| `src/infrastructure/config.ts` | Typed configuration accessor |
| `src/infrastructure/env.ts` | Environment validation at startup |

#### Presentation Layer (1 file)

| File | Purpose |
|------|---------|
| `src/presentation/controllers/healthController.ts` | Health check controller |

#### Application Layer (2 files)

| File | Purpose |
|------|---------|
| `src/application/services/healthService.ts` | Health check service |
| `src/application/validator.ts` | Input validation utilities |

#### Domain Layer (3 files)

| File | Purpose |
|------|---------|
| `src/domain/models/index.ts` | Domain models barrel export |
| `src/domain/repositories/index.ts` | Repository interfaces barrel export |
| `src/domain/errors/index.ts` | Custom error classes (NotFoundError, ValidationError, etc.) |

#### Routes (2 files)

| File | Purpose |
|------|---------|
| `src/routes/index.ts` | Main router aggregator |
| `src/routes/healthRoutes.ts` | Health endpoint routes |

#### Middleware (4 files)

| File | Purpose |
|------|---------|
| `src/middleware/errorHandler.ts` | Global error handling middleware |
| `src/middleware/requestLogger.ts` | HTTP request logging middleware |
| `src/middleware/corsMiddleware.ts` | CORS configuration middleware |
| `src/middleware/prismaMiddleware.ts` | Prisma client injection |

### Test Utilities (3 files)

| File | Purpose |
|------|---------|
| `test-utils/builders/index.ts` | Test data builders |
| `test-utils/mocks/prisma.ts` | Prisma client mock |
| `test-utils/mocks/index.ts` | Mock utilities barrel export |

### Tests (3 files)

| File | Purpose |
|------|---------|
| `__tests__/presentation/controllers/healthController.test.ts` | Health controller tests |
| `__tests__/application/services/healthService.test.ts` | Health service tests |
| `__tests__/infrastructure/env.test.ts` | Environment validation tests |

### Total Files: 26 files

---

## Environment Variables

### Required Variables (`.env.example`)

```bash
# ========================================
# Database Configuration
# ========================================
# Note: Replace DB_HOST with your database server IP/hostname
DB_HOST=your-database-host
DB_PORT=5432
DB_NAME=jaarvi_dev
DB_USER=jaarvi_dev
DB_PASSWORD=jaarvi_local_dev_2024
DATABASE_URL="postgresql://${DB_USER}:${DB_PASSWORD}@${DB_HOST}:${DB_PORT}/${DB_NAME}"

# ========================================
# Server Configuration
# ========================================
NODE_ENV=development
PORT=3000
SERVER_HOST=0.0.0.0

# ========================================
# Authentication Configuration
# ========================================
JWT_SECRET="development-secret-min-32-chars-change-in-production"
JWT_ACCESS_EXPIRATION=15m
JWT_REFRESH_EXPIRATION=7d

# ========================================
# CORS Configuration (Mobile App)
# ========================================
# For React Native/Expo mobile apps, CORS is typically not needed since
# mobile apps don't run in a browser. However, we keep these for:
# - Expo development server (exp://, http://localhost:19006)
# - Future web client support
# - API testing tools
ALLOWED_ORIGINS=exp://localhost:19000,http://localhost:19006

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
    "format": "prettier --write \"src/**/*.ts\"",
    "typecheck": "tsc --noEmit"
  }
}
```

**Note**: Database scripts (`prisma:*`, `seed`, `db:*`) are added in `TICKETS/database-init.md`

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
  "dotenv": "^16.x"
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
  "@typescript-eslint/parser": "^6.x",
  "prettier": "^3.x"
}
```

---

## Acceptance Criteria

### AC1: Project Structure
- [ ] Project folder `backend/` is created at workspace root
- [ ] All directories follow the layered architecture pattern
- [ ] All 26 files are created

### AC2: TypeScript Configuration
- [ ] `tsconfig.json` uses strict mode
- [ ] ESLint is configured with TypeScript rules
- [ ] Prettier is configured
- [ ] `npm run build` compiles without errors

### AC3: Express Application
- [ ] Server binds to configured SERVER_HOST and PORT: `npm run dev`
- [ ] CORS is configured for allowed origins
- [ ] Helmet middleware is active for security headers
- [ ] Request logging middleware logs all requests
- [ ] Error handling middleware catches all errors and returns consistent format

### AC4: Health Endpoint
- [ ] `GET /api/health` returns 200 with expected JSON structure
- [ ] Response includes `success`, `message`, `timestamp`, `version`, `environment`
- [ ] Message is "Hola, soy Jaarvi"

### AC5: Infrastructure
- [ ] Prisma client singleton is created (ready for schema)
- [ ] Logger is configured with Winston
- [ ] Config module exports typed environment variables
- [ ] Environment validation runs at startup and fails fast on missing variables

### AC6: Testing
- [ ] Jest is configured with TypeScript support
- [ ] `npm test` runs without configuration errors
- [ ] Health controller tests pass
- [ ] Health service tests pass
- [ ] Environment validation tests pass
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
    it('should include environment from config');
  });
});
```

### Environment Validation Tests (`env.test.ts`)

```typescript
describe('Environment Validation', () => {
  describe('validateEnvironment', () => {
    it('should pass with all required variables');
    it('should throw error when DB_HOST is missing');
    it('should throw error when DB_USER is missing');
    it('should throw error when DB_PASSWORD is missing');
    it('should throw error when JWT_SECRET is missing');
    it('should throw error when JWT_SECRET is too short');
    it('should validate NODE_ENV values');
    it('should use default PORT if not provided');
    it('should use default SERVER_HOST (0.0.0.0) if not provided');
    it('should use default DB_PORT (5432) if not provided');
    it('should use default DB_NAME (jaarvi_dev) if not provided');
    it('should construct DATABASE_URL from DB_* variables');
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
- [ ] Error responses don't leak internal details

### Performance
- [ ] Prisma client is singleton (not recreated per request)
- [ ] Logger uses async transport
- [ ] No blocking operations in request handlers

### Code Quality
- [ ] ESLint passes with zero errors
- [ ] Prettier format is consistent
- [ ] TypeScript strict mode enabled
- [ ] All functions have explicit return types
- [ ] No `any` types in production code
- [ ] All public functions have JSDoc comments

### Documentation
- [ ] README.md includes setup instructions
- [ ] README.md includes available npm scripts
- [ ] README.md includes environment variables list
- [ ] README.md references database-init ticket for DB setup

---

## Implementation Steps

### Phase 1: Project Initialization
1. Create `backend/` directory
2. Initialize npm: `npm init -y`
3. Install all dependencies
4. Configure TypeScript (`tsconfig.json`)
5. Configure ESLint (`.eslintrc.js`)
6. Configure Prettier (`.prettierrc`)
7. Configure Jest (`jest.config.js`)
8. Create `.gitignore`

### Phase 2: Infrastructure Setup
1. Create `src/infrastructure/env.ts` with validation
2. Create `src/infrastructure/config.ts` with typed accessors
3. Create `src/infrastructure/logger.ts` with Winston
4. Create `src/infrastructure/prismaClient.ts` singleton

### Phase 3: Domain Layer
1. Create `src/domain/errors/index.ts` with custom error classes
2. Create `src/domain/models/index.ts` barrel export
3. Create `src/domain/repositories/index.ts` barrel export

### Phase 4: Application Layer
1. Create `src/application/validator.ts`
2. Create `src/application/services/healthService.ts`

### Phase 5: Presentation Layer
1. Create `src/presentation/controllers/healthController.ts`

### Phase 6: Middleware
1. Create `src/middleware/errorHandler.ts`
2. Create `src/middleware/requestLogger.ts`
3. Create `src/middleware/corsMiddleware.ts`
4. Create `src/middleware/prismaMiddleware.ts`

### Phase 7: Routes
1. Create `src/routes/healthRoutes.ts`
2. Create `src/routes/index.ts`

### Phase 8: Entry Point
1. Create `src/index.ts` with Express app

### Phase 9: Test Utilities
1. Create `test-utils/mocks/prisma.ts`
2. Create `test-utils/mocks/index.ts`
3. Create `test-utils/builders/index.ts`

### Phase 10: Tests
1. Create `__tests__/infrastructure/env.test.ts`
2. Create `__tests__/application/services/healthService.test.ts`
3. Create `__tests__/presentation/controllers/healthController.test.ts`

### Phase 11: Documentation & Config
1. Create `.env.example`
2. Create `README.md`

### Phase 12: Verification
1. Run `npm run lint`
2. Run `npm run build`
3. Run `npm test`
4. Run `npm run dev` and test `/api/health`

---

## Definition of Done

- [ ] All 26 files are created
- [ ] `npm install` completes without errors
- [ ] `npm run build` compiles without errors
- [ ] `npm run lint` passes with zero errors
- [ ] `npm test` passes with 90%+ coverage
- [ ] `npm run dev` starts server successfully
- [ ] `GET /api/health` returns expected response
- [ ] README.md is complete with setup instructions
- [ ] No secrets are committed to the repository
- [ ] All JSDoc comments are in place

---

## Next Steps

After completing this ticket, proceed with:

1. **`TICKETS/database-init.md`** - Create database schema, migrations, and seed data

---

## References

- `ai-specs/specs/backend-standards.mdc` - Backend development standards
- `product-discovery/6-DataBase.md` - Database schema specification (for database-init ticket)
