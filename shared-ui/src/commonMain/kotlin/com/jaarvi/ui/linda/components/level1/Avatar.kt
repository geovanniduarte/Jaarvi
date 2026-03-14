package com.jaarvi.ui.linda.components.level1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import com.jaarvi.ui.linda.components.level0.icons.*
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
// LindaAvatar — LEVEL 1
//
// Circular user image with a glass-border ring.
// Loaded via Landscapist (shimmer placeholder + crossfade).
// ─────────────────────────────────────────────────────────────

// ── Size preset ───────────────────────────────────────────────

enum class AvatarSize {
    SM,  // 32 dp — sizes.avatarSm
    MD,  // 36 dp — sizes.avatarMd
    LG,  // 48 dp — sizes.avatarLg
}

// ── Component ─────────────────────────────────────────────────

/**
 * @param imageUrl            Remote or local image URL.
 * @param modifier            Optional layout modifier.
 * @param size                Diameter preset: [AvatarSize.SM], [AvatarSize.MD], [AvatarSize.LG].
 * @param contentDescription  Accessibility description for the image.
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
        LandscapistImage(
            imageModel   = { imageUrl },
            modifier     = Modifier
                .fillMaxSize()
                .padding(borders.widthThin)
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
                    modifier         = Modifier
                        .fillMaxSize()
                        .background(colors.surfaceGlass),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = LindaIcons.Default.Profile,
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
// LindaAvatarGroup — horizontal stack of overlapping avatars.
//
// Slot-based DSL: call [LindaAvatarGroupScope.LindaAvatar] inside
// the content lambda. The group owns the overlap spacing; the caller
// is responsible for each avatar's imageUrl, size, and description.
//
// Usage:
//   LindaAvatarGroup {
//       LindaAvatar(imageUrl = "https://…", size = AvatarSize.LG)
//       LindaAvatar(imageUrl = "https://…", size = AvatarSize.LG)
//   }
// ─────────────────────────────────────────────────────────────

// ── Scope interface ───────────────────────────────────────────

/** DSL scope — call [LindaAvatar] inside [LindaAvatarGroup]'s content lambda. */
interface LindaAvatarGroupScope {
    /**
     * Renders a single avatar inside the group.
     *
     * @param imageUrl            Remote or local image URL.
     * @param size                Diameter preset for this avatar.
     * @param contentDescription  Accessibility description.
     */
    @Composable
    fun LindaAvatar(
        imageUrl          : String,
        size              : AvatarSize = AvatarSize.MD,
        contentDescription: String?    = null,
    )
}

// ── Root composable ───────────────────────────────────────────

/**
 * @param modifier Optional layout modifier.
 * @param content  DSL lambda — call [LindaAvatarGroupScope.LindaAvatar] inside.
 */
@Composable
fun LindaAvatarGroup(
    modifier: Modifier = Modifier,
    content : @Composable LindaAvatarGroupScope.() -> Unit,
) {
    Row(
        modifier              = modifier,
        horizontalArrangement = Arrangement.spacedBy((-8).dp),
    ) {
        val scope = object : LindaAvatarGroupScope {
            @Composable
            override fun LindaAvatar(
                imageUrl          : String,
                size              : AvatarSize,
                contentDescription: String?,
            ) {
                // Delegates to the top-level LindaAvatar composable above.
                com.jaarvi.ui.linda.components.level1.LindaAvatar(
                    imageUrl           = imageUrl,
                    size               = size,
                    contentDescription = contentDescription,
                )
            }
        }
        scope.content()
    }
}