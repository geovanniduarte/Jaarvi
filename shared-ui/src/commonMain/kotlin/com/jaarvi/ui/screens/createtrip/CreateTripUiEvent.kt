package com.jaarvi.ui.screens.createtrip

import com.jaarvi.shared.domain.models.City
import com.jaarvi.shared.domain.models.Country
import kotlinx.datetime.LocalDate

/** All user intents that can occur during the Create Trip wizard. */
sealed class CreateTripUiEvent {

    // ── Step 1 — Basic info ──────────────────────────────────────────────────

    /** User updated the trip name field. */
    data class TripNameChanged(val name: String) : CreateTripUiEvent()

    /** User selected a start date from the date picker. */
    data class StartDateSelected(val date: LocalDate) : CreateTripUiEvent()

    /** User selected an end date from the date picker. */
    data class EndDateSelected(val date: LocalDate) : CreateTripUiEvent()

    /** User moved the budget slider. */
    data class BudgetChanged(val budget: Float) : CreateTripUiEvent()

    /** User tapped a travel-style segment (0 = Relaxed, 1 = Moderate, 2 = Intense). */
    data class TravelStyleChanged(val style: Int) : CreateTripUiEvent()

    /** User changed the group size via the counter. */
    data class GroupSizeChanged(val size: Int) : CreateTripUiEvent()

    /** User edited the quick-notes text field. */
    data class QuickNotesChanged(val text: String) : CreateTripUiEvent()

    // ── Navigation ───────────────────────────────────────────────────────────

    /** User tapped "Next" to advance to the next step. */
    data object NextStep : CreateTripUiEvent()

    /** User tapped "Back" — goes to previous step or pops the screen from step 1. */
    data object Back : CreateTripUiEvent()

    /** User tapped "Cancel" — always pops the screen. */
    data object Cancel : CreateTripUiEvent()

    // ── Step 2 — Destinations ────────────────────────────────────────────────

    /** User typed in the destination search box. */
    data class SearchQueryChanged(val query: String) : CreateTripUiEvent()

    /** User tapped a suggested city chip — toggles the city in/out of destinations. */
    data class ActiveChipChanged(val city: String) : CreateTripUiEvent()

    /** User adjusted the day count of destination at [index]. */
    data class DestinationDaysChanged(val index: Int, val days: Int) : CreateTripUiEvent()

    /** User removed destination at [index]. */
    data class DestinationRemoved(val index: Int) : CreateTripUiEvent()

    // ── Step 3 — Preferences ─────────────────────────────────────────────────

    /** User toggled an interest row. [checked] is the new state of the toggle. */
    data class InterestToggled(val interest: TripInterest, val checked: Boolean) : CreateTripUiEvent()

    /** User selected a daily pace option. */
    data class DailyPaceChanged(val pace: String) : CreateTripUiEvent()

    /** User tapped "Create Trip" on step 3. */
    data object CreateTrip : CreateTripUiEvent()

    // ── Common ───────────────────────────────────────────────────────────────

    /** User dismissed an error snackbar or dialog. */
    data object DismissError : CreateTripUiEvent()
}
