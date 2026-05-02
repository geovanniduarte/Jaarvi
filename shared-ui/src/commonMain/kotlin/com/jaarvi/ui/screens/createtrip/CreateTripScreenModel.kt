package com.jaarvi.ui.screens.createtrip

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jaarvi.shared.domain.repositories.ITripRepository
import com.jaarvi.shared.domain.usecases.CreateTripUseCase
import com.jaarvi.shared.domain.usecases.GetCitiesUseCase
import com.jaarvi.shared.domain.usecases.GetCountriesUseCase
import com.jaarvi.ui.screens.createtrip.CreateTripUiEvent.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ScreenModel for the Create Trip wizard.
 *
 * A single model owns state across all 3 steps so data produced in earlier
 * steps (e.g. the `tripId` from step 1) is available in later steps.
 *
 * @property createTripUseCase   Validates and creates the trip via the API.
 * @property getCountriesUseCase Pre-fetches the country catalog for city lookup.
 * @property getCitiesUseCase    Fetches cities lazily (used for city-ID resolution).
 * @property tripRepository      Used for destination and planning-context calls.
 */
class CreateTripScreenModel(
    private val createTripUseCase  : CreateTripUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCitiesUseCase   : GetCitiesUseCase,
    private val tripRepository     : ITripRepository,
) : ScreenModel {

    private val _state = MutableStateFlow(CreateTripUiState())
    val state: StateFlow<CreateTripUiState> = _state.asStateFlow()

    /** Call once from a `LaunchedEffect(Unit)` in the Screen to pre-fetch catalog data. */
    fun start() {
        loadCountries()
    }

    /** Entry point for all user interactions. */
    fun onEvent(event: CreateTripUiEvent) {
        when (event) {
            // ── Step 1 ──────────────────────────────────────────────────────
            is TripNameChanged      -> _state.update { it.copy(tripName = event.name, step1Error = null) }
            is StartDateSelected    -> _state.update { it.copy(startDate = event.date, step1Error = null) }
            is EndDateSelected      -> _state.update { it.copy(endDate = event.date, step1Error = null) }
            is BudgetChanged        -> _state.update { it.copy(budget = event.budget, error = null) }
            is TravelStyleChanged   -> _state.update { it.copy(travelStyle = event.style, error = null) }
            is GroupSizeChanged     -> _state.update { it.copy(groupSize = event.size.coerceIn(1, 50)) }
            is QuickNotesChanged    -> _state.update { it.copy(quickNotes = event.text, error = null) }

            // ── Navigation ──────────────────────────────────────────────────
            is NextStep             -> handleNextStep()
            is Back                 -> handleBack()
            is Cancel               -> _state.update { it.copy(shouldNavigateBack = true) }

            // ── Step 2 ──────────────────────────────────────────────────────
            is SearchQueryChanged   -> _state.update { it.copy(searchQuery = event.query, step2Error = null) }
            is ActiveChipChanged    -> handleActiveChipChanged(event.city)
            is DestinationDaysChanged -> handleDestinationDaysChanged(event.index, event.days)
            is DestinationRemoved   -> handleDestinationRemoved(event.index)

            // ── Step 3 ──────────────────────────────────────────────────────
            is InterestToggled      -> handleInterestToggle(event.interest, event.checked)
            is DailyPaceChanged     -> _state.update { it.copy(dailyPace = event.pace, error = null) }
            is CreateTrip           -> handleCreateTrip()

            // ── Common ──────────────────────────────────────────────────────
            is DismissError         -> _state.update { it.copy(error = null, step1Error = null, step2Error = null) }
        }
    }

    // ── Private handlers ──────────────────────────────────────────────────────

    private fun handleNextStep() {
        val s = _state.value
        when (s.currentStep) {
            1 -> {
                if (!s.isStep1Valid) {
                    _state.update { it.copy(step1Error = "Please provide valid start and end dates") }
                    return
                }
                screenModelScope.launch {
                    _state.update { it.copy(isLoading = true, step1Error = null, error = null) }
                    createTripUseCase.invoke(
                        name      = s.tripName.ifBlank { null },
                        startDate = s.startDate!!,
                        endDate   = s.endDate!!,
                    ).onSuccess { trip ->
                        _state.update { it.copy(isLoading = false, currentStep = 2, createdTripId = trip.id) }
                    }.onFailure { e ->
                        _state.update { it.copy(isLoading = false, step1Error = e.toFriendlyMessage()) }
                    }
                }
            }
            2 -> {
                if (!_state.value.isStep2Valid) {
                    _state.update { it.copy(step2Error = "Add at least one destination before continuing") }
                    return
                }
                _state.update { it.copy(currentStep = 3, step2Error = null) }
            }
        }
    }

    private fun handleBack() {
        val step = _state.value.currentStep
        if (step > 1)
            _state.update { it.copy(currentStep = it.currentStep - 1) }
        else
            _state.update { it.copy(shouldNavigateBack = true) }
    }

    private fun handleActiveChipChanged(cityName: String) {
        _state.update { s ->
            val existingIndex = s.destinations.indexOfFirst { it.cityName == cityName }
            val updated = if (existingIndex >= 0) {
                // Remove — city already in list
                s.destinations
                    .filterIndexed { i, _ -> i != existingIndex }
                    .mapIndexed { i, d -> d.copy(ordinal = i + 1) }
            } else {
                // Add with default 3 days
                s.destinations + Destination(
                    ordinal  = s.destinations.size + 1,
                    cityName = cityName,
                    days     = 3,
                )
            }
            s.copy(
                activeChip   = if (s.activeChip == cityName) "" else cityName,
                destinations = updated,
                step2Error   = null,
            )
        }
    }

    private fun handleDestinationDaysChanged(index: Int, days: Int) {
        _state.update { s ->
            val updated = s.destinations.toMutableList().also { list ->
                if (index in list.indices) list[index] = list[index].copy(days = days.coerceAtLeast(1))
            }.toList()
            s.copy(destinations = updated)
        }
    }

    private fun handleDestinationRemoved(index: Int) {
        _state.update { s ->
            val updated = s.destinations
                .filterIndexed { i, _ -> i != index }
                .mapIndexed { i, d -> d.copy(ordinal = i + 1) }
            s.copy(destinations = updated)
        }
    }

    private fun handleInterestToggle(interest: TripInterest, checked: Boolean) {
        _state.update { s ->
            when (interest) {
                TripInterest.ART     -> s.copy(interestArt     = checked)
                TripInterest.HISTORY -> s.copy(interestHistory = checked)
                TripInterest.FOOD    -> s.copy(interestFood    = checked)
                TripInterest.CULTURE -> s.copy(interestCulture = checked)
            }
        }
    }

    private fun handleCreateTrip() {
        val tripId = _state.value.createdTripId ?: return
        val s = _state.value

        val travelStyleStr = when (s.travelStyle) {
            0    -> "relaxed"
            2    -> "adventurous"
            else -> "mixed"
        }
        val budgetStr = when {
            s.budget < 1000f -> "budget"
            s.budget < 3000f -> "moderate"
            else             -> "premium"
        }
        val paceStr = when (s.dailyPace) {
            "Relaxed"         -> "slow"
            "Active Explorer" -> "fast"
            else              -> "medium"
        }
        val interests = buildList {
            if (s.interestArt)     add("art")
            if (s.interestHistory) add("history")
            if (s.interestFood)    add("food")
            if (s.interestCulture) add("culture")
        }

        screenModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            tripRepository.savePlanningContext(
                tripId              = tripId,
                travelStyle         = travelStyleStr,
                budget              = budgetStr,
                pace                = paceStr,
                interests           = interests,
                specialRequirements = s.quickNotes.ifBlank { null },
            ).onSuccess {
                _state.update { it.copy(isLoading = false) }
                // createdTripId is already set; CreateTripScreen observes it and navigates
            }.onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.toFriendlyMessage()) }
            }
        }
    }

    private fun loadCountries() {
        screenModelScope.launch {
            getCountriesUseCase.invoke()
                .onSuccess { countries ->
                    _state.update { it.copy(availableCountries = countries) }
                    // Pre-fetch cities for all countries to enable city-name → ID resolution
                    countries.firstOrNull()?.let { loadCitiesForCountry(it.id) }
                }
                .onFailure { /* catalog is non-blocking; silently ignored */ }
        }
    }

    private fun loadCitiesForCountry(countryId: String) {
        screenModelScope.launch {
            getCitiesUseCase.invoke(countryId)
                .onSuccess { cities -> _state.update { it.copy(availableCities = cities) } }
                .onFailure { /* non-blocking */ }
        }
    }
}
