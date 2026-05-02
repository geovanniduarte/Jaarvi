package com.jaarvi.ui.screens.createtrip.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.level0.icons.*
import com.jaarvi.ui.linda.components.level1.BadgeVariant
import com.jaarvi.ui.linda.components.level1.LindaMessage
import com.jaarvi.ui.linda.components.level1.LindaMessageType
import com.jaarvi.ui.linda.components.level1.ButtonVariant
import com.jaarvi.ui.linda.components.level1.DestinationStatus
import com.jaarvi.ui.linda.components.level1.IconButtonVariant
import com.jaarvi.ui.linda.components.level1.LindaBadge
import com.jaarvi.ui.linda.components.level1.LindaButton
import com.jaarvi.ui.linda.components.level1.LindaCard
import com.jaarvi.ui.linda.components.level1.LindaChip
import com.jaarvi.ui.linda.components.level1.LindaChipRow
import com.jaarvi.ui.linda.components.level1.LindaIconButton
import com.jaarvi.ui.linda.components.level1.LindaProgressBar
import com.jaarvi.ui.linda.components.level1.LindaSearchBox
import com.jaarvi.ui.linda.components.level1.LindaStatusIndicatorDot
import com.jaarvi.ui.linda.components.level1.LindaTextLabel
import com.jaarvi.ui.linda.components.level1.LindaTextSmall
import com.jaarvi.ui.linda.components.level1.ProgressSize
import com.jaarvi.ui.linda.components.level1.ProgressVariant
import com.jaarvi.ui.linda.components.level1.StatusDotSize
import com.jaarvi.ui.linda.components.level2.LindaBottomBar
import com.jaarvi.ui.linda.components.level2.LindaHeader
import com.jaarvi.ui.linda.theme.LindaTheme
import com.jaarvi.ui.screens.createtrip.CreateTripUiEvent
import com.jaarvi.ui.screens.createtrip.CreateTripUiState
import com.jaarvi.ui.screens.createtrip.Destination

// ─────────────────────────────────────────────────────────────
// Step2DestinationsContent — Add Destinations (Step 2/3)
//
// Stateless: reads from CreateTripUiState, writes via onEvent.
//
// Emitted events:
//   SearchQueryChanged · ActiveChipChanged
//   DestinationDaysChanged(index, days) · DestinationRemoved(index)
//   Back · NextStep
// ─────────────────────────────────────────────────────────────

@Composable
fun Step2DestinationsContent(
    state   : CreateTripUiState,
    onEvent : (CreateTripUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors  = LindaTheme.colors
    val spacing = LindaTheme.spacing
    val glass   = LindaTheme.glass
    val typo    = LindaTheme.typography

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background),
    ) {

        // ── Ambient glow orbs ─────────────────────────────────
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(280.dp).offset(x = 180.dp, y = (-40).dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowGold, CircleShape),
            ) {}
            Box(
                modifier = Modifier
                    .size(220.dp).offset(x = (-60).dp, y = 600.dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowEmerald, CircleShape),
            ) {}
        }

        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                LindaHeader(
                    title           = "Add Destinations",
                    leadingContent  = {
                        LindaIconButton(
                            onClick = { onEvent(CreateTripUiEvent.Back) },
                            variant = IconButtonVariant.GHOST,
                        ) {
                            Icon(
                                imageVector        = LindaIcons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint               = LindaTheme.colors.textPrimary,
                                modifier           = Modifier.size(LindaTheme.sizes.iconMd),
                            )
                        }
                    },
                    trailingContent = {
                        LindaBadge(text = "STEP 2/3", variant = BadgeVariant.OUTLINED_ROUNDED)
                    },
                )
            },
            bottomBar = {
                LindaBottomBar(onFabClick = {}) {
                    LindaNavItem(icon = LindaIcons.Default.Home,    label = "Home",    isActive = false, onClick = {})
                    LindaNavItem(icon = LindaIcons.Default.Trips,   label = "Explore", isActive = false, onClick = {})
                    LindaNavItem(icon = LindaIcons.Default.Guide,   label = "Saved",   isActive = false, onClick = {})
                    LindaNavItem(icon = LindaIcons.Default.Profile, label = "Profile", isActive = false, onClick = {})
                }
            },
        ) { paddingValues ->

            LazyColumn(
                modifier            = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding      = PaddingValues(horizontal = spacing.lg, vertical = spacing.lg),
                verticalArrangement = Arrangement.spacedBy(spacing.lg),
            ) {

                // ── Search box ─────────────────────────────────
                item {
                    LindaSearchBox(
                        value          = state.searchQuery,
                        onValueChange  = { onEvent(CreateTripUiEvent.SearchQueryChanged(it)) },
                        onClearClicked = { onEvent(CreateTripUiEvent.SearchQueryChanged("")) },
                        placeholderText = {
                            Text(
                                text  = "Search cities...",
                                style = typo.bodyMedium.copy(color = colors.textMuted),
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                // ── Suggested chips ────────────────────────────
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.md)) {
                        LindaTextSmall(text = "Suggested For You", color = colors.textSecondary)
                        LindaChipRow(modifier = Modifier.fillMaxWidth()) {
                            listOf("Paris", "Rome", "London", "Barcelona").forEach { city ->
                                item {
                                    LindaChip(
                                        label    = city,
                                        selected = city == state.activeChip,
                                        onClick  = { onEvent(CreateTripUiEvent.ActiveChipChanged(city)) },
                                    )
                                }
                            }
                        }
                    }
                }

                // ── Route header ───────────────────────────────
                item {
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically,
                    ) {
                        Text(
                            text  = "Your Route",
                            style = typo.headlineMedium.copy(color = colors.textPrimary),
                        )
                        Row(
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(spacing.xs),
                        ) {
                            LindaStatusIndicatorDot(
                                status = DestinationStatus.ACTIVE,
                                size   = StatusDotSize.SM,
                            )
                            Text(
                                text  = "Optimized",
                                style = typo.labelLarge.copy(color = colors.accentLime),
                            )
                        }
                    }
                }

                // ── Destination rows ───────────────────────────
                state.destinations.forEachIndexed { index, destination ->
                    item(key = "dest_$index") {
                        DestinationRow(
                            destination = destination,
                            onDecrement = {
                                if (destination.days > 1)
                                    onEvent(CreateTripUiEvent.DestinationDaysChanged(index, destination.days - 1))
                            },
                            onIncrement = {
                                onEvent(CreateTripUiEvent.DestinationDaysChanged(index, destination.days + 1))
                            },
                            onRemove    = { onEvent(CreateTripUiEvent.DestinationRemoved(index)) },
                        )
                    }
                }

                // ── Days summary + progress ────────────────────
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.md)) {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.CenterVertically,
                        ) {
                            Text(
                                text  = "Total: ${state.daysAllocated}/${state.tripTotalDays} Days Allocated",
                                style = typo.bodyMedium.copy(color = colors.textPrimary),
                            )
                            if (state.daysRemaining > 0) {
                                LindaBadge(
                                    text    = "${state.daysRemaining} Day${if (state.daysRemaining != 1) "s" else ""} Remaining",
                                    variant = BadgeVariant.OUTLINED_ROUNDED,
                                )
                            }
                        }
                        LindaProgressBar(
                            value     = state.daysAllocated.toFloat(),
                            max       = state.tripTotalDays.toFloat(),
                            variant   = ProgressVariant.GRADIENT,
                            showLabel = false,
                            size      = ProgressSize.SM,
                        )
                    }
                }

                // ── Error banner ───────────────────────────────
                item {
                    LindaMessage(
                        message  = state.step2Error,
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
                            text    = "BACK",
                            onClick = { onEvent(CreateTripUiEvent.Back) },
                            variant = ButtonVariant.GHOST,
                        )
                        LindaButton(
                            text     = "NEXT STEP",
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

// ─────────────────────────────────────────────────────────────
// DestinationRow — glassmorphic card:
//   • Gold ordinal badge
//   • City name + inline pill days-stepper (−  N Days  +)
//   • Remove (✕) + drag handle on the right
// ─────────────────────────────────────────────────────────────

@Composable
private fun DestinationRow(
    destination: Destination,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    onRemove   : () -> Unit,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography
    val shadow  = LindaTheme.shadow
    val sizes   = LindaTheme.sizes

    LindaCard(
        modifier       = Modifier.fillMaxWidth(),
        glowColor      = Color.Transparent,
        contentPadding = PaddingValues(spacing.lg),
    ) {
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            // Gold ordinal badge
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .shadow(
                        elevation    = shadow.glowElevation,
                        shape        = RoundedCornerShape(borders.radiusSm),
                        ambientColor = colors.glowGold,
                        spotColor    = colors.glowGold,
                    )
                    .clip(RoundedCornerShape(borders.radiusSm))
                    .background(colors.accentGold),
            ) {
                Text(
                    text  = "${destination.ordinal}",
                    style = typo.labelLarge.copy(color = colors.onAccent),
                )
            }

            Spacer(Modifier.width(spacing.lg))

            // City name + inline pill stepper
            Column(
                modifier            = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing.sm),
            ) {
                Text(
                    text  = destination.cityName,
                    style = typo.headlineMedium.copy(color = colors.textPrimary),
                )

                val pillShape = RoundedCornerShape(borders.radiusFull)
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .clip(pillShape)
                        .background(colors.surfaceGlass)
                        .border(borders.widthThin, colors.borderGlassStrong, pillShape)
                        .padding(horizontal = spacing.lg, vertical = spacing.xs),
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.md),
                ) {
                    // Decrement (−)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(sizes.iconSm)
                            .then(if (destination.days > 1) Modifier.clickable(onClick = onDecrement) else Modifier),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(sizes.iconSm * 0.65f, 2.dp)
                                .background(
                                    if (destination.days > 1) colors.textPrimary
                                    else colors.textMuted.copy(alpha = 0.4f),
                                    RoundedCornerShape(1.dp),
                                ),
                        ) {}
                    }

                    Text(
                        text  = "${destination.days} Days",
                        style = typo.bodyMedium.copy(color = colors.textPrimary),
                    )

                    // Increment (+)
                    Icon(
                        imageVector        = LindaIcons.Default.Add,
                        contentDescription = "Add day",
                        tint               = colors.textPrimary,
                        modifier           = Modifier
                            .size(sizes.iconSm)
                            .clickable(onClick = onIncrement),
                    )
                }
            }

            Spacer(Modifier.width(spacing.md))

            // Trailing — remove + drag handle
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.sm),
            ) {
                Icon(
                    imageVector        = LindaIcons.Default.Close,
                    contentDescription = "Remove destination",
                    tint               = colors.textMuted,
                    modifier           = Modifier
                        .size(sizes.iconSm)
                        .clickable(onClick = onRemove),
                )
                Icon(
                    imageVector        = LindaIcons.Default.DragHandle,
                    contentDescription = "Drag to reorder",
                    tint               = colors.textMuted,
                    modifier           = Modifier.size(sizes.iconMd),
                )
            }
        }
    }
}
