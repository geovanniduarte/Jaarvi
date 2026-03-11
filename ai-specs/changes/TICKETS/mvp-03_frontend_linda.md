# Linda Component Specs — mvp-03: Create Trip with Destinations

## Overview

This document identifies the Linda Design System components required for the 3-step trip creation wizard (Screens 12 → 13 → 14). It separates **existing components** (already in `shared-ui/.../linda/components/`) from **new components** that must be built as part of this ticket.

---

## Existing Components Used (no changes needed)

| Component | File | Usage in wizard |
|---|---|---|
| `LindaHeader` | `Header.kt` | Page title "Create Trip" with back arrow on all 3 steps |
| `LindaButton` | `Button.kt` | "Next" (GLASS), "Add Destination" (GLASS), "Create Trip" (PRIMARY, GLOW_LIME) |
| `LindaProgressBar` | `ProgressBar.kt` | Used internally by `LindaDaysAllocationBar` |
| `LindaCard` | `Card.kt` | Wraps destination draft rows in Step 2 |
| `LindaBadge` | `Badge.kt` | Status chip: "DRAFT" badge on completion |
| `LindaStatCard` / `LindaStatsRow` | `StatCard.kt` | Days summary: "Allocated", "Total", "Remaining" in Step 2 |

---

## New Components to Build


**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/components/StepIndicator.kt`

**Purpose:** Horizontal 3-step progress stepper. Appears at the top of all 3 wizard screens below the `LindaHeader`.

**Wireframe:**

```
  ●━━━━━━━━━━━●━━━━━━━━━━━○
 [1]          [2]         [3]
Basic Info  Destinations Preferences
```

- **Step done (past):** Filled circle with gold-to-lime gradient fill + checkmark icon; connector line is gradient-filled.
- **Step active (current):** Filled circle with lime glow (`glowLime`) + step number in `onAccent`; connector line is muted.
- **Step pending (future):** Hollow circle with muted border; label in `textMuted`.

**Kotlin signature:**
```kotlin
@Composable
fun LindaStepIndicator(
    totalSteps  : Int,
    currentStep : Int,           // 1-indexed
    stepLabels  : List<String>,
    modifier    : Modifier = Modifier,
)
```

**Token usage:** `colors.accentGold`, `colors.accentLime`, `colors.glowLime`, `colors.textMuted`, `colors.textLabel`, `borders.radiusFull`, `spacing.sm`, `spacing.lg`

**Usage example:**
```kotlin
LindaStepIndicator(
    totalSteps  = 3,
    currentStep = state.currentStep,
    stepLabels  = listOf("Basic Info", "Destinations", "Preferences"),
    modifier    = Modifier
        .fillMaxWidth()
        .padding(horizontal = LindaTheme.spacing.xl, vertical = LindaTheme.spacing.lg),
)
```

---

### 2. `LindaTextField`

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/components/TextField.kt`

**Purpose:** Single-line text input with a glassmorphic surface, floating label, and optional character counter. Used for trip name in Step 1 and search in Step 2.

**Wireframe:**

```
┌─────────────────────────────┐
│ TRIP NAME               0/100│
│  My Summer Adventure...      │
└─────────────────────────────┘
```

- Background: `surfaceGlass` with `borderGlassStrong` border.
- Focused state: border switches to `accentLime`, thin lime glow.
- Counter text (`X/100`) in `textMuted`; turns `accentGold` when approaching max.
- Error state: border and counter turn red (`statusError`).

**Kotlin signature:**
```kotlin
@Composable
fun LindaTextField(
    value        : String,
    onValueChange: (String) -> Unit,
    label        : String,
    modifier     : Modifier = Modifier,
    placeholder  : String   = "",
    maxLength    : Int?     = null,   // null = no counter
    singleLine   : Boolean  = true,
    isError      : Boolean  = false,
    errorMessage : String?  = null,
    enabled      : Boolean  = true,
)
```

**Token usage:** `colors.surfaceGlass`, `colors.borderGlassStrong`, `colors.accentLime`, `colors.textMuted`, `colors.textPrimary`, `borders.radiusMd`, `spacing.md`, `spacing.lg`, `typography.bodyMedium`, `typography.labelSmall`

