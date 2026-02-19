# Backend Infrastructure Specification

## ADDED Requirements

### Requirement: Express Application Server

The backend system SHALL provide an Express.js application server that binds to a configurable host and port for remote deployment.

#### Scenario: Server starts successfully

- **GIVEN** valid environment configuration (SERVER_HOST, PORT)
- **WHEN** the application starts with `npm run dev`
- **THEN** the server binds to the configured SERVER_HOST and PORT
- **AND** logs a startup message indicating the server is ready

#### Scenario: Server binds to all interfaces by default

- **GIVEN** SERVER_HOST is not configured
- **WHEN** the application starts
- **THEN** the server defaults to binding on `0.0.0.0` (all interfaces)
- **AND** the server is accessible from external network addresses

---

### Requirement: Health Check Endpoint

The backend system SHALL provide a health check endpoint at `GET /api/health` that returns server status information.

#### Scenario: Health check returns success

- **GIVEN** the server is running
- **WHEN** a GET request is made to `/api/health`
- **THEN** the response status is 200
- **AND** the response body contains:
  - `success`: true
  - `message`: "Hola, soy Jaarvi"
  - `timestamp`: valid ISO 8601 timestamp
  - `version`: application version from package.json
  - `environment`: current NODE_ENV value

#### Scenario: Health check error response

- **GIVEN** the server encounters an internal error
- **WHEN** a GET request is made to `/api/health`
- **THEN** the response status is 500
- **AND** the response body contains:
  - `success`: false
  - `error.message`: "Health check failed"
  - `error.code`: "HEALTH_CHECK_ERROR"

---

### Requirement: Environment Variable Validation

The backend system SHALL validate all required environment variables at startup and fail fast if configuration is invalid.

#### Scenario: Successful validation with all required variables

- **GIVEN** all required environment variables are set (DB_HOST, DB_USER, DB_PASSWORD, JWT_SECRET)
- **WHEN** the application starts
- **THEN** validation passes
- **AND** the application continues startup

#### Scenario: Missing required database host

- **GIVEN** DB_HOST environment variable is not set
- **WHEN** the application starts
- **THEN** an error is thrown with message indicating DB_HOST is missing
- **AND** the application exits with non-zero status

#### Scenario: JWT secret too short

- **GIVEN** JWT_SECRET is set but less than 32 characters
- **WHEN** the application starts
- **THEN** an error is thrown indicating JWT_SECRET must be at least 32 characters
- **AND** the application exits with non-zero status

#### Scenario: Default values applied

- **GIVEN** optional environment variables are not set (PORT, SERVER_HOST, DB_PORT, DB_NAME)
- **WHEN** the application starts
- **THEN** default values are applied:
  - PORT: 3000
  - SERVER_HOST: 0.0.0.0
  - DB_PORT: 5432
  - DB_NAME: jaarvi_dev

#### Scenario: DATABASE_URL constructed from components

- **GIVEN** DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD are set
- **WHEN** the application starts
- **THEN** DATABASE_URL is constructed as `postgresql://${DB_USER}:${DB_PASSWORD}@${DB_HOST}:${DB_PORT}/${DB_NAME}`

---

### Requirement: Security Middleware

The backend system SHALL implement security middleware including Helmet for HTTP headers and CORS for cross-origin requests.

#### Scenario: Helmet headers applied

- **GIVEN** the server is running
- **WHEN** any HTTP request is received
- **THEN** security headers are added by Helmet middleware

#### Scenario: CORS allows configured origins

- **GIVEN** ALLOWED_ORIGINS contains "exp://localhost:19000,http://localhost:19006"
- **WHEN** a request arrives from an allowed origin
- **THEN** CORS headers permit the request

#### Scenario: CORS rejects unknown origins

- **GIVEN** ALLOWED_ORIGINS is configured
- **WHEN** a request arrives from an origin not in the list
- **THEN** CORS headers reject the request

---

### Requirement: Request Logging

The backend system SHALL log all HTTP requests using Winston logger with structured output.

#### Scenario: Request logged with metadata

- **GIVEN** the server receives an HTTP request
- **WHEN** the request is processed
- **THEN** a log entry is created containing:
  - HTTP method
  - Request path
  - Response status code
  - Response time in milliseconds

---

### Requirement: Global Error Handling

The backend system SHALL implement global error handling middleware that returns consistent error responses.

#### Scenario: Domain error handled

- **GIVEN** a request handler throws a NotFoundError
- **WHEN** the error propagates to error middleware
- **THEN** the response status is 404
- **AND** the response body follows the error format:
  - `success`: false
  - `error.message`: error message
  - `error.code`: "NOT_FOUND"

#### Scenario: Validation error handled

- **GIVEN** a request handler throws a ValidationError
- **WHEN** the error propagates to error middleware
- **THEN** the response status is 400
- **AND** the response includes validation details

#### Scenario: Unexpected error handled

- **GIVEN** a request handler throws an unexpected Error
- **WHEN** the error propagates to error middleware
- **THEN** the response status is 500
- **AND** internal error details are NOT exposed to the client
- **AND** the error is logged for debugging

---

### Requirement: Prisma Client Singleton

The backend system SHALL provide a Prisma client as a singleton, injected into request handlers via middleware.

#### Scenario: Single client instance

- **GIVEN** the application is running
- **WHEN** multiple requests are processed
- **THEN** all requests use the same Prisma client instance

#### Scenario: Client available in request

- **GIVEN** a request is received
- **WHEN** the request reaches a controller
- **THEN** `req.prisma` provides access to the Prisma client

---

### Requirement: Layered Architecture Structure

The backend system SHALL follow a 4-layer DDD architecture with clear separation of concerns.

#### Scenario: Layer dependencies enforced

- **GIVEN** the project structure
- **WHEN** code is written
- **THEN** dependencies flow in one direction:
  - Presentation → Application → Domain ← Infrastructure
- **AND** Domain layer has no external dependencies

#### Scenario: All layers present

- **GIVEN** the backend project
- **WHEN** the structure is examined
- **THEN** these directories exist:
  - `src/presentation/` (controllers)
  - `src/application/` (services, validator)
  - `src/domain/` (models, repositories, errors)
  - `src/infrastructure/` (prismaClient, logger, config, env)
  - `src/middleware/` (Express middleware)
  - `src/routes/` (route definitions)

---

### Requirement: Test Infrastructure

The backend system SHALL provide testing infrastructure with Jest, achieving 90% code coverage.

#### Scenario: Tests execute successfully

- **GIVEN** the test suite
- **WHEN** `npm test` is executed
- **THEN** all tests pass

#### Scenario: Coverage threshold enforced

- **GIVEN** the Jest configuration
- **WHEN** `npm run test:coverage` is executed
- **THEN** the build fails if coverage is below 90% for branches, functions, lines, or statements

#### Scenario: Test utilities available

- **GIVEN** the test-utils directory
- **WHEN** tests are written
- **THEN** Prisma mock and test data builders are available for import
