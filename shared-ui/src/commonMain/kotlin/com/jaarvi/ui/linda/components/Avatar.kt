package com.jaarvi.ui.linda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
// LindaAvatar size variants
// ─────────────────────────────────────────────────────────────

enum class AvatarSize {
    SM,  // 32 dp
    MD,  // 36 dp
    LG,  // 48 dp
}

// ─────────────────────────────────────────────────────────────

/**
 * LindaAvatar — circular user image with a glass border.
 *
 * Loads [imageUrl] via Landscapist (KMP image loading). While loading,
 * a shimmer placeholder is shown. On failure, a fallback person icon is shown.
 *
 * @param imageUrl           Remote image URL to load.
 * @param modifier           Modifier applied to the outer circle container.
 * @param size               Diameter preset: [AvatarSize.SM], [AvatarSize.MD], [AvatarSize.LG].
 * @param contentDescription Accessibility description forwarded to the image.
 */
@Composable
fun LindaAvatar(
    imageUrl          : String,
    modifier          : Modifier   = Modifier,
    size              : AvatarSize = AvatarSize.MD,
    contentDescription: String?    = null,
) {
    val colors  = LindaTheme.colors
    val borders = LindaTheme.borders
    val sizes   = LindaTheme.sizes

    val avatarSize = when (size) {
        AvatarSize.SM -> sizes.avatarSm
        AvatarSize.MD -> sizes.avatarMd
        AvatarSize.LG -> sizes.avatarLg
    }

    Box(
        modifier = modifier
            .size(avatarSize)
            .clip(CircleShape)
            .border(width = borders.widthThin, color = colors.borderGlassStrong, shape = CircleShape),
    ) {
        LandscapistImage(
            imageModel = { imageUrl },
            modifier   = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            imageOptions = ImageOptions(
                contentScale       = ContentScale.Crop,
                alignment          = Alignment.Center,
                contentDescription = contentDescription,
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
                    modifier          = Modifier
                        .fillMaxSize()
                        .background(colors.surfaceGlass),
                    contentAlignment  = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Default.Person,
                        contentDescription = null,
                        tint               = colors.textMuted,
                        modifier           = Modifier.size(avatarSize / 2),
                    )
                }
            },
        )
    }
}

// ─────────────────────────────────────────────────────────────

/**
 * LindaAvatarGroup — a horizontal stack of overlapping [LindaAvatar]s.
 *
 * @param imageUrls List of image URLs. Capped at [max].
 * @param modifier  Modifier applied to the Row container.
 * @param max       Maximum number of avatars displayed.
 * @param size      Size preset applied to all avatars.
 */
@Composable
fun LindaAvatarGroup(
    imageUrls: List<String>,
    modifier : Modifier   = Modifier,
    max      : Int        = 3,
    size     : AvatarSize = AvatarSize.MD,
) {
    val displayUrls = imageUrls.take(max)

    Row(
        modifier              = modifier,
        horizontalArrangement = Arrangement.spacedBy((-8).dp),
    ) {
        displayUrls.forEachIndexed { index, url ->
            LindaAvatar(
                imageUrl = url,
                size     = size,
                modifier = Modifier.offset(x = (index * (-8)).dp),
            )
        }
    }
}

// ─── Backward-compat aliases ─────────────────────────────────

/** @deprecated Use [LindaAvatar]. */
@Deprecated("Use LindaAvatar", ReplaceWith("LindaAvatar(imageUrl, modifier, size, contentDescription)"))
@Composable
fun TravelAvatar(
    imageUrl          : String,
    modifier          : Modifier   = Modifier,
    size              : AvatarSize = AvatarSize.MD,
    contentDescription: String?    = null,
) = LindaAvatar(imageUrl, modifier, size, contentDescription)

/** @deprecated Use [LindaAvatarGroup]. */
@Deprecated("Use LindaAvatarGroup", ReplaceWith("LindaAvatarGroup(imageUrls, modifier, max, size)"))
@Composable
fun TravelAvatarGroup(
    imageUrls: List<String>,
    modifier : Modifier   = Modifier,
    max      : Int        = 3,
    size     : AvatarSize = AvatarSize.MD,
) = LindaAvatarGroup(imageUrls, modifier, max, size)