**Usage example:**
```kotlin
// Trip name field with character counter and error state
LindaTextField(
    value         = state.tripName,
    onValueChange = { onEvent(TripNameChanged(it)) },
    label         = "Trip Name",
    placeholder   = "e.g. France & Italy Spring",
    maxLength     = 100,
    isError       = state.tripName.length > 100,
    errorMessage  = if (state.tripName.length > 100) "Name must be 100 characters or fewer" else null,
    modifier      = Modifier.fillMaxWidth(),
)

// Special requirements — multi-line variant
LindaTextField(
    value         = state.specialRequirements,
    onValueChange = { onEvent(SpecialRequirementsChanged(it)) },
    label         = "Special Requirements",
    placeholder   = "e.g. Vegetarian meals, wheelchair access...",
    maxLength     = 500,
    singleLine    = false,
    modifier      = Modifier.fillMaxWidth(),
)
```

---

### 3. `LindaDatePickerField`

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/components/DatePickerField.kt`

**Purpose:** Tappable read-only field that displays a selected date. On tap, shows a bottom sheet date picker (platform-delegated via `DatePickerCapability`). Used for Start Date and End Date in Step 1.

**Wireframe:**

```
┌─────────────────────────────────┐
│ START DATE           📅          │
│  2026-07-01                      │
└─────────────────────────────────┘
   ↓ on tap → bottom sheet date picker
```

- Placeholder text "Select a date" in `textMuted` when no date selected.
- Selected date formatted as `MMM dd, yyyy` in `textPrimary`.
- Calendar icon (`Icons.Default.DateRange`) in `accentLime`.
- Error state: red border + error message below.

**Kotlin signature:**
```kotlin
@Composable
fun LindaDatePickerField(
    selectedDate : LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    label        : String,
    modifier     : Modifier   = Modifier,
    minDate      : LocalDate? = null,
    maxDate      : LocalDate? = null,
    isError      : Boolean    = false,
    errorMessage : String?    = null,
)
```

**Platform note:** Delegate the actual picker UI via `expect/actual` — `DatePickerCapability` interface with Android (`DatePickerDialog`) and iOS (`UIDatePicker`) implementations. The field composable itself is fully `commonMain`.

**Usage example:**
```kotlin
val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

// Start date — must be today or future
LindaDatePickerField(
    selectedDate  = state.startDate,
    onDateSelected = { onEvent(StartDateSelected(it)) },
    label         = "Start Date",
    minDate       = today,
    isError       = state.step1Error != null && state.startDate == null,
    errorMessage  = "Start date is required",
    modifier      = Modifier.fillMaxWidth(),
)

// End date — must be strictly after start date
LindaDatePickerField(
    selectedDate  = state.endDate,
    onDateSelected = { onEvent(EndDateSelected(it)) },
    label         = "End Date",
    minDate       = state.startDate?.plus(1, DateTimeUnit.DAY),
    isError       = state.step1Error != null && state.endDate != null
                        && state.endDate <= state.startDate,
    errorMessage  = "End date must be after start date",
    modifier      = Modifier.fillMaxWidth(),
)
```

---

### 4. `LindaSearchBox`

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/components/SearchBox.kt`

**Purpose:** A focused search input field that shows a clear ("×") button when text is present and supports a `collapsed` state (shows only the search icon). It is **not** responsible for displaying options — the caller pairs it with whichever overlay they choose (bottom sheet, inline list, etc.). Used in Step 2 inside a bottom sheet for Country and City filtering.

**Wireframe:**

```
 Expanded — text entered:
┌──────────────────────────────────┐
│ 🔍  France                    ✕  │  ← clear button appears
└──────────────────────────────────┘

 Collapsed:
    🔍                              ← icon only
```

**Kotlin signature:**
```kotlin
// Shape preset
class LindaSearchBoxShape private constructor(internal val value: Int) {
    companion object {
        val Rounded  : LindaSearchBoxShape = LindaSearchBoxShape(1)  // radiusFull pill
        val Rectangle: LindaSearchBoxShape = LindaSearchBoxShape(2)  // radiusMd rect
    }
}

// Size preset — controls height and icon/text sizing
class LindaSearchBoxSize private constructor(
    internal val height       : Dp,
    internal val iconSize     : Dp,
    internal val textStyle    : TextStyle,
) {
    companion object {
        @Composable fun small()  = LindaSearchBoxSize(
            height    = LindaTheme.spacing.xxl,
            iconSize  = LindaTheme.spacing.lg,
            textStyle = LindaTheme.typography.bodySmall,
        )
        @Composable fun medium() = LindaSearchBoxSize(
            height    = LindaTheme.spacing.xxxl,
            iconSize  = LindaTheme.spacing.xl,
            textStyle = LindaTheme.typography.bodyMedium,
        )
        @Composable fun large()  = LindaSearchBoxSize(
            height    = LindaTheme.spacing.xxxxl,
            iconSize  = LindaTheme.spacing.xxl,
            textStyle = LindaTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun LindaSearchBox(
    value          : String,
    onValueChange  : (String) -> Unit,
    modifier       : Modifier          = Modifier,
    shape          : LindaSearchBoxShape = LindaSearchBoxShape.Rounded,
    size           : LindaSearchBoxSize  = LindaSearchBoxSize.medium(),
    collapsed      : Boolean           = false,
    enabled        : Boolean           = true,
    onClearClicked : (() -> Unit)?     = null,   // when non-null, ✕ button shown if value.isNotEmpty()
    placeholderText: @Composable (() -> Unit)? = null,
)
```

