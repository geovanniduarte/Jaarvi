# Linda Design System — Theme Implementation Reference

**Package:** `com.jaarvi.ui.linda.theme`
**Location:** `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/linda/theme/`

All tokens are consumed exclusively via `LindaTheme.*` accessors inside `@Composable` functions. Never import raw constants or scheme instances directly into UI components.

---

## File Map

| File | Responsibility |
|---|---|
| `LindaColor.kt` | Raw hex palette — internal source of truth |
| `LindaColorScheme.kt` | Semantic color tokens + dark-mode defaults |
| `LindaTypographyScheme.kt` | Text style tokens |
| `LindaBorderScheme.kt` | Corner radius + stroke width tokens |
| `LindaSpacingScheme.kt` | Spacing scale tokens |
| `LindaSizeScheme.kt` | Component & icon size tokens |
| `LindaShadowScheme.kt` | Elevation & glow tokens |
| `LindaGlassScheme.kt` | Glassmorphism effect tokens |
| `LindaTheme.kt` | `CompositionLocalProvider` + `LindaTheme` accessor object |
| `Theme.kt` | `TravelAppTheme` backward-compatibility wrapper |

---

## `LindaColor.kt` — Raw Palette

Internal constants only. Never referenced from UI components directly.

```kotlin
// Backgrounds
internal val ColorBgPrimary       = Color(0xFF0A0C0A)
internal val ColorBgGlass         = Color(0x1AFFFFFF)   // 10% white
internal val ColorBgGlassLight    = Color(0x0DFFFFFF)   //  5% white

// Accents
internal val ColorAccentGold      = Color(0xFFF2B90D)
internal val ColorAccentLime      = Color(0xFFA3E635)

// Text
internal val ColorTextPrimary     = Color(0xFFF1F5F9)
internal val ColorTextSecondary   = Color(0xFFE2E8F0)
internal val ColorTextMuted       = Color(0xFF94A3B8)
internal val ColorTextTertiary    = Color(0xFF64748B)
internal val ColorTextLabel       = Color(0xFFCBD5E1)

// Borders
internal val ColorBorderGlass       = Color(0x33FFFFFF)  // 20% white
internal val ColorBorderGlassStrong = Color(0x4DFFFFFF)  // 30% white

// Overlays
internal val ColorOverlayWarmStart  = Color(0x66F2B90D)
internal val ColorOverlayWarmEnd    = Color(0x1AF2B90D)
internal val ColorOverlayDarkStart  = Color(0xCC000000)
internal val ColorOverlayDarkEnd    = Color(0x00000000)

// Glows
internal val ColorGlowGold          = Color(0x66F2B90D)
internal val ColorGlowLime          = Color(0x66A3E635)
internal val ColorGlowEmerald       = Color(0x66064E3B)
internal val ColorGlowGoldLight     = Color(0x66F2B90D)
internal val ColorGlowEmeraldLight  = Color(0x4D063F2E)

// Status semantics
internal val ColorStatusActive    = Color(0xFFA3E635)   // lime   — active / in-progress
internal val ColorStatusCompleted = Color(0xFFF2B90D)   // gold   — completed
internal val ColorStatusPlanned   = Color(0x99FFFFFF)   // white 60% — planned / upcoming
```

---

## `LindaColorScheme.kt` — Semantic Color Tokens

