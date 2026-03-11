package com.jaarvi.ui.linda.components.level1

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.jaarvi.ui.linda.theme.LindaTheme

// ─────────────────────────────────────────────────────────────
// LindaText — typography wrapper composables.
//
// Each function corresponds to one slot in LindaTypographyScheme and
// automatically applies the correct color from LindaColorScheme.
// Pass an explicit [color] to override.
//
// Design-system hierarchy:
//   H1  24 sp ExtraBold  → LindaTextH1
//   H2  20 sp Bold       → LindaTextH2
//   H3  18 sp Bold       → LindaTextH3
//   Body 16 sp Regular   → LindaTextBody
//   Small / BodyMedium   → LindaTextSmall
//   Micro 12 sp          → LindaTextMicro
//   Label 10 sp Bold ALL CAPS → LindaTextLabel
// ─────────────────────────────────────────────────────────────

// ── Internal helper ───────────────────────────────────────────
@Composable
private fun LindaTextBase(
    text    : String,
    style   : TextStyle,
    color   : Color       = Color.Unspecified,
    modifier: Modifier    = Modifier,
    maxLines: Int         = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    val resolvedStyle = if (color != Color.Unspecified) style.copy(color = color) else style
    Text(
        text     = text,
        style    = resolvedStyle,
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow,
    )
}

// ── H1 ────────────────────────────────────────────────────────

/**
 * H1 — 24 sp ExtraBold. Default color: [LindaColorScheme.textPrimary].
 */
@Composable
fun LindaTextH1(
    text    : String,
    modifier: Modifier = Modifier,
    color   : Color    = Color.Unspecified,
    maxLines: Int      = Int.MAX_VALUE,
) {
    val typo   = LindaTheme.typography
    val colors = LindaTheme.colors
    val resolvedColor = if (color != Color.Unspecified) color else colors.textPrimary
    LindaTextBase(text, typo.h1, resolvedColor, modifier, maxLines)
}

// ── H2 ────────────────────────────────────────────────────────

/**
 * H2 — 20 sp Bold. Default color: [LindaColorScheme.textPrimary].
 */
@Composable
fun LindaTextH2(
    text    : String,
    modifier: Modifier = Modifier,
    color   : Color    = Color.Unspecified,
    maxLines: Int      = Int.MAX_VALUE,
) {
    val typo   = LindaTheme.typography
    val colors = LindaTheme.colors
    val resolvedColor = if (color != Color.Unspecified) color else colors.textPrimary
    LindaTextBase(text, typo.h2, resolvedColor, modifier, maxLines)
}

// ── H3 ────────────────────────────────────────────────────────

/**
 * H3 — 18 sp Bold. Default color: [LindaColorScheme.textPrimary].
 */
@Composable
fun LindaTextH3(
    text    : String,
    modifier: Modifier = Modifier,
    color   : Color    = Color.Unspecified,
    maxLines: Int      = Int.MAX_VALUE,
) {
    val typo   = LindaTheme.typography
    val colors = LindaTheme.colors
    val resolvedColor = if (color != Color.Unspecified) color else colors.textPrimary
    LindaTextBase(text, typo.h3, resolvedColor, modifier, maxLines)
}

// ── Body ──────────────────────────────────────────────────────

/**
 * Body — 16 sp Regular. Default color: [LindaColorScheme.textSecondary].
 */
@Composable
fun LindaTextBody(
    text    : String,
    modifier: Modifier = Modifier,
    color   : Color    = Color.Unspecified,
    maxLines: Int      = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    val typo   = LindaTheme.typography
    val colors = LindaTheme.colors
    val resolvedColor = if (color != Color.Unspecified) color else colors.textSecondary
    LindaTextBase(text, typo.body, resolvedColor, modifier, maxLines, overflow)
}

// ── Small / BodyMedium ────────────────────────────────────────

/**
 * Small — 14 sp Medium. Default color: [LindaColorScheme.textSecondary].
 */
@Composable
fun LindaTextSmall(
    text    : String,
    modifier: Modifier = Modifier,
    color   : Color    = Color.Unspecified,
    maxLines: Int      = Int.MAX_VALUE,
) {
    val typo   = LindaTheme.typography
    val colors = LindaTheme.colors
    val resolvedColor = if (color != Color.Unspecified) color else colors.textSecondary
    LindaTextBase(text, typo.bodyMedium, resolvedColor, modifier, maxLines)
}

// ── Micro ─────────────────────────────────────────────────────

/**
 * Micro — 12 sp Regular. Default color: [LindaColorScheme.textMuted].
 */
@Composable
fun LindaTextMicro(
    text    : String,
    modifier: Modifier = Modifier,
    color   : Color    = Color.Unspecified,
    maxLines: Int      = Int.MAX_VALUE,
) {
    val typo   = LindaTheme.typography
    val colors = LindaTheme.colors
    val resolvedColor = if (color != Color.Unspecified) color else colors.textMuted
    LindaTextBase(text, typo.micro, resolvedColor, modifier, maxLines)
}

// ── Label ─────────────────────────────────────────────────────

/**
 * Label — 10 sp Bold UPPERCASE. Default color: [LindaColorScheme.textLabel].
 * Text is automatically uppercased to match the design spec.
 */
@Composable
fun LindaTextLabel(
    text    : String,
    modifier: Modifier = Modifier,
    color   : Color    = Color.Unspecified,
) {
    val typo   = LindaTheme.typography
    val colors = LindaTheme.colors
    val resolvedColor = if (color != Color.Unspecified) color else colors.textLabel
    LindaTextBase(text.uppercase(), typo.label, resolvedColor, modifier)
}