**Behaviour notes:**
- When `collapsed = true` the field shrinks to show only the search icon; tapping it sets `collapsed = false` via the caller.
- The clear button is shown only when `onClearClicked != null && value.isNotEmpty()`.
- The component emits no events for option selection — that is entirely the caller's concern. Pair `LindaSearchBox` with a `LindaBottomSheet` or any other overlay for the results list.

**Token usage:** `colors.surfaceGlass`, `colors.borderGlassStrong`, `colors.accentLime`, `colors.textMuted`, `colors.textPrimary`, `borders.radiusFull`, `borders.radiusMd`, `spacing.md`, `spacing.lg`

**Usage example:**
```kotlin
// ── Country picker — LindaSearchBox inside a bottom sheet ─────────────────────
var countrySheetVisible by remember { mutableStateOf(false) }
var countryQuery        by remember { mutableStateOf("") }
val filteredCountries = remember(countryQuery, state.availableCountries) {
    state.availableCountries.filter { it.name.contains(countryQuery, ignoreCase = true) }
}

// 1. Trigger field — shows selected value or placeholder, tap opens the sheet
LindaSearchBox(
    value         = state.selectedCountry?.name ?: "",
    onValueChange = {},                          // read-only trigger; editing happens inside sheet
    collapsed     = false,
    enabled       = true,
    modifier      = Modifier.fillMaxWidth(),
    size          = LindaSearchBoxSize.medium(),
    shape         = LindaSearchBoxShape.Rounded,
    placeholderText = { Text("Select a country") },
)

// 2. Inside the bottom sheet — editable LindaSearchBox + results
if (countrySheetVisible) {
    LindaBottomSheet(onDismiss = { countrySheetVisible = false; countryQuery = "" }) {
        LindaSearchBox(
            value          = countryQuery,
            onValueChange  = { countryQuery = it },
            modifier       = Modifier
                .fillMaxWidth()
                .padding(horizontal = LindaTheme.spacing.lg, vertical = LindaTheme.spacing.md),
            size           = LindaSearchBoxSize.medium(),
            shape          = LindaSearchBoxShape.Rounded,
            onClearClicked = { countryQuery = "" },
            placeholderText = { Text("Search countries...") },
        )
        LazyColumn {
            items(filteredCountries) { country ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(CountrySelected(country))
                            countrySheetVisible = false
                            countryQuery        = ""
                        }
                        .padding(horizontal = LindaTheme.spacing.lg, vertical = LindaTheme.spacing.md),
                    horizontalArrangement = Arrangement.spacedBy(LindaTheme.spacing.sm),
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    Text(text = countryFlag(country.isoCode))
                    Text(
                        text  = country.name,
                        color = if (country == state.selectedCountry)
                                    LindaTheme.colors.accentLime
                                else LindaTheme.colors.textPrimary,
                        style = LindaTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}
```

---

### 5. `LindaStepper`

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/components/Stepper.kt`

**Purpose:** Stepper with "−" and "+" icon buttons flanking a **caller-defined value slot**. The component owns only the chrome (button layout, disabled styling, size tokens); **all value logic lives in the caller** — the component fires a single `onClick(isIncrement: Boolean)` and the caller decides how to clamp, increment, or decrement the value and whether each button should be enabled. Used for Days Count per destination in Step 2.

**Wireframe:**

```
  DAYS COUNT
  ┌───────────────────────┐
  │ −       5 days      + │
  └───────────────────────┘
  helper text (optional)
```

- "−" and "+" are `LindaIconButton` (GLASS variant); their enabled state is driven by `decrementEnabled` / `incrementEnabled`.
- Center area: fully caller-controlled `valueContent` slot.
- `label` and `helper` are composable slots — not strings — so the caller can use `LindaLabel`, `LindaHelper`, or any composable.

**Kotlin signature:**
```kotlin
// Size preset — controls button size and center-area min-width
class LindaStepperSize private constructor(
    internal val buttonSize  : Dp,
    internal val centerMinWidth: Dp,
) {
    companion object {
        @Composable fun small()  = LindaStepperSize(buttonSize = LindaTheme.spacing.xl,  centerMinWidth = LindaTheme.spacing.xxl)
        @Composable fun medium() = LindaStepperSize(buttonSize = LindaTheme.spacing.xxl, centerMinWidth = LindaTheme.spacing.xxxl)
        @Composable fun large()  = LindaStepperSize(buttonSize = LindaTheme.spacing.xxxl, centerMinWidth = LindaTheme.spacing.xxxxl)
    }
}

@Composable
fun LindaStepper(
    onClick           : (isIncrement: Boolean) -> Unit,
    valueContent      : @Composable () -> Unit,          // slot: caller renders current value
    modifier          : Modifier         = Modifier,
    size              : LindaStepperSize = LindaStepperSize.medium(),
    incrementEnabled  : Boolean          = true,
    decrementEnabled  : Boolean          = true,
    accessibilityValue: String           = "",            // read by screen readers
    label             : @Composable (() -> Unit)? = null,
    helper            : @Composable (() -> Unit)? = null,
)
```

**Behaviour notes:**
- The component calls `onClick(true)` when "+" is pressed, `onClick(false)` when "−" is pressed.
- It never reads or stores a value itself — that is always the caller's state.
- `incrementEnabled = false` disables and dims the "+" button; same for `decrementEnabled`.
- `accessibilityValue` is passed to the center area's `semantics { }` so screen readers announce the current value correctly.

**Token usage:** `colors.textMuted`, `spacing.md`, `spacing.lg`, `typography.labelSmall`

**Usage example:**
```kotlin
// Days count — "5 days" value, clamped between 1 and remaining days
var days             by remember { mutableStateOf(1) }
val maxDays           = state.totalTripDays - state.allocatedDays + days
var incrementEnabled by remember { mutableStateOf(days < maxDays) }
var decrementEnabled by remember { mutableStateOf(days > 1) }

LindaStepper(
    modifier           = Modifier.fillMaxWidth(),
    size               = LindaStepperSize.large(),
    accessibilityValue = "$days",
    label              = { Text(text = "Days Count", style = LindaTheme.typography.labelSmall,
                               color = LindaTheme.colors.textMuted) },
    helper             = if (days == maxDays) {
        { Text(text = "All remaining days allocated", color = LindaTheme.colors.accentLime) }
    } else null,
    incrementEnabled   = incrementEnabled,
    decrementEnabled   = decrementEnabled,
    onClick            = { isIncrement ->
        if (isIncrement) {
            if (days < maxDays) {
                days++
                onEvent(DaysCountChanged(days))
                decrementEnabled = days > 1
                incrementEnabled = days < maxDays
            }
        } else {
            if (days > 1) {
                days--
                onEvent(DaysCountChanged(days))
                decrementEnabled = days > 1
                incrementEnabled = days < maxDays
            }
        }
    },
    valueContent = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text  = "$days",
                style = LindaTheme.typography.displaySmall,
                color = LindaTheme.colors.textPrimary,
            )
            Text(
                text  = "days",
                style = LindaTheme.typography.labelSmall,
                color = LindaTheme.colors.textMuted,
            )
        }
    },
)
```

---

### 6. `LindaDestinationCard`

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/components/DestinationCard.kt`

**Purpose:** Card for a destination entry. Shows day order badge, city + country name, days count, an optional remove button, and a `LindaProgressBar` that reflects `executionPercentage`. The progress bar is always rendered — at `0f` it appears empty (draft state in Step 2), at `1f` it is fully filled (completed destination in trip detail). Used in Step 2 for staged destinations and reusable in any trip detail screen.

**Wireframe:**

```
┌────────────────────────────────────────────────┐
│  [1]  Paris, France                 5 days  ✕  │
│  ━━━━━━━━━━━━━━━░░░░░░░░░░░░░░░░░░░  60 %      │
└────────────────────────────────────────────────┘

 executionPercentage = 0f  →  bar empty  (draft, not started)
 executionPercentage = 0.6f →  60 % filled (in progress)
 executionPercentage = 1f  →  bar full + lime glow  (complete)
```

- Day order badge: small circle, `accentGold` text, `surfaceGlass` background.
- City name: `bodyMedium` `textPrimary`.
- Country name: `bodySmall` `textMuted`.
- Days count: `labelLarge` `accentLime` with lime glow.
- Progress bar: GRADIENT variant `(accentGold → accentLime)`; switches to full lime glow when `executionPercentage == 1f`.
- Percentage label: `labelSmall` `textMuted`; hidden when `executionPercentage == 0f`.
- Remove "✕": `LindaIconButton` GHOST — only rendered when `onRemove != null`.

