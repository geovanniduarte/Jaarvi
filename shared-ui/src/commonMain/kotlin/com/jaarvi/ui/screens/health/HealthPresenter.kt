package com.jaarvi.ui.screens.health

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jaarvi.shared.domain.repositories.HealthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Presenter (ScreenModel) for the Health Check screen.
 * Manages UI state and handles user events using unidirectional data flow.
 *
 * @property repository Repository for health check operations
 */
class HealthPresenter(
    private val repository: HealthRepository
) : ScreenModel {

    private val _state = MutableStateFlow(HealthUiState())
    val state: StateFlow<HealthUiState> = _state.asStateFlow()

    init {
        checkHealth()
    }

    /**
     * Handles user events from the UI.
     *
     * @param event The event triggered by user interaction
     */
    fun onEvent(event: HealthUiEvent) {
        when (event) {
            HealthUiEvent.CheckHealth -> checkHealth()
            HealthUiEvent.Retry -> checkHealth()
        }
    }

    private fun checkHealth() {
        screenModelScope.launch {
            _state.value = HealthUiState(isLoading = true)

            repository.getHealth().fold(
                onSuccess = { healthStatus ->
                    _state.value = HealthUiState(
                        isLoading = false,
                        message = healthStatus.message,
                        timestamp = healthStatus.timestamp,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _state.value = HealthUiState(
                        isLoading = false,
                        message = null,
                        timestamp = null,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }
}
