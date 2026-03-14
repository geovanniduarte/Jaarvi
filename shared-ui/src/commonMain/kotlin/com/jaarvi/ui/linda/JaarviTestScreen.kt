package com.jaarvi.ui.linda

import android.os.Bundle
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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.level0.icons.*
import com.jaarvi.ui.linda.components.level1.AvatarSize
import com.jaarvi.ui.linda.components.level1.BadgeVariant
import com.jaarvi.ui.linda.components.level1.ButtonGlow
import com.jaarvi.ui.linda.components.level1.ButtonVariant
import com.jaarvi.ui.linda.components.level1.CardVariant
import com.jaarvi.ui.linda.components.level1.DestinationStatus
import com.jaarvi.ui.linda.components.level1.DividerOrientation
import com.jaarvi.ui.linda.components.level1.FabSize
import com.jaarvi.ui.linda.components.level1.IconButtonVariant
import com.jaarvi.ui.linda.components.level1.LindaAvatar
import com.jaarvi.ui.linda.components.level1.LindaAvatarGroup
import com.jaarvi.ui.linda.components.level1.LindaBadge
import com.jaarvi.ui.linda.components.level1.LindaButton
import com.jaarvi.ui.linda.components.level1.LindaCard
import com.jaarvi.ui.linda.components.level1.LindaCheckboxGroup
import com.jaarvi.ui.linda.components.level1.LindaChip
import com.jaarvi.ui.linda.components.level1.LindaChipRow
import com.jaarvi.ui.linda.components.level1.LindaDivider
import com.jaarvi.ui.linda.components.level1.LindaFAB
import com.jaarvi.ui.linda.components.level1.LindaIconButton
import com.jaarvi.ui.linda.components.level1.LindaProgressBar
import com.jaarvi.ui.linda.components.level1.LindaRadioButton
import com.jaarvi.ui.linda.components.level1.LindaRadioGroup
import com.jaarvi.ui.linda.components.level1.LindaSearchBox
import com.jaarvi.ui.linda.components.level1.LindaSegmentedControl
import com.jaarvi.ui.linda.components.level1.LindaSlider
import com.jaarvi.ui.linda.components.level1.LindaStatCard
import com.jaarvi.ui.linda.components.level1.LindaStatusIndicatorDot
import com.jaarvi.ui.linda.components.level1.LindaStatsRow
import com.jaarvi.ui.linda.components.level1.LindaStepper
import com.jaarvi.ui.linda.components.level1.LindaStepperSize
import com.jaarvi.ui.linda.components.level1.LindaTagGroup
import com.jaarvi.ui.linda.components.level1.LindaTextField
import com.jaarvi.ui.linda.components.level1.LindaTextBody
import com.jaarvi.ui.linda.components.level1.LindaTextH1
import com.jaarvi.ui.linda.components.level1.LindaTextH2
import com.jaarvi.ui.linda.components.level1.LindaTextH3
import com.jaarvi.ui.linda.components.level1.LindaTextLabel
import com.jaarvi.ui.linda.components.level1.LindaTextMicro
import com.jaarvi.ui.linda.components.level1.LindaTextSmall
import com.jaarvi.ui.linda.components.level1.ProgressSize
import com.jaarvi.ui.linda.components.level1.ProgressVariant
import com.jaarvi.ui.linda.components.level1.SegmentedControlWidthBehavior
import com.jaarvi.ui.linda.components.level1.StatusDotSize
import com.jaarvi.ui.linda.components.level2.LindaBottomBar
import com.jaarvi.ui.linda.components.level2.LindaBudgetSlider
import com.jaarvi.ui.linda.components.level2.LindaDateSelectors
import com.jaarvi.ui.linda.components.level2.LindaDestinationCard
import com.jaarvi.ui.linda.components.level2.LindaDestinationCardOld
import com.jaarvi.ui.linda.components.level2.LindaHeader
import com.jaarvi.ui.linda.components.level2.LindaItemCounter
import com.jaarvi.ui.linda.components.level2.LindaNavBar
import com.jaarvi.ui.linda.components.level2.LindaNavBarScope
import com.jaarvi.ui.linda.components.level2.LindaRow
import com.jaarvi.ui.linda.components.level2.LindaSelector
import com.jaarvi.ui.linda.theme.LindaTheme