**Kotlin signature:**
```kotlin
@Composable
fun LindaDestinationCard(
    dayOrder            : Int,
    cityName            : String,
    countryName         : String,
    daysCount           : Int,
    executionPercentage : Float,          // 0f..1f — drives the progress bar
    modifier            : Modifier = Modifier,
    onRemove            : (() -> Unit)? = null,   // null = no remove button (read-only mode)
)
```

**Behaviour notes:**
- `executionPercentage` is clamped internally to `0f..1f`; callers do not need to guard it.
- When `executionPercentage == 0f` the percentage label is hidden so the card looks clean in draft/planning mode (Step 2).
- `onRemove = null` switches the card to read-only mode — no "✕" button is rendered. Useful for trip detail screens where destinations cannot be removed.

**Token usage:** `colors.accentGold`, `colors.accentLime`, `colors.glowLime`, `colors.surfaceGlass`, `colors.textPrimary`, `colors.textMuted`, `borders.radiusMd`, `spacing.sm`, `spacing.md`, `spacing.lg`, `typography.bodyMedium`, `typography.bodySmall`, `typography.labelSmall`, `typography.labelLarge`

**Usage example:**
```kotlin
// Step 2 — draft destinations, 0 % progress, remove enabled
LazyColumn(verticalArrangement = Arrangement.spacedBy(LindaTheme.spacing.sm)) {
    items(
        items = state.destinations,
        key   = { it.dayOrder },
    ) { draft ->
        LindaDestinationCard(
            dayOrder            = draft.dayOrder,
            cityName            = draft.city.name,
            countryName         = draft.city.country.name,
            daysCount           = draft.daysCount,
            executionPercentage = 0f,
            onRemove            = { onEvent(RemoveDestination(draft.dayOrder)) },
            modifier            = Modifier.fillMaxWidth().animateItem(),
        )
    }
}

// Trip detail screen — live progress, no remove button
LazyColumn(verticalArrangement = Arrangement.spacedBy(LindaTheme.spacing.sm)) {
    items(
        items = trip.destinations,
        key   = { it.dayOrder },
    ) { destination ->
        LindaDestinationCard(
            dayOrder            = destination.dayOrder,
            cityName            = destination.city.name,
            countryName         = destination.city.country.name,
            daysCount           = destination.daysCount,
            executionPercentage = destination.completedDays.toFloat() / destination.daysCount,
            onRemove            = null,   // read-only
            modifier            = Modifier.fillMaxWidth(),
        )
    }
}
```

---

### 7. `LindaCompletion`

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/components/Completion.kt`

**Purpose:** Generic completion row for any event with an actual vs total status. Composed of three independent composable slots — `status`, `remaining`, and `progressBar` — laid out in a fixed vertical structure. The component owns the layout and spacing; all content is caller-defined. Used in Step 2 for trip day allocation and reusable for any completion context (activities, budget, tasks, etc.).

**Wireframe:**

```
┌──────────────────────────────────────────────────┐
│  13 of 14 days allocated       1 day remaining   │  ← status slot (left) + remaining slot (right)
│  ━━━━━━━━━━━━━━━━━━━━━━━━░░░░░░░░░░░░░░░░░░░░░  │  ← progressBar slot
└──────────────────────────────────────────────────┘
```

- `status` (left): the actual vs total summary — any composable (e.g. `"13 of 14 days allocated"`).
- `remaining` (right, end-aligned): the remaining quantity — any composable (e.g. `"1 day remaining"`).
- `progressBar`: any composable placed full-width below the status row (e.g. `LindaProgressBar`).
- Status row uses `Row` with `SpaceBetween` arrangement; `progressBar` is placed below with `spacing.sm` gap.

**Kotlin signature:**
```kotlin
@Composable
fun LindaCompletion(
    status     : @Composable () -> Unit,
    remaining  : @Composable () -> Unit,
    progressBar: @Composable () -> Unit,
    modifier   : Modifier = Modifier,
)
```

**Behaviour notes:**
- The component is purely a layout shell — it applies no colour, typography, or progress logic. Every visual decision belongs to the composables passed in the three slots.
- This makes `LindaCompletion` reusable for any domain: day allocation, budget spent, tasks completed, distance covered, etc.

**Token usage:** `spacing.sm`, `spacing.md` (layout gaps only — all colour/typography tokens belong to the slot content)

**Usage example:**
```kotlin
// Step 2 — trip day allocation
val allocated = state.allocatedDays
val total     = state.totalTripDays
val remaining = total - allocated
val progress  = (allocated.toFloat() / total).coerceIn(0f, 1f)

