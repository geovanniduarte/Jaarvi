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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.level0.icons.*
import com.jaarvi.ui.linda.components.level1.BadgeVariant
import com.jaarvi.ui.linda.components.level1.LindaMessage
import com.jaarvi.ui.linda.components.level1.LindaMessageType
import com.jaarvi.ui.linda.components.level1.ButtonVariant
import com.jaarvi.ui.linda.components.level1.IconButtonVariant
import com.jaarvi.ui.linda.components.level1.LindaBadge
import com.jaarvi.ui.linda.components.level1.LindaButton
import com.jaarvi.ui.linda.components.level1.LindaIconButton
import com.jaarvi.ui.linda.components.level1.LindaTextLabel
import com.jaarvi.ui.linda.components.level2.LindaBottomBar
import com.jaarvi.ui.linda.components.level2.LindaHeader
import com.jaarvi.ui.linda.components.level2.LindaRow
import com.jaarvi.ui.linda.theme.LindaTheme
import com.jaarvi.ui.screens.createtrip.CreateTripUiEvent
import com.jaarvi.ui.screens.createtrip.CreateTripUiState
import com.jaarvi.ui.screens.createtrip.TripInterest

// ─────────────────────────────────────────────────────────────
// Step3PreferencesContent — Trip Preferences (Step 3/3)
//
// Stateless: reads from CreateTripUiState, writes via onEvent.
//
// Emitted events:
//   InterestToggled(interest, checked)
//   DailyPaceChanged(pace)
//   Back · CreateTrip
// ─────────────────────────────────────────────────────────────

