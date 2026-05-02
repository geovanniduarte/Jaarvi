# Frontend Implementation Plan: mvp-03 — Create Trip with Destinations

---

## 1. Overview

Implement the 3-step trip creation wizard (Screens 12 → 13 → 14) as a Compose Multiplatform screen targeting Android and iOS from a single shared codebase based in @ai-specs/changes/TICKETS/mvp-03_frontend.md .

The wizard covers:
- **Step 1 — Basic Info**: trip name, start date, end date → `POST /api/trips`
- **Step 2 — Add Destinations**: country/city picker, days count, ordered destinations → `POST /api/trips/{id}/destinations` (one call per city)
- **Step 3 — Preferences**: travel style, budget, pace, interests, special requirements → `POST /api/trips/{id}/planning-context`

On completion the user is navigated to the Trip Detail screen.

**Architecture:** CMP composable-based UI, KMP layered repository/use-case pattern, `StateFlow`-driven state via Voyager `ScreenModel`.

**Linda components:** 6 existing + 9 new. See `ai-specs/changes/mvp-03_frontend_linda.md` for full component wireframe specs.

---

## 2. Architecture Context

### Composables / Screens
All in `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/`:

| File | Role |
|---|---|
| `screens/createtrip/CreateTripScreen.kt` | Root Voyager screen; delegates to step sub-composables |
| `screens/createtrip/steps/Step1BasicInfoContent.kt` | Step 1 UI |
| `screens/createtrip/steps/Step2DestinationsContent.kt` | Step 2 UI |
| `screens/createtrip/steps/Step3PreferencesContent.kt` | Step 3 UI |

### Presenters / ScreenModels
| File | Exposed state |
|---|---|
| `screens/createtrip/CreateTripScreenModel.kt` | `StateFlow<CreateTripUiState>` |

**`CreateTripUiState`** (full definition in Step 3 below)

**`CreateTripUiEvent`** sealed class:
```kotlin
sealed class CreateTripUiEvent {
    // Step 1
    data class TripNameChanged(val name: String) : CreateTripUiEvent()
    data class StartDateSelected(val date: LocalDate) : CreateTripUiEvent()
    data class EndDateSelected(val date: LocalDate) : CreateTripUiEvent()
    object NextStep : CreateTripUiEvent()
    object PreviousStep : CreateTripUiEvent()
    // Step 2
    data class CountrySelected(val country: Country) : CreateTripUiEvent()
    data class CitySelected(val city: City) : CreateTripUiEvent()
    data class DaysCountChanged(val days: Int) : CreateTripUiEvent()
    object AddDestination : CreateTripUiEvent()
    data class RemoveDestination(val dayOrder: Int) : CreateTripUiEvent()
    // Step 3
    data class TravelStyleSelected(val style: String) : CreateTripUiEvent()
    data class BudgetSelected(val budget: String) : CreateTripUiEvent()
    data class PaceSelected(val pace: String) : CreateTripUiEvent()
    data class InterestToggled(val interest: String) : CreateTripUiEvent()
    data class SpecialRequirementsChanged(val text: String) : CreateTripUiEvent()
    object CreateTrip : CreateTripUiEvent()
    // Common
    object DismissError : CreateTripUiEvent()
}
```

### Shared module files
All in `shared/src/commonMain/kotlin/com/jaarvi/shared/`:

| File | Role |
|---|---|
| `domain/models/Trip.kt` | Domain data class |
| `domain/models/TripDestination.kt` | Domain data class |
| `domain/models/Country.kt` | Domain data class |
| `domain/models/City.kt` | Domain data class |
| `domain/repositories/ITripRepository.kt` | Repository interface |
| `domain/repositories/IDestinationRepository.kt` | Repository interface |
| `data/repositories/TripRepositoryImpl.kt` | Ktor-backed implementation |
| `data/repositories/DestinationRepositoryImpl.kt` | Ktor-backed implementation |
| `data/datasources/dto/TripDto.kt` | Network DTO + mapper |
| `domain/usecases/CreateTripUseCase.kt` | Orchestrates 3-step API calls |
| `domain/usecases/GetCountriesUseCase.kt` | Fetches country list |
| `domain/usecases/GetCitiesUseCase.kt` | Fetches cities (filtered by countryId) |

### Navigation
Voyager-based. `CreateTripScreen` is pushed onto the navigator from the Home/Trip list screen. On success, replace with Trip Detail screen.

---

## 3. Implementation Steps

### Step 0 — Create Feature Branch

- **Action:** Create and switch to a new feature branch.
- **Branch name:** `ticket-mvp03-create-trip-wizard`
- **Steps:**
  1. `git checkout main && git pull origin main`
  2. `git checkout -b ticket-mvp03-create-trip-wizard`
  3. `git branch` — verify active branch

---

### Step 1 — Define Domain Models

**Files:** `shared/src/commonMain/kotlin/com/jaarvi/shared/domain/models/`

Create the following data classes:

**`Trip.kt`**
```kotlin
data class Trip(
    val id        : String,
    val ownerId   : String,
    val name      : String?,
    val startDate : LocalDate,
    val endDate   : LocalDate,
    val status    : TripStatus,
    val createdAt : Instant,
)

enum class TripStatus { DRAFT, ACTIVE, COMPLETED }
```

**`TripDestination.kt`**
```kotlin
data class TripDestination(
    val id       : String,
    val tripId   : String,
    val cityId   : String,
    val city     : City,
    val dayOrder : Int,
    val daysCount: Int,
    val notes    : String?,
)
```

**`Country.kt`**
```kotlin
data class Country(
    val id     : String,
    val isoCode: String,
    val name   : String,
)
```

**`City.kt`**
```kotlin
data class City(
    val id       : String,
    val countryId: String,
    val name     : String,
    val timezone : String,
    val country  : Country,
)
```

**`DestinationDraft.kt`** (UI-only; in `shared-ui` or as inner class of UiState):
```kotlin
data class DestinationDraft(
    val city     : City,
    val daysCount: Int,
    val dayOrder : Int,
)
```

**Dependencies:** `kotlinx-datetime` for `LocalDate` and `Instant`.

---

### Step 2 — Define Repository Interfaces

**Files:** `shared/src/commonMain/kotlin/com/jaarvi/shared/domain/repositories/`

**`ITripRepository.kt`**
```kotlin
interface ITripRepository {
    suspend fun createTrip(name: String?, startDate: LocalDate, endDate: LocalDate): Result<Trip>
    suspend fun addDestination(tripId: String, cityId: String, dayOrder: Int, daysCount: Int): Result<TripDestination>
    suspend fun savePlanningContext(
        tripId: String,
        travelStyle: String?,
        budget: String?,
        pace: String?,
        interests: List<String>,
        specialRequirements: String?,
    ): Result<Unit>
    suspend fun getTripById(tripId: String): Result<Trip>
}
```

**`IDestinationRepository.kt`**
```kotlin
interface IDestinationRepository {
    suspend fun getCountries(): Result<List<Country>>
    suspend fun getCities(countryId: String?): Result<List<City>>
}
```

---

### Step 3 — Implement Network DTOs and Repository Implementations

**Files:** `shared/src/commonMain/kotlin/com/jaarvi/shared/data/`

#### DTOs (`data/datasources/dto/`)

**`TripDto.kt`**
```kotlin
@Serializable
data class TripDto(
    val id       : String,
    val ownerId  : String,
    val name     : String?,
    val startDate: String,   // "2026-07-01T00:00:00.000Z"
    val endDate  : String,
    val status   : String,
    val createdAt: String,
)

fun TripDto.toDomain() = Trip(
    id        = id,
    ownerId   = ownerId,
    name      = name,
    startDate = LocalDate.parse(startDate.take(10)),
    endDate   = LocalDate.parse(endDate.take(10)),
    status    = TripStatus.valueOf(status.uppercase()),
    createdAt = Instant.parse(createdAt),
)
```

**`CountryDto.kt`** and **`CityDto.kt`** — similarly annotated with `@Serializable` and mapped to domain models.