LindaCompletion(
    modifier = Modifier.fillMaxWidth(),
    status = {
        Text(
            text  = "$allocated of $total days allocated",
            style = LindaTheme.typography.labelSmall,
            color = when {
                allocated > total -> LindaTheme.colors.accentGold
                allocated == total -> LindaTheme.colors.accentLime
                else -> LindaTheme.colors.textMuted
            },
        )
    },
    remaining = {
        Text(
            text  = if (remaining > 0) "$remaining remaining"
                    else if (allocated == total) "Complete ✓"
                    else "Over by ${allocated - total}",
            style = LindaTheme.typography.labelSmall,
            color = when {
                allocated > total  -> LindaTheme.colors.accentGold
                allocated == total -> LindaTheme.colors.accentLime
                else               -> LindaTheme.colors.textMuted
            },
        )
    },
    progressBar = {
        LindaProgressBar(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
        )
    },
)

// Budget completion — same component, different domain
LindaCompletion(
    modifier = Modifier.fillMaxWidth(),
    status = {
        Text(
            text  = "$$spent of $$budget spent",
            style = LindaTheme.typography.labelSmall,
            color = LindaTheme.colors.textMuted,
        )
    },
    remaining = {
        Text(
            text  = "$$remaining left",
            style = LindaTheme.typography.labelSmall,
            color = if (remaining < 0) LindaTheme.colors.accentGold
                    else LindaTheme.colors.accentLime,
        )
    },
    progressBar = {
        LindaProgressBar(
            progress = (spent.toFloat() / budget).coerceIn(0f, 1f),
            modifier = Modifier.fillMaxWidth(),
        )
    },
)
```

---

### 8. `LindaSegmentedControl`

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/components/SegmentedControl.kt`

**Purpose:** Horizontal pill-group for single selection from a fixed set of options. Uses a **slot-based DSL** — the caller places `LindaSegment` children inside a builder lambda, keeping content fully flexible (icons, icons+text, text-only). Used in Step 3 for Travel Style, Budget, and Pace.

**Wireframe (Travel Style — 4 options):**

```
┌─────────────────────────────────────────────────┐
│  Adventurous  │ [Cultural] │  Relaxed  │ Mixed  │
└─────────────────────────────────────────────────┘
          ↑ selected: filled with gradient
```

- Unselected segment: `surfaceGlass` background, `textMuted` label.
- Selected segment: gold→lime gradient background, `onAccent` label, lime glow.
- Entire control: `LindaCard` GLASS container, `radiusFull` border.

**Kotlin signature:**
```kotlin
// Width distribution behaviour
class SegmentedControlWidthBehavior private constructor(internal val value: Int) {
    companion object {
        /** Each segment width adapts to its own content. */
        val Variable    : SegmentedControlWidthBehavior = SegmentedControlWidthBehavior(1)
        /** All segments share equal proportional width. */
        val Proportional: SegmentedControlWidthBehavior = SegmentedControlWidthBehavior(2)
    }
}

// Size preset — controls vertical/horizontal padding inside each segment
class SegmentedControlSize private constructor(internal val paddingV: Dp, internal val paddingH: Dp) {
    companion object {
        @Composable fun small()  = SegmentedControlSize(paddingV = LindaTheme.spacing.xs, paddingH = LindaTheme.spacing.sm)
        @Composable fun medium() = SegmentedControlSize(paddingV = LindaTheme.spacing.sm, paddingH = LindaTheme.spacing.md)
        @Composable fun large()  = SegmentedControlSize(paddingV = LindaTheme.spacing.md, paddingH = LindaTheme.spacing.lg)
    }
}

// Scope that declares the segment slot builder
interface LindaSegmentedControlScope {
    @Composable
    fun LindaSegment(
        selected: Boolean,
        onClick : () -> Unit,
        content : @Composable () -> Unit,
    )
}

// Root composable — content lambda is @Composable so LindaSegment can be called inside it
@Composable
fun LindaSegmentedControl(
    selectedSegment: Int,
    modifier       : Modifier                      = Modifier,
    widthBehavior  : SegmentedControlWidthBehavior = SegmentedControlWidthBehavior.Proportional,
    size           : SegmentedControlSize          = SegmentedControlSize.medium(),
    label          : String?                       = null,
    content        : @Composable LindaSegmentedControlScope.() -> Unit,
)
```

**Token usage:** `colors.surfaceGlass`, `colors.borderGlass`, `colors.accentGold`, `colors.accentLime`, `colors.onAccent`, `colors.textMuted`, `colors.glowLime`, `borders.radiusFull`, `spacing.xs`, `typography.labelLarge`

