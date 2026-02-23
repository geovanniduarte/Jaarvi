## ADDED Requirements

### Requirement: Kotlin Multiplatform Project Structure

The mobile frontend SHALL be implemented using Kotlin Multiplatform (KMP) with Compose Multiplatform (CMP) to enable maximum code sharing between Android and iOS platforms.

#### Scenario: Project modules are properly configured

- **WHEN** the project is built with `./gradlew build`
- **THEN** all modules (shared, shared-ui, androidApp) compile successfully for both Android and iOS targets
- **AND** the build completes without errors or warnings about platform incompatibility

#### Scenario: Gradle version catalog provides dependency management

- **WHEN** a developer adds a new dependency to any module
- **THEN** the version catalog (`gradle/libs.versions.toml`) provides centralized version definitions
- **AND** all modules reference dependencies using type-safe accessors (e.g., `libs.ktor.client.core`)

#### Scenario: Shared module does not contain platform-specific code

- **WHEN** the shared module is compiled for iOS target
- **THEN** no Android-specific imports (e.g., `android.*`, `androidx.*`) are present in `commonMain` source sets
- **AND** platform-specific implementations use expect/actual pattern in `androidMain` and `iosMain` source sets

---

### Requirement: Dependency Injection with Koin

The application SHALL use Koin for dependency injection across shared and platform-specific modules.

#### Scenario: Shared dependencies are registered in common Koin module

- **WHEN** the shared module initializes Koin
- **THEN** repositories, use cases, and network clients are registered in `CommonModule`
- **AND** platform implementations can access shared dependencies via Koin

#### Scenario: Platform-specific capabilities are registered in platform modules

- **WHEN** Android app starts
- **THEN** `AndroidMapCapability`, `AndroidBrowserCapability`, and other platform implementations are registered in `AndroidModule`
- **AND** shared code can resolve capability interfaces via Koin without knowing platform details

#### Scenario: Voyager ScreenModels are integrated with Koin

- **WHEN** a screen is displayed using Voyager Navigator
- **THEN** presenters (ScreenModels) are resolved from Koin with their dependencies injected
- **AND** presenters have proper lifecycle management via Voyager's ScreenModel API

---

### Requirement: Platform Capability Abstractions

The system SHALL define capability interfaces for platform-specific features to enable migration from external to embedded implementations without breaking shared code.

#### Scenario: MapCapability supports external navigation (V1)

- **WHEN** user taps a location to navigate
- **THEN** the system opens the platform's default maps app (Google Maps on Android, Apple Maps on iOS)
- **AND** the navigation request includes latitude, longitude, and optional label

#### Scenario: MapCapability interface is platform-agnostic

- **WHEN** shared code calls `MapCapability.openExternalNavigation(lat, lng, label)`
- **THEN** the call returns a `Result<Unit>` wrapping success or failure
- **AND** the implementation details (Intent vs MKMapItem) are hidden behind the abstraction

#### Scenario: NotificationCapability is defined for future use

- **WHEN** the capability interface is inspected
- **THEN** it includes methods for scheduling notifications, canceling notifications, and checking permission status
- **AND** V1 implementations provide basic platform-specific notification channel setup (Android) and UNUserNotificationCenter registration (iOS)

#### Scenario: BrowserCapability enables in-app browsing for affiliates

- **WHEN** user taps an affiliate link (e.g., flight booking)
- **THEN** the system opens an in-app browser (Chrome Custom Tabs on Android, SFSafariViewController on iOS)
- **AND** the browser includes close button to return to app

#### Scenario: DocumentVaultCapability is defined for future document storage

- **WHEN** the capability interface is inspected
- **THEN** it includes methods for storing, retrieving, and deleting documents (tickets, permits)
- **AND** V1 implementations are placeholders (no-op or simple file storage)

---

### Requirement: Health Check Integration

The application SHALL include a health check screen that verifies backend connectivity and demonstrates full stack integration.

#### Scenario: Health screen displays loading state initially

- **WHEN** HealthScreen is displayed
- **THEN** the UI shows a loading indicator
- **AND** the presenter automatically triggers health check API call

#### Scenario: Successful health check displays backend message

- **WHEN** the backend `/api/health` endpoint returns `200 OK` with `{ message, timestamp, status }`
- **THEN** the UI displays the backend message
- **AND** the UI shows a success indicator (e.g., green checkmark)
- **AND** the timestamp is displayed in user-friendly format

#### Scenario: Failed health check displays error state

- **WHEN** the backend is unreachable or returns an error
- **THEN** the UI displays an error message
- **AND** the UI shows a retry button
- **AND** the loading indicator is hidden

#### Scenario: Retry button re-triggers health check

- **WHEN** user taps retry button after error
- **THEN** the presenter triggers a new health check API call
- **AND** the UI returns to loading state
- **AND** the error message is cleared

---

### Requirement: State Management Pattern

Shared UI screens SHALL use a unidirectional data flow pattern with StateFlow and event-based user interactions.

