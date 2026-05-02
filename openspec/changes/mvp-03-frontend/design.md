## Context

The Jaarvi KMP project already has a working scaffold (Voyager navigation, Koin DI, Ktor client, Linda design system). The backend exposes `POST /api/trips`, `POST /api/trips/{id}/destinations`, `POST /api/trips/{id}/planning-context`, `GET /api/destinations/countries`, and `GET /api/destinations/cities`. The task is to wire a 3-step CMP wizard to those endpoints, keeping all business logic in the shared `shared/` module and all UI in the `shared-ui/` module.

## Goals / Non-Goals

**Goals:**
- Implement a 3-step wizard (`CreateTripScreen`) that creates a trip, adds destinations, and saves preferences through sequential API calls.
- Keep all business logic (validation, use cases, repository) in `shared/` (KMP), shared by Android and iOS.
- Keep all UI in `shared-ui/` (CMP), reused by both platforms.
- Follow the established `StateFlow` + `UiEvent` + Voyager `ScreenModel` pattern.
- Use only Linda design-system components (no raw Material tokens in wizard screens).

**Non-Goals:**
- Editing or deleting an existing trip (separate feature).
- AI itinerary generation (triggered after creation, not part of this wizard).
- Offline support or local persistence of the draft.
- Dark/light theme switching (Linda tokens handle this transparently).

## Decisions

### D1 — Single ScreenModel for all 3 steps
**Decision:** One `CreateTripScreenModel` manages `CreateTripUiState` with a `currentStep: Int` field. Sub-composables (`Step1BasicInfoContent`, `Step2DestinationsContent`, `Step3PreferencesContent`) receive state slices and emit `CreateTripUiEvent`.

**Alternatives considered:**
- One ScreenModel per step — rejected; requires passing `tripId` across models and coordinating navigation between three presenters.
- Step state stored in `SavedStateHandle` — rejected; not available in KMP shared code.

**Rationale:** Steps share state (e.g., `tripId` created in step 1 is needed in steps 2 and 3). A single model eliminates cross-step coupling.

---

### D2 — `CreateTripUseCase` owns client-side validation; repository does none
**Decision:** `CreateTripUseCase` validates `endDate > startDate`, `startDate >= today`, and `name.length ≤ 100` before calling the repository. `TripRepositoryImpl` passes requests to the API without re-validating.

**Rationale:** Keeps validation logic unit-testable without Ktor; repository tests only need to verify HTTP mapping.

---

### D3 — Sequential API calls on wizard completion, not on each step transition
**Decision:**
- Step 1 "Next" → calls `POST /api/trips` immediately and stores the returned `tripId` in state. Without a real `tripId`, step 2 cannot add destinations.
- Step 2 "Next" → calls `POST /api/trips/{id}/destinations` once per added destination (sequentially, not parallel).
- Step 3 "Create" → calls `POST /api/trips/{id}/planning-context`, then navigates to Trip Detail.

**Alternatives considered:**
- Batch all calls only at the final step — rejected; without a `tripId` the destination API cannot be called.
- Parallel destination calls — rejected; day order must be deterministic; backend does not guarantee order for concurrent requests.

---

### D4 — Country/city data fetched eagerly on step 2 entry
**Decision:** `GetCountriesUseCase` is called when `currentStep` transitions to 2. Cities are fetched lazily when a country is selected (`GetCitiesUseCase(countryId)`).

**Rationale:** Country list is small and rarely changes. Fetching upfront removes latency from the selection UX.

---

### D5 — `DestinationDraft` lives in `shared-ui` UiState, not in `shared` domain
**Decision:** `DestinationDraft(city, daysCount, dayOrder)` is a UI-only data class inside `CreateTripUiState`. The domain `TripDestination` is only created when the API responds.

**Rationale:** The wizard may be cancelled before any destination is persisted; keeping the draft in UI state avoids polluting the domain layer with ephemeral data.

---

### D6 — Linda Level-2 components wrap wizard sections
**Decision:** Wizard step bodies use `LindaSelector`, `LindaDateSelectors`, `LindaBudgetSlider`, `LindaItemCounter`, `LindaRow`, and `LindaCompletion` (level 2) as the primary building blocks, backed by level-1 primitives.

**Rationale:** Maximises design-system reuse and keeps step composables declarative (< 150 lines each).

## Risks / Trade-offs

- **Sequential destination POST calls** → If the user adds many destinations and the network drops mid-way, some will be persisted and some won't. Mitigation: show per-destination loading state; on failure display which destinations failed and offer retry.
- **No draft persistence** → If the app is killed during step 2 or 3, partial data (the created `tripId`) is lost. Mitigation: V1 accepts this; V2 can add `datastore` persistence of the draft.
- **Country/city list size** → If the catalog grows to thousands of entries, fetching all countries upfront may be slow. Mitigation: `LindaSearchBox` in the picker provides client-side filtering; server-side pagination can be added later.
- **Step 2 validation (allocatedDays == totalTripDays)** → This constraint may feel rigid to users who haven't decided exact days. Mitigation: display the remaining-days counter prominently; consider relaxing to `allocatedDays ≤ totalTripDays` in a follow-up.