**`TripDestinationDto.kt`** — maps `city` + `country` nested objects.

#### Repository Implementations

**`TripRepositoryImpl.kt`** — uses `JaarviApiClient` (existing Ktor client):
```kotlin
class TripRepositoryImpl(private val apiClient: JaarviApiClient) : ITripRepository {
    override suspend fun createTrip(...): Result<Trip> = runCatching {
        apiClient.post<ApiResponse<TripDto>>("/trips", body = CreateTripRequest(name, startDate, endDate))
            .data.toDomain()
    }
    // addDestination, savePlanningContext, getTripById follow the same pattern
}
```

**`DestinationRepositoryImpl.kt`** — similarly backed by `JaarviApiClient`.

**Request body classes** (annotated `@Serializable`):
```kotlin
@Serializable data class CreateTripRequest(val name: String?, val startDate: String, val endDate: String)
@Serializable data class AddDestinationRequest(val cityId: String, val dayOrder: Int, val daysCount: Int)
@Serializable data class SavePlanningContextRequest(
    val travelStyle: String?, val budget: String?, val pace: String?,
    val interests: List<String>, val specialRequirements: String?,
)
```

---

### Step 4 — Implement Use Cases

**Files:** `shared/src/commonMain/kotlin/com/jaarvi/shared/domain/usecases/`

**`CreateTripUseCase.kt`** — validates client-side then delegates to repository:
```kotlin
class CreateTripUseCase(private val repository: ITripRepository) {
    suspend fun invoke(name: String?, startDate: LocalDate, endDate: LocalDate): Result<Trip> {
        if (!endDate.isAfter(startDate)) return Result.failure(IllegalArgumentException("endDate must be after startDate"))
        if (startDate.isBefore(Clock.System.todayIn(TimeZone.currentSystemDefault()))) return Result.failure(IllegalArgumentException("startDate cannot be in the past"))
        if (name != null && name.length > 100) return Result.failure(IllegalArgumentException("name exceeds 100 characters"))
        return repository.createTrip(name, startDate, endDate)
    }
}
```

**`GetCountriesUseCase.kt`** — thin wrapper around `IDestinationRepository.getCountries()`.

**`GetCitiesUseCase.kt`** — thin wrapper around `IDestinationRepository.getCities(countryId)`.

---

### Step 5 — Define `CreateTripUiState` and `CreateTripUiEvent`

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/screens/createtrip/CreateTripUiState.kt`

```kotlin
data class CreateTripUiState(
    val currentStep         : Int             = 1,
    // Step 1
    val tripName            : String          = "",
    val startDate           : LocalDate?      = null,
    val endDate             : LocalDate?      = null,
    val step1Error          : String?         = null,
    // Step 2
    val availableCountries  : List<Country>   = emptyList(),
    val selectedCountry     : Country?        = null,
    val availableCities     : List<City>      = emptyList(),
    val selectedCity        : City?           = null,
    val daysCountInput      : Int             = 1,
    val destinations        : List<DestinationDraft> = emptyList(),
    val step2Error          : String?         = null,
    // Step 3
    val travelStyle         : String?         = null,
    val budget              : String?         = null,
    val pace                : String?         = null,
    val selectedInterests   : Set<String>     = emptySet(),
    val specialRequirements : String          = "",
    // Common
    val isLoading           : Boolean         = false,
    val error               : String?         = null,
    val createdTripId       : String?         = null,  // set on success → triggers navigation
) {
    val totalTripDays: Int
        get() = if (startDate != null && endDate != null) {
            startDate.daysUntil(endDate) + 1
        } else 0

    val allocatedDays: Int
        get() = destinations.sumOf { it.daysCount }

    val isStep1Valid: Boolean
        get() = startDate != null && endDate != null && endDate.isAfter(startDate)

    val isStep2Valid: Boolean
        get() = destinations.isNotEmpty() && allocatedDays == totalTripDays
}
```

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/screens/createtrip/CreateTripUiEvent.kt`
(see events listed in Architecture Context above)

---

