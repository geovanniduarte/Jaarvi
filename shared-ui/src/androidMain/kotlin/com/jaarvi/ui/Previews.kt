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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.components.BadgeVariant
import com.jaarvi.ui.components.ButtonGlow
import com.jaarvi.ui.components.ButtonVariant
import com.jaarvi.ui.components.DestinationStatus
import com.jaarvi.ui.components.IconButtonVariant
import com.jaarvi.ui.components.ProgressSize
import com.jaarvi.ui.components.ProgressVariant
import com.jaarvi.ui.components.AvatarGroup
import com.jaarvi.ui.components.Badge
import com.jaarvi.ui.components.Button
import com.jaarvi.ui.components.Card
import com.jaarvi.ui.components.DestinationCard
import com.jaarvi.ui.components.Header
import com.jaarvi.ui.components.IconButton
import com.jaarvi.ui.components.NavBar
import com.jaarvi.ui.components.ProgressBar
import com.jaarvi.ui.components.StatCard
import com.jaarvi.ui.components.StatsRow
import com.jaarvi.ui.components.buttons.JaarviButton
import com.jaarvi.ui.linda.components.LindaButton
import com.jaarvi.ui.theme.*


@Composable
@Preview(showBackground = false)
fun DefaultPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        JaarviButton(
            text = "Hello, World!",
            onClick = { /* Do something */ }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
            LindaButton(text = "Primary",  onClick = {}, variant = com.jaarvi.ui.linda.components.ButtonVariant.PRIMARY)
            LindaButton(text = "Gold",     onClick = {}, variant = com.jaarvi.ui.linda.components.ButtonVariant.GLASS, glow = com.jaarvi.ui.linda.components.ButtonGlow.GOLD)
            LindaButton(text = "Lime",     onClick = {}, variant = com.jaarvi.ui.linda.components.ButtonVariant.GLASS, glow = com.jaarvi.ui.linda.components.ButtonGlow.LIME)
        }
    }

}

@Preview
@Composable
fun TravelAppScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPrimary)
    ) {
        // Background Glow Effects
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .offset(x = 250.dp, y = (-80).dp)
                    .blur(Blur.lg)
                    .background(ColorGlowGold, shape = androidx.compose.foundation.shape.CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(400.dp)
                    .offset(x = (-160).dp, y = 400.dp)
                    .blur(Blur.lg)
                    .background(ColorGlowEmerald, shape = androidx.compose.foundation.shape.CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .offset(x = 250.dp, y = 800.dp)
                    .blur(Blur.lg)
                    .background(ColorGlowGoldLight, shape = androidx.compose.foundation.shape.CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(350.dp)
                    .offset(x = 100.dp, y = 1200.dp)
                    .blur(Blur.lg)
                    .background(ColorGlowEmeraldLight, shape = androidx.compose.foundation.shape.CircleShape)
            )
        }

        Scaffold(
            topBar = {
                Header(
                    title = "Trip Detail: Golden Horizon",
                    subtitle = "Explorer",
                    onBackClick = { /* Handle back */ },
                    onShareClick = { /* Handle share */ }
                )
            },
            bottomBar = {
                NavBar(
                    onNavItemClick = { index -> /* Handle nav click */ },
                    onFabClick = { /* Handle FAB click */ }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Spacing.lg),
                verticalArrangement = Arrangement.spacedBy(Spacing.lg)
            ) {
                Spacer(modifier = Modifier.height(Spacing.lg))

                // Trip Summary Card
                Card {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Spacing.xxl)
                    ) {
                        // Header with Badge
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Badge(
                                    text = "Status: Active Journey",
                                    variant = BadgeVariant.LIME
                                )
                                Text(
                                    text = "Trip Summary",
                                    style = LocalTravelTypography.current.displayMedium.copy(
                                        color = Color.White
                                    )
                                )
                            }

                            IconButton(
                                onClick = { /* Handle click */ },
                                variant = IconButtonVariant.GLASS
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ThumbUp,
                                    contentDescription = "Tips",
                                    tint = ColorAccentGold,
                                    modifier = Modifier.size(IconSize.md)
                                )
                            }
                        }

                        // Stats
                        StatsRow {
                            StatCard(
                                label = "Budget Remaining",
                                value = "$4,500",
                                subtext = "/ $5k",
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                label = "Travel Dates",
                                value = "Sept 12 - 24",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Progress Bar
                        ProgressBar(
                            value = 65f,
                            label = "Total Trip Completion",
                            variant = ProgressVariant.GRADIENT
                        )
                    }
                }

                // Section Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.lg),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Itinerary Journey",
                        style = LocalTravelTypography.current.displaySmall.copy(
                            color = Color.White
                        )
                    )

                    Badge(
                        text = "3 Cities",
                        variant = BadgeVariant.GOLD
                    )
                }

                // Paris Card
                DestinationCard(
                    imageUrl = "https://images.unsplash.com/photo-1719870042839-c48ba42ac7ef?w=600",
                    title = "Paris, France",
                    duration = "3 Days",
                    theme = "Art & Cafes",
                    status = DestinationStatus.ACTIVE
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Spacing.xl)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AvatarGroup(
                                imageUrls = listOf(
                                    "https://images.unsplash.com/photo-1759423672542-c51b3b1b9e69?w=100",
                                    "https://images.unsplash.com/photo-1768933294181-82778103e501?w=100"
                                )
                            )
                            Button(
                                text = "Details",
                                onClick = { /* Handle click */ },
                                glow = ButtonGlow.GOLD
                            )
                        }

                        ProgressBar(
                            value = 88f,
                            label = "Daily Budget Used",
                            variant = ProgressVariant.GOLD,
                            size = ProgressSize.SM
                        )
                    }
                }

                // Rome Card
                DestinationCard(
                    imageUrl = "https://images.unsplash.com/photo-1662898290891-a6c7f022e851?w=600",
                    title = "Rome, Italy",
                    duration = "4 Days",
                    theme = "Ancient Wonders",
                    status = DestinationStatus.ACTIVE
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Spacing.xl)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = ColorAccentGold,
                                    modifier = Modifier.size(IconSize.sm)
                                )
                                Text(
                                    text = "12 Saved Spots",
                                    style = LocalTravelTypography.current.bodyMedium.copy(
                                        color = ColorTextSecondary
                                    )
                                )
                            }
                            Button(
                                text = "View Map",
                                onClick = { /* Handle click */ },
                                glow = ButtonGlow.GOLD
                            )
                        }

                        ProgressBar(
                            value = 45f,
                            label = "Exploration Progress",
                            variant = ProgressVariant.LIME,
                            size = ProgressSize.SM
                        )
                    }
                }

                // Florence Card
                DestinationCard(
                    imageUrl = "https://images.unsplash.com/photo-1528632799532-401ebb5a8e7f?w=600",
                    title = "Florence, Italy",
                    duration = "3 Days",
                    theme = "Tuscan Sunsets",
                    status = DestinationStatus.PLANNED
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Next Destination",
                            style = LocalTravelTypography.current.bodyMedium.copy(
                                color = ColorTextMuted
                            )
                        )
                        Button(
                            text = "Planned",
                            onClick = { /* Handle click */ },
                            glow = ButtonGlow.NONE
                        )
                    }
                }

                // Design System Showcase
                Spacer(modifier = Modifier.height(Spacing.xxxl))

                Text(
                    text = "Design System Components",
                    style = LocalTravelTypography.current.displaySmall.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = Spacing.lg)
                )

                Card {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Spacing.xxl)
                    ) {
                        // Buttons
                        Column(
                            verticalArrangement = Arrangement.spacedBy(Spacing.md)
                        ) {
                            Text(
                                text = "Buttons",
                                style = LocalTravelTypography.current.headlineLarge.copy(
                                    color = Color.White
                                )
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    text = "Primary",
                                    onClick = {},
                                    variant = ButtonVariant.PRIMARY
                                )
                                Button(
                                    text = "Glass",
                                    onClick = {},
                                    variant = ButtonVariant.GLASS,
                                    glow = ButtonGlow.GOLD
                                )
                            }
                        }

                        // Badges
                        Column(
                            verticalArrangement = Arrangement.spacedBy(Spacing.md)
                        ) {
                            Text(
                                text = "Badges",
                                style = LocalTravelTypography.current.headlineLarge.copy(
                                    color = Color.White
                                )
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Spacing.md)
                            ) {
                                Badge(text = "Active", variant = BadgeVariant.GOLD)
                                Badge(text = "65%", variant = BadgeVariant.LIME)
                                Badge(text = "Next", variant = BadgeVariant.MUTED, glow = false)
                            }
                        }

                        // Progress Bars
                        Column(
                            verticalArrangement = Arrangement.spacedBy(Spacing.md)
                        ) {
                            Text(
                                text = "Progress Barssss",
                                style = LocalTravelTypography.current.headlineLarge.copy(
                                    color = Color.White
                                )
                            )
                            ProgressBar(
                                value = 75f,
                                label = "Gradient",
                                variant = ProgressVariant.GRADIENT
                            )
                            ProgressBar(
                                value = 60f,
                                label = "Gold",
                                variant = ProgressVariant.GOLD,
                                size = ProgressSize.SM
                            )
                        }

                        // Typography
                        Column(
                            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                        ) {
                            Text(
                                text = "Typography",
                                style = LocalTravelTypography.current.headlineLarge.copy(
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "Display Medium - 24sp Bold",
                                style = LocalTravelTypography.current.displayMedium.copy(color = Color.White)
                            )
                            Text(
                                text = "Display Small - 20sp Bold",
                                style = LocalTravelTypography.current.displaySmall.copy(color = Color.White)
                            )
                            Text(
                                text = "Body Medium - 14sp",
                                style = LocalTravelTypography.current.bodyMedium.copy(color = ColorTextSecondary)
                            )
                            Badge(text = "Label - 12sp Bold", variant = BadgeVariant.GOLD)
                        }

                        // Colors
                        Column(
                            verticalArrangement = Arrangement.spacedBy(Spacing.md)
                        ) {
                            Text(
                                text = "Colors",
                                style = LocalTravelTypography.current.headlineLarge.copy(
                                    color = Color.White
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(Spacing.md)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                            .background(ColorAccentGold)
                                    )
                                    Text(
                                        text = "Accent Gold",
                                        style = LocalTravelTypography.current.bodySmall.copy(
                                            color = ColorTextMuted
                                        )
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                            .background(ColorAccentLime)
                                    )
                                    Text(
                                        text = "Accent Lime",
                                        style = LocalTravelTypography.current.bodySmall.copy(
                                            color = ColorTextMuted
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.xxxl))
            }
        }
    }
}
