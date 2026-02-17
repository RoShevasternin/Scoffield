package com.turbowin.safeunlock.game
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
import com.turbowin.safeunlock.R

val primaryLight = Color(0xFF15451F)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFF2E5D34)
val onPrimaryContainerLight = Color(0xFFA0D4A0)
val secondaryLight = Color(0xFF795900)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFD6A83F)
val onSecondaryContainerLight = Color(0xFF563E00)
val tertiaryLight = Color(0xFF4C662C)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFF8FAD6A)
val onTertiaryContainerLight = Color(0xFF284009)
val errorLight = Color(0xFF942225)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFB53A3A)
val onErrorContainerLight = Color(0xFFFFDCD9)
val backgroundLight = Color(0xFFF9FAF4)
val onBackgroundLight = Color(0xFF191C19)
val surfaceLight = Color(0xFFF9FAF4)
val onSurfaceLight = Color(0xFF191C19)
val surfaceVariantLight = Color(0xFFDDE5D9)
val onSurfaceVariantLight = Color(0xFF414940)
val outlineLight = Color(0xFF72796F)
val outlineVariantLight = Color(0xFFC1C9BD)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF2E312D)
val inverseOnSurfaceLight = Color(0xFFF0F1EB)
val inversePrimaryLight = Color(0xFF9FD3A0)
val surfaceDimLight = Color(0xFFD9DBD5)
val surfaceBrightLight = Color(0xFFF9FAF4)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF3F4EE)
val surfaceContainerLight = Color(0xFFEDEEE8)
val surfaceContainerHighLight = Color(0xFFE7E9E3)
val surfaceContainerHighestLight = Color(0xFFE2E3DD)

val primaryDark = Color(0xFF9FD3A0)
val onPrimaryDark = Color(0xFF063914)
val primaryContainerDark = Color(0xFF2E5D34)
val onPrimaryContainerDark = Color(0xFFA0D4A0)
val secondaryDark = Color(0xFFF4C357)
val onSecondaryDark = Color(0xFF402D00)
val secondaryContainerDark = Color(0xFFD6A83F)
val onSecondaryContainerDark = Color(0xFF563E00)
val tertiaryDark = Color(0xFFB1D18A)
val onTertiaryDark = Color(0xFF1F3701)
val tertiaryContainerDark = Color(0xFF8FAD6A)
val onTertiaryContainerDark = Color(0xFF284009)
val errorDark = Color(0xFFFFB3AE)
val onErrorDark = Color(0xFF68000C)
val errorContainerDark = Color(0xFFB53A3A)
val onErrorContainerDark = Color(0xFFFFDCD9)
val backgroundDark = Color(0xFF111411)
val onBackgroundDark = Color(0xFFE2E3DD)
val surfaceDark = Color(0xFF111411)
val onSurfaceDark = Color(0xFFE2E3DD)
val surfaceVariantDark = Color(0xFF414940)
val onSurfaceVariantDark = Color(0xFFC1C9BD)
val outlineDark = Color(0xFF8B9388)
val outlineVariantDark = Color(0xFF414940)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFE2E3DD)
val inverseOnSurfaceDark = Color(0xFF2E312D)
val inversePrimaryDark = Color(0xFF39693E)
val surfaceDimDark = Color(0xFF111411)
val surfaceBrightDark = Color(0xFF373A36)
val surfaceContainerLowestDark = Color(0xFF0C0F0C)
val surfaceContainerLowDark = Color(0xFF191C19)
val surfaceContainerDark = Color(0xFF1D201D)
val surfaceContainerHighDark = Color(0xFF282B27)
val surfaceContainerHighestDark = Color(0xFF333631)

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
            resId = R.font.kirang_haerang_regular,
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

