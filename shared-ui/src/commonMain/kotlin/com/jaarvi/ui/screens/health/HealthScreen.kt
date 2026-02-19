package com.jaarvi.ui.screens.health

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.jaarvi.ui.components.buttons.JaarviButton
import com.jaarvi.ui.components.buttons.JaarviTextButton
import com.jaarvi.ui.components.cards.StatusCard
import com.jaarvi.ui.components.cards.StatusCardVariant
import com.jaarvi.ui.components.loading.LoadingIndicator
import com.jaarvi.ui.theme.JaarviTheme
import com.jaarvi.ui.theme.Spacing

/**
 * Health Check screen that verifies backend connectivity.
 * Demonstrates full stack integration with loading, success, and error states.
 */
class HealthScreen : Screen {

    @Composable
    override fun Content() {
        val presenter: HealthPresenter = getScreenModel()
        val state by presenter.state.collectAsState()

        JaarviTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Jaarvi Health Check") }
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(Spacing.medium),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        state.isLoading -> {
                            LoadingContent()
                        }
                        state.isSuccess -> {
                            SuccessContent(
                                message = state.message ?: "",
                                timestamp = state.timestamp ?: "",
                                onCheckAgain = {
                                    presenter.onEvent(HealthUiEvent.CheckHealth)
                                }
                            )
                        }
                        state.isError -> {
                            ErrorContent(
                                error = state.error ?: "Unknown error",
                                onRetry = {
                                    presenter.onEvent(HealthUiEvent.Retry)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        LoadingIndicator()
        Text("Checking backend connectivity...")
    }
}

@Composable
private fun SuccessContent(
    message: String,
    timestamp: String,
    onCheckAgain: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        StatusCard(
            variant = StatusCardVariant.SUCCESS,
            title = "Backend Connected",
            message = message
        )
        
        Text(
            text = "Last checked: $timestamp",
            style = androidx.compose.material.MaterialTheme.typography.caption
        )
        
        Spacer(modifier = Modifier.height(Spacing.small))
        
        JaarviTextButton(
            text = "Check Again",
            onClick = onCheckAgain
        )
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        StatusCard(
            variant = StatusCardVariant.ERROR,
            title = "Connection Failed",
            message = error
        )
        
        Spacer(modifier = Modifier.height(Spacing.small))
        
        JaarviButton(
            text = "Retry",
            onClick = onRetry
        )
    }
}