```kotlin
@Immutable
data class LindaColorScheme(
    // Backgrounds
    val background      : Color,   // Primary app background
    val surfaceGlass    : Color,   // Glassmorphic surface ~10% white
    val surfaceGlassLight: Color,  // Lighter glass surface ~5% white

    // Text / Foreground
    val textPrimary  : Color,   // #F1F5F9 — highest contrast
    val textSecondary: Color,   // #E2E8F0
    val textMuted    : Color,   // #94A3B8 — de-emphasised
    val textTertiary : Color,   // #64748B — placeholder
    val textLabel    : Color,   // #CBD5E1 — caption

    // Accents
    val accentGold: Color,   // #F2B90D
    val accentLime: Color,   // #A3E635
    val onAccent  : Color,   // foreground on top of accent fills

    // Borders
    val borderGlass      : Color,   // 20% white
    val borderGlassStrong: Color,   // 30% white

    // Overlays (image gradients)
    val overlayWarmStart: Color,   // gold 40%
    val overlayWarmEnd  : Color,   // gold 10%
    val overlayDarkStart: Color,   // black 80%
    val overlayDarkEnd  : Color,   // transparent

    // Glows
    val glowGold        : Color,
    val glowLime        : Color,
    val glowEmerald     : Color,
    val glowGoldLight   : Color,
    val glowEmeraldLight: Color,

    // Status semantics
    val statusActive   : Color,   // lime — active / in-progress
    val statusCompleted: Color,   // gold — completed
    val statusPlanned  : Color,   // white 60% — upcoming
)

val LocalLindaColorScheme = compositionLocalOf { LindaDarkColorScheme }

val LindaDarkColorScheme = LindaColorScheme(
    background          = ColorBgPrimary,
    surfaceGlass        = ColorBgGlass,
    surfaceGlassLight   = ColorBgGlassLight,

    textPrimary         = ColorTextPrimary,
    textSecondary       = ColorTextSecondary,
    textMuted           = ColorTextMuted,
    textTertiary        = ColorTextTertiary,
    textLabel           = ColorTextLabel,

    accentGold          = ColorAccentGold,
    accentLime          = ColorAccentLime,
    onAccent            = ColorBgPrimary,

    borderGlass         = ColorBorderGlass,
    borderGlassStrong   = ColorBorderGlassStrong,

    overlayWarmStart    = ColorOverlayWarmStart,
    overlayWarmEnd      = ColorOverlayWarmEnd,
    overlayDarkStart    = ColorOverlayDarkStart,
    overlayDarkEnd      = ColorOverlayDarkEnd,

    glowGold            = ColorGlowGold,
    glowLime            = ColorGlowLime,
    glowEmerald         = ColorGlowEmerald,
    glowGoldLight       = ColorGlowGoldLight,
    glowEmeraldLight    = ColorGlowEmeraldLight,

    statusActive        = ColorStatusActive,
    statusCompleted     = ColorStatusCompleted,
    statusPlanned       = ColorStatusPlanned,
)
```

---

## `LindaTypographyScheme.kt` — Text Styles

Font family: **Plus Jakarta Sans** (`FontFamily.Default` until resources are loaded).

```kotlin
@Immutable
data class LindaTypographyScheme(
    // Primary design-system hierarchy
    val h1        : TextStyle,   // 24 sp / ExtraBold / ls −0.5
    val h2        : TextStyle,   // 20 sp / Bold
    val h3        : TextStyle,   // 18 sp / Bold / ls −0.45
    val body      : TextStyle,   // 16 sp / Regular
    val bodyMedium: TextStyle,   // 14 sp / Medium
    val small     : TextStyle,   // 14 sp / Regular
    val micro     : TextStyle,   // 12 sp / Regular
    val label     : TextStyle,   // 10 sp / Bold / UPPERCASE / ls +1

    // Extended aliases (Material3-style names)
    val displayLarge  : TextStyle,   // 32 sp / Bold
    val displayMedium : TextStyle,   // 24 sp / Bold  (≡ h1)
    val displaySmall  : TextStyle,   // 20 sp / Bold  (≡ h2)
    val headlineLarge : TextStyle,   // 18 sp / Bold  (≡ h3)
    val headlineMedium: TextStyle,   // 16 sp / Bold
    val bodyLarge     : TextStyle,   // 16 sp / Regular (≡ body)
    val bodySmall     : TextStyle,   // 12 sp / Regular (≡ micro)
    val labelLarge    : TextStyle,   // 14 sp / Bold / UPPERCASE
    val labelMedium   : TextStyle,   // 12 sp / Bold / UPPERCASE / ls +1
    val labelSmall    : TextStyle,   // 10 sp / Bold / UPPERCASE / ls +1 (≡ label)
)

val LocalLindaTypographyScheme = compositionLocalOf<LindaTypographyScheme> { LindaDefaultTypographyScheme }

// All styles share: fontFamily = PlusJakartaSans, lineHeightFactor = 1.4f
val LindaDefaultTypographyScheme = LindaTypographyScheme(
    h1            = base(24f, ExtraBold, 1.33f, -0.5f),
    h2            = base(20f, Bold,      1.40f),
    h3            = base(18f, Bold,      1.56f, -0.45f),
    body          = base(16f, Normal,    1.50f),
    bodyMedium    = base(14f, Medium,    1.43f),
    small         = base(14f, Normal,    1.43f),
    micro         = base(12f, Normal,    1.33f),
    label         = base(10f, Bold,      1.50f, 1f),

    displayLarge  = base(32f, Bold,      1.25f, -0.5f),
    displayMedium = base(24f, Bold,      1.33f),
    displaySmall  = base(20f, Bold,      1.40f),
    headlineLarge  = base(18f, Bold,     1.56f, -0.45f),
    headlineMedium = base(16f, Bold,     1.50f),
    bodyLarge     = base(16f, Normal,    1.50f),
    bodySmall     = base(12f, Normal,    1.33f),
    labelLarge    = base(14f, Bold,      1.43f),
    labelMedium   = base(12f, Bold,      1.33f, 1f),
    labelSmall    = base(10f, Bold,      1.50f, 1f),
)
```

---

## `LindaBorderScheme.kt` — Corner Radii & Stroke Widths

```kotlin
@Immutable
data class LindaBorderScheme(
    // Corner radii
    val radiusSm  : Dp,   //  8 dp — inputs, chips
    val radiusMd  : Dp,   // 12 dp — buttons, badges
    val radiusLg  : Dp,   // 16 dp — cards, sheets
    val radiusXl  : Dp,   // 24 dp — bottom sheets, large panels
    val radiusFull: Dp,   // 9999 dp — pill / fully-circular

    // Stroke widths
    val widthThin  : Dp,   // 1 dp — standard thin border
    val widthMedium: Dp,   // 2 dp — emphasis border
    val widthThick : Dp,   // 4 dp — strong border (e.g. FAB inner ring)
)

val LocalLindaBorderScheme = compositionLocalOf<LindaBorderScheme> { LindaDefaultBorderScheme }

val LindaDefaultBorderScheme = LindaBorderScheme(
    radiusSm    = 8.dp,
    radiusMd    = 12.dp,
    radiusLg    = 16.dp,
    radiusXl    = 24.dp,
    radiusFull  = 9999.dp,
    widthThin   = 1.dp,
    widthMedium = 2.dp,
    widthThick  = 4.dp,
)
```

---

## `LindaSpacingScheme.kt` — Spacing Scale

```kotlin
@Immutable
data class LindaSpacingScheme(
    val xs   : Dp,   //  4 dp
    val sm   : Dp,   //  8 dp
    val md   : Dp,   // 12 dp
    val lg   : Dp,   // 16 dp
    val xl   : Dp,   // 20 dp
    val xxl  : Dp,   // 24 dp
    val xxxl : Dp,   // 32 dp
    val xxxxl: Dp,   // 48 dp
)

val LocalLindaSpacingScheme = compositionLocalOf<LindaSpacingScheme> { LindaDefaultSpacingScheme }

val LindaDefaultSpacingScheme = LindaSpacingScheme(
    xs    = 4.dp,
    sm    = 8.dp,
    md    = 12.dp,
    lg    = 16.dp,
    xl    = 20.dp,
    xxl   = 24.dp,
    xxxl  = 32.dp,
    xxxxl = 48.dp,
)
```

---

## `LindaSizeScheme.kt` — Component & Icon Sizes

```kotlin
@Immutable
data class LindaSizeScheme(
    // Icon sizes
    val iconXs: Dp,   // 12 dp
    val iconSm: Dp,   // 16 dp
    val iconMd: Dp,   // 20 dp
    val iconLg: Dp,   // 24 dp
    val iconXl: Dp,   // 32 dp

    // Avatar sizes
    val avatarSm: Dp,   // 32 dp
    val avatarMd: Dp,   // 36 dp
    val avatarLg: Dp,   // 48 dp

    // Button heights
    val buttonHeightSm: Dp,   // 32 dp
    val buttonHeightMd: Dp,   // 40 dp
    val buttonHeightLg: Dp,   // 52 dp

    // FAB
    val fabSize    : Dp,   // 64 dp — main FAB diameter
    val fabMiniSize: Dp,   // 48 dp — mini FAB diameter

    // Nav bar
    val navBarIconSize: Dp,   // 20 dp

    // Destination card image
    val destinationImageHeight: Dp,   // 208 dp

    // Status indicator dot
    val statusDotSm: Dp,   //  6 dp
    val statusDotMd: Dp,   //  8 dp
    val statusDotLg: Dp,   // 12 dp

    // Divider
    val dividerThickness: Dp,   // 1 dp
)

val LocalLindaSizeScheme = compositionLocalOf<LindaSizeScheme> { LindaDefaultSizeScheme }

val LindaDefaultSizeScheme = LindaSizeScheme(
    iconXs                 = 12.dp,
    iconSm                 = 16.dp,
    iconMd                 = 20.dp,
    iconLg                 = 24.dp,
    iconXl                 = 32.dp,
    avatarSm               = 32.dp,
    avatarMd               = 36.dp,
    avatarLg               = 48.dp,
    buttonHeightSm         = 32.dp,
    buttonHeightMd         = 40.dp,
    buttonHeightLg         = 52.dp,
    fabSize                = 64.dp,
    fabMiniSize            = 48.dp,
    navBarIconSize         = 20.dp,
    destinationImageHeight = 208.dp,
    statusDotSm            = 6.dp,
    statusDotMd            = 8.dp,
    statusDotLg            = 12.dp,
    dividerThickness       = 1.dp,
)
```

