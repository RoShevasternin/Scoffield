package com.turbowin.gemshuffle.game
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
import com.turbowin.gemshuffle.R

val primaryLight = Color(0xFF7C008E)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFA200BA)
val onPrimaryContainerLight = Color(0xFFFFC6FF)
val secondaryLight = Color(0xFF006689)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFF00B2EC)
val onSecondaryContainerLight = Color(0xFF004058)
val tertiaryLight = Color(0xFF6D5E00)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFF6D700)
val onTertiaryContainerLight = Color(0xFF6C5D00)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF93000A)
val backgroundLight = Color(0xFFFFF7FA)
val onBackgroundLight = Color(0xFF221922)
val surfaceLight = Color(0xFFFFF7FA)
val onSurfaceLight = Color(0xFF221922)
val surfaceVariantLight = Color(0xFFF3DCEF)
val onSurfaceVariantLight = Color(0xFF524251)
val outlineLight = Color(0xFF847182)
val outlineVariantLight = Color(0xFFD6C0D3)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF382D37)
val inverseOnSurfaceLight = Color(0xFFFDECF9)
val inversePrimaryLight = Color(0xFFFAABFF)
val surfaceDimLight = Color(0xFFE6D5E2)
val surfaceBrightLight = Color(0xFFFFF7FA)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFFFEFFB)
val surfaceContainerLight = Color(0xFFFBE9F6)
val surfaceContainerHighLight = Color(0xFFF5E3F1)
val surfaceContainerHighestLight = Color(0xFFEFDDEB)

val primaryDark = Color(0xFFFAABFF)
val onPrimaryDark = Color(0xFF570065)
val primaryContainerDark = Color(0xFFA200BA)
val onPrimaryContainerDark = Color(0xFFFFC6FF)
val secondaryDark = Color(0xFF77D1FF)
val onSecondaryDark = Color(0xFF003549)
val secondaryContainerDark = Color(0xFF00B2EC)
val onSecondaryContainerDark = Color(0xFF004058)
val tertiaryDark = Color(0xFFFFF4CB)
val onTertiaryDark = Color(0xFF393000)
val tertiaryContainerDark = Color(0xFFF6D700)
val onTertiaryContainerDark = Color(0xFF6C5D00)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF191019)
val onBackgroundDark = Color(0xFFEFDDEB)
val surfaceDark = Color(0xFF191019)
val onSurfaceDark = Color(0xFFEFDDEB)
val surfaceVariantDark = Color(0xFF524251)
val onSurfaceVariantDark = Color(0xFFD6C0D3)
val outlineDark = Color(0xFF9E8B9D)
val outlineVariantDark = Color(0xFF524251)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFEFDDEB)
val inverseOnSurfaceDark = Color(0xFF382D37)
val inversePrimaryDark = Color(0xFFA200BA)
val surfaceDimDark = Color(0xFF191019)
val surfaceBrightDark = Color(0xFF413640)
val surfaceContainerLowestDark = Color(0xFF140B14)
val surfaceContainerLowDark = Color(0xFF221922)
val surfaceContainerDark = Color(0xFF261C26)
val surfaceContainerHighDark = Color(0xFF312731)
val surfaceContainerHighestDark = Color(0xFF3C313C)

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
            resId = R.font.fondamento_regular,
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

