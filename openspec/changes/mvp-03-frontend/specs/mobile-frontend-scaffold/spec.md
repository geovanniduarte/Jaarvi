## ADDED Requirements

### Requirement: CreateTripScreen is reachable from the navigation graph
The Voyager navigation graph SHALL include `CreateTripScreen` so it can be pushed from any screen that triggers trip creation.

#### Scenario: CreateTripScreen is pushed from the trip list
- **WHEN** the user taps the "Create Trip" action on the home or trip list screen
- **THEN** `navigator.push(CreateTripScreen())` navigates to the wizard
- **AND** the back stack allows the user to return to the previous screen

#### Scenario: CreateTripScreen replaces itself with Trip Detail on success
- **WHEN** trip creation completes successfully
- **THEN** the navigator calls `navigator.replace(TripDetailScreen(tripId))` so the back button does not return to the wizard

#### Scenario: CreateTripScreen is a type-safe Voyager Screen
- **WHEN** `CreateTripScreen` class is inspected
- **THEN** it implements `cafe.adriel.voyager.core.screen.Screen`
- **AND** it carries no mutable state (all state lives in `CreateTripScreenModel`)

---

### Requirement: CreateTripScreenModel is registered in Koin
`CreateTripScreenModel` and its use-case dependencies SHALL be registered in the shared Koin module so Voyager can inject them automatically.

#### Scenario: ScreenModel resolved via Koin on screen display
- **WHEN** `CreateTripScreen` is displayed for the first time
- **THEN** Voyager resolves `CreateTripScreenModel` from Koin with all constructor dependencies injected (use cases, repositories)

#### Scenario: ScreenModel is scoped to the screen lifecycle
- **WHEN** `CreateTripScreen` is popped from the navigator
- **THEN** `CreateTripScreenModel.onDispose()` is called and resources are released