// ─────────────────────────────────────────────────────────────
// Root screen
// ─────────────────────────────────────────────────────────────
@Composable
fun TravelAppScreen2() {
    val colors  = LindaTheme.colors
    val spacing = LindaTheme.spacing
    val glass   = LindaTheme.glass
    val typo    = LindaTheme.typography
    val sizes   = LindaTheme.sizes

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
    ) {

        // ── Ambient background glow orbs ──────────────────────
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(300.dp).offset(x = 250.dp, y = (-80).dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowGold, CircleShape),
            )
            Box(
                modifier = Modifier
                    .size(400.dp).offset(x = (-160).dp, y = 400.dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowEmerald, CircleShape),
            )
            Box(
                modifier = Modifier
                    .size(250.dp).offset(x = 250.dp, y = 900.dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowGoldLight, CircleShape),
            )
            Box(
                modifier = Modifier
                    .size(350.dp).offset(x = 100.dp, y = 1600.dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowEmeraldLight, CircleShape),
            )
            Box(
                modifier = Modifier
                    .size(300.dp).offset(x = (-80).dp, y = 2400.dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowGold, CircleShape),
            )
        }

        // ── Scaffold ────────���─────────────────────────────────
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                LindaHeader(
                    title           = "Trip Detail: Golden Horizon",
                    subtitle        = "Explorer",
                    leadingContent  = {
                        LindaIconButton(
                            onClick = {},
                            variant = IconButtonVariant.GHOST,
                        ) {
                            Icon(
                                imageVector        = LindaIcons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint               = colors.textPrimary,
                                modifier           = Modifier.size(sizes.iconMd),
                            )
                        }
                    },
                    trailingContent = {
                        LindaBadge(
                            text    = "Active",
                            variant = BadgeVariant.SOLID_LIME_PILL,
                        )
                    },
                )
            },
            bottomBar = {
                LindaBottomBar(onFabClick = {}) {
                    LindaNavItem(icon = LindaIcons.Default.Home,    label = "Home",    isActive = true,  onClick = {})
                    LindaNavItem(icon = LindaIcons.Default.Trips,   label = "Trips",   isActive = false, onClick = {})
                    LindaNavItem(icon = LindaIcons.Default.Guide,   label = "Guide",   isActive = false, onClick = {})
                    LindaNavItem(icon = LindaIcons.Default.Profile, label = "Profile", isActive = false, onClick = {})
                }
            },
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(
                    horizontal = spacing.lg,
                    vertical   = spacing.lg,
                ),
                verticalArrangement = Arrangement.spacedBy(spacing.lg),
            ) {

                // ─────────────────────────────────────────────
                // ① Linda Design System — Trip Summary
                // ─────────────────────────────────────────────
                item {
                    LindaCard {
                        Column(verticalArrangement = Arrangement.spacedBy(spacing.xxl)) {
                            Row(
                                modifier              = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment     = Alignment.Top,
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
                                    LindaBadge(text = "Status: Active Journey", variant = BadgeVariant.SOLID_LIME_PILL)
                                    Text(
                                        text  = "Trip Summary",
                                        style = typo.displayMedium.copy(color = colors.textPrimary),
                                    )
                                }
                                LindaIconButton(onClick = {}, variant = IconButtonVariant.GLASS) {
                                    Icon(
                                        imageVector        = LindaIcons.Default.Lightbulb,
                                        contentDescription = "Tips",
                                        tint               = colors.accentGold,
                                        modifier           = Modifier.size(sizes.iconMd),
                                    )
                                }
                            }
                            LindaStatsRow {
                                LindaStatCard(label = "Budget Remaining", value = "$4,500", subtext = "/ $5k",  modifier = Modifier.weight(1f))
                                LindaStatCard(label = "Travel Dates",     value = "Sept 12–24",                 modifier = Modifier.weight(1f))
                            }
                            LindaDivider()
                            LindaProgressBar(value = 65f, label = "Total Trip Completion", variant = ProgressVariant.GRADIENT)
                        }
                    }
                }

                item {
                    Row(
                        modifier              = Modifier.fillMaxWidth().padding(horizontal = spacing.lg),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically,
                    ) {
                        Text(
                            text  = "Itinerary Journey",
                            style = typo.displaySmall.copy(color = colors.textPrimary),
                        )
                        LindaBadge(text = "3 Cities", variant = BadgeVariant.SUBTLE_GOLD_PILL)
                    }
                }

                item {
                    LindaDestinationCard(
                        imageUrl = "https://images.unsplash.com/photo-1719870042839-c48ba42ac7ef?w=600",
                        title    = "Paris, France",
                        duration = "3 Days",
                        theme    = "Art & Cafes",
                        status   = DestinationStatus.ACTIVE,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(spacing.xl)) {
                            Row(
                                modifier              = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment     = Alignment.CenterVertically,
                            ) {
                                Row(
                                    verticalAlignment     = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(spacing.sm),
                                ) {
                                    LindaStatusIndicatorDot(status = DestinationStatus.ACTIVE, size = StatusDotSize.MD)
                                    LindaAvatarGroup {
                                        LindaAvatar(imageUrl = "https://images.unsplash.com/photo-1759423672542-c51b3b1b9e69?w=100", size = AvatarSize.MD)
                                        LindaAvatar(imageUrl = "https://images.unsplash.com/photo-1768933294181-82778103e501?w=100", size = AvatarSize.MD)
                                    }
                                }
                                LindaButton(text = "Details", onClick = {}, glow = ButtonGlow.GOLD)
                            }
                            LindaProgressBar(value = 88f, label = "Daily Budget Used", variant = ProgressVariant.GOLD, size = ProgressSize.SM)
                        }
                    }
                }

                item {
                    LindaDestinationCard(
                        imageUrl = "https://images.unsplash.com/photo-1528632799532-401ebb5a8e7f?w=600",
                        title    = "Florence, Italy",
                        duration = "3 Days",
                        theme    = "Tuscan Sunsets",
                        status   = DestinationStatus.PLANNED,
                    ) {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.CenterVertically,
                        ) {
                            Row(
                                verticalAlignment     = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(spacing.sm),
                            ) {
                                LindaStatusIndicatorDot(status = DestinationStatus.PLANNED, size = StatusDotSize.MD)
                                LindaTextSmall(text = "Next Destination", color = colors.textMuted)
                            }
                            LindaButton(text = "Planned", onClick = {}, glow = ButtonGlow.NONE)
                        }
                    }
                }

                // ─────────────────────────────────────────────
                // ② LINDA LEVEL 0 — Foundation tokens
                // ─────────────────────────────────────────────
                item { LindaSectionDivider(level = "LEVEL 0", title = "Linda Design System") }

                item {
                    LindaShowcaseSection(title = "01 — Color Palette") {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            LindaColorSwatch(color = Color(0xFF0B2418), hex = "#0B2418", name = "Forest Green",    modifier = Modifier.weight(1f))
                            LindaColorSwatch(color = Color(0xFFFFD700), hex = "#FFD700", name = "Golden Yellow",   modifier = Modifier.weight(1f))
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            LindaColorSwatch(color = Color(0xFF39FF14), hex = "#39FF14", name = "Neon Green",        modifier = Modifier.weight(1f))
                            LindaColorSwatch(color = Color(0xFFE0E0E0), hex = "#E0E0E0", name = "Slate / Off-white", modifier = Modifier.weight(1f))
                        }
                    }
                }

                item {
                    LindaShowcaseSection(title = "02 — Typography") {
                        val lSpacing = LindaTheme.spacing
                        val lColors  = LindaTheme.colors
                        val lTypo    = LindaTheme.typography
                        Column(verticalArrangement = Arrangement.spacedBy(lSpacing.xs)) {
                            LindaTextLabel(text = "Display Bold")
                            LindaTextH1(text = "Trip Summary")
                        }
                        LindaDivider(modifier = Modifier.padding(vertical = lSpacing.md))
                        Column(verticalArrangement = Arrangement.spacedBy(lSpacing.xs)) {
                            LindaTextLabel(text = "Section Header")
                            LindaTextH2(text = "Itinerary Journey")
                        }
                        LindaDivider(modifier = Modifier.padding(vertical = lSpacing.md))
                        Column(verticalArrangement = Arrangement.spacedBy(lSpacing.xs)) {
                            LindaTextLabel(text = "Body Regular")
                            LindaTextBody(text = "Experience the golden hour across the most beautiful cities in Europe with our AI assistant.")
                        }
                        LindaDivider(modifier = Modifier.padding(vertical = lSpacing.md))
                        Column(verticalArrangement = Arrangement.spacedBy(lSpacing.xs)) {
                            LindaTextLabel(text = "Metadata / Labels")
                            Text(
                                text  = "DAILY BUDGET USED — 88%",
                                style = lTypo.labelSmall.copy(
                                    color         = lColors.textMuted,
                                    letterSpacing = androidx.compose.ui.unit.TextUnit(1.5f, androidx.compose.ui.unit.TextUnitType.Sp),
                                ),
                            )
                        }
                    }
                }

                item {
                    LindaShowcaseSection(title = "03 — Icons") {
                        val lSpacing = LindaTheme.spacing
                        val lColors  = LindaTheme.colors
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(lSpacing.xl),
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(lSpacing.sm)) {
                                Icon(LindaIcons.Default.WbSunny,    contentDescription = "Sun",    tint = lColors.accentGold, modifier = Modifier.size(28.dp))
                                LindaTextMicro(text = "Sun")
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(lSpacing.sm)) {
                                Icon(LindaIcons.Default.Clock,      contentDescription = "Clock",  tint = lColors.accentGold, modifier = Modifier.size(28.dp))
                                LindaTextMicro(text = "Clock")
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(lSpacing.sm)) {
                                Icon(LindaIcons.Default.Restaurant,  contentDescription = "Dining", tint = lColors.accentGold, modifier = Modifier.size(28.dp))
                                LindaTextMicro(text = "Dining")
                            }
                        }
                    }
                }

                // ─────────────────────────────────────────────
                // ③ LINDA LEVEL 1 — Atoms
                // ─────────────────────────────────────────────
                item { LindaSectionDivider(level = "LEVEL 1", title = "Linda Design System") }

                // 1. TextField
                item {
                    var tripName  by remember { mutableStateOf("") }
                    var quickNote by remember { mutableStateOf("") }
                    LindaShowcaseSection(title = "1. Text Field") {
                        val lSpacing = LindaTheme.spacing
                        LindaTextField(
                            value         = tripName,
                            onValueChange = { tripName = it },
                            label         = "Trip Name",
                            placeholder   = "e.g. Amalfi Coast Adventure",
                            maxLength     = 40,
                        )
                        Spacer(Modifier.height(lSpacing.md))
                        LindaTextField(
                            value         = quickNote,
                            onValueChange = { quickNote = it },
                            label         = "Quick Notes",
                            placeholder   = "Mention dietary restrictions...",
                            singleLine    = false,
                        )
                    }
                }

                // 2. DatePicker Field
                item {
                    LindaShowcaseSection(title = "2. DatePicker Field") {
                        LindaCard(
                            glowColor      = Color.Transparent,
                            contentPadding = PaddingValues(LindaTheme.spacing.lg),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier            = Modifier.fillMaxWidth(),
                            ) {
                                LindaTextMicro(text = "START DATE")
                                Spacer(Modifier.height(LindaTheme.spacing.xs))
                                Text(
                                    text  = "Oct 12",
                                    style = LindaTheme.typography.headlineLarge.copy(color = LindaTheme.colors.textPrimary),
                                )
                            }
                        }
                    }
                }

                // 3. Stepper
                item {
                    var days by remember { mutableIntStateOf(12) }
                    LindaShowcaseSection(title = "3. Stepper") {
                        LindaStepper(
                            onClick          = { isInc -> if (isInc) days++ else if (days > 1) days-- },
                            incrementEnabled = days < 30,
                            decrementEnabled = days > 1,
                            size             = LindaStepperSize.large(),
                            valueContent     = {
                                Text(
                                    text  = "$days Days",
                                    style = LindaTheme.typography.headlineLarge.copy(color = LindaTheme.colors.textPrimary),
                                )
                            },
                        )
                    }
                }

                // 4. Segmented Control
                item {
                    var pace by remember { mutableIntStateOf(1) }
                    LindaShowcaseSection(title = "4. Segmented Control") {
                        LindaSegmentedControl(
                            selectedSegment = pace,
                            widthBehavior   = SegmentedControlWidthBehavior.Proportional,
                        ) {
                            LindaSegment(selected = 0 == pace, onClick = { pace = 0 }) {
                                Text("Relaxed",  style = LindaTheme.typography.labelLarge)
                            }
                            LindaSegment(selected = 1 == pace, onClick = { pace = 1 }) {
                                Text("Balanced", style = LindaTheme.typography.labelLarge)
                            }
                            LindaSegment(selected = 2 == pace, onClick = { pace = 2 }) {
                                Text("Intense",  style = LindaTheme.typography.labelLarge)
                            }
                        }
                    }
                }

                // 5. Search Field
                item {
                    var query by remember { mutableStateOf("") }
                    LindaShowcaseSection(title = "5. Search Field") {
                        LindaSearchBox(
                            value           = query,
                            onValueChange   = { query = it },
                            onClearClicked  = { query = "" },
                            placeholderText = {
                                Text(
                                    "Search cities...",
                                    style = LindaTheme.typography.bodyMedium.copy(color = LindaTheme.colors.textMuted),
                                )
                            },
                        )
                    }
                }

                // 6. Badge
                item {
                    LindaShowcaseSection(title = "6. Badge") {
                        val lSpacing = LindaTheme.spacing
                        Column(verticalArrangement = Arrangement.spacedBy(lSpacing.sm)) {
                            LindaBadge(text = "1 Day Remaining", variant = BadgeVariant.OUTLINED_ROUNDED)
                            LindaBadge(text = "Active Journey",  variant = BadgeVariant.SOLID_LIME_PILL)
                            LindaBadge(text = "14 Days",         variant = BadgeVariant.SUBTLE_GOLD_PILL)
                            LindaBadge(text = "Step 1/3",        variant = BadgeVariant.OUTLINED_GOLD_PILL)
                        }
                    }
                }

                // 7. Avatars
                item {
                    LindaShowcaseSection(title = "7. Avatars") {
                        val lSpacing = LindaTheme.spacing
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(lSpacing.xxl),
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(lSpacing.sm)) {
                                LindaAvatar(
                                    imageUrl = "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?w=100",
                                    size     = AvatarSize.LG,
                                )
                                LindaTextMicro(text = "Single")
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(lSpacing.sm)) {
                                LindaAvatarGroup {
                                    LindaAvatar(imageUrl = "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?w=100", size = AvatarSize.LG)
                                    LindaAvatar(imageUrl = "https://images.unsplash.com/photo-1607990281513-2c110a25bd8c?w=100", size = AvatarSize.LG)
                                }
                                LindaTextMicro(text = "Stacked")
                            }
                        }
                    }
                }

                // 8. Progress
                item {
                    LindaShowcaseSection(title = "8. Progress") {
                        val lSpacing = LindaTheme.spacing
                        Column(verticalArrangement = Arrangement.spacedBy(lSpacing.lg)) {
                            LindaProgressBar(value = 65f, label = "Journey Gradient",    variant = ProgressVariant.GRADIENT, size = ProgressSize.MD)
                            LindaProgressBar(value = 40f, label = "Budget Track",        variant = ProgressVariant.GOLD,     size = ProgressSize.SM)
                            LindaProgressBar(value = 45f, label = "Exploration Progress",variant = ProgressVariant.LIME,     size = ProgressSize.SM)
                        }
                    }
                }

                // 9. Slider
                item {
                    var sliderValue by remember { mutableFloatStateOf(0.75f) }
                    LindaShowcaseSection(title = "9. Slider") {
                        LindaSlider(
                            value         = sliderValue,
                            onValueChange = { sliderValue = it },
                            modifier      = Modifier.fillMaxWidth(),
                        )
                    }
                }

                // 10. Card
                item {
                    LindaShowcaseSection(title = "10. Card") {
                        val lColors  = LindaTheme.colors
                        LindaCard(
                            variant        = CardVariant.GLASS,
                            contentPadding = PaddingValues(0.dp),
                            modifier       = Modifier.fillMaxWidth(),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(
                                        androidx.compose.ui.graphics.Brush.verticalGradient(
                                            listOf(Color(0xFF1A3A2A), Color(0xFF0A1A0A)),
                                        )
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    "Image Placeholder",
                                    style = LindaTheme.typography.bodyMedium.copy(color = lColors.textMuted),
                                )
                            }
                        }
                    }
                }

                // 11. Button
                item {
                    LindaShowcaseSection(title = "11. Button") {
                        val lSpacing = LindaTheme.spacing
                        Column(verticalArrangement = Arrangement.spacedBy(lSpacing.md)) {
                            LindaButton(text = "View Map (Primary)",   onClick = {}, variant = ButtonVariant.PRIMARY,                            modifier = Modifier.fillMaxWidth())
                            LindaButton(text = "View Map (Secondary)", onClick = {}, variant = ButtonVariant.PRIMARY, glow = ButtonGlow.LIME,   modifier = Modifier.fillMaxWidth())
                            LindaButton(text = "Tertiary (Active)",    onClick = {}, variant = ButtonVariant.GLASS,   glow = ButtonGlow.GOLD,   modifier = Modifier.fillMaxWidth())
                            LindaButton(text = "Planned (Disabled)",   onClick = {}, variant = ButtonVariant.GHOST,   enabled = false,          modifier = Modifier.fillMaxWidth())
                            Spacer(Modifier.height(lSpacing.sm))
                            LindaTextLabel(text = "Floating Action Button")
                            Spacer(Modifier.height(lSpacing.sm))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(lSpacing.lg)) {
                                LindaFAB(onClick = {}, size = FabSize.REGULAR)
                                LindaFAB(onClick = {}, size = FabSize.MINI)
                            }
                        }
                    }
                }

                // 12. Chips
                item {
                    var activeChip by remember { mutableStateOf("Paris") }
                    LindaShowcaseSection(title = "12. Chips") {
                        LindaChipRow(modifier = Modifier.fillMaxWidth()) {
                            listOf("Paris", "London", "Tokyo", "New York", "Rome").forEach { label ->
                                item {
                                    LindaChip(
                                        label    = label,
                                        selected = label == activeChip,
                                        onClick  = { activeChip = label },
                                    )
                                }
                            }
                        }
                    }
                }

                // 13. Tag
                item {
                    var sharedChecked by remember { mutableStateOf(true) }
                    var paidChecked   by remember { mutableStateOf(false) }
                    LindaShowcaseSection(title = "13. Tag") {
                        LindaTagGroup {
                            LindaTag(checked = sharedChecked, onClick = { sharedChecked = !sharedChecked }) {
                                Text("Shared",     style = LindaTheme.typography.bodySmall)
                            }
                            LindaTag(checked = paidChecked, onClick = { paidChecked = !paidChecked }) {
                                Text("Paid / Used", style = LindaTheme.typography.bodySmall)
                            }
                            LindaTag(checked = false, onClick = {}) {
                                Text("Relaxed",    style = LindaTheme.typography.bodySmall)
                            }
                            LindaTag(checked = false, onClick = {}) {
                                Text("Adventure",  style = LindaTheme.typography.bodySmall)
                            }
                            LindaTag(checked = true,  onClick = {}) {
                                Text("Foodie",     style = LindaTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                // 14. Checkbox
                item {
                    var checkedA by remember { mutableStateOf(true) }
                    var checkedB by remember { mutableStateOf(false) }
                    LindaShowcaseSection(title = "14. Checkbox") {
                        LindaCheckboxGroup {
                            LindaCheckbox(
                                checked         = checkedA,
                                onCheckedChange = { checkedA = !checkedA },
                                label           = "Checked",
                            )
                            LindaCheckbox(
                                checked         = checkedB,
                                onCheckedChange = { checkedB = !checkedB },
                                label           = "Unchecked",
                            )
                        }
                    }
                }

                // 15. RadioButton
                item {
                    var option by remember { mutableStateOf("Selected") }
                    LindaShowcaseSection(title = "15. RadioButton") {
                        LindaRadioGroup {
                            LindaRadioButton(label = "Selected",   selected = option == "Selected",   onClick = { option = "Selected" })
                            LindaRadioButton(label = "Unselected", selected = option == "Unselected", onClick = { option = "Unselected" })
                        }
                    }
                }

                // ─────────────────────────────────────────────
                // ④ LINDA LEVEL 2 — Compositions
                // ─────────────────────────────────────────────
                item { LindaSectionDivider(level = "LEVEL 2", title = "Linda Design System") }

                // 1. Row
                item {
                    var localExpert  by remember { mutableStateOf(true) }
                    var economyFlight by remember { mutableStateOf(false) }
                    LindaShowcaseSection(title = "1. Row") {
                        val lSpacing = LindaTheme.spacing
                        Column(verticalArrangement = Arrangement.spacedBy(lSpacing.sm)) {
                            LindaRow(label = "Florence, Italy",            ordinal = 1,           showDragHandle = true,  onRemove = {})
                            LindaRow(label = "Include Local Expert Guide", checked = localExpert,   onToggle = { localExpert  = !localExpert  })
                            LindaRow(label = "Standard Economy Flight",    checked = economyFlight, onToggle = { economyFlight = !economyFlight })
                        }
                    }
                }

                // 2. Bottom Bar
                item {
                    LindaShowcaseSection(title = "2. Bottom Bar") {
                        Box(modifier = Modifier.fillMaxWidth().height(96.dp)) {
                            LindaBottomBar(
                                modifier   = Modifier.align(Alignment.BottomCenter),
                                onFabClick = {},
                            ) {
                                LindaNavItem(icon = LindaIcons.Default.Home,    label = "Home",    isActive = false, onClick = {})
                                LindaNavItem(icon = LindaIcons.Default.Trips,   label = "Trips",   isActive = true,  onClick = {})
                                LindaNavItem(icon = LindaIcons.Default.Guide,   label = "Guide",   isActive = false, onClick = {})
                                LindaNavItem(icon = LindaIcons.Default.Profile, label = "Profile", isActive = false, onClick = {})
                            }
                        }
                    }
                }

                // 3. Item Counter
                item {
                    var groupSize by remember { mutableIntStateOf(1) }
                    LindaShowcaseSection(title = "3. Item Counter") {
                        LindaItemCounter(
                            label       = "Group Size",
                            subtitle    = "Including children & adults",
                            value       = groupSize,
                            onDecrement = { if (groupSize > 1) groupSize-- },
                            onIncrement = { groupSize++ },
                            min         = 1,
                            max         = 20,
                        )
                    }
                }

                // 4. Selector
                item {
                    var q    by remember { mutableStateOf("") }
                    var chip by remember { mutableStateOf("Paris") }
                    LindaShowcaseSection(title = "4. Selector") {
                        LindaSelector(
                            query         = q,
                            onQueryChange = { q = it },
                        ) {
                            listOf("Paris", "London", "Tokyo", "New York", "Rome").forEach { label ->
                                item {
                                    LindaChip(
                                        label    = label,
                                        selected = label == chip,
                                        onClick  = { chip = label },
                                    )
                                }
                            }
                        }
                    }
                }

                // 5. Destination Card
                item {
                    LindaShowcaseSection(title = "5. Destination Card") {
                        LindaDestinationCard(
                            imageUrl = "https://images.unsplash.com/photo-1719870042839-c48ba42ac7ef?w=600",
                            title    = "Paris, France",
                            duration = "3 Days",
                            theme    = "Art & Cafes",
                            status   = DestinationStatus.ACTIVE,
                        ) {
                            val lColors  = LindaTheme.colors
                            val lSpacing = LindaTheme.spacing
                            Column(verticalArrangement = Arrangement.spacedBy(lSpacing.sm)) {
                                Row(
                                    modifier              = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment     = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        "88%",
                                        style = LindaTheme.typography.displaySmall.copy(color = lColors.accentGold),
                                    )
                                    LindaTextLabel(text = "Budget")
                                }
                                LindaProgressBar(value = 88f, showLabel = false, variant = ProgressVariant.GRADIENT, size = ProgressSize.SM)
                            }
                        }
                    }
                }

                // 6. Budget Slider
                item {
                    var budget by remember { mutableFloatStateOf(250f) }
                    LindaShowcaseSection(title = "6. Budget Slider") {
                        LindaBudgetSlider(
                            value         = budget,
                            onValueChange = { budget = it },
                            valueRange    = 50f..1000f,
                            label         = "Daily Budget",
                            currency      = "$",
                            unit          = "/day",
                        )
                    }
                }

                // 7. Date Selectors
                item {
                    LindaShowcaseSection(title = "7. Date Selectors") {
                        LindaDateSelectors(
                            startDate        = "Oct 12",
                            endDate          = "Oct 26",
                            durationContent  = {
                                LindaBadge(text = "14 DAYS", variant = BadgeVariant.SUBTLE_GOLD_PILL)
                            },
                        )
                    }
                }

                // 8. Header Component
                item {
                    LindaShowcaseSection(title = "8. Header Component") {
                        val lSpacing = LindaTheme.spacing
                        val lColors  = LindaTheme.colors
                        val lBorders = LindaTheme.borders
                        val lShadow  = LindaTheme.shadow
                        val lTypo    = LindaTheme.typography
                        val lSizes   = LindaTheme.sizes
                        Column(verticalArrangement = Arrangement.spacedBy(lSpacing.md)) {
                            // Variant A — step badge in trailingContent
                            LindaHeader(
                                title          = "Trip Details",
                                leadingContent = {
                                    LindaIconButton(
                                        onClick = {},
                                        variant = IconButtonVariant.GHOST,
                                    ) {
                                        Icon(
                                            imageVector        = LindaIcons.Default.ArrowBack,
                                            contentDescription = "Back",
                                            tint               = lColors.textPrimary,
                                            modifier           = Modifier.size(lSizes.iconMd),
                                        )
                                    }
                                },
                                trailingContent = {
                                    val pillShape = RoundedCornerShape(lBorders.radiusFull)
                                    Box(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .shadow(
                                                elevation    = lShadow.glowElevation,
                                                shape        = pillShape,
                                                ambientColor = lColors.glowGold,
                                                spotColor    = lColors.glowGold,
                                            )
                                            .clip(pillShape)
                                            .background(lColors.accentGold.copy(alpha = 0.20f))
                                            .border(lBorders.widthThin, lColors.accentGold.copy(alpha = 0.30f), pillShape)
                                            .padding(horizontal = lSpacing.md, vertical = lSpacing.xs),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text  = "STEP 1/3",
                                            style = lTypo.labelSmall.copy(color = lColors.accentGold),
                                        )
                                    }
                                },
                            )
                            LindaDivider(modifier = Modifier.padding(vertical = lSpacing.xs))
                            // Variant B — more button in trailingContent
                            LindaHeader(
                                title          = "Select Route",
                                leadingContent = {
                                    LindaIconButton(
                                        onClick = {},
                                        variant = IconButtonVariant.GHOST,
                                    ) {
                                        Icon(
                                            imageVector        = LindaIcons.Default.ArrowBack,
                                            contentDescription = "Back",
                                            tint               = lColors.textPrimary,
                                            modifier           = Modifier.size(lSizes.iconMd),
                                        )
                                    }
                                },
                                trailingContent = {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier         = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(lColors.surfaceGlass)
                                            .border(lBorders.widthThin, lColors.borderGlassStrong, CircleShape)
                                            .clickable { },
                                    ) {
                                        Icon(
                                            imageVector        = LindaIcons.Default.MoreVert,
                                            contentDescription = "More options",
                                            tint               = lColors.textPrimary,
                                            modifier           = Modifier.size(lSizes.iconMd),
                                        )
                                    }
                                },
                            )
                        }
                    }
                }

                // Bottom spacer
                item { Spacer(Modifier.height(spacing.xxxl)) }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
// Private helpers
// ─────────────────────────────────────────────────────────────

/** Full-width divider banner between design system levels. */
@Composable
private fun LindaSectionDivider(level: String, title: String) {
    Column(
        modifier            = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text  = title,
            style = androidx.compose.ui.text.TextStyle(
                fontFamily    = null,
                color         = Color.White,
                fontSize      = androidx.compose.ui.unit.TextUnit(20f, androidx.compose.ui.unit.TextUnitType.Sp),
                letterSpacing = androidx.compose.ui.unit.TextUnit(-0.5f, androidx.compose.ui.unit.TextUnitType.Sp),
            ),
        )
        Text(
            text  = level,
            style = androidx.compose.ui.text.TextStyle(
                color         = Color(0xB3FFD700),
                fontSize      = androidx.compose.ui.unit.TextUnit(12f, androidx.compose.ui.unit.TextUnitType.Sp),
                letterSpacing = androidx.compose.ui.unit.TextUnit(2.4f, androidx.compose.ui.unit.TextUnitType.Sp),
            ),
        )
    }
}

/**
 * Glass card wrapper with a sub-heading label.
 * Must be called inside a [LindaTheme] composition.
 */
@Composable
private fun LindaShowcaseSection(
    title  : String,
    content: @Composable () -> Unit,
) {
    val colors  = LindaTheme.colors
    val spacing = LindaTheme.spacing
    val typo    = LindaTheme.typography

    Column(
        modifier            = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.md),
    ) {
        Text(
            text  = title.uppercase(),
            style = typo.labelSmall.copy(
                color         = colors.textMuted,
                letterSpacing = androidx.compose.ui.unit.TextUnit(1.4f, androidx.compose.ui.unit.TextUnitType.Sp),
            ),
        )
        LindaCard(glowColor = Color.Transparent) {
            Column(
                modifier            = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing.md),
            ) {
                content()
            }
        }
    }
}

/**
 * Single colour swatch tile — rounded rect with hex + name label.
 * Must be called inside a [LindaTheme] composition.
 */
@Composable
private fun LindaColorSwatch(
    color   : Color,
    hex     : String,
    name    : String,
    modifier: Modifier = Modifier,
) {
    val colors = LindaTheme.colors
    val typo   = LindaTheme.typography

    Column(
        modifier            = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color)
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
        ) {
            Text(
                text     = hex,
                style    = typo.labelSmall.copy(
                    color = if (color.luminance() > 0.4f) Color(0x80000000) else Color.White.copy(alpha = 0.5f),
                ),
                modifier = Modifier.padding(10.dp),
            )
        }
        Text(
            text     = name,
            style    = typo.labelSmall.copy(color = colors.textPrimary),
            modifier = Modifier.padding(horizontal = 4.dp),
        )
    }
}


/** Compute approximate relative luminance for contrast decision. */
private fun Color.luminance(): Float {
    val r = red.toDouble()
    val g = green.toDouble()
    val b = blue.toDouble()
    return (0.2126 * r + 0.7152 * g + 0.0722 * b).toFloat()
}