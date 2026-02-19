# Jaarvi Backend

Backend API for the Jaarvi travel planning assistant application.

## Technology Stack

- **Runtime**: Node.js 18+
- **Language**: TypeScript (strict mode)
- **Framework**: Express.js
- **Database**: PostgreSQL with Prisma ORM
- **Testing**: Jest with 90% coverage threshold
- **Code Quality**: ESLint + Prettier

## Architecture

The backend follows a layered Domain-Driven Design (DDD) architecture:

```
src/
├── domain/          # Core business logic (models, errors, repository interfaces)
├── application/     # Use cases and services
├── presentation/    # HTTP controllers
├── infrastructure/  # External concerns (database, logging, config)
├── middleware/      # Express middleware
├── routes/          # Route definitions
└── index.ts         # Application entry point
```

## Getting Started

### Prerequisites

- Node.js 18 or higher
- PostgreSQL database (see [database-init ticket](../ai-specs/changes/TICKETS/database-init.md))

### Installation

```bash
# Install dependencies
npm install

# Copy environment template
cp .env.example .env

# Edit .env with your configuration
# At minimum, set:
# - DB_HOST (your database server IP)
# - DB_USER
# - DB_PASSWORD
# - JWT_SECRET (at least 32 characters)
```

### Development

```bash
# Start development server with hot reload
npm run dev

# The server will start on http://{SERVER_HOST}:{PORT}
# Default: http://0.0.0.0:3000
```

### Verify Installation

```bash
# Check health endpoint
curl http://localhost:3000/api/health

# Expected response:
# {
#   "success": true,
#   "message": "Hola, soy Jaarvi",
#   "timestamp": "2026-01-31T12:00:00.000Z",
#   "version": "1.0.0",
#   "environment": "development"
# }
```

## NPM Scripts

### Development

| Script | Description |
|--------|-------------|
| `npm run dev` | Start development server with hot reload |
| `npm run build` | Compile TypeScript to JavaScript |
| `npm start` | Run compiled production server |
| `npm test` | Run tests |
| `npm run test:watch` | Run tests in watch mode |
| `npm run test:coverage` | Run tests with coverage report |
| `npm run lint` | Check code with ESLint |
| `npm run lint:fix` | Fix ESLint issues automatically |
| `npm run format` | Format code with Prettier |
| `npm run typecheck` | Check types without compiling |

### Database (Prisma)

| Script | Description |
|--------|-------------|
| `npm run prisma:generate` | Generate Prisma client from schema |
| `npm run prisma:migrate` | Create and apply migrations (dev) |
| `npm run prisma:migrate:create` | Create migration without applying |
| `npm run prisma:migrate:deploy` | Apply migrations (production) |
| `npm run prisma:studio` | Open Prisma Studio GUI |
| `npm run prisma:format` | Format schema file |

### Seeding

| Script | Description |
|--------|-------------|
| `npm run seed` | Run all seeds |
| `npm run seed:activity-types` | Seed activity type configurations |
| `npm run seed:destinations` | Seed countries and cities |
| `npm run seed:users` | Seed test users (dev only) |
| `npm run seed:trips` | Seed sample trips (dev only) |

## Environment Variables

| Variable | Required | Default | Description |
|----------|----------|---------|-------------|
| `DB_HOST` | Yes | - | Database server hostname/IP |
| `DB_PORT` | No | `5432` | Database port |
| `DB_NAME` | No | `jaarvi_dev` | Database name |
| `DB_USER` | Yes | - | Database username |
| `DB_PASSWORD` | Yes | - | Database password |
| `NODE_ENV` | No | `development` | Environment (development/staging/production/test) |
| `PORT` | No | `3000` | Server port |
| `SERVER_HOST` | No | `0.0.0.0` | Server bind address |
| `JWT_SECRET` | Yes | - | JWT signing secret (min 32 chars) |
| `JWT_ACCESS_EXPIRATION` | No | `15m` | Access token expiration |
| `JWT_REFRESH_EXPIRATION` | No | `7d` | Refresh token expiration |
| `ALLOWED_ORIGINS` | No | - | Comma-separated CORS origins |
| `LOG_LEVEL` | No | `info` | Logging level (error/warn/info/http/debug) |

## API Endpoints

### Health Check

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/health` | Server health status | No |

## Project Structure

```
backend/
├── src/
│   ├── domain/
│   │   ├── models/          # Domain entities
│   │   ├── repositories/    # Repository interfaces
│   │   └── errors/          # Domain errors
│   ├── application/
│   │   ├── services/        # Business logic
│   │   └── validator.ts     # Input validation
│   ├── presentation/
│   │   └── controllers/     # HTTP handlers
│   ├── infrastructure/
│   │   ├── config.ts        # Configuration
│   │   ├── env.ts           # Environment validation
│   │   ├── logger.ts        # Winston logger
│   │   └── prismaClient.ts  # Database client
│   ├── middleware/          # Express middleware
│   ├── routes/              # Route definitions
│   └── index.ts             # Entry point
├── __tests__/               # Test files
├── test-utils/              # Test utilities
├── prisma/                  # Database schema (see database-init)
└── package.json
```

## Testing

```bash
# Run all tests
npm test

# Run with coverage
npm run test:coverage

# Watch mode
npm run test:watch
```

Coverage threshold: 90% for branches, functions, lines, and statements.

## Database Setup

### Quick Start

```bash
# 1. Configure your database connection in .env
#    Set DB_HOST, DB_USER, DB_PASSWORD

# 2. Generate Prisma client
npm run prisma:generate

# 3. Run migrations to create tables
npm run prisma:migrate

# 4. Seed the database with initial data
npm run seed

# 5. Open Prisma Studio to explore data
npm run prisma:studio
```

### Database Schema

The database includes 32 models organized into 10 functional areas:

| Area | Models | Count |
|------|--------|-------|
| Authentication & Identity | User, UserCredential, Session, RefreshToken, PasswordResetToken, EmailVerificationToken, OAuthAccount | 7 |
| User Preferences | PreferenceSet | 1 |
| Geography | Country, City, CityCoverage | 3 |
| Trip Management | Trip, TripDestination, TripPlanningContext | 3 |
| Itinerary | ItineraryVersion, ItineraryGeneration, DayPlan, DailyContext | 4 |
| Activities | ActivityTypeConfig, Activity, ActivityProgress | 3 |
| Execution | StepGuide, StepGuideStep, Reminder, Incident | 4 |
| Documents | Document, DocumentLink | 2 |
| Playbooks | PlaybookPack, PlaybookEntry, LocalAdvicePack | 3 |
| Affiliates | Recommendation, AffiliateClick | 2 |

### Seed Data

The seed script populates:

- **Activity Types**: 5 types (sleep, transfer, visit, meal, free_time)
- **Destinations**: 6 countries, 13 cities with coverage levels
- **Test Users** (dev only): test@jaarvi.app, demo@jaarvi.app, admin@jaarvi.app
- **Sample Trips** (dev only): 2 trips with destinations

## License

ISC