### Step 6 — Implement `CreateTripScreenModel`

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/screens/createtrip/CreateTripScreenModel.kt`

```kotlin
class CreateTripScreenModel(
    private val createTripUseCase : CreateTripUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCitiesUseCase  : GetCitiesUseCase,
    private val tripRepository    : ITripRepository,
) : ScreenModel {

    private val _state = MutableStateFlow(CreateTripUiState())
    val state: StateFlow<CreateTripUiState> = _state.asStateFlow()

    fun start() {
        loadCountries()
    }

    fun onEvent(event: CreateTripUiEvent) {
        when (event) {
            is TripNameChanged         -> _state.update { it.copy(tripName = event.name) }
            is StartDateSelected       -> _state.update { it.copy(startDate = event.date, step1Error = null) }
            is EndDateSelected         -> _state.update { it.copy(endDate = event.date, step1Error = null) }
            is NextStep                -> handleNextStep()
            is PreviousStep            -> _state.update { it.copy(currentStep = it.currentStep - 1) }
            is CountrySelected         -> handleCountrySelected(event.country)
            is CitySelected            -> _state.update { it.copy(selectedCity = event.city) }
            is DaysCountChanged        -> _state.update { it.copy(daysCountInput = event.days) }
            is AddDestination          -> handleAddDestination()
            is RemoveDestination       -> handleRemoveDestination(event.dayOrder)
            is TravelStyleSelected     -> _state.update { it.copy(travelStyle = event.style) }
            is BudgetSelected          -> _state.update { it.copy(budget = event.budget) }
            is PaceSelected            -> _state.update { it.copy(pace = event.pace) }
            is InterestToggled         -> handleInterestToggle(event.interest)
            is SpecialRequirementsChanged -> _state.update { it.copy(specialRequirements = event.text) }
            is CreateTrip              -> handleCreateTrip()
            is DismissError            -> _state.update { it.copy(error = null, step1Error = null, step2Error = null) }
        }
    }

    private fun handleNextStep() {
        val s = _state.value
        when (s.currentStep) {
            1 -> {
                if (!s.isStep1Valid) {
                    _state.update { it.copy(step1Error = "Please fill in valid start and end dates") }
                    return
                }
                // POST /api/trips happens here as first API call; store tripId in state
                screenModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    createTripUseCase.invoke(s.tripName.ifBlank { null }, s.startDate!!, s.endDate!!)
                        .onSuccess { trip ->
                            _state.update { it.copy(isLoading = false, currentStep = 2, createdTripId = trip.id) }
                        }
                        .onFailure { e ->
                            _state.update { it.copy(isLoading = false, step1Error = e.message) }
                        }
                }
            }
            2 -> {
                if (!_state.value.isStep2Valid) {
                    _state.update { it.copy(step2Error = "Allocate all trip days before continuing") }
                    return
                }
                _state.update { it.copy(currentStep = 3, step2Error = null) }
            }
        }
    }

    private fun handleAddDestination() {
        val s = _state.value
        val city = s.selectedCity ?: return
        val tripId = s.createdTripId ?: return
        val dayOrder = s.destinations.size + 1
        screenModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            tripRepository.addDestination(tripId, city.id, dayOrder, s.daysCountInput)
                .onSuccess { dest ->
                    _state.update { it.copy(
                        isLoading = false,
                        destinations = it.destinations + DestinationDraft(city, dest.daysCount, dest.dayOrder),
                        selectedCity = null,
                        daysCountInput = 1,
                        step2Error = null,
                    )}
                }
                .onFailure { e -> _state.update { it.copy(isLoading = false, step2Error = e.message) } }
        }
    }

    private fun handleRemoveDestination(dayOrder: Int) {
        // Client-side removal only (no delete endpoint at MVP); reorder remaining
        _state.update { s ->
            val updated = s.destinations
                .filter { it.dayOrder != dayOrder }
                .mapIndexed { i, d -> d.copy(dayOrder = i + 1) }
            s.copy(destinations = updated)
        }
    }

    private fun handleCreateTrip() {
        val s = _state.value
        val tripId = s.createdTripId ?: return
        screenModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            tripRepository.savePlanningContext(
                tripId = tripId,
                travelStyle = s.travelStyle,
                budget = s.budget,
                pace = s.pace,
                interests = s.selectedInterests.toList(),
                specialRequirements = s.specialRequirements.ifBlank { null },
            ).onSuccess {
                // Signal navigation by keeping createdTripId set; screen observes this
                _state.update { it.copy(isLoading = false) }
            }.onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun loadCountries() {
        screenModelScope.launch {
            getCountriesUseCase.invoke()
                .onSuccess { countries -> _state.update { it.copy(availableCountries = countries) } }
                .onFailure { e -> _state.update { it.copy(error = "Failed to load countries: ${e.message}") } }
        }
    }

    private fun handleCountrySelected(country: Country) {
        _state.update { it.copy(selectedCountry = country, selectedCity = null, availableCities = emptyList()) }
        screenModelScope.launch {
            getCitiesUseCase.invoke(country.id)
                .onSuccess { cities -> _state.update { it.copy(availableCities = cities) } }
                .onFailure { e -> _state.update { it.copy(error = "Failed to load cities: ${e.message}") } }
        }
    }

    private fun handleInterestToggle(interest: String) {
        _state.update { s ->
            val updated = if (interest in s.selectedInterests)
                s.selectedInterests - interest
            else
                s.selectedInterests + interest
            s.copy(selectedInterests = updated)
        }
    }
}
```

---

### Step 7 — Build New Linda Components

**Files:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/components/`

