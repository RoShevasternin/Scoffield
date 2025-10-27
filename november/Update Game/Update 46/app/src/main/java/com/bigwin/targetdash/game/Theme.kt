package com.bigwin.targetdash.game
import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bigwin.targetdash.R

val primaryLight = Color(0xFF705D00)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFFFD700)
val onPrimaryContainerLight = Color(0xFF705E00)
val secondaryLight = Color(0xFF840028)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFA91B3C)
val onSecondaryContainerLight = Color(0xFFFFBCC1)
val tertiaryLight = Color(0xFF006A3B)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFF268451)
val onTertiaryContainerLight = Color(0xFFF6FFF4)
val errorLight = Color(0xFFAF101A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFD32F2F)
val onErrorContainerLight = Color(0xFFFFF2F0)
val backgroundLight = Color(0xFFFFF9EF)
val onBackgroundLight = Color(0xFF1F1B10)
val surfaceLight = Color(0xFFFFF9EF)
val onSurfaceLight = Color(0xFF1F1B10)
val surfaceVariantLight = Color(0xFFECE2C5)
val onSurfaceVariantLight = Color(0xFF4D4732)
val outlineLight = Color(0xFF7E775F)
val outlineVariantLight = Color(0xFFD0C6AB)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF343024)
val inverseOnSurfaceLight = Color(0xFFF9F0DD)
val inversePrimaryLight = Color(0xFFE9C400)
val surfaceDimLight = Color(0xFFE1D9C7)
val surfaceBrightLight = Color(0xFFFFF9EF)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFFBF3E0)
val surfaceContainerLight = Color(0xFFF6EDDA)
val surfaceContainerHighLight = Color(0xFFF0E7D5)
val surfaceContainerHighestLight = Color(0xFFEAE2CF)

val primaryDark = Color(0xFFFFF6DF)
val onPrimaryDark = Color(0xFF3A3000)
val primaryContainerDark = Color(0xFFFFD700)
val onPrimaryContainerDark = Color(0xFF705E00)
val secondaryDark = Color(0xFFFFB2B8)
val onSecondaryDark = Color(0xFF67001D)
val secondaryContainerDark = Color(0xFFA91B3C)
val onSecondaryContainerDark = Color(0xFFFFBCC1)
val tertiaryDark = Color(0xFF7ED99E)
val onTertiaryDark = Color(0xFF00391D)
val tertiaryContainerDark = Color(0xFF47A26B)
val onTertiaryContainerDark = Color(0xFF001206)
val errorDark = Color(0xFFFFB3AC)
val onErrorDark = Color(0xFF680008)
val errorContainerDark = Color(0xFFD32F2F)
val onErrorContainerDark = Color(0xFFFFF2F0)
val backgroundDark = Color(0xFF161308)
val onBackgroundDark = Color(0xFFEAE2CF)
val surfaceDark = Color(0xFF161308)
val onSurfaceDark = Color(0xFFEAE2CF)
val surfaceVariantDark = Color(0xFF4D4732)
val onSurfaceVariantDark = Color(0xFFD0C6AB)
val outlineDark = Color(0xFF999077)
val outlineVariantDark = Color(0xFF4D4732)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFEAE2CF)
val inverseOnSurfaceDark = Color(0xFF343024)
val inversePrimaryDark = Color(0xFF705D00)
val surfaceDimDark = Color(0xFF161308)
val surfaceBrightDark = Color(0xFF3D392C)
val surfaceContainerLowestDark = Color(0xFF110E05)
val surfaceContainerLowDark = Color(0xFF1F1B10)
val surfaceContainerDark = Color(0xFF231F14)
val surfaceContainerHighDark = Color(0xFF2E2A1E)
val surfaceContainerHighestDark = Color(0xFF393528)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

private val appFontFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.bangers_regular,
            weight = FontWeight.W900,
            style = FontStyle.Normal
        )
    )
)

val baseline = Typography()
val letterSpacing = 0.sp
val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    displayMedium = baseline.displayMedium.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    displaySmall = baseline.displaySmall.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    titleLarge = baseline.titleLarge.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    titleMedium = baseline.titleMedium.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    titleSmall = baseline.titleSmall.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    bodySmall = baseline.bodySmall.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    labelLarge = baseline.labelLarge.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    labelMedium = baseline.labelMedium.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
    labelSmall = baseline.labelSmall.copy(fontFamily = appFontFamily, letterSpacing = letterSpacing),
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
  val colorScheme = localTheme(
      LocalContext.current,
      darkTheme = darkTheme,
      dynamicColor = dynamicColor
  )

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}

private fun localTheme(
    context: Context,
    darkTheme: Boolean,
    dynamicColor: Boolean,
) = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    darkTheme -> darkScheme
    else -> lightScheme
}

