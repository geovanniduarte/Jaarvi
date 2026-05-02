package com.jaarvi.ui.screens.createtrip.steps

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.level0.icons.*
import com.jaarvi.ui.linda.components.level1.BadgeVariant
import com.jaarvi.ui.linda.components.level1.ButtonVariant
import com.jaarvi.ui.linda.components.level1.IconButtonVariant
import com.jaarvi.ui.linda.components.level1.LindaBadge
import com.jaarvi.ui.linda.components.level1.LindaButton
import com.jaarvi.ui.linda.components.level1.LindaCard
import com.jaarvi.ui.linda.components.level1.LindaIconButton
import com.jaarvi.ui.linda.components.level1.LindaSegmentedControl
import com.jaarvi.ui.linda.components.level1.LindaSlider
import com.jaarvi.ui.linda.components.level1.LindaTextField
import com.jaarvi.ui.linda.components.level1.LindaMessage
import com.jaarvi.ui.linda.components.level1.LindaMessageType
import com.jaarvi.ui.linda.components.level1.LindaTextLabel
import com.jaarvi.ui.linda.components.level1.LindaTextMicro
import com.jaarvi.ui.linda.components.level1.SegmentedControlWidthBehavior
import com.jaarvi.ui.linda.components.level2.LindaBottomBar
import com.jaarvi.ui.linda.components.level2.LindaDateSelectors
import com.jaarvi.ui.linda.components.level2.LindaHeader
import com.jaarvi.ui.linda.components.level2.LindaItemCounter
import com.jaarvi.ui.linda.theme.LindaTheme
import com.jaarvi.ui.screens.createtrip.CreateTripUiEvent
import com.jaarvi.ui.screens.createtrip.CreateTripUiState
import kotlinx.datetime.LocalDate

// ─────────────────────────────────────────────────────────────
// Step1BasicInfoContent — Create New Trip (Step 1/3)
//
// Stateless: reads from CreateTripUiState, writes via onEvent.
//
// Emitted events:
//   TripNameChanged · StartDateSelected · EndDateSelected
//   BudgetChanged · TravelStyleChanged
//   GroupSizeChanged · QuickNotesChanged
//   Cancel · NextStep
// ─────────────────────────────────────────────────────────────