**Usage example:**
```kotlin
// Travel Style — 4 options, proportional width
val travelStyles = listOf("adventurous", "cultural", "relaxed", "mixed")
var selectedTravelStyleIndex by remember {
    mutableStateOf(travelStyles.indexOf(state.travelStyle).coerceAtLeast(0))
}

LindaSegmentedControl(
    selectedSegment = selectedTravelStyleIndex,
    widthBehavior   = SegmentedControlWidthBehavior.Proportional,
    size            = SegmentedControlSize.medium(),
    label           = "Travel Style",
    modifier        = Modifier.fillMaxWidth(),
) {
    travelStyles.forEachIndexed { index, style ->
        LindaSegment(
            selected = index == selectedTravelStyleIndex,
            onClick  = {
                selectedTravelStyleIndex = index
                onEvent(TravelStyleSelected(style))
            },
        ) {
            Text(text = style.replaceFirstChar { it.uppercase() })
        }
    }
}

// Budget — 3 options
val budgets = listOf("budget", "moderate", "premium")
var selectedBudgetIndex by remember {
    mutableStateOf(budgets.indexOf(state.budget).coerceAtLeast(0))
}

LindaSegmentedControl(
    selectedSegment = selectedBudgetIndex,
    widthBehavior   = SegmentedControlWidthBehavior.Proportional,
    size            = SegmentedControlSize.medium(),
    label           = "Budget",
    modifier        = Modifier.fillMaxWidth(),
) {
    budgets.forEachIndexed { index, budget ->
        LindaSegment(
            selected = index == selectedBudgetIndex,
            onClick  = {
                selectedBudgetIndex = index
                onEvent(BudgetSelected(budget))
            },
        ) {
            Text(text = budget.replaceFirstChar { it.uppercase() })
        }
    }
}

// Pace — 3 short labels, variable width
val paces = listOf("slow", "medium", "fast")
var selectedPaceIndex by remember {
    mutableStateOf(paces.indexOf(state.pace).coerceAtLeast(0))
}

LindaSegmentedControl(
    selectedSegment = selectedPaceIndex,
    widthBehavior   = SegmentedControlWidthBehavior.Variable,
    size            = SegmentedControlSize.medium(),
    label           = "Pace",
    modifier        = Modifier.fillMaxWidth(),
) {
    paces.forEachIndexed { index, pace ->
        LindaSegment(
            selected = index == selectedPaceIndex,
            onClick  = {
                selectedPaceIndex = index
                onEvent(PaceSelected(pace))
            },
        ) {
            Text(text = pace.replaceFirstChar { it.uppercase() })
        }
    }
}
```

---

### 9. `LindaCheckboxGroup`

**File:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/components/CheckboxGroup.kt`

**Purpose:** Wrapping multi-select group of checkboxes. Uses a **slot-based DSL** — consistent with `LindaSegmentedControl` — so each checkbox's content (icon + label, emoji + text, text-only) is fully caller-controlled. Uses `FlowRow` layout so items wrap naturally. Used in Step 3 for Interests selection.

**Wireframe:**

```
INTERESTS
  [☐ Museums]  [☑ Food]  [☑ Nature]  [☐ Nightlife]
  [☐ Shopping]  [☐ Outdoor]
```

- Unselected: `surfaceGlass` background, `borderGlass` border, `textMuted` content.
- Selected: gradient border (`accentGold` → `accentLime`), `accentLime` content + lime glow + checkmark icon prepended automatically by `LindaCheckbox`.

**Kotlin signature:**
```kotlin
// Scope that declares the checkbox slot builder
interface LindaCheckboxGroupScope {
    @Composable
    fun LindaCheckbox(
        checked : Boolean,
        onClick : () -> Unit,
        content : @Composable () -> Unit,
    )
}

// Root composable — content lambda is @Composable so LindaCheckbox can be called inside it
@Composable
fun LindaCheckboxGroup(
    label   : @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    content : @Composable LindaCheckboxGroupScope.() -> Unit,
)
```

**Note:** Requires `androidx.compose.foundation.layout.FlowRow` (available in Compose Multiplatform ≥ 1.6).

**Usage example:**
```kotlin
val allInterests = listOf("museums", "food", "nature", "nightlife", "shopping", "outdoor")

LindaCheckboxGroup(
    label    = { Text("Interests", style = LindaTheme.typography.labelSmall,
                      color = LindaTheme.colors.textMuted) },
    modifier = Modifier.fillMaxWidth(),
) {
    allInterests.forEach { interest ->
        LindaCheckbox(
            checked = interest in state.selectedInterests,
            onClick = { onEvent(InterestToggled(interest)) },
        ) {
            Text(text = interest.replaceFirstChar { it.uppercase() })
        }
    }
}

// Custom content — emoji + label per checkbox
LindaCheckboxGroup(
    label    = { Text("Interests", style = LindaTheme.typography.labelSmall,
                      color = LindaTheme.colors.textMuted) },
    modifier = Modifier.fillMaxWidth(),
) {
    val interestIcons = mapOf(
        "museums"   to "🏛️",
        "food"      to "🍽️",
        "nature"    to "🌿",
        "nightlife" to "🎶",
        "shopping"  to "🛍️",
        "outdoor"   to "🏔️",
    )
    interestIcons.forEach { (interest, emoji) ->
        LindaCheckbox(
            checked = interest in state.selectedInterests,
            onClick = { onEvent(InterestToggled(interest)) },
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(LindaTheme.spacing.xs),
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Text(text = emoji)
                Text(text = interest.replaceFirstChar { it.uppercase() })
            }
        }
    }
}
```

---

## Component Dependency Map by Screen

### Screen 12 — Basic Info (Step 1)

```
CreateTripScreen (Step 1)
├── LindaHeader               [existing]   title="Create Trip", onBackClick
├── LindaStepIndicator        [NEW]        currentStep=1, labels=["Basic Info","Destinations","Preferences"]
├── LindaTextField            [NEW]        tripName, maxLength=100
├── LindaDatePickerField      [NEW]        startDate, minDate=today
├── LindaDatePickerField      [NEW]        endDate, minDate=startDate+1
└── LindaButton               [existing]   "Next", variant=GLASS, disabled if dates invalid
```

### Screen 13 — Add Destinations (Step 2)

```
CreateTripScreen (Step 2)
├── LindaHeader               [existing]   onBackClick → go to Step 1
├── LindaStepIndicator        [NEW]        currentStep=2
├── LindaSearchBox            [NEW]        country trigger field + editable box inside bottom sheet
├── LindaSearchBox            [NEW]        city trigger field + editable box inside bottom sheet
├── LindaStepper              [NEW]        daysCount for new destination
├── LindaButton               [existing]   "Add Destination", variant=GLASS
├── LindaCompletion           [NEW]        status + remaining + progressBar slots for day allocation
├── LindaStatsRow             [existing]   "Allocated", "Total", "Remaining"
│   ├── LindaStatCard         [existing]   allocated days
│   ├── LindaStatCard         [existing]   total days
│   └── LindaStatCard         [existing]   remaining days
├── LindaDestinationCard      [NEW]        ×N (one per added destination, executionPercentage=0f)
└── LindaButton               [existing]   "Next", variant=GLASS, disabled until fully allocated
```

### Screen 14 — Preferences (Step 3)

```
CreateTripScreen (Step 3)
├── LindaHeader               [existing]   onBackClick → go to Step 2
├── LindaStepIndicator        [NEW]        currentStep=3
├── LindaSegmentedControl     [NEW]        travelStyle: adventurous/cultural/relaxed/mixed
├── LindaSegmentedControl     [NEW]        budget: budget/moderate/premium
├── LindaSegmentedControl     [NEW]        pace: slow/medium/fast
├── LindaCheckboxGroup        [NEW]        interests: museums/food/nature/nightlife/shopping/outdoor
├── LindaTextField            [NEW]        specialRequirements, maxLength=500, singleLine=false
└── LindaButton               [existing]   "Create Trip", variant=PRIMARY, glow=LIME
```

---

## Summary Table

| Component | Status | File to Create |
|---|---|---|
| `LindaHeader` | Existing | — |
| `LindaButton` | Existing | — |
| `LindaProgressBar` | Existing | — |
| `LindaCard` | Existing | — |
| `LindaBadge` | Existing | — |
| `LindaStatCard` / `LindaStatsRow` | Existing | — |
| `LindaStepIndicator` | **NEW** | `StepIndicator.kt` |
| `LindaTextField` | **NEW** | `TextField.kt` |
| `LindaDatePickerField` | **NEW** | `DatePickerField.kt` |
| `LindaSearchBox` | **NEW** | `SearchBox.kt` |
| `LindaStepper` | **NEW** | `Stepper.kt` |
| `LindaDestinationCard` | **NEW** | `DestinationCard.kt` |
| `LindaCompletion` | **NEW** | `Completion.kt` |
| `LindaSegmentedControl` | **NEW** | `SegmentedControl.kt` |
| `LindaCheckboxGroup` | **NEW** | `CheckboxGroup.kt` |