package com.jaarvi.ui.screens.createtrip

import com.jaarvi.shared.domain.models.City
import com.jaarvi.shared.domain.models.Country
import kotlinx.datetime.LocalDate

/** Interest categories a traveler can select for their trip. */
enum class TripInterest { ART, HISTORY, FOOD, CULTURE }

/**
 * Lightweight UI model representing a destination in the wizard list.
 *
 * Distinct from [com.jaarvi.shared.domain.models.TripDestination] — this only
 * lives in memory during the wizard flow.
 *
 * @param ordinal  1-based position in the itinerary.
 * @param cityName Display name of the city.
 * @param days     Number of days allocated to this stop.
 */
data class Destination(
    val ordinal : Int,
    val cityName: String,
    val days    : Int,
)

/**
 * Immutable state for the Create Trip wizard.
 *
 * All fields default to safe values; computed properties derive UI-ready values
 * from existing fields so the view layer never needs to do arithmetic.
 */
data class CreateTripUiState(
    val currentStep        : Int               = 1,

    // ── Step 1 — Basic info ──────────────────────────────────────────────────
    val tripName           : String            = "",
    val startDate          : LocalDate?        = null,
    val endDate            : LocalDate?        = null,
    val budget             : Float             = 2000f,
    val travelStyle        : Int               = 1,      // 0=Relaxed  1=Moderate  2=Intense
    val groupSize          : Int               = 2,
    val quickNotes         : String            = "",
    val step1Error         : String?           = null,

    // ── Step 2 — Destinations ────────────────────────────────────────────────
    val searchQuery        : String            = "",
    val activeChip         : String            = "",
    val destinations       : List<Destination> = emptyList(),
    val step2Error         : String?           = null,

    // ── Step 3 — Preferences ─────────────────────────────────────────────────
    val interestArt        : Boolean           = false,
    val interestHistory    : Boolean           = false,
    val interestFood       : Boolean           = true,
    val interestCulture    : Boolean           = false,
    val dailyPace          : String            = "Normal Day",

    // ── Internal (catalog data, not rendered directly) ───────────────────────
    val availableCountries : List<Country>     = emptyList(),
    val availableCities    : List<City>        = emptyList(),

    // ── Common ───────────────────────────────────────────────────────────────
    val isLoading          : Boolean           = false,
    val error              : String?           = null,
    val createdTripId      : String?           = null,
    val shouldNavigateBack : Boolean           = false,
) {
    private fun LocalDate.formatted(): String {
        val months = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
        )
        return "${months[monthNumber - 1]} $dayOfMonth"
    }

    /** Formatted start date string for display (e.g. "Dec 12"). */
    val startDateText: String get() = startDate?.formatted() ?: "Select"

    /** Formatted end date string for display. */
    val endDateText: String get() = endDate?.formatted() ?: "Select"

    /** Total days in the trip range inclusive of both endpoints. */
    val totalTripDays: Int
        get() = if (startDate != null && endDate != null)
            (endDate.toEpochDays() - startDate.toEpochDays() + 1).toInt().coerceAtLeast(0)
        else 0

    /** Alias used by Step 1 badge label. */
    val durationDays: Int get() = totalTripDays

    /** Alias used by Step 2 progress bar. */
    val tripTotalDays: Int get() = totalTripDays

    /** Total days allocated across all destinations. */
    val daysAllocated: Int get() = destinations.sumOf { it.days }

    /** Unallocated days remaining in the trip range. */
    val daysRemaining: Int get() = (totalTripDays - daysAllocated).coerceAtLeast(0)

    /** Step 1 is valid when both dates are set and end is strictly after start. */
    val isStep1Valid: Boolean
        get() = startDate != null && endDate != null && endDate > startDate

    /** Step 2 is valid when at least one destination has been added. */
    val isStep2Valid: Boolean
        get() = destinations.isNotEmpty()
}