@Composable
fun Step1BasicInfoContent(
    state   : CreateTripUiState,
    onEvent : (CreateTripUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors  = LindaTheme.colors
    val spacing = LindaTheme.spacing
    val glass   = LindaTheme.glass
    val typo    = LindaTheme.typography
    val context = LocalContext.current

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker   by remember { mutableStateOf(false) }

    // ── Platform date-picker dialogs ──────────────────────────────────────────
    if (showStartPicker) {
        DisposableEffect(Unit) {
            val today = java.time.LocalDate.now()
            val ref   = state.startDate
            DatePickerDialog(
                context,
                { _, y, m, d ->
                    onEvent(CreateTripUiEvent.StartDateSelected(LocalDate(y, m + 1, d)))
                    showStartPicker = false
                },
                ref?.year       ?: today.year,
                (ref?.monthNumber?.minus(1)) ?: (today.monthValue - 1),
                ref?.dayOfMonth ?: today.dayOfMonth,
            ).also { dlg ->
                dlg.setOnDismissListener { showStartPicker = false }
                dlg.show()
            }
            onDispose { }
        }
    }

    if (showEndPicker) {
        DisposableEffect(Unit) {
            val ref = state.endDate ?: state.startDate
            val today = java.time.LocalDate.now()
            DatePickerDialog(
                context,
                { _, y, m, d ->
                    onEvent(CreateTripUiEvent.EndDateSelected(LocalDate(y, m + 1, d)))
                    showEndPicker = false
                },
                ref?.year       ?: today.year,
                (ref?.monthNumber?.minus(1)) ?: (today.monthValue - 1),
                ref?.dayOfMonth ?: today.dayOfMonth,
            ).also { dlg ->
                dlg.setOnDismissListener { showEndPicker = false }
                dlg.show()
            }
            onDispose { }
        }
    }

    // ── Auto-dismiss step1Error after 5 seconds ───────────────────────────────
    LaunchedEffect(state.step1Error) {
        if (state.step1Error != null) {
            delay(5_000)
            onEvent(CreateTripUiEvent.DismissError)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background),
    ) {

        // ── Ambient glow orbs ─────────────────────────────────
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(320.dp).offset(x = 180.dp, y = (-60).dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowGold, CircleShape),
            ) {}
            Box(
                modifier = Modifier
                    .size(260.dp).offset(x = (-80).dp, y = 480.dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowEmerald, CircleShape),
            ) {}
        }

        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                LindaHeader(
                    title           = "Create New Trip",
                    leadingContent  = {
                        LindaIconButton(
                            onClick = { onEvent(CreateTripUiEvent.Cancel) },
                            variant = IconButtonVariant.GHOST,
                        ) {
                            Icon(
                                imageVector        = LindaIcons.Default.ArrowBack,
                                contentDescription = "Cancel",
                                tint               = LindaTheme.colors.textPrimary,
                                modifier           = Modifier.size(LindaTheme.sizes.iconMd),
                            )
                        }
                    },
                    trailingContent = {
                        LindaBadge(text = "STEP 1/3", variant = BadgeVariant.OUTLINED_GOLD_PILL)
                    },
                )
            },
            bottomBar = {
                LindaBottomBar(onFabClick = {}) {
                    LindaNavItem(icon = LindaIcons.Default.Home,    label = "Home",      isActive = false, onClick = {})
                    LindaNavItem(icon = LindaIcons.Default.Trips,   label = "Explore",   isActive = false, onClick = {})
                    LindaNavItem(icon = LindaIcons.Default.Guide,   label = "Itinerary", isActive = false, onClick = {})
                    LindaNavItem(icon = LindaIcons.Default.Profile, label = "Profile",   isActive = false, onClick = {})
                }
            },
        ) { paddingValues ->

            LazyColumn(
                modifier            = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding      = PaddingValues(horizontal = spacing.lg, vertical = spacing.lg),
                verticalArrangement = Arrangement.spacedBy(spacing.xl),
            ) {

                // ── Trip Name ──────────────────────────────────
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
                        LindaTextLabel(text = "Trip Name")
                        LindaTextField(
                            value         = state.tripName,
                            onValueChange = { onEvent(CreateTripUiEvent.TripNameChanged(it)) },
                            label         = "Trip Name",
                            placeholder   = "e.g. Amalfi Coast Adventure",
                            maxLength     = 40,
                        )
                    }
                }

                // ── Select Dates ───────────────────────────────
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.CenterVertically,
                        ) {
                            LindaTextLabel(text = "Select Dates")
                            LindaBadge(
                                text    = "${state.durationDays} DAYS",
                                variant = BadgeVariant.SUBTLE_GOLD_PILL,
                            )
                        }
                        LindaDateSelectors(
                            startDate    = state.startDateText,
                            endDate      = state.endDateText,
                            onStartClick = { showStartPicker = true },
                            onEndClick   = { showEndPicker   = true },
                        )
                    }
                }

                // ── Estimated Budget ───────────────────────────
                item {
                    LindaCard(glowColor = Color.Transparent) {
                        Column(
                            modifier            = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(spacing.lg),
                        ) {
                            LindaTextLabel(text = "Estimated Budget")

                            // Large display — gold "$" prefix + white amount
                            Row(verticalAlignment = Alignment.Top) {
                                Text(
                                    text     = "\$",
                                    style    = typo.displaySmall.copy(color = colors.accentGold),
                                    modifier = Modifier.padding(top = spacing.sm),
                                )
                                Text(
                                    text  = "%,d".format(state.budget.toInt()),
                                    style = typo.headlineLarge.copy(
                                        color    = colors.textPrimary,
                                        fontSize = TextUnit(52f, TextUnitType.Sp),
                                    ),
                                )
                            }

                            LindaSlider(
                                value         = state.budget,
                                onValueChange = { onEvent(CreateTripUiEvent.BudgetChanged(it)) },
                                valueRange    = 500f..5000f,
                                modifier      = Modifier.fillMaxWidth(),
                            )

                            Row(
                                modifier              = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                LindaTextMicro(text = "\$500")
                                LindaTextMicro(text = "\$5,000+")
                            }
                        }
                    }
                }

                // ── Travel Style ───────────────────────────────
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
                        LindaTextLabel(text = "Travel Style")
                        LindaSegmentedControl(
                            selectedSegment = state.travelStyle,
                            widthBehavior   = SegmentedControlWidthBehavior.Proportional,
                        ) {
                            LindaSegment(
                                selected = state.travelStyle == 0,
                                onClick  = { onEvent(CreateTripUiEvent.TravelStyleChanged(0)) },
                            ) {
                                Text("Relaxed",  style = typo.labelLarge)
                            }
                            LindaSegment(
                                selected = state.travelStyle == 1,
                                onClick  = { onEvent(CreateTripUiEvent.TravelStyleChanged(1)) },
                            ) {
                                Text("Moderate", style = typo.labelLarge)
                            }
                            LindaSegment(
                                selected = state.travelStyle == 2,
                                onClick  = { onEvent(CreateTripUiEvent.TravelStyleChanged(2)) },
                            ) {
                                Text("Intense",  style = typo.labelLarge)
                            }
                        }
                    }
                }

                // ── Travelers ──────────────────────────────────
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
                        LindaTextLabel(text = "Travelers")
                        LindaItemCounter(
                            label       = "Group Size",
                            subtitle    = "Including yourself",
                            value       = state.groupSize,
                            onDecrement = {
                                if (state.groupSize > 1)
                                    onEvent(CreateTripUiEvent.GroupSizeChanged(state.groupSize - 1))
                            },
                            onIncrement = {
                                onEvent(CreateTripUiEvent.GroupSizeChanged(state.groupSize + 1))
                            },
                            min = 1,
                            max = 20,
                        )
                    }
                }

                // ── Quick Notes ────────────────────────────────
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
                        LindaTextLabel(text = "Quick Notes")
                        LindaTextField(
                            value         = state.quickNotes,
                            onValueChange = { onEvent(CreateTripUiEvent.QuickNotesChanged(it)) },
                            label         = "Quick Notes",
                            placeholder   = "What are you dreaming about for this trip?",
                            singleLine    = false,
                        )
                    }
                }

                // ── Error banner (auto-dismissed after 5 s) ───
                item {
                    LindaMessage(
                        message  = state.step1Error,
                        type     = LindaMessageType.Error,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                // ── Bottom Actions ─────────────────────────────
                item {
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically,
                    ) {
                        LindaButton(
                            text    = "Cancel",
                            onClick = { onEvent(CreateTripUiEvent.Cancel) },
                            variant = ButtonVariant.GHOST,
                        )
                        LindaButton(
                            text     = "Next Step  ›",
                            onClick  = { onEvent(CreateTripUiEvent.NextStep) },
                            variant  = ButtonVariant.PRIMARY,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = spacing.xl),
                        )
                    }
                }

                item { Spacer(Modifier.height(spacing.xxxl)) }
            }
        }
    }
}