Build all 9 new components following the wireframe specs in `ai-specs/changes/mvp-03_frontend_linda.md`.

**Build order** (dependencies first):

1. `TextField.kt` — `LindaTextField` (no internal Linda deps)
2. `NumberStepper.kt` — `LindaNumberStepper` (uses `LindaIconButton`)
3. `StepIndicator.kt` — `LindaStepIndicator` (uses `LindaBadge` tokens)
4. `DatePickerField.kt` — `LindaDatePickerField` (uses `LindaTextField` shell + `DatePickerCapability`)
5. `SearchableDropdown.kt` — `LindaSearchableDropdown` (uses `LindaTextField` + `LindaCard`)
6. `DestinationDraftCard.kt` — `LindaDestinationDraftCard` (uses `LindaCard` + `LindaIconButton`)
7. `DaysAllocationBar.kt` — `LindaDaysAllocationBar` (uses `LindaProgressBar` + `LindaStatCard`)
8. `SegmentedControl.kt` — `LindaSegmentedControl` (uses `LindaCard` + gradient Brush)
9. `ChipGroup.kt` — `LindaChipGroup` (uses `FlowRow` + gradient border)

**Key rules for all new components:**
- All files in `commonMain` — no `android.*` imports.
- All tokens accessed via `LindaTheme.colors`, `LindaTheme.spacing`, etc.
- Every interactive element must have `contentDescription` for accessibility.
- Add `@Suppress("UNUSED")` to public functions if not yet wired to avoid lint errors during build.

---

### Step 8 — Implement `DatePickerCapability`

**File:** `shared/src/commonMain/kotlin/com/jaarvi/shared/capabilities/DatePickerCapability.kt`

```kotlin
interface DatePickerCapability {
    fun showDatePicker(
        initialDate: LocalDate?,
        minDate    : LocalDate?,
        maxDate    : LocalDate?,
        onDateSelected: (LocalDate) -> Unit,
        onDismiss  : () -> Unit,
    )
}
```

**Android implementation** (`androidApp/`): delegates to `MaterialDatePicker` (AndroidX).
**iOS implementation** (`iosApp/`): delegates to `UIDatePicker` via UIKit interop.

For MVP, a **pure-Compose fallback** (inline calendar grid) can be shipped in `commonMain` to avoid the `expect/actual` complexity, then replaced in a follow-up.

---

### Step 9 — Build `CreateTripScreen` Composable

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/screens/createtrip/CreateTripScreen.kt`

```kotlin
class CreateTripScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<CreateTripScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) { screenModel.start() }

        // Navigate to Trip Detail when creation is complete
        LaunchedEffect(state.createdTripId, state.currentStep) {
            if (state.createdTripId != null && state.currentStep == 3 && !state.isLoading && state.error == null) {
                navigator.replace(TripDetailScreen(state.createdTripId!!))
            }
        }

        LindaTheme {
            Scaffold(
                topBar = {
                    LindaHeader(
                        title = "Create Trip",
                        onBackClick = {
                            if (state.currentStep > 1) screenModel.onEvent(PreviousStep)
                            else navigator.pop()
                        }
                    )
                }
            ) { padding ->
                Column(modifier = Modifier.padding(padding).fillMaxSize()) {
                    LindaStepIndicator(
                        totalSteps  = 3,
                        currentStep = state.currentStep,
                        stepLabels  = listOf("Basic Info", "Destinations", "Preferences"),
                    )
                    when (state.currentStep) {
                        1 -> Step1BasicInfoContent(state, screenModel::onEvent)
                        2 -> Step2DestinationsContent(state, screenModel::onEvent)
                        3 -> Step3PreferencesContent(state, screenModel::onEvent)
                    }
                }
            }
        }
    }
}
```

**Step sub-composables** (`screens/createtrip/steps/`):

**`Step1BasicInfoContent.kt`** — renders `LindaTextField` (trip name), two `LindaDatePickerField` (start/end), `LindaButton` "Next" (disabled when `!state.isStep1Valid`).

**`Step2DestinationsContent.kt`** — renders `LindaSearchableDropdown` (countries), `LindaSearchableDropdown` (cities filtered), `LindaNumberStepper` (daysCount), `LindaButton` "Add Destination", `LindaDaysAllocationBar`, `LindaStatsRow` with 3 `LindaStatCard`s, `LazyColumn` of `LindaDestinationDraftCard`s, `LindaButton` "Next" (disabled when `!state.isStep2Valid`).

**`Step3PreferencesContent.kt`** — renders three `LindaSegmentedControl`s (travel style, budget, pace), `LindaChipGroup` (interests), `LindaTextField` multiline (specialRequirements, max 500), `LindaButton` "Create Trip" (PRIMARY, glow LIME).

---

### Step 10 — Wire Dependency Injection (Koin)

**Files to update:**
- `shared/src/commonMain/.../di/CommonModule.kt` — add `TripRepositoryImpl`, `DestinationRepositoryImpl`, use cases.
- `shared-ui/src/commonMain/.../di/UiModule.kt` — add `CreateTripScreenModel`.

```kotlin
// CommonModule additions
single<ITripRepository> { TripRepositoryImpl(get()) }
single<IDestinationRepository> { DestinationRepositoryImpl(get()) }
factory { CreateTripUseCase(get()) }
factory { GetCountriesUseCase(get()) }
factory { GetCitiesUseCase(get()) }

// UiModule additions
screenModel { CreateTripScreenModel(get(), get(), get(), get()) }
```

---

### Step 11 — Register Navigation Route

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/navigation/AppNavigation.kt`

`CreateTripScreen` is a Voyager `Screen` object, no explicit route registration needed — push with `navigator.push(CreateTripScreen())` from the trip list / home screen.

Ensure `TripDetailScreen(tripId: String)` exists or add a stub for the post-creation redirect.

---

### Step 12 — Write `commonTest` Tests

**File:** `shared-ui/src/commonTest/kotlin/com/jaarvi/ui/screens/createtrip/CreateTripScreenModelTest.kt`

```kotlin
class CreateTripScreenModelTest {
    // Test: NextStep on Step 1 with valid dates → moves to step 2
    // Test: NextStep on Step 1 with endDate <= startDate → sets step1Error
    // Test: NextStep on Step 1 with past startDate → sets step1Error
    // Test: AddDestination updates destinations list and recalculates allocatedDays
    // Test: NextStep on Step 2 with allocatedDays != totalTripDays → sets step2Error
    // Test: NextStep on Step 2 when fully allocated → moves to step 3
    // Test: RemoveDestination removes item and reorders dayOrder
    // Test: InterestToggled adds/removes from selectedInterests set
    // Test: CreateTrip with no createdTripId → no-op (defensive)
    // Test: CreateTrip on success → isLoading = false, error = null
    // Test: CountrySelected → loads cities via GetCitiesUseCase
}
```

Use `kotlinx-coroutines-test` (`runTest`, `TestScope`) and fake implementations of all use cases/repositories.

**File:** `shared/src/commonTest/kotlin/com/jaarvi/shared/domain/usecases/CreateTripUseCaseTest.kt`

```kotlin
class CreateTripUseCaseTest {
    // Test: valid dates → returns Trip from repository
    // Test: endDate == startDate → returns failure
    // Test: endDate before startDate → returns failure
    // Test: startDate in past → returns failure
    // Test: name > 100 chars → returns failure
    // Test: null name → passes through to repository
}
```

---

### Step 13 — Update Technical Documentation

- **`ai-specs/specs/api-spec.yml`**: already updated in backend plan — verify `POST /api/trips/{id}/planning-context` schema matches the frontend request body fields.
- **`ai-specs/specs/frontend-standards.mdc`**: add section on the 3-step wizard pattern, `DatePickerCapability` abstraction approach, and `FlowRow` chip usage.
- **`ai-specs/changes/mvp-03_frontend_linda.md`**: mark components as ✓ Done as they are implemented.

---

## 4. Implementation Order

1. Step 0 — Create feature branch
2. Step 1 — Domain models
3. Step 2 — Repository interfaces
4. Step 3 — DTOs + repository implementations
5. Step 4 — Use cases
6. Step 5 — `CreateTripUiState` + `CreateTripUiEvent`
7. Step 6 — `CreateTripScreenModel`
8. Step 7 — New Linda components (in dependency order per component build order)
9. Step 8 — `DatePickerCapability`
10. Step 9 — `CreateTripScreen` + 3 step sub-composables
11. Step 10 — DI wiring
12. Step 11 — Navigation registration
13. Step 12 — Tests
14. Step 13 — Documentation

---

## 5. Testing Checklist

- [ ] `CreateTripUseCaseTest`: 6 cases — valid, endDate invalid, endDate before start, past start, name too long, null name
- [ ] `CreateTripScreenModelTest`: 10 cases listed in Step 12
- [ ] `GetCountriesUseCase` + `GetCitiesUseCase`: repository result propagation
- [ ] Android Compose UI test: Step 1 → fill dates → tap Next → Step 2 renders
- [ ] Android Compose UI test: "Create Trip" button is disabled when `isLoading = true`
- [ ] Android Compose UI test: error message appears when `step1Error` is non-null
- [ ] `LindaStepIndicator` renders correct active/done/pending states
- [ ] `LindaDaysAllocationBar` shows correct state at 0%, partial, 100%, and over-100%
- [ ] `LindaSegmentedControl` reflects selected item visually
- [ ] `LindaChipGroup` toggles selection correctly on tap

---

## 6. Error Handling Patterns

| Error | Source | `UiState` field | UI rendering |
|---|---|---|---|
| Past `startDate` | `CreateTripUseCase` | `step1Error` | Red text below date field |
| Invalid date range | `CreateTripUseCase` | `step1Error` | Red text below End Date field |
| Days not fully allocated | ScreenModel | `step2Error` | Warning text on `LindaDaysAllocationBar` |
| City not found (404) | `TripRepositoryImpl` | `step2Error` | Snackbar/toast via `error` |
| Network failure | Any repository | `error` | Full-screen error with retry via `DismissError` event |

All errors propagated as `Result<T>` through the use case / repository chain; ScreenModel catches and maps to the appropriate `UiState` field. No exceptions bubble to composables.

---

## 7. UI/UX Considerations

- **Linda components used:** `LindaHeader`, `LindaButton`, `LindaProgressBar`, `LindaCard`, `LindaBadge`, `LindaStatCard`, `LindaStatsRow` (existing) + 9 new components (see `mvp-03_frontend_linda.md`).
- **Layout:** all screens `fillMaxSize()` with `Column` root; content in `LazyColumn` for Step 2 to handle many destinations without overflow.
- **Loading overlay:** when `isLoading = true`, the primary CTA button shows `isEnabled = false` and a `CircularProgressIndicator` replaces its label.
- **Days counter lock:** "Next" on Step 2 has `enabled = state.isStep2Valid`; `LindaDaysAllocationBar` provides visual feedback.
- **Validation timing:** Step 1 dates validate on "Next" tap (not as-you-type). `LindaTextField` character counter updates on every keystroke for trip name and special requirements.
- **Spacing:** all internal padding uses `LindaTheme.spacing`. Inter-section gaps: `spacing.xl` (24 dp). Card padding: `spacing.xxl` (handled by `LindaCard` internally).
- **Accessibility:** every `LindaIconButton` has `onClickLabel`; every form field has a `label`; `LindaStepIndicator` step circles have `contentDescription = "Step N of 3: label (status)"`.

---

## 8. Dependencies

| Library | Usage |
|---|---|
| `cafe.adriel.voyager:voyager-navigator` | Navigation + `ScreenModel` |
| `cafe.adriel.voyager:voyager-koin` | `getScreenModel<>()` in Compose |
| `io.ktor:ktor-client-core` | HTTP calls in repositories |
| `org.jetbrains.kotlinx:kotlinx-datetime` | `LocalDate`, `Instant` |
| `org.jetbrains.kotlinx:kotlinx-coroutines-core` | `StateFlow`, `screenModelScope` |
| `org.jetbrains.kotlinx:kotlinx-coroutines-test` | `runTest` in `commonTest` |
| `io.insert-koin:koin-core` | DI |
| `androidx.compose.foundation:foundation` | `FlowRow` for `LindaChipGroup` (CMP ≥ 1.6) |

No new Gradle dependencies beyond those already declared for the existing `shared` and `shared-ui` modules. Verify `kotlinx-datetime` is already in `shared/build.gradle.kts`; add it if missing.

---

## 9. Notes

- **No `android.*` imports in `commonMain`** — all platform edges (date picker dialog) go through `DatePickerCapability`.
- **`DestinationDraft.dayOrder` is managed client-side** — always `index + 1` based on list position; the remove handler reorders in-memory before any re-add.
- **Stub auth middleware** — the current backend stub hardcodes `test@jaarvi.app` user. Until `mvp-02` (real JWT) is merged, all API calls will use this user. The frontend sends whatever JWT the auth token provider supplies; during dev this can be any non-empty Bearer token.
- **Trip deletion of abandoned draft** — if the user cancels after Step 1 (trip already created in `draft`), the orphaned draft is not cleaned up at MVP. This is a known limitation; a background cleanup job or explicit cancel endpoint is deferred to `mvp-04`.
- **All code, comments, logs, and documentation in English only.**
- **Kotlin Gradle DSL** (`build.gradle.kts`) for all build files.

---

## 10. Next Steps After Implementation

- Wire `CreateTripScreen` launch from the Home / Trip List screen (entry point TBD in a future ticket).
- Implement `TripDetailScreen` (Screen 2) to receive the redirect after creation.
- Merge `mvp-02` to replace the auth stub with real JWT flow.
- Add integration tests once the auth stub is removed and a real test user can be provisioned.

---

## 11. Implementation Verification

- [ ] **Code quality:** no `android.*` in `commonMain`; Kotlin idioms; Linda DS used consistently in all new composables
- [ ] **Functionality:** all 3 steps navigate correctly; `isStep1Valid` / `isStep2Valid` gate "Next" accurately; `createdTripId` triggers navigation to Trip Detail
- [ ] **Testing:** `commonTest` covers all ScreenModel event → state transitions; use case validation rules; at least 1 Android Compose UI smoke test per step
- [ ] **Integration:** Koin module wires all use cases, repositories, and ScreenModel; Voyager `ScreenModel` lifecycle managed correctly (`screenModelScope` used for all coroutines)
- [ ] **Documentation:** `frontend-standards.mdc` updated; `mvp-03_frontend_linda.md` components marked done; API spec verified consistent with backend