#### Scenario: UiState is an immutable data class

- **WHEN** a presenter emits a new state
- **THEN** the state is represented as an immutable data class (e.g., `HealthUiState`)
- **AND** state updates use `copy()` method to create new instances

#### Scenario: UiEvent represents user intents

- **WHEN** user performs an action (e.g., taps retry button)
- **THEN** the UI emits a `UiEvent` (e.g., `HealthUiEvent.Retry`)
- **AND** the presenter handles the event via `onEvent(event: UiEvent)` method

#### Scenario: Presenter exposes StateFlow to UI

- **WHEN** a composable screen collects presenter state
- **THEN** the state is exposed as `StateFlow<UiState>`
- **AND** the UI recomposes automatically when state changes
- **AND** the StateFlow is collected with appropriate lifecycle awareness

#### Scenario: Presenter does not use Android-only ViewModel

- **WHEN** the presenter class is compiled for iOS target
- **THEN** no references to `androidx.lifecycle.ViewModel` exist
- **AND** the presenter extends Voyager's `ScreenModel` for lifecycle management

---

### Requirement: Design System Foundation

The application SHALL implement a foundational design system with colors, typography, and spacing aligned with Jaarvi branding.

#### Scenario: JaarviTheme provides MaterialTheme wrapper

- **WHEN** a composable is wrapped in `JaarviTheme { ... }`
- **THEN** MaterialTheme is configured with Jaarvi color scheme, typography, and shapes
- **AND** all descendant composables use theme values via `MaterialTheme.colors`, `MaterialTheme.typography`, etc.

#### Scenario: Colors are defined for light theme (V1)

- **WHEN** the app runs in light mode
- **THEN** primary, secondary, background, surface, and error colors are defined
- **AND** colors have sufficient contrast ratios for accessibility (WCAG AA minimum)

#### Scenario: Typography defines text styles

- **WHEN** text is rendered with theme typography
- **THEN** styles are defined for h1, h2, h3, body1, body2, button, caption
- **AND** font family, size, weight, and line height are specified for each style

#### Scenario: Spacing uses consistent increments

- **WHEN** components use spacing values
- **THEN** spacing is defined in 4dp increments (e.g., 4dp, 8dp, 16dp, 24dp, 32dp)
- **AND** spacing values are accessed via theme constants (e.g., `Spacing.small`, `Spacing.medium`)

---

### Requirement: Reusable UI Components

The shared UI SHALL include reusable composable components for common UI patterns.

#### Scenario: JaarviButton provides primary action button

- **WHEN** `JaarviButton` is used in a screen
- **THEN** the button uses primary theme color and typography
- **AND** the button supports `onClick`, `enabled`, and `text` parameters
- **AND** the button shows loading state when `enabled = false` (optional)

#### Scenario: JaarviTextButton provides secondary action button

- **WHEN** `JaarviTextButton` is used in a screen
- **THEN** the button is text-only with no background (Material TextButton)
- **AND** the button uses theme color for text

#### Scenario: StatusCard displays status information

- **WHEN** `StatusCard` is used to show health status
- **THEN** the card displays icon, title, and message
- **AND** the card supports success, error, and warning variants with different colors

#### Scenario: LoadingIndicator provides consistent loading UI

- **WHEN** `LoadingIndicator` is displayed during async operations
- **THEN** the indicator is centered and uses primary theme color
- **AND** the indicator is a Material CircularProgressIndicator

---

### Requirement: Navigation with Voyager

The application SHALL use Voyager Navigator for screen navigation with type-safe routing.

#### Scenario: Navigator manages screen stack

- **WHEN** app launches
- **THEN** Voyager Navigator is initialized with initial screen (SplashScreen or HealthScreen)
- **AND** navigation between screens uses `navigator.push(screen)` and `navigator.pop()`

#### Scenario: Screens are type-safe data classes

- **WHEN** a screen is defined (e.g., `HealthScreen`)
- **THEN** the screen implements Voyager's `Screen` interface
- **AND** screen parameters are type-safe (no string-based routing)

#### Scenario: Screen transitions use default animations

- **WHEN** user navigates from HealthScreen to another screen
- **THEN** a slide transition is applied by default
- **AND** transitions can be customized per navigation action (future enhancement)

---

### Requirement: Network Layer with Ktor

The shared module SHALL use Ktor HTTP client for API communication with platform-specific engines.

#### Scenario: HttpClient uses OkHttp engine on Android

- **WHEN** Android app makes an HTTP request
- **THEN** Ktor uses OkHttp engine (`ktor-client-okhttp`)
- **AND** the engine is configured in `androidMain` source set

#### Scenario: HttpClient uses Darwin engine on iOS

- **WHEN** iOS app makes an HTTP request
- **THEN** Ktor uses Darwin engine (`ktor-client-darwin`)
- **AND** the engine is configured in `iosMain` source set

#### Scenario: HttpClient includes JSON serialization

- **WHEN** API responses are received
- **THEN** Ktor ContentNegotiation plugin deserializes JSON to Kotlin data classes
- **AND** kotlinx.serialization is used for serialization

#### Scenario: HttpClient includes logging for debugging

- **WHEN** API requests are made in debug builds
- **THEN** Ktor Logging plugin logs request/response details to console
- **AND** logging is disabled in release builds for performance

---

### Requirement: Testing Infrastructure

The shared module SHALL include comprehensive unit tests for business logic and state management.

#### Scenario: HealthRepository tests cover success and failure cases

- **WHEN** repository tests are executed with `./gradlew :shared:test`
- **THEN** tests verify successful health check returns `HealthStatus` domain model
- **AND** tests verify network errors return `Result.failure`
- **AND** tests verify DTO to domain mapping is correct

#### Scenario: HealthPresenter tests verify state transitions

- **WHEN** presenter tests are executed with `./gradlew :shared-ui:test`
- **THEN** tests verify initial state is loading
- **AND** tests verify successful API call updates state to success with message
- **AND** tests verify failed API call updates state to error with error message
- **AND** tests verify retry event triggers new API call

#### Scenario: StateFlow testing uses Turbine

- **WHEN** presenter emits state changes
- **THEN** Turbine library is used to assert on StateFlow emissions
- **AND** tests use `state.test { ... }` syntax for clean assertions
- **AND** tests verify exact sequence of state emissions

#### Scenario: All shared tests pass before merge

- **WHEN** pull request is ready for review
- **THEN** `./gradlew :shared:allTests` passes (minimum 3 repository tests)
- **AND** `./gradlew :shared-ui:allTests` passes (minimum 6 presenter tests)
- **AND** no tests are skipped or ignored

---

### Requirement: Environment Configuration

The application SHALL support different API base URLs for debug and release builds.

#### Scenario: Android debug builds use emulator localhost alias

- **WHEN** Android app is built in debug mode
- **THEN** API base URL is `http://10.0.2.2:3000/api` (emulator alias for host machine localhost)
- **AND** URL is configured via BuildConfig field

#### Scenario: iOS debug builds use simulator localhost

- **WHEN** iOS app is built in debug mode
- **THEN** API base URL is `http://localhost:3000/api` (simulator shares host network)
- **AND** URL is configured in `ApiConfig.ios.kt`

#### Scenario: Release builds use production API URL

- **WHEN** app is built in release mode (Android or iOS)
- **THEN** API base URL is `https://api.jaarvi.app`
- **AND** HTTPS is enforced (no plain HTTP allowed)

#### Scenario: Local networking is allowed only in debug builds

- **WHEN** iOS debug build makes request to localhost
- **THEN** `Info.plist` includes `NSAllowsLocalNetworking` key set to `true`
- **AND** release builds do not include this key (enforcing HTTPS)

---

### Requirement: Platform Compatibility

The application SHALL support minimum platform versions for broad device compatibility.

#### Scenario: Android minimum SDK is API 24 (Android 7.0)

- **WHEN** Android app is configured in `build.gradle.kts`
- **THEN** `minSdk = 24` is set
- **AND** app can run on devices with Android 7.0 or higher

#### Scenario: Android target SDK is API 34 (Android 14)

- **WHEN** Android app is configured
- **THEN** `targetSdk = 34` is set
- **AND** app follows Android 14 behavior and requirements

#### Scenario: iOS deployment target is 15.0

- **WHEN** iOS app is configured in Xcode project
- **THEN** deployment target is set to iOS 15.0
- **AND** app can run on devices with iOS 15.0 or higher

#### Scenario: No platform-specific code leaks into commonMain

- **WHEN** shared modules are compiled for both targets
- **THEN** static analysis confirms no Android or iOS imports in `commonMain`
- **AND** expect/actual pattern is used for all platform differences

---

### Requirement: Code Quality Standards

All code SHALL follow Kotlin coding conventions and include documentation for public APIs.

#### Scenario: Public APIs have KDoc comments

- **WHEN** a public class, function, or property is defined
- **THEN** it includes KDoc comment with description, `@param`, and `@return` tags
- **AND** KDoc examples are provided for complex APIs (e.g., capabilities)

#### Scenario: No use of `Any` type in public APIs

- **WHEN** public APIs are reviewed
- **THEN** all parameters and return types are explicitly typed
- **AND** `Any` type is only used internally when absolutely necessary

#### Scenario: English-only naming and comments

- **WHEN** code is written or reviewed
- **THEN** all variables, functions, classes, and comments use English
- **AND** no Spanish or other languages are used in technical artifacts

#### Scenario: Consistent naming conventions are applied

- **WHEN** code is written
- **THEN** classes use PascalCase (e.g., `HealthPresenter`)
- **AND** functions and variables use camelCase (e.g., `checkHealth`)
- **AND** constants use UPPER_SNAKE_CASE (e.g., `MAX_RETRY_ATTEMPTS`)