@Composable
fun Step3PreferencesContent(
    state   : CreateTripUiState,
    onEvent : (CreateTripUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors  = LindaTheme.colors
    val spacing = LindaTheme.spacing
    val glass   = LindaTheme.glass
    val typo    = LindaTheme.typography
    val sizes   = LindaTheme.sizes

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background),
    ) {

        // ── Ambient glow orbs ─────────────────────────────────
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(240.dp).offset(x = 160.dp, y = (-40).dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowGold, CircleShape),
            ) {}
            Box(
                modifier = Modifier
                    .size(200.dp).offset(x = (-60).dp, y = 700.dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowEmerald, CircleShape),
            ) {}
        }

        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                LindaHeader(
                    title           = "Trip Preferences",
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
                        LindaBadge(text = "STEP 3/3", variant = BadgeVariant.OUTLINED_GOLD_PILL)
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
                verticalArrangement = Arrangement.spacedBy(spacing.xl),
            ) {

                // ── Step progress dots ─────────────────────────
                item {
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment     = Alignment.CenterVertically,
                    ) {
                        // Steps 1 & 2 — inactive (narrow, muted)
                        repeat(2) {
                            Box(
                                modifier = Modifier
                                    .width(24.dp).height(6.dp)
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(colors.borderGlass),
                            ) {}
                            Spacer(Modifier.width(spacing.sm))
                        }
                        // Step 3 — active (wider, gold)
                        Box(
                            modifier = Modifier
                                .width(48.dp).height(6.dp)
                                .clip(RoundedCornerShape(9999.dp))
                                .background(colors.accentGold),
                        ) {}
                    }
                }

                // ── INTERESTS ──────────────────────────────────
                // LindaRow(checked) renders gold-square-✓ / glass-circle toggle
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
                        LindaTextLabel(text = "Interests")
                        LindaRow(
                            label    = "Art & Museums",
                            checked  = state.interestArt,
                            onToggle = {
                                onEvent(CreateTripUiEvent.InterestToggled(TripInterest.ART, !state.interestArt))
                            },
                        )
                        LindaRow(
                            label    = "History",
                            checked  = state.interestHistory,
                            onToggle = {
                                onEvent(CreateTripUiEvent.InterestToggled(TripInterest.HISTORY, !state.interestHistory))
                            },
                        )
                        LindaRow(
                            label    = "Food",
                            checked  = state.interestFood,
                            onToggle = {
                                onEvent(CreateTripUiEvent.InterestToggled(TripInterest.FOOD, !state.interestFood))
                            },
                        )
                        LindaRow(
                            label    = "Local Culture",
                            checked  = state.interestCulture,
                            onToggle = {
                                onEvent(CreateTripUiEvent.InterestToggled(TripInterest.CULTURE, !state.interestCulture))
                            },
                        )
                    }
                }

                // ── DAILY PACE ─────────────────────────────────
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
                        LindaTextLabel(text = "Daily Pace")
                        PaceOption(
                            title    = "Relaxed",
                            subtitle = "Take it slow, soak it in",
                            selected = state.dailyPace == "Relaxed",
                            onClick  = { onEvent(CreateTripUiEvent.DailyPaceChanged("Relaxed")) },
                        )
                        PaceOption(
                            title    = "Normal Day",
                            subtitle = "Perfect balance of sight & rest",
                            selected = state.dailyPace == "Normal Day",
                            onClick  = { onEvent(CreateTripUiEvent.DailyPaceChanged("Normal Day")) },
                        )
                        PaceOption(
                            title    = "Active Explorer",
                            subtitle = "Maximize every hour",
                            selected = state.dailyPace == "Active Explorer",
                            onClick  = { onEvent(CreateTripUiEvent.DailyPaceChanged("Active Explorer")) },
                        )
                    }
                }

                // ── Settings rows ──────────────────────────────
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
                        SettingRow(
                            icon         = LindaIcons.Default.WbSunny,
                            title        = "Transportation Preference",
                            trailingText = "Public Transit",
                        )
                        SettingRow(
                            icon         = LindaIcons.Default.Restaurant,
                            title        = "Meal Preferences",
                            trailingText = "Local Cuisine",
                        )
                        SettingRow(
                            icon         = LindaIcons.Default.Lightbulb,
                            title        = "Notification Settings",
                            trailingText = "On",
                        )
                    }
                }

                // ── Error banner ───────────────────────────────
                item {
                    LindaMessage(
                        message  = state.error,
                        type     = LindaMessageType.Error,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                // ── Bottom Actions ─────────────────────────────
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.md)) {
                        LindaButton(
                            text     = "Create Trip  ✓",
                            onClick  = { onEvent(CreateTripUiEvent.CreateTrip) },
                            variant  = ButtonVariant.PRIMARY,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        LindaButton(
                            text     = "Back to previous step",
                            onClick  = { onEvent(CreateTripUiEvent.Back) },
                            variant  = ButtonVariant.GHOST,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }

                item { Spacer(Modifier.height(spacing.xxxl)) }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
// PaceOption — radio-style card with title + subtitle.
//
// selected = true  → gold border + gold filled radio circle
// selected = false → glass border + empty glass circle
// ─────────────────────────────────────────────────────────────

@Composable
private fun PaceOption(
    title   : String,
    subtitle: String,
    selected: Boolean,
    onClick : () -> Unit,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography

    val shape       = RoundedCornerShape(borders.radiusLg)
    val borderColor = if (selected) colors.accentGold.copy(alpha = 0.5f) else colors.borderGlass

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(colors.surfaceGlass)
            .border(width = borders.widthThin, color = borderColor, shape = shape)
            .clickable(onClick = onClick)
            .padding(spacing.lg),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(if (selected) colors.accentGold else colors.surfaceGlass)
                    .then(
                        if (!selected) Modifier.border(
                            width = borders.widthThin,
                            color = colors.borderGlassStrong,
                            shape = CircleShape,
                        ) else Modifier
                    ),
            ) {}

            Spacer(Modifier.width(spacing.lg))

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text  = title,
                    style = typo.bodyMedium.copy(color = colors.textPrimary),
                )
                Text(
                    text  = subtitle,
                    style = typo.bodySmall.copy(color = colors.textMuted),
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
// SettingRow — glass row with leading icon, label, and
// trailing value + chevron (›).
// ─────────────────────────────────────────────────────────────

@Composable
private fun SettingRow(
    icon        : ImageVector,
    title       : String,
    trailingText: String,
    onClick     : () -> Unit = {},
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography
    val sizes   = LindaTheme.sizes

    val shape = RoundedCornerShape(borders.radiusMd)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(colors.surfaceGlass)
            .border(width = borders.widthThin, color = colors.borderGlass, shape = shape)
            .clickable(onClick = onClick)
            .padding(horizontal = spacing.lg, vertical = spacing.md),
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.md),
            ) {
                Icon(
                    imageVector        = icon,
                    contentDescription = null,
                    tint               = colors.accentGold,
                    modifier           = Modifier.size(sizes.iconMd),
                )
                Text(
                    text  = title,
                    style = typo.bodyMedium.copy(color = colors.textPrimary),
                )
            }
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.xs),
            ) {
                Text(
                    text  = trailingText,
                    style = typo.bodySmall.copy(color = colors.textMuted),
                )
                Text(
                    text  = "›",
                    style = typo.bodyMedium.copy(color = colors.textMuted),
                )
            }
        }
    }
}
