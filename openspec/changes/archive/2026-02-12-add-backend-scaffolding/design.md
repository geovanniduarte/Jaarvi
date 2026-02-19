# Design: Backend Scaffolding

## Context

Jaarvi is a travel planning assistant mobile application that requires a robust backend API. The backend must follow Domain-Driven Design (DDD) principles with a layered architecture to ensure maintainability, testability, and scalability.

**Stakeholders**: Development team, future API consumers (mobile app)

**Constraints**:
- Must follow standards in `backend-standards.mdc`
- Backend will be deployed to a remote server (not localhost)
- Frontend is a React Native/Expo mobile app (no web frontend)
- 90% test coverage threshold required

## Goals / Non-Goals

### Goals
- Establish a production-ready project structure following layered DDD
- Configure all development tooling (TypeScript strict mode, ESLint, Prettier, Jest)
- Create a working Express application with essential middleware
- Provide a health check endpoint to verify deployment
- Set up flexible configuration for different deployment environments
- Enable immediate feature development after scaffolding

### Non-Goals
- Database schema definition (handled in `database-init` ticket)
- Authentication implementation (separate ticket)
- Business domain features (separate tickets)
- Production deployment configuration (future ticket)

## Decisions

### Decision 1: Layered DDD Architecture

**Choice**: 4-layer architecture (Presentation → Application → Domain → Infrastructure)

**Rationale**:
- Clear separation of concerns
- Domain layer remains pure and testable
- Infrastructure details isolated from business logic
- Follows team's established patterns in `backend-standards.mdc`

**Alternatives considered**:
- Hexagonal/Ports & Adapters: More complex, deferred to future if needed
- Simple MVC: Insufficient separation for complex domain

### Decision 2: Environment Variable Strategy

**Choice**: Individual DB_* variables that construct DATABASE_URL

**Rationale**:
- Flexible for different deployment environments
- Secrets can be managed separately (DB_PASSWORD)
- Easy to override individual values without changing entire URL
- SERVER_HOST defaults to `0.0.0.0` for container/remote deployment

**Structure**:
```
DB_HOST=<server-ip>
DB_PORT=5432 (default)
DB_NAME=jaarvi_dev (default)
DB_USER=<required>
DB_PASSWORD=<required>
DATABASE_URL=postgresql://${DB_USER}:${DB_PASSWORD}@${DB_HOST}:${DB_PORT}/${DB_NAME}
```

### Decision 3: CORS Configuration for Mobile App

**Choice**: Configure ALLOWED_ORIGINS for Expo development only

**Rationale**:
- Mobile apps don't have browser CORS restrictions
- Expo development server needs CORS for debugging
- No web frontend currently planned
- Configuration remains flexible for future web support

### Decision 4: Prisma Client as Singleton

**Choice**: Single Prisma client instance injected via middleware

**Rationale**:
- Efficient connection pooling
- Consistent across all request handlers
- Easy to mock in tests
- Follows dependency injection pattern

## Risks / Trade-offs

| Risk | Mitigation |
|------|------------|
| Over-engineering for MVP | Keep layers minimal, add complexity only when needed |
| Configuration drift between environments | Environment validation fails fast on startup |
| Missing database at startup | Prisma client handles connection lazily |

## Migration Plan

Not applicable - this is greenfield development.

## Open Questions

1. **Kubernetes deployment**: Will be handled separately with deploy skill
2. **CI/CD pipeline**: Not in scope for this ticket
3. **OAuth providers**: Configuration prepared but implementation in separate ticket
