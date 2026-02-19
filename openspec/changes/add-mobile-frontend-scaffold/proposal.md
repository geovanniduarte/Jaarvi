# Change: Add Mobile Frontend Scaffolding

## Why

Jaarvi needs a cross-platform mobile frontend foundation to deliver the travel assistant experience on Android and iOS. Currently, only backend infrastructure exists. This change establishes the complete mobile scaffolding with Kotlin Multiplatform (KMP) + Compose Multiplatform (CMP) to enable immediate feature development with shared UI and business logic across both platforms.

## What Changes

- **NEW**: Complete mobile project structure with KMP shared core, CMP shared UI, Android app, and iOS app modules
- **NEW**: Gradle configuration with version catalog for dependency management
- **NEW**: Dependency injection setup with Koin for shared and platform-specific components
- **NEW**: Navigation foundation with Voyager for cross-platform screen routing
- **NEW**: Platform capability abstractions (maps, notifications, browser, document vault) for V1 external integrations and V2 embedded implementations
- **NEW**: Design system foundation (theme, colors, typography, spacing) aligned with Jaarvi branding
- **NEW**: Health check screen to verify backend connectivity and demonstrate complete stack integration
- **NEW**: Testing infrastructure with Turbine for StateFlow testing in shared module

## Impact

### Affected Specs
- **Creates new spec**: `mobile-frontend-scaffold` - Defines mobile app architecture, module structure, capability patterns, and testing requirements

### Affected Code
- **New modules**: `shared/`, `shared-ui/`, `androidApp/`, `iosApp/`
- **New configuration**: `settings.gradle.kts`, `gradle/libs.versions.toml`, `gradle.properties`
- **Total new files**: 55 files (18 shared, 16 shared-ui, 10 Android, 6 iOS, 5 Gradle config)

### Dependencies
- **Requires**: Backend running at `http://localhost:3000/api` for health check validation
- **Enables**: All future mobile feature development (itinerary creation, Today Mode, document vault, etc.)

### Breaking Changes
None - this is net-new functionality.

### Migration Notes
N/A - no existing mobile code to migrate.
