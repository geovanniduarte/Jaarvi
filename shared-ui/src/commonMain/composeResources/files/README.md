# Compose Multiplatform resources

## Fonts

Add Plus Jakarta Sans font files into the `font/` subdirectory (lowercase, underscores; `.ttf` or `.otf`):

- `plusjakartasans_regular.ttf`
- `plusjakartasans_medium.ttf`
- `plusjakartasans_semibold.ttf`
- `plusjakartasans_bold.ttf`
- `plusjakartasans_extrabold.ttf`

They will be exposed as `Res.font.plusjakartasans_regular`, etc. After adding the files, sync Gradle.

To use them in typography, in `Typography.kt` change `PlusJakartaSans()` to load from resources:

```kotlin
import androidx.compose.ui.text.font.FontStyle
import jaarvi.`shared-ui`.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun PlusJakartaSans(): FontFamily = FontFamily(
    Font(Res.font.plusjakartasans_regular, FontWeight.Normal, FontStyle.Normal),
    Font(Res.font.plusjakartasans_medium, FontWeight.Medium, FontStyle.Normal),
    Font(Res.font.plusjakartasans_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(Res.font.plusjakartasans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(Res.font.plusjakartasans_extrabold, FontWeight.ExtraBold, FontStyle.Normal)
)
```

You can download the family from [Google Fonts](https://fonts.google.com/specimen/Plus+Jakarta+Sans).
