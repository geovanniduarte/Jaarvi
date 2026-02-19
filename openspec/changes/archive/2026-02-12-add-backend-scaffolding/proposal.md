# Change: Add Backend Scaffolding for Jaarvi

## Why

Jaarvi requires a foundational backend infrastructure to enable feature development. Currently, there is no backend codebase. This scaffolding establishes the project structure, tooling, and conventions required for the development team to immediately start building features following DDD architecture and established best practices.

## What Changes

- **NEW**: Create `backend/` directory with complete layered DDD architecture
- **NEW**: Configure TypeScript, ESLint, Prettier, and Jest with project standards
- **NEW**: Implement Express.js application with middleware stack (CORS, Helmet, logging, error handling)
- **NEW**: Create health check endpoint (`GET /api/health`) as verification of setup
- **NEW**: Add Prisma client singleton (ready for schema defined in database-init ticket)
- **NEW**: Set up infrastructure layer with environment validation, typed config, and Winston logger
- **NEW**: Create test utilities (mocks, builders) and initial test suite
- **NEW**: Configure environment variables for flexible deployment (SERVER_HOST, DB_* variables)

## Impact

- Affected specs: `backend-infrastructure` (new capability)
- Affected code: Creates new `backend/` directory at workspace root (26 files)
- Blocks: All backend feature tickets depend on this scaffolding
- Dependencies: None (first backend ticket)

## References

- Source ticket: `ai-specs/changes/TICKETS/backend-scaffolding.md`
- Standards: `ai-specs/specs/backend-standards.mdc`
- Database ticket: `ai-specs/changes/TICKETS/database-init.md` (follows this)
