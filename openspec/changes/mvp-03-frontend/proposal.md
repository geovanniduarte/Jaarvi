## Why

The Jaarvi mobile app has backend APIs ready for trip creation but no UI for users to actually create a trip. Without the 3-step wizard (Basic Info → Destinations → Preferences), users cannot generate the AI-powered itinerary that is the core value of the product.

## What Changes

- **New screen:** `CreateTripScreen` — a Voyager-based 3-step wizard shared across Android and iOS.
- **Step 1 — Basic Info:** Trip name input, start/end date pickers → calls `POST /api/trips`.
- **Step 2 — Destinations:** Country/city picker with days counter; ordered destination list with add/remove → calls `POST /api/trips/{id}/destinations` per city.
- **Step 3 — Preferences:** Travel style, budget, pace, interests (multi-select), special requirements → calls `POST /api/trips/{id}/planning-context`.
- **New KMP domain layer:** `Trip`, `TripDestination`, `Country`, `City` models; `ITripRepository` and `IDestinationRepository` interfaces; Ktor-backed implementations; `CreateTripUseCase`, `GetCountriesUseCase`, `GetCitiesUseCase`.
- **New ScreenModel:** `CreateTripScreenModel` managing `StateFlow<CreateTripUiState>` and handling `CreateTripUiEvent`.
- **New Linda components (9):** `StepIndicator`, `DatePickerField`, `SearchBox`, `TextField`, `SegmentedControl`, `Slider`, `Chips`, `Tag`, `RadioButton` — plus new `BudgetSlider`, `ItemCounter`, `DateSelectors`, `Selector`, `Row`, `Completion` Level-2 compositions.
- **Navigation:** On successful creation, navigate to the Trip Detail screen.

## Capabilities

### New Capabilities

- `create-trip-wizard`: 3-step CMP wizard screen with step navigation, validation, and sequential API calls to create a trip, add destinations, and set planning preferences.
- `trip-wizard-domain`: KMP domain models, repository interfaces, Ktor implementations, and use cases for trip creation and destination catalog (countries/cities).

### Modified Capabilities

- `trip-management`: Adding `createTrip` and `addDestination` and `savePlanningContext` operations to the existing trip management scope.
- `destination-catalog`: Adding `getCountries` and `getCities` read operations to the destination catalog.
- `mobile-frontend-scaffold`: New screen registered in the Voyager navigation graph.

## Impact

- **`shared/` module:** New domain models, repository interfaces, DTOs, repository implementations, and use cases under `com.jaarvi.shared.domain` and `com.jaarvi.shared.data`.
- **`shared-ui/` module:** New screen composables under `com.jaarvi.ui.screens.createtrip`; 9 new Linda Level-1 components + 6 new Level-2 compositions under `com.jaarvi.ui.linda.components`.
- **APIs consumed:** `POST /api/trips`, `POST /api/trips/{id}/destinations`, `POST /api/trips/{id}/planning-context`, `GET /api/destinations/countries`, `GET /api/destinations/cities`.
- **Dependencies:** `kotlinx-datetime` (already in KMP), `Voyager` navigator (existing), `Ktor` client (existing).
- **No breaking changes** to existing screens or APIs.
