package com.jaarvi.ui.linda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.AvatarSize
import com.jaarvi.ui.linda.components.BadgeVariant
import com.jaarvi.ui.linda.components.ButtonGlow
import com.jaarvi.ui.linda.components.ButtonVariant
import com.jaarvi.ui.linda.components.DestinationStatus
import com.jaarvi.ui.linda.components.FabSize
import com.jaarvi.ui.linda.components.IconButtonVariant
import com.jaarvi.ui.linda.components.ProgressSize
import com.jaarvi.ui.linda.components.ProgressVariant
import com.jaarvi.ui.linda.components.StatusDotSize
import com.jaarvi.ui.linda.components.LindaAvatarGroup
import com.jaarvi.ui.linda.components.LindaBadge
import com.jaarvi.ui.linda.components.LindaButton
import com.jaarvi.ui.linda.components.LindaCard
import com.jaarvi.ui.linda.components.LindaDestinationCard
import com.jaarvi.ui.linda.components.LindaDivider
import com.jaarvi.ui.linda.components.LindaFAB
import com.jaarvi.ui.linda.components.LindaHeader
import com.jaarvi.ui.linda.components.LindaIconButton
import com.jaarvi.ui.linda.components.LindaNavBar
import com.jaarvi.ui.linda.components.LindaProgressBar
import com.jaarvi.ui.linda.components.LindaStatCard
import com.jaarvi.ui.linda.components.LindaStatusIndicatorDot
import com.jaarvi.ui.linda.components.LindaStatsRow
import com.jaarvi.ui.linda.components.LindaTextBody
import com.jaarvi.ui.linda.components.LindaTextH2
import com.jaarvi.ui.linda.components.LindaTextH3
import com.jaarvi.ui.linda.components.LindaTextLabel
import com.jaarvi.ui.linda.components.LindaTextMicro
import com.jaarvi.ui.linda.components.LindaTextSmall
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
                    .size(250.dp).offset(x = 250.dp, y = 800.dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowGoldLight, CircleShape),
            )
            Box(
                modifier = Modifier
                    .size(350.dp).offset(x = 100.dp, y = 1200.dp)
                    .blur(glass.blurAmbient)
                    .background(colors.glowEmeraldLight, CircleShape),
            )
        }

        // ── Scaffold ──────────────────────────────────────────
        Scaffold(
            topBar = {
                LindaHeader(
                    title        = "Trip Detail: Golden Horizon",
                    subtitle     = "Explorer",
                    onBackClick  = {},
                    onShareClick = {},
                )
            },
            bottomBar = {
                LindaNavBar(
                    onNavItemClick = { /* index -> */ },
                    onFabClick     = {},
                )
            },
            backgroundColor = Color.Transparent,
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = spacing.lg),
                verticalArrangement = Arrangement.spacedBy(spacing.lg),
            ) {
                Spacer(modifier = Modifier.height(spacing.lg))

                // ── Trip Summary Card ─────────────────────────
                LindaCard {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.xxl)) {

                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.Top,
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
                                LindaBadge(text = "Status: Active Journey", variant = BadgeVariant.LIME)
                                Text(
                                    text  = "Trip Summary",
                                    style = typo.displayMedium.copy(color = colors.textPrimary),
                                )
                            }
                            LindaIconButton(onClick = {}, variant = IconButtonVariant.GLASS) {
                                Icon(
                                    imageVector        = Icons.Rounded.ThumbUp,
                                    contentDescription = "Tips",
                                    tint               = colors.accentGold,
                                    modifier           = Modifier.size(sizes.iconMd),
                                )
                            }
                        }

                        LindaStatsRow {
                            LindaStatCard(label = "Budget Remaining", value = "$4,500", subtext = "/ $5k",    modifier = Modifier.weight(1f))
                            LindaStatCard(label = "Travel Dates",     value = "Sept 12–24",                   modifier = Modifier.weight(1f))
                        }

                        LindaDivider()

                        LindaProgressBar(value = 65f, label = "Total Trip Completion", variant = ProgressVariant.GRADIENT)
                    }
                }

                // ── Section header ────────────────────────────
                Row(
                    modifier              = Modifier.fillMaxWidth().padding(horizontal = spacing.lg),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    Text(text = "Itinerary Journey", style = typo.displaySmall.copy(color = colors.textPrimary))
                    LindaBadge(text = "3 Cities", variant = BadgeVariant.GOLD)
                }

                // ── Paris — ACTIVE ────────────────────────────
                LindaDestinationCard(
                    imageUrl = "https://images.unsplash.com/photo-1719870042839-c48ba42ac7ef?w=600",
                    title = "Paris, France", duration = "3 Days", theme = "Art & Cafes",
                    status = DestinationStatus.ACTIVE,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.xl)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
                                LindaStatusIndicatorDot(status = DestinationStatus.ACTIVE, size = StatusDotSize.MD)
                                LindaAvatarGroup(
                                    imageUrls = listOf(
                                        "https://images.unsplash.com/photo-1759423672542-c51b3b1b9e69?w=100",
                                        "https://images.unsplash.com/photo-1768933294181-82778103e501?w=100",
                                    ),
                                )
                            }
                            LindaButton(text = "Details", onClick = {}, glow = ButtonGlow.GOLD)
                        }
                        LindaProgressBar(value = 88f, label = "Daily Budget Used", variant = ProgressVariant.GOLD, size = ProgressSize.SM)
                    }
                }

                // ── Rome — ACTIVE ─────────────────────────────
                LindaDestinationCard(
                    imageUrl = "https://images.unsplash.com/photo-1662898290891-a6c7f022e851?w=600",
                    title = "Rome, Italy", duration = "4 Days", theme = "Ancient Wonders",
                    status = DestinationStatus.ACTIVE,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.xl)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
                                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = colors.accentGold, modifier = Modifier.size(sizes.iconSm))
                                LindaTextSmall(text = "12 Saved Spots", color = colors.textSecondary)
                            }
                            LindaButton(text = "View Map", onClick = {}, glow = ButtonGlow.GOLD)
                        }
                        LindaProgressBar(value = 45f, label = "Exploration Progress", variant = ProgressVariant.LIME, size = ProgressSize.SM)
                    }
                }

                // ── Florence — PLANNED ────────────────────────
                LindaDestinationCard(
                    imageUrl = "https://images.unsplash.com/photo-1528632799532-401ebb5a8e7f?w=600",
                    title = "Florence, Italy", duration = "3 Days", theme = "Tuscan Sunsets",
                    status = DestinationStatus.PLANNED,
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
                            LindaStatusIndicatorDot(status = DestinationStatus.PLANNED, size = StatusDotSize.MD)
                            LindaTextSmall(text = "Next Destination", color = colors.textMuted)
                        }
                        LindaButton(text = "Planned", onClick = {}, glow = ButtonGlow.NONE)
                    }
                }

                // ─────────────────────────────────────────────
                // Design System Showcase
                // ─────────────────────────────────────────────
                Spacer(modifier = Modifier.height(spacing.xxl))
                LindaTextH2(text = "Design System Components")

                LindaCard {
                    Column(verticalArrangement = Arrangement.spacedBy(spacing.xxl)) {

                        // Buttons
                        LindaTextH3(text = "Buttons")
                        Row(horizontalArrangement = Arrangement.spacedBy(spacing.md)) {
                            LindaButton(text = "Primary",  onClick = {}, variant = ButtonVariant.PRIMARY)
                            LindaButton(text = "Gold",     onClick = {}, variant = ButtonVariant.GLASS, glow = ButtonGlow.GOLD)
                            LindaButton(text = "Lime",     onClick = {}, variant = ButtonVariant.GLASS, glow = ButtonGlow.LIME)
                        }

                        LindaDivider()

                        // Badges
                        LindaTextH3(text = "Badges")
                        Row(horizontalArrangement = Arrangement.spacedBy(spacing.md)) {
                            LindaBadge(text = "Active",  variant = BadgeVariant.GOLD)
                            LindaBadge(text = "65%",     variant = BadgeVariant.LIME)
                            LindaBadge(text = "Next",    variant = BadgeVariant.MUTED, glow = false)
                        }

                        LindaDivider()

                        // Status Indicator Dots
                        LindaTextH3(text = "Status Indicators")
                        Row(horizontalArrangement = Arrangement.spacedBy(spacing.xl), verticalAlignment = Alignment.CenterVertically) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
                                LindaStatusIndicatorDot(status = DestinationStatus.ACTIVE,    size = StatusDotSize.LG)
                                LindaTextMicro(text = "Active")
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
                                LindaStatusIndicatorDot(status = DestinationStatus.COMPLETED, size = StatusDotSize.LG)
                                LindaTextMicro(text = "Done")
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
                                LindaStatusIndicatorDot(status = DestinationStatus.PLANNED,   size = StatusDotSize.LG)
                                LindaTextMicro(text = "Planned")
                            }
                        }

                        LindaDivider()

                        // Progress Bars
                        LindaTextH3(text = "Progress Bars")
                        LindaProgressBar(value = 75f, label = "Gradient", variant = ProgressVariant.GRADIENT)
                        LindaProgressBar(value = 60f, label = "Gold",     variant = ProgressVariant.GOLD, size = ProgressSize.SM)
                        LindaProgressBar(value = 40f, label = "Lime",     variant = ProgressVariant.LIME, size = ProgressSize.SM)

                        LindaDivider()

                        // FABs
                        LindaTextH3(text = "FAB")
                        Row(horizontalArrangement = Arrangement.spacedBy(spacing.lg), verticalAlignment = Alignment.CenterVertically) {
                            LindaFAB(onClick = {}, size = FabSize.REGULAR)
                            LindaFAB(onClick = {}, size = FabSize.MINI)
                        }

                        LindaDivider()

                        // Avatars
                        LindaTextH3(text = "Avatars")
                        LindaAvatarGroup(
                            imageUrls = listOf(
                                "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?w=100",
                                "https://images.unsplash.com/photo-1607990281513-2c110a25bd8c?w=100",
                                "https://images.unsplash.com/photo-1531746020798-e6953c6e8e04?w=100",
                            ),
                            max  = 3,
                            size = AvatarSize.LG,
                        )

                        LindaDivider()

                        // Typography scale
                        LindaTextH3(text = "Typography")
                        LindaTextH2(text  = "H2 — 20 sp Bold")
                        LindaTextH3(text  = "H3 — 18 sp Bold")
                        LindaTextBody(text  = "Body — 16 sp Regular. Tempor incididunt ut labore.")
                        LindaTextSmall(text = "Small — 14 sp Medium")
                        LindaTextMicro(text = "Micro — 12 sp Regular")
                        LindaTextLabel(text = "Label — 10 sp Bold Uppercase")

                        LindaDivider()

                        // Colour swatches
                        LindaTextH3(text = "Accent Colours")
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacing.md)) {
                            Column(modifier = Modifier.weight(1f)) {
                                Box(modifier = Modifier.fillMaxWidth().height(48.dp).background(colors.accentGold))
                                LindaTextMicro(text = "Gold #F2B90D")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Box(modifier = Modifier.fillMaxWidth().height(48.dp).background(colors.accentLime))
                                LindaTextMicro(text = "Lime #A3E635")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(spacing.xxxl))
            }
        }
    }
}
