## 1. Feature Branch

- [x] 1.1 Create and switch to branch `ticket-mvp03-create-trip-wizard` from `main`

## 2. Domain Models

- [x] 2.1 Create `shared/.../domain/models/Trip.kt` with `Trip` data class and `TripStatus` enum (`DRAFT`, `ACTIVE`, `COMPLETED`)
- [x] 2.2 Create `shared/.../domain/models/TripDestination.kt` with `TripDestination` data class
- [x] 2.3 Create `shared/.../domain/models/Country.kt` with `Country` data class
- [x] 2.4 Create `shared/.../domain/models/City.kt` with `City` data class (including nested `country: Country`)

## 3. Repository Interfaces

- [x] 3.1 Create `shared/.../domain/repositories/ITripRepository.kt` with `createTrip`, `addDestination`, `savePlanningContext`, and `getTripById` methods returning `Result<T>`
- [x] 3.2 Create `shared/.../domain/repositories/IDestinationRepository.kt` with `getCountries` and `getCities(countryId)` methods

## 4. Network DTOs and Repository Implementations

- [x] 4.1 Create `TripDto.kt` with `@Serializable` annotation and `toDomain()` extension that parses ISO-8601 strings to `LocalDate` / `Instant`
- [x] 4.2 Create `TripDestinationDto.kt` with nested city/country mapping
- [x] 4.3 Create `CountryDto.kt` and `CityDto.kt` with `toDomain()` extensions
- [x] 4.4 Create `@Serializable` request body classes: `CreateTripRequest`, `AddDestinationRequest`, `SavePlanningContextRequest`
- [x] 4.5 Create `TripRepositoryImpl.kt` backed by `JaarviApiClient`; wrap all network calls in `runCatching`
- [x] 4.6 Create `DestinationRepositoryImpl.kt` backed by `JaarviApiClient`; wrap all network calls in `runCatching`

## 5. Use Cases

- [x] 5.1 Create `CreateTripUseCase.kt` with client-side validation (endDate > startDate, startDate ≥ today, name ≤ 100 chars) before delegating to `ITripRepository`
- [x] 5.2 Create `GetCountriesUseCase.kt` as thin wrapper around `IDestinationRepository.getCountries()`
- [x] 5.3 Create `GetCitiesUseCase.kt` as thin wrapper around `IDestinationRepository.getCities(countryId)`

## 6. UI State and Events

- [x] 6.1 Create `shared-ui/.../screens/createtrip/CreateTripUiState.kt` with all fields, computed properties (`totalTripDays`, `allocatedDays`, `isStep1Valid`, `isStep2Valid`), and `DestinationDraft` inner/companion data class
- [x] 6.2 Create `shared-ui/.../screens/createtrip/CreateTripUiEvent.kt` with all sealed subclasses for steps 1, 2, 3, and common events

## 7. ScreenModel

- [x] 7.1 Create `CreateTripScreenModel.kt` extending `ScreenModel` with `StateFlow<CreateTripUiState>`, `onEvent()` dispatcher, and all private handlers (`handleNextStep`, `handleAddDestination`, `handleRemoveDestination`, `handleCreateTrip`, `loadCountries`, `handleCountrySelected`, `handleInterestToggle`)

## 8. New Linda Level-1 Components

- [x] 8.1 Implement `LindaTextField` in `level1/TextField.kt` (no internal Linda deps)
- [x] 8.2 Implement `LindaStepIndicator` in `level1/StepIndicator.kt` (uses badge tokens)
- [x] 8.3 Implement `LindaDatePickerField` in `level1/DatePickerField.kt` (uses `LindaTextField` + inline calendar fallback)
- [x] 8.4 Implement `LindaSearchBox` in `level1/SearchBox.kt` (uses `LindaTextField`)
- [x] 8.5 Implement `LindaSegmentedControl` in `level1/SegmentedControl.kt` (uses `LindaCard` + gradient brush)
- [x] 8.6 Implement `LindaSlider` in `level1/Slider.kt`
- [x] 8.7 Implement `LindaChips` in `level1/Chips.kt` (uses `FlowRow` from Compose Foundation)
- [x] 8.8 Implement `LindaTag` in `level1/Tag.kt`
- [x] 8.9 Implement `LindaRadioButton` in `level1/RadioButton.kt`

## 9. New Linda Level-2 Compositions

- [x] 9.1 Implement `LindaBudgetSlider` in `level2/BudgetSlider.kt`
- [x] 9.2 Implement `LindaItemCounter` in `level2/ItemCounter.kt`
- [x] 9.3 Implement `LindaDateSelectors` in `level2/DateSelectors.kt`
- [x] 9.4 Implement `LindaSelector` in `level2/Selector.kt`
- [x] 9.5 Implement `LindaRow` in `level2/Row.kt`
- [x] 9.6 Implement `LindaCompletion` in `level2/Completion.kt`

## 10. Wizard Screen and Step Sub-Composables

- [x] 10.1 Create `CreateTripScreen.kt` (Voyager `Screen`): collects state, wires `LaunchedEffect` for navigation on success, renders `LindaStepIndicator` and step switcher
- [x] 10.2 Create `steps/Step1BasicInfoContent.kt`: `LindaTextField` (trip name), two `LindaDatePickerField` (start/end), "Next" `LindaButton` gated by `isStep1Valid`
- [x] 10.3 Create `steps/Step2DestinationsContent.kt`: country/city `LindaSelector` dropdowns, `LindaItemCounter` (days), "Add" button, `LindaDestinationCard` list with remove, days allocation summary, "Next" gated by `isStep2Valid`
- [x] 10.4 Create `steps/Step3PreferencesContent.kt`: three `LindaSegmentedControl` (style/budget/pace), `LindaChipRow` (interests), multiline `LindaTextField` (special requirements), "Create Trip" `LindaButton` with lime glow

## 11. Dependency Injection

- [x] 11.1 Register `TripRepositoryImpl`, `DestinationRepositoryImpl`, `CreateTripUseCase`, `GetCountriesUseCase`, and `GetCitiesUseCase` in `CommonModule.kt`
- [x] 11.2 Register `CreateTripScreenModel` in `UiModule.kt` using `factory { ... }`

## 12. Navigation

- [x] 12.1 Confirm `TripDetailScreen(tripId: String)` exists or create a stub
- [x] 12.2 Add `navigator.push(CreateTripScreen())` call to the home / trip list entry point (SplashScreen already navigates to CreateTripScreen)

## 13. Tests

- [x] 13.1 Write `CreateTripUseCaseTest.kt` in `shared/commonTest`: 6 cases covering valid input, endDate ≤ startDate, endDate before start, past startDate, name > 100 chars, null name
- [x] 13.2 Write `CreateTripScreenModelTest.kt` in `shared-ui/commonTest`: 10 cases covering all event → state transitions (see Step 12 in implementation plan)
- [x] 13.3 Write `GetCountriesUseCase` and `GetCitiesUseCase` result-propagation tests
- [x] 13.4 Run `./gradlew :shared:allTests` — all tests green
- [x] 13.5 Run `./gradlew :shared-ui:allTests` — CreateTripScreenModel tests all pass; 5 pre-existing HealthPresenterTest Turbine failures are unrelated to this change

## 15. Friendly Error Feedback (added after spec update)

- [x] 15.1 Create `shared-ui/.../screens/createtrip/WizardErrorMapper.kt` with a `Throwable.toFriendlyMessage()` extension that maps `IOException`/`UnknownHostException`/timeouts → "No internet connection. Check your connection and try again.", HTTP 5xx → "Something went wrong on our end. Please try again in a moment.", HTTP 4xx → "We couldn't process your request. Please review your data and try again.", and any other `Throwable` → "An unexpected error occurred. Please try again."
- [x] 15.2 Update `CreateTripScreenModel`: replace all `e.message` error assignments with `e.toFriendlyMessage()`; add `step1Error = null` clearing in `TripNameChanged` handler; verify `isLoading = false` is set before error in all `onFailure` branches
- [x] 15.3 Implement `LindaErrorBanner` in `level1/ErrorBanner.kt`: a styled banner composable that shows an error string with `colors.statusError` text on a `colors.surfaceGlass` background with a `colors.statusError` left-side accent border — hidden when the message is null
- [x] 15.4 Replace inline error `Text` in `Step1BasicInfoContent` with `LindaErrorBanner(message = state.step1Error)` positioned below the date fields and above the Next button
- [x] 15.5 Replace inline error `Text` in `Step2DestinationsContent` with `LindaErrorBanner(message = state.step2Error)` positioned below the destination list and above the Next button
- [x] 15.6 Replace inline error `Text` in `Step3PreferencesContent` with `LindaErrorBanner(message = state.error)` positioned below the special requirements field and above the Create Trip button

## 14. Documentation

- [x] 14.1 Update `ai-specs/specs/frontend-standards.mdc` with the 3-step wizard pattern, `DatePickerCapability` abstraction approach, and `FlowRow` chip usage
- [x] 14.2 Mark implemented Linda components as done in `ai-specs/changes/TICKETS/mvp-03_frontend_linda.md`
- [x] 14.3 Verify `api-spec.yml` `POST /api/trips/{id}/planning-context` schema matches the frontend `SavePlanningContextRequest` fields — all 5 fields match (travelStyle, budget, pace, interests, specialRequirements)
