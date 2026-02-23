package com.jaarvi.ui.linda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.theme.LindaTheme

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
 * @param imageUrl           Remote or local image URL (loaded via Coil).
 * @param size               Diameter preset: [AvatarSize.SM], [AvatarSize.MD], [AvatarSize.LG].
 * @param contentDescription Accessibility description for the image.
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
            .background(colors.surfaceGlass)
            .border(width = borders.widthThin, color = colors.borderGlassStrong, shape = CircleShape),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(borders.widthThin)
                .clip(CircleShape)
                .background(Color.DarkGray)
        )
    }
}

// ─────────────────────────────────────────────────────────────

/**
 * LindaAvatarGroup — a horizontal stack of overlapping [LindaAvatar]s.
 *
 * @param imageUrls List of image URLs. Capped at [max].
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
        modifier            = modifier,
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
