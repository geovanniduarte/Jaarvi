## ADDED Requirements

### Requirement: Step 1 — Collect basic trip info
The wizard SHALL present a first step where the user provides a trip name (optional), a start date, and an end date. Advancing requires both dates to be set with end after start.

#### Scenario: Advance from step 1 with valid dates
- **WHEN** the user has selected a valid `startDate` and `endDate` (end > start) and taps "Next"
- **THEN** the wizard calls `POST /api/trips`, stores the returned `tripId` in state, and transitions to step 2

#### Scenario: Advance blocked when dates are missing
- **WHEN** the user taps "Next" without selecting both dates
- **THEN** the wizard MUST NOT call the API and MUST display an inline validation message

#### Scenario: Advance blocked when end date is not after start date
- **WHEN** `endDate <= startDate` and the user taps "Next"
- **THEN** the wizard MUST NOT call the API and MUST display an inline error on the end date field

#### Scenario: Loading state during API call
- **WHEN** the wizard is calling `POST /api/trips`
- **THEN** the "Next" button SHALL be disabled and a loading indicator SHALL be visible

#### Scenario: API error on step 1
- **WHEN** `POST /api/trips` returns an error
- **THEN** the wizard MUST display the error message and remain on step 1

---

### Requirement: Step 2 — Add ordered destinations
The wizard SHALL present a second step where the user selects a country, then a city, sets a days count, and adds the destination to a list. The step is valid only when `allocatedDays == totalTripDays`.

#### Scenario: Country selection filters city list
- **WHEN** the user selects a country from the country picker
- **THEN** the wizard calls `GET /api/destinations/cities?countryId=<id>` and populates the city picker

#### Scenario: Add destination to the list
- **WHEN** the user has selected a city and a valid days count (≥ 1) and taps "Add"
- **THEN** a `DestinationDraft` is appended to the destination list with an auto-incremented `dayOrder`
- **AND** the city and country pickers reset to unselected
- **AND** the days counter resets to 1

#### Scenario: Remove destination from the list
- **WHEN** the user taps the remove button on a destination card
- **THEN** that destination is removed from the list
- **AND** `dayOrder` values are recalculated to remain contiguous starting at 1

#### Scenario: Advance blocked when allocated days differ from total trip days
- **WHEN** `allocatedDays ≠ totalTripDays` and the user taps "Next"
- **THEN** the wizard MUST NOT proceed and MUST display the remaining days count

#### Scenario: Advance from step 2 saves all destinations
- **WHEN** `allocatedDays == totalTripDays` and the user taps "Next"
- **THEN** the wizard calls `POST /api/trips/{id}/destinations` once per destination in `dayOrder` order
- **AND** on success, transitions to step 3

#### Scenario: Partial destination save failure
- **WHEN** one of the `POST /api/trips/{id}/destinations` calls fails
- **THEN** the wizard MUST display which destination failed and offer a retry without losing previously saved destinations

---

### Requirement: Step 3 — Set travel preferences
The wizard SHALL present a third step where the user optionally selects travel style, budget tier, pace, interests (multi-select), and special requirements text.

#### Scenario: All step 3 fields are optional
- **WHEN** the user taps "Create Trip" without filling any preference
- **THEN** the wizard calls `POST /api/trips/{id}/planning-context` with all fields null/empty and proceeds

#### Scenario: Interests multi-select toggles correctly
- **WHEN** the user taps an interest chip that is unselected
- **THEN** the interest is added to `selectedInterests`
- **WHEN** the user taps an already-selected interest chip
- **THEN** the interest is removed from `selectedInterests`

#### Scenario: Successful trip creation navigates to Trip Detail
- **WHEN** `POST /api/trips/{id}/planning-context` returns success
- **THEN** `createdTripId` is set in state
- **AND** the wizard navigates to the Trip Detail screen, replacing itself in the back stack

#### Scenario: API error on step 3
- **WHEN** `POST /api/trips/{id}/planning-context` returns an error
- **THEN** the wizard MUST display the error message and remain on step 3

---

### Requirement: Friendly error feedback on wizard action failures

Every button that commits data to the backend (Step 1 "Next", Step 2 "Add Destination", Step 2 "Next", Step 3 "Create Trip") SHALL surface a user-friendly error message when the call fails due to a network problem or an unexpected server response. Error messages MUST be non-technical: no HTTP status codes, no exception class names, no raw server payloads.

#### Error classification

| Class | Trigger | Friendly copy (default) |
|---|---|---|
| **No connectivity** | `IOException`, `UnknownHostException`, socket timeout | "No internet connection. Check your connection and try again." |
| **Server error** | HTTP 5xx or unexpected error body | "Something went wrong on our end. Please try again in a moment." |
| **Client error** | HTTP 4xx (other than validation) | "We couldn't process your request. Please review your data and try again." |
| **Validation error** | HTTP 422 with structured field errors | Inline field-level message derived from the API response body; if unparseable, fall back to the client-error copy above. |
| **Unknown** | Any other `Throwable` | "An unexpected error occurred. Please try again." |

#### Scenario: Network failure on any commit action
- **WHEN** any commit button is tapped and the device has no connectivity or the request times out
- **THEN** the wizard SHALL display the "No internet connection" message
- **AND** remain on the current step with all previously entered data intact
- **AND** the commit button SHALL be re-enabled so the user can retry without reloading the step

#### Scenario: Server error on any commit action
- **WHEN** any commit button is tapped and the server responds with a 5xx status or an unrecognised error body
- **THEN** the wizard SHALL display the "Something went wrong on our end" message
- **AND** remain on the current step with all previously entered data intact

#### Scenario: Error message placement
- **WHEN** an error occurs on Step 1 or Step 3
- **THEN** the error message SHALL be displayed as a highlighted banner **below the form fields and above the commit button**, using `colors.statusError` as the text colour and `colors.surfaceGlass` as background, so it is visible without scrolling to the bottom
- **WHEN** an error occurs on Step 2 during the sequential destination save
- **THEN** the error message SHALL identify which destination failed (by city name and day order) and be displayed inline in the same position

#### Scenario: Error is dismissed when the user retries
- **WHEN** the user taps the commit button after an error is visible
- **THEN** the error message SHALL be cleared immediately as the new loading state begins
- **AND** the button SHALL be disabled during the retry call

#### Scenario: Error is dismissed when the user edits a field
- **WHEN** any error message is visible and the user modifies any input field on the current step
- **THEN** the error message SHALL be cleared immediately (the user has taken corrective action)

#### Scenario: Loading state is always cleared on failure
- **WHEN** any commit call returns an error (any class)
- **THEN** `isLoading` SHALL be set to `false` before the error message is shown
- **AND** the commit button SHALL become re-enabled

---

### Requirement: Step navigation and progress indicator
The wizard SHALL display a visual step indicator and support backward navigation without losing data.

#### Scenario: Step indicator reflects current step
- **WHEN** the wizard is on step N (1, 2, or 3)
- **THEN** the `LindaStepIndicator` component highlights step N and shows completed steps

#### Scenario: Back navigation preserves state
- **WHEN** the user taps "Back" on step 2 or step 3
- **THEN** the wizard returns to the previous step
- **AND** all previously entered values for that step are retained in state

#### Scenario: Back from step 1 exits the wizard
- **WHEN** the user taps the system back button or close icon on step 1
- **THEN** the wizard is dismissed and the user returns to the previous screen
