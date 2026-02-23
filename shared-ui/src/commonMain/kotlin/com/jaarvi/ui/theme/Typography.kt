package com.jaarvi.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import jaarvi.shared_ui.generated.resources.Res
import jaarvi.shared_ui.generated.resources.plusjakartasans_bold
import jaarvi.shared_ui.generated.resources.plusjakartasans_extrabold
import jaarvi.shared_ui.generated.resources.plusjakartasans_medium
import jaarvi.shared_ui.generated.resources.plusjakartasans_regular
import jaarvi.shared_ui.generated.resources.plusjakartasans_semibold
import org.jetbrains.compose.resources.Font

/**
 * Plus Jakarta Sans font family.
 * Uses [FontFamily.Default] until you add the font files to `composeResources/font/`
 * and load them via expect/actual or platform-specific Res.
 * See `composeResources/files/README.md` for how to load custom fonts from resources.
 */
@Composable
fun PlusJakartaSans(): FontFamily = FontFamily(
    Font(Res.font.plusjakartasans_regular, FontWeight.Normal, FontStyle.Normal),
    Font(Res.font.plusjakartasans_medium, FontWeight.Medium, FontStyle.Normal),
    Font(Res.font.plusjakartasans_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(Res.font.plusjakartasans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(Res.font.plusjakartasans_extrabold, FontWeight.ExtraBold, FontStyle.Normal)
)

internal fun PlusJakartaSansDefault(): FontFamily {
    return FontFamily.Default
}

data class TravelTypographyData(
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle
)

@Composable
fun TravelTypography(): TravelTypographyData = run {
    val plusJakartaSans = PlusJakartaSans()
    return remember(plusJakartaSans) {
        TravelTypographyData(
            displayLarge = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
                letterSpacing = (-0.5).sp
            ),
            displayMedium = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.sp
            ),
            displaySmall = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp
            ),
            headlineLarge = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                lineHeight = 28.sp,
                letterSpacing = (-0.45).sp
            ),
            headlineMedium = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.sp
            ),
            bodyLarge = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp
            ),
            bodySmall = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.sp
            ),
            labelLarge = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp
            ),
            labelMedium = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 1.sp
            ),
            labelSmall = TextStyle(
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                lineHeight = 15.sp,
                letterSpacing = 1.sp
            )
        )
    }
}

val LocalTravelTypography = compositionLocalOf<TravelTypographyData>(defaultFactory = {
    val fontFamilySystem = PlusJakartaSansDefault()
    TravelTypographyData(
        displayLarge = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = (-0.5).sp
        ),
        displayMedium = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp
        ),
        displaySmall = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.45).sp
        ),
        headlineMedium = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.sp
        ),
        bodySmall = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp
        ),
        labelLarge = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.sp
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 1.sp
        ),
        labelSmall = TextStyle(
            fontFamily = fontFamilySystem,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            lineHeight = 15.sp,
            letterSpacing = 1.sp
        )
    )

})

@Composable
fun ProvideTravelTypography(content: @Composable () -> Unit) {
    val typography = TravelTypography()
    CompositionLocalProvider(LocalTravelTypography provides typography) { content() }
}
