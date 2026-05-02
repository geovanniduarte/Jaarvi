package com.jaarvi.ui.screens.createtrip

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jaarvi.ui.linda.theme.LindaTheme
import com.jaarvi.ui.screens.createtrip.steps.Step1BasicInfoContent
import com.jaarvi.ui.screens.createtrip.steps.Step2DestinationsContent
import com.jaarvi.ui.screens.createtrip.steps.Step3PreferencesContent
import com.jaarvi.ui.screens.tripdetail.TripDetailScreen

/**
 * Root Voyager screen for the 3-step Create Trip wizard.
 *
 * Each step composable owns its own [Scaffold] with a header and bottom bar;
 * this screen is a thin router that collects state and delegates rendering.
 * Navigation side-effects ([shouldNavigateBack], [createdTripId]) are observed
 * via [LaunchedEffect].
 */
class CreateTripScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel: CreateTripScreenModel = getScreenModel()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) { screenModel.start() }

        // Cancel / Back-from-step-1 → pop this screen
        LaunchedEffect(state.shouldNavigateBack) {
            if (state.shouldNavigateBack) navigator.pop()
        }

        // Trip created → navigate to Trip Detail
        LaunchedEffect(state.createdTripId, state.currentStep, state.isLoading) {
            if (state.createdTripId != null &&
                state.currentStep == 3 &&
                !state.isLoading &&
                state.error == null
            ) {
                navigator.replace(TripDetailScreen(state.createdTripId!!))
            }
        }

        LindaTheme {
            when (state.currentStep) {
                1 -> Step1BasicInfoContent(
                    state    = state,
                    onEvent  = screenModel::onEvent,
                    modifier = Modifier.fillMaxSize(),
                )
                2 -> Step2DestinationsContent(
                    state    = state,
                    onEvent  = screenModel::onEvent,
                    modifier = Modifier.fillMaxSize(),
                )
                3 -> Step3PreferencesContent(
                    state    = state,
                    onEvent  = screenModel::onEvent,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
