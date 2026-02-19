package com.jaarvi.ui.screens.health

import app.cash.turbine.test
import com.jaarvi.shared.domain.models.HealthStatus
import com.jaarvi.shared.domain.repositories.HealthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Unit tests for HealthPresenter.
 * Tests verify state transitions using Turbine for StateFlow testing.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class HealthPresenterTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() = runTest {
        // Arrange
        val mockRepository = MockHealthRepository()
        val presenter = HealthPresenter(mockRepository)

        // Act & Assert
        presenter.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `successful health check updates state to success`() = runTest {
        // Arrange
        val mockRepository = MockHealthRepository(
            successResponse = HealthStatus(
                message = "Backend is healthy",
                timestamp = "2024-01-01T12:00:00Z"
            )
        )
        val presenter = HealthPresenter(mockRepository)

        // Act & Assert
        presenter.state.test {
            // Skip initial loading state
            awaitItem()
            
            testDispatcher.scheduler.advanceUntilIdle()
            
            val successState = awaitItem()
            assertTrue(successState.isSuccess)
            assertEquals("Backend is healthy", successState.message)
            assertFalse(successState.isLoading)
            assertEquals(null, successState.error)
            
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `failed health check updates state to error`() = runTest {
        // Arrange
        val mockRepository = MockHealthRepository(shouldFail = true)
        val presenter = HealthPresenter(mockRepository)

        // Act & Assert
        presenter.state.test {
            // Skip initial loading state
            awaitItem()
            
            testDispatcher.scheduler.advanceUntilIdle()
            
            val errorState = awaitItem()
            assertTrue(errorState.isError)
            assertFalse(errorState.isLoading)
            assertEquals("Network error", errorState.error)
            assertEquals(null, errorState.message)
            
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `retry event triggers new health check`() = runTest {
        // Arrange
        val mockRepository = MockHealthRepository(shouldFail = true)
        val presenter = HealthPresenter(mockRepository)

        // Act
        presenter.state.test {
            // Skip initial states
            awaitItem() // loading
            testDispatcher.scheduler.advanceUntilIdle()
            awaitItem() // error
            
            // Trigger retry
            mockRepository.shouldFail = false
            mockRepository.successResponse = HealthStatus("Retry successful", "2024-01-02T10:00:00Z")
            presenter.onEvent(HealthUiEvent.Retry)
            
            testDispatcher.scheduler.advanceUntilIdle()
            
            // Assert - should go back to loading then success
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            
            val successState = awaitItem()
            assertTrue(successState.isSuccess)
            assertEquals("Retry successful", successState.message)
            
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `check health event triggers health check`() = runTest {
        // Arrange
        val mockRepository = MockHealthRepository(
            successResponse = HealthStatus("Manual check", "2024-01-03T14:30:00Z")
        )
        val presenter = HealthPresenter(mockRepository)

        // Act
        presenter.state.test {
            awaitItem() // initial loading
            testDispatcher.scheduler.advanceUntilIdle()
            awaitItem() // first success
            
            presenter.onEvent(HealthUiEvent.CheckHealth)
            testDispatcher.scheduler.advanceUntilIdle()
            
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            
            val successState = awaitItem()
            assertEquals("Manual check", successState.message)
            
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state transitions preserve immutability`() = runTest {
        // Arrange
        val mockRepository = MockHealthRepository(
            successResponse = HealthStatus("Test", "2024-01-04T08:00:00Z")
        )
        val presenter = HealthPresenter(mockRepository)

        // Act & Assert
        presenter.state.test {
            val state1 = awaitItem()
            testDispatcher.scheduler.advanceUntilIdle()
            val state2 = awaitItem()
            
            // States should be different instances
            assertTrue(state1 !== state2)
            
            cancelAndIgnoreRemainingEvents()
        }
    }
}

/**
 * Mock implementation of HealthRepository for testing.
 */
private class MockHealthRepository(
    var successResponse: HealthStatus? = null,
    var shouldFail: Boolean = false
) : HealthRepository {

    override suspend fun getHealth(): Result<HealthStatus> {
        return if (shouldFail) {
            Result.failure(Exception("Network error"))
        } else {
            Result.success(successResponse ?: HealthStatus("OK", "2024-01-01T00:00:00Z"))
        }
    }
}