---

## `LindaShadowScheme.kt` — Elevation & Glow

```kotlin
@Immutable
data class LindaShadowScheme(
    // Elevation steps
    val none: Dp,   //  0 dp
    val sm  : Dp,   //  2 dp
    val md  : Dp,   //  4 dp — progress fill, small elements
    val lg  : Dp,   //  8 dp — glow halos
    val xl  : Dp,   // 16 dp — cards, headers
    val xxl : Dp,   // 32 dp — design spec: 0px 8px 32px rgba

    // Card shadow
    val cardElevation  : Dp,     // 16 dp (≡ xl)
    val cardShadowAlpha: Float,  // 0.37f

    // Glow elevation
    val glowElevation   : Dp,   //  1 dp — coloured glow halos
    val fabGlowElevation: Dp,   // 12 dp — FAB gold glow
)

val LocalLindaShadowScheme = compositionLocalOf<LindaShadowScheme> { LindaDefaultShadowScheme }

val LindaDefaultShadowScheme = LindaShadowScheme(
    none             = 0.dp,
    sm               = 2.dp,
    md               = 4.dp,
    lg               = 8.dp,
    xl               = 16.dp,
    xxl              = 32.dp,
    cardElevation    = 16.dp,
    cardShadowAlpha  = 0.37f,
    glowElevation    = 1.dp,
    fabGlowElevation = 12.dp,
)
```

---

## `LindaGlassScheme.kt` — Glassmorphism Tokens

> Blur values document design intent. Use `Modifier.blur()` or `RenderEffect` (API 31+) where the platform supports it.

```kotlin
@Immutable
data class LindaGlassScheme(
    // Blur radii
    val blurLight  : Dp,   //  6 dp — cards, nav items
    val blurHeavy  : Dp,   // 12 dp — modals, sheets
    val blurAmbient: Dp,   // 40 dp — ambient glow orbs

    // Background opacity (0f–1f)
    val bgOpacityHeavy: Float,   // 0.10 — surfaceGlass
    val bgOpacityLight: Float,   // 0.05 — surfaceGlassLight

    // Border opacity (0f–1f)
    val borderOpacity      : Float,   // 0.20 — borderGlass
    val borderOpacityStrong: Float,   // 0.30 — borderGlassStrong
)

val LocalLindaGlassScheme = compositionLocalOf<LindaGlassScheme> { LindaDefaultGlassScheme }

val LindaDefaultGlassScheme = LindaGlassScheme(
    blurLight           = 6.dp,
    blurHeavy           = 12.dp,
    blurAmbient         = 40.dp,
    bgOpacityHeavy      = 0.10f,
    bgOpacityLight      = 0.05f,
    borderOpacity       = 0.20f,
    borderOpacityStrong = 0.30f,
)
```

---

## `LindaTheme.kt` — Entry Point & Accessor Object

```kotlin
@Composable
fun LindaTheme(
    colors    : LindaColorScheme     = LindaDarkColorScheme,
    borders   : LindaBorderScheme    = LindaDefaultBorderScheme,
    sizes     : LindaSizeScheme      = LindaDefaultSizeScheme,
    spacing   : LindaSpacingScheme   = LindaDefaultSpacingScheme,
    typography: LindaTypographyScheme = LindaDefaultTypographyScheme,
    glass     : LindaGlassScheme     = LindaDefaultGlassScheme,
    shadow    : LindaShadowScheme    = LindaDefaultShadowScheme,
    content   : @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalLindaColorScheme    provides colors,
        LocalLindaBorderScheme   provides borders,
        LocalLindaSizeScheme     provides sizes,
        LocalLindaSpacingScheme  provides spacing,
        LocalLindaTypographyScheme provides typography,
        LocalLindaGlassScheme    provides glass,
        LocalLindaShadowScheme   provides shadow,
        content = content,
    )
}

object LindaTheme {
    val colors    : LindaColorScheme      @Composable @ReadOnlyComposable get() = LocalLindaColorScheme.current
    val borders   : LindaBorderScheme     @Composable @ReadOnlyComposable get() = LocalLindaBorderScheme.current
    val sizes     : LindaSizeScheme       @Composable @ReadOnlyComposable get() = LocalLindaSizeScheme.current
    val spacing   : LindaSpacingScheme    @Composable @ReadOnlyComposable get() = LocalLindaSpacingScheme.current
    val typography: LindaTypographyScheme @Composable @ReadOnlyComposable get() = LocalLindaTypographyScheme.current
    val glass     : LindaGlassScheme      @Composable @ReadOnlyComposable get() = LocalLindaGlassScheme.current
    val shadow    : LindaShadowScheme     @Composable @ReadOnlyComposable get() = LocalLindaShadowScheme.current
}
```

---

## `Theme.kt` — Backward-Compatibility Wrapper

```kotlin
@Composable
fun TravelAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content  : @Composable () -> Unit,
) {
    // Extend: provide LindaLightColorScheme when darkTheme == false
    val colors = if (darkTheme) LindaDarkColorScheme else LindaDarkColorScheme
    LindaTheme(colors = colors, content = content)
}
```

> New code must use `LindaTheme { }` directly. `TravelAppTheme` is kept only for existing call-sites migrating incrementally.

---

## Token Quick-Reference

### Colors — `LindaTheme.colors.*`

| Token | Dark value | Notes |
|---|---|---|
| `background` | `#0A0C0A` | App background |
| `surfaceGlass` | `#FFFFFF 10%` | Glass card surface |
| `surfaceGlassLight` | `#FFFFFF 5%` | Lighter glass surface |
| `textPrimary` | `#F1F5F9` | |
| `textSecondary` | `#E2E8F0` | |
| `textMuted` | `#94A3B8` | De-emphasised text |
| `textTertiary` | `#64748B` | Placeholder text |
| `textLabel` | `#CBD5E1` | Caption / label |
| `accentGold` | `#F2B90D` | Primary accent |
| `accentLime` | `#A3E635` | Secondary accent |
| `onAccent` | `#0A0C0A` | Text on accent fills |
| `borderGlass` | `#FFFFFF 20%` | |
| `borderGlassStrong` | `#FFFFFF 30%` | |
| `glowGold` | `#F2B90D 40%` | |
| `glowLime` | `#A3E635 40%` | |
| `glowEmerald` | `#064E3B 40%` | |
| `statusActive` | `#A3E635` | In-progress |
| `statusCompleted` | `#F2B90D` | Completed |
| `statusPlanned` | `#FFFFFF 60%` | Upcoming |

### Spacing — `LindaTheme.spacing.*`

`xs=4` · `sm=8` · `md=12` · `lg=16` · `xl=20` · `xxl=24` · `xxxl=32` · `xxxxl=48` _(all dp)_

### Borders — `LindaTheme.borders.*`

| Token | Value |
|---|---|
| `radiusSm` | 8 dp |
| `radiusMd` | 12 dp |
| `radiusLg` | 16 dp |
| `radiusXl` | 24 dp |
| `radiusFull` | 9999 dp |
| `widthThin` | 1 dp |
| `widthMedium` | 2 dp |
| `widthThick` | 4 dp |

### Typography — `LindaTheme.typography.*`

| Token | Size | Weight |
|---|---|---|
| `displayLarge` | 32 sp | Bold |
| `displayMedium` / `h1` | 24 sp | ExtraBold |
| `displaySmall` / `h2` | 20 sp | Bold |
| `headlineLarge` / `h3` | 18 sp | Bold |
| `headlineMedium` | 16 sp | Bold |
| `bodyLarge` / `body` | 16 sp | Regular |
| `bodyMedium` | 14 sp | Medium |
| `bodySmall` / `micro` | 12 sp | Regular |
| `labelLarge` | 14 sp | Bold |
| `labelMedium` | 12 sp | Bold |
| `labelSmall` / `label` | 10 sp | Bold |

### Sizes — `LindaTheme.sizes.*`

| Token | Value |
|---|---|
| `iconXs/Sm/Md/Lg/Xl` | 12/16/20/24/32 dp |
| `avatarSm/Md/Lg` | 32/36/48 dp |
| `buttonHeightSm/Md/Lg` | 32/40/52 dp |
| `fabSize` / `fabMiniSize` | 64/48 dp |
| `navBarIconSize` | 20 dp |
| `destinationImageHeight` | 208 dp |
| `statusDotSm/Md/Lg` | 6/8/12 dp |

### Shadow — `LindaTheme.shadow.*`

`none=0` · `sm=2` · `md=4` · `lg=8` · `xl=16` · `xxl=32` _(dp)_ · `cardElevation=16dp` · `cardShadowAlpha=0.37f` · `glowElevation=1dp` · `fabGlowElevation=12dp`

### Glass — `LindaTheme.glass.*`

`blurLight=6dp` · `blurHeavy=12dp` · `blurAmbient=40dp` · `bgOpacityHeavy=0.10f` · `bgOpacityLight=0.05f` · `borderOpacity=0.20f` · `borderOpacityStrong=0.30f`
