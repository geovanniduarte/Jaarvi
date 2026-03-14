package com.jaarvi.ui.linda.components.level2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import com.jaarvi.ui.linda.components.level0.icons.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.level1.CardVariant
import com.jaarvi.ui.linda.components.level1.DestinationStatus
import com.jaarvi.ui.linda.components.level1.LindaCard
import com.jaarvi.ui.linda.theme.LindaTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.crossfade.CrossfadePlugin
import com.skydoves.landscapist.image.LandscapistImage
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

// ─────────────────────────────────────────────────────────────
// LindaDestinationCardOld — LEVEL 2
//
// Hero card for a trip leg / city. Migrated from SunDestinationCard.
// Uses [LindaCard] as its outer container.
//
// Shows a full-bleed [imageUrl] with warm + dark gradient overlays,
// a title, a duration/theme subtitle row, and an optional
// [bottomContent] slot (e.g. avatar group + CTA button + progress bar).
//
// Status drives accent colour and card opacity:
//   ACTIVE    → statusActive (lime) + lime glow on subtitle row
//   COMPLETED → statusCompleted (gold) + gold glow on subtitle row
//   PLANNED   → statusPlanned (white 60%), no glow, 85 % opacity card
//
// DestinationStatus is defined in StatusIndicatorDot.kt (Level 1).
// ─────────────────────────────────────────────────────────────

/**
 * @param imageUrl      Hero image URL — loaded via Landscapist (shimmer + crossfade).
 * @param title         Destination name (e.g. "Paris, France").
 * @param duration      Trip duration label (e.g. "3 Days").
 * @param theme         Thematic tag (e.g. "Art & Cafes").
 * @param modifier      Optional layout modifier.
 * @param status        Controls accent colour and card opacity.
 * @param bottomContent Optional slot rendered beneath the image panel.
 */
@Composable
fun LindaDestinationCardOld(
    imageUrl     : String,
    title        : String,
    duration     : String,
    theme        : String,
    modifier     : Modifier                  = Modifier,
    status       : DestinationStatus         = DestinationStatus.ACTIVE,
    bottomContent: @Composable (() -> Unit)? = null,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val spacing = LindaTheme.spacing
    val shadow  = LindaTheme.shadow
    val sizes   = LindaTheme.sizes
    val typo    = LindaTheme.typography

    val accentColor = when (status) {
        DestinationStatus.ACTIVE    -> colors.statusActive
        DestinationStatus.COMPLETED -> colors.statusCompleted
        DestinationStatus.PLANNED   -> colors.statusPlanned
    }
    val glowColor = when (status) {
        DestinationStatus.ACTIVE    -> colors.glowLime
        DestinationStatus.COMPLETED -> colors.glowGold
        DestinationStatus.PLANNED   -> Color.Transparent
    }
    // PLANNED cards are slightly dimmed
    val cardAlpha = if (status == DestinationStatus.PLANNED) 0.4f else 0.6f

    // ── LindaCard owns the glassmorphic surface ───────────────
    // contentPadding = 0.dp so the hero image fills edge-to-edge.
    // backgroundColor drives the PLANNED dim state.
    LindaCard(
        modifier        = modifier.fillMaxWidth(),
        variant         = CardVariant.GLASS,
        shape           = RoundedCornerShape(borders.radiusLg),
        glowColor       = null,   // standard black card-elevation shadow
        borderColor     = colors.borderGlass,
        backgroundColor = colors.surfaceGlass.copy(alpha = cardAlpha),
        contentPadding  = PaddingValues(0.dp),
    ) {
        Column {
            // ── Hero image ────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sizes.destinationImageHeight),
            ) {
                LandscapistImage(
                    imageModel   = { imageUrl },
                    modifier     = Modifier.fillMaxSize(),
                    imageOptions = ImageOptions(
                        contentScale       = ContentScale.Crop,
                        alignment          = Alignment.Center,
                        contentDescription = title,
                    ),
                    component = rememberImageComponent {
                        +ShimmerPlugin(
                            shimmer = Shimmer.Flash(
                                baseColor      = colors.surfaceGlass,
                                highlightColor = colors.borderGlass,
                            ),
                        )
                        +CrossfadePlugin(duration = 300)
                    },
                    failure = {
                        Box(
                            modifier         = Modifier
                                .fillMaxSize()
                                .background(colors.surfaceGlass),
                            contentAlignment = Alignment.Center,
                        ) {}
                    },
                )

                // Warm gradient — bottom-up gold tint
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(colors.overlayWarmEnd, colors.overlayWarmStart),
                            ),
                        ),
                )

                // Dark vignette — readability for text overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    colors.overlayDarkEnd,
                                    Color.Black.copy(alpha = 0.20f),
                                    colors.overlayDarkStart,
                                ),
                            ),
                        ),
                )

                // Title + subtitle overlay (bottom-start)
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(spacing.lg),
                ) {
                    Text(
                        text     = title,
                        style    = typo.displayMedium.copy(color = Color.White),
                        modifier = Modifier.padding(bottom = spacing.xs),
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(spacing.xs),
                        verticalAlignment     = Alignment.CenterVertically,
                        modifier = Modifier.then(
                            if (status != DestinationStatus.PLANNED) Modifier.shadow(
                                elevation    = shadow.glowElevation,
                                ambientColor = glowColor,
                                spotColor    = glowColor,
                            ) else Modifier
                        ),
                    ) {
                        Icon(
                            imageVector        = LindaIcons.Default.Clock,
                            contentDescription = null,
                            tint               = accentColor,
                            modifier           = Modifier.size(sizes.iconXs),
                        )
                        Text(
                            text  = "$duration — $theme",
                            style = typo.headlineMedium.copy(color = accentColor),
                        )
                    }
                }
            }

            // ── Optional bottom content ───────────────────────
            if (bottomContent != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.surfaceGlassLight)
                        .padding(spacing.xl),
                ) {
                    bottomContent()
                }
            }
        }
    }
}