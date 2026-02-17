package com.bonusprize.chestopen.game
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
import com.bonusprize.chestopen.R

val primaryLight = Color(0xFF5F549D)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFBBAFFF)
val onPrimaryContainerLight = Color(0xFF4A3F86)
val secondaryLight = Color(0xFF6300DF)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFF7D31FF)
val onSecondaryContainerLight = Color(0xFFEEE3FF)
val tertiaryLight = Color(0xFF9000D3)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFAE30F4)
val onTertiaryContainerLight = Color(0xFFFFFBFF)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF93000A)
val backgroundLight = Color(0xFFFDF8FF)
val onBackgroundLight = Color(0xFF1C1B20)
val surfaceLight = Color(0xFFFDF8FF)
val onSurfaceLight = Color(0xFF1C1B20)
val surfaceVariantLight = Color(0xFFE5E0EE)
val onSurfaceVariantLight = Color(0xFF484550)
val outlineLight = Color(0xFF797581)
val outlineVariantLight = Color(0xFFC9C4D2)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF313035)
val inverseOnSurfaceLight = Color(0xFFF4EFF6)
val inversePrimaryLight = Color(0xFFC9BFFF)
val surfaceDimLight = Color(0xFFDDD8DF)
val surfaceBrightLight = Color(0xFFFDF8FF)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF7F2F9)
val surfaceContainerLight = Color(0xFFF1ECF3)
val surfaceContainerHighLight = Color(0xFFEBE6EE)
val surfaceContainerHighestLight = Color(0xFFE5E1E8)

val primaryDark = Color(0xFFD7CFFF)
val onPrimaryDark = Color(0xFF30246B)
val primaryContainerDark = Color(0xFFBBAFFF)
val onPrimaryContainerDark = Color(0xFF4A3F86)
val secondaryDark = Color(0xFFD1BCFF)
val onSecondaryDark = Color(0xFF3D008F)
val secondaryContainerDark = Color(0xFF7D31FF)
val onSecondaryContainerDark = Color(0xFFEEE3FF)
val tertiaryDark = Color(0xFFE6B4FF)
val onTertiaryDark = Color(0xFF4F0076)
val tertiaryContainerDark = Color(0xFFC563FF)
val onTertiaryContainerDark = Color(0xFF26003C)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF141317)
val onBackgroundDark = Color(0xFFE5E1E8)
val surfaceDark = Color(0xFF141317)
val onSurfaceDark = Color(0xFFE5E1E8)
val surfaceVariantDark = Color(0xFF484550)
val onSurfaceVariantDark = Color(0xFFC9C4D2)
val outlineDark = Color(0xFF938F9B)
val outlineVariantDark = Color(0xFF484550)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFE5E1E8)
val inverseOnSurfaceDark = Color(0xFF313035)
val inversePrimaryDark = Color(0xFF5F549D)
val surfaceDimDark = Color(0xFF141317)
val surfaceBrightDark = Color(0xFF3A383E)
val surfaceContainerLowestDark = Color(0xFF0E0E12)
val surfaceContainerLowDark = Color(0xFF1C1B20)
val surfaceContainerDark = Color(0xFF201F24)
val surfaceContainerHighDark = Color(0xFF2B292E)
val surfaceContainerHighestDark = Color(0xFF353439)

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
            resId = R.font.new_rocker_regular,
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

