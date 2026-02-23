package com.jaarvi.ui.linda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.theme.LindaTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.crossfade.CrossfadePlugin
import com.skydoves.landscapist.image.LandscapistImage
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

// ─────────────────────────────────────────────────────────────
// DestinationStatus
// ─────────────────────────────────────────────────────────────

enum class DestinationStatus {
    /** Currently active / in-progress leg of the trip. */
    ACTIVE,
    /** Leg already completed. */
    COMPLETED,
    /** Upcoming planned leg. */
    PLANNED,
}

// ─────────────────────────────────────────────────────────────

/**
 * LindaDestinationCard — hero card for a trip leg / city.
 *
 * The card shows a full-bleed [imageUrl] with gradient overlays, a
 * title, duration/theme subtitle, and an optional [bottomContent] slot
 * (e.g. avatar group + CTA button + progress bar).
 *
 * @param imageUrl      Hero image URL.
 * @param title         Destination name (e.g. "Paris, France").
 * @param duration      Trip duration label (e.g. "3 Days").
 * @param theme         Thematic tag (e.g. "Art & Cafes").
 * @param status        Controls accent colour and card opacity.
 * @param bottomContent Optional slot rendered beneath the image.
 */
@Composable
fun LindaDestinationCard(
    imageUrl     : String,
    title        : String,
    duration     : String,
    theme        : String,
    modifier     : Modifier           = Modifier,
    status       : DestinationStatus  = DestinationStatus.ACTIVE,
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

    val shape = RoundedCornerShape(borders.radiusLg)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation    = shadow.cardElevation,
                shape        = shape,
                ambientColor = Color.Black.copy(alpha = shadow.cardShadowAlpha),
                spotColor    = Color.Black.copy(alpha = shadow.cardShadowAlpha),
            )
            .clip(shape)
            .background(colors.surfaceGlass)
            .border(width = borders.widthThin, color = colors.borderGlass, shape = shape),
    ) {
        Column {
            // ── Hero image ───────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sizes.destinationImageHeight),
            ) {
                LandscapistImage(
                    imageModel   = { imageUrl },
                    modifier     = Modifier.fillMaxSize(),
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment    = Alignment.Center,
                    ),
                    component = rememberImageComponent {
                        +ShimmerPlugin(
                            shimmer = Shimmer.Flash(
                                baseColor      = colors.surfaceGlass,
                                highlightColor = colors.borderGlass,
                            ),
                        )
                        +CrossfadePlugin(duration = 500)
                    },
                    failure = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF1C1C1E)),
                        )
                    },
                )

                // Warm gradient (bottom-up gold tint)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(colors.overlayWarmEnd, colors.overlayWarmStart),
                            ),
                        ),
                )

                // Dark vignette for legibility
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

                // Title + subtitle overlay
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
                            if (status != DestinationStatus.PLANNED) {
                                Modifier.shadow(
                                    elevation    = shadow.glowElevation,
                                    ambientColor = glowColor,
                                    spotColor    = glowColor,
                                )
                            } else Modifier,
                        ),
                    ) {
                        Icon(
                            imageVector        = Icons.Default.Info,
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

// ─── Backward-compat alias ───────────────────────────────────
/** @deprecated Use [LindaDestinationCard]. */
@Deprecated("Use LindaDestinationCard")
@Composable
fun TravelDestinationCard(
    imageUrl     : String,
    title        : String,
    duration     : String,
    theme        : String,
    modifier     : Modifier          = Modifier,
    status       : DestinationStatus = DestinationStatus.ACTIVE,
    bottomContent: @Composable (() -> Unit)? = null,
) = LindaDestinationCard(imageUrl, title, duration, theme, modifier, status, bottomContent)
