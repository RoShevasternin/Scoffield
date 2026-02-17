package com.winrush.itemguesser.game
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
import com.winrush.itemguesser.R

val primaryLight = Color(0xFF7D5700)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFD9A441)
val onPrimaryContainerLight = Color(0xFF573C00)
val secondaryLight = Color(0xFF2D353C)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFF444C53)
val onSecondaryContainerLight = Color(0xFFB4BCC4)
val tertiaryLight = Color(0xFF934618)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFB25E2E)
val onTertiaryContainerLight = Color(0xFFFFFCFF)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF93000A)
val backgroundLight = Color(0xFFFFF8F3)
val onBackgroundLight = Color(0xFF201B14)
val surfaceLight = Color(0xFFFFF8F3)
val onSurfaceLight = Color(0xFF201B14)
val surfaceVariantLight = Color(0xFFF0E0CC)
val onSurfaceVariantLight = Color(0xFF4F4536)
val outlineLight = Color(0xFF817564)
val outlineVariantLight = Color(0xFFD3C4B1)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF353028)
val inverseOnSurfaceLight = Color(0xFFFAEFE3)
val inversePrimaryLight = Color(0xFFF5BD58)
val surfaceDimLight = Color(0xFFE3D8CD)
val surfaceBrightLight = Color(0xFFFFF8F3)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFFDF2E6)
val surfaceContainerLight = Color(0xFFF7ECE0)
val surfaceContainerHighLight = Color(0xFFF1E7DB)
val surfaceContainerHighestLight = Color(0xFFEBE1D5)

val primaryDark = Color(0xFFF7BF59)
val onPrimaryDark = Color(0xFF422C00)
val primaryContainerDark = Color(0xFFD9A441)
val onPrimaryContainerDark = Color(0xFF573C00)
val secondaryDark = Color(0xFFBFC7D0)
val onSecondaryDark = Color(0xFF293138)
val secondaryContainerDark = Color(0xFF444C53)
val onSecondaryContainerDark = Color(0xFFB4BCC4)
val tertiaryDark = Color(0xFFFFB691)
val onTertiaryDark = Color(0xFF552100)
val tertiaryContainerDark = Color(0xFFB25E2E)
val onTertiaryContainerDark = Color(0xFFFFFCFF)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF17130C)
val onBackgroundDark = Color(0xFFEBE1D5)
val surfaceDark = Color(0xFF17130C)
val onSurfaceDark = Color(0xFFEBE1D5)
val surfaceVariantDark = Color(0xFF4F4536)
val onSurfaceVariantDark = Color(0xFFD3C4B1)
val outlineDark = Color(0xFF9C8F7D)
val outlineVariantDark = Color(0xFF4F4536)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFEBE1D5)
val inverseOnSurfaceDark = Color(0xFF353028)
val inversePrimaryDark = Color(0xFF7D5700)
val surfaceDimDark = Color(0xFF17130C)
val surfaceBrightDark = Color(0xFF3E3830)
val surfaceContainerLowestDark = Color(0xFF120E07)
val surfaceContainerLowDark = Color(0xFF201B14)
val surfaceContainerDark = Color(0xFF241F18)
val surfaceContainerHighDark = Color(0xFF2E2922)
val surfaceContainerHighestDark = Color(0xFF3A342C)

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
            resId = R.font.barriecito_regular,
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

