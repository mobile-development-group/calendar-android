package com.mdgroup.sample.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = PacificBridge,
    onPrimary = Color.White,
    primaryContainer = Contrail,
    onPrimaryContainer = CetaceanBlue,
    inversePrimary = NightSnow,
    secondary = Grisaille,
    onSecondary = Color.White,
    secondaryContainer = BrilliantWhite,
    onSecondaryContainer = Sambucus,
    tertiary = GrassCourt,
    onTertiary = Color.White,
    tertiaryContainer = MintChiffon,
    onTertiaryContainer = ClockChimes,
    background = Contrail,
    surface = Contrail,
    onSurface = GluonGrey,
    surfaceContainer = Color.White,
    onSurfaceVariant = IcedLavender,
    inverseOnSurface = AlpineGoat,
    surfaceContainerLow = Color.White,
    error = Carnelian,
    onError = Color.White,
    errorContainer = ForgottenPink,
    onErrorContainer = Biltong,
    outline = GreyCloud,
    outlineVariant = HerringSilver,
    scrim = Color.Black
)

@Composable
fun CalendarTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun CalendarThemePreview(
    content: @Composable () -> Unit
) = CalendarTheme(
    // It not work on preview
    dynamicColor = false,
    content = content
)