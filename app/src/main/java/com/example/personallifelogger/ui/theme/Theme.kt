package com.example.personallifelogger.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Blue theme colors
val Blue80 = Color(0xFF2196F3)
val BlueGrey80 = Color(0xFF1976D2)
val Blue40 = Color(0xFF1976D2)
val BlueGrey40 = Color(0xFF1565C0)

// Custom color schemes - Blue theme
val LightBlueColorScheme = lightColorScheme(
    primary = Color(0xFF2196F3),        // Blue
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBBDEFB),
    onPrimaryContainer = Color(0xFF0D47A1),
    secondary = Color(0xFF03DAC5),       // Teal
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFB2DFDB),
    onSecondaryContainer = Color(0xFF004D40),
    tertiary = Color(0xFF4CAF50),        // Green accent
    onTertiary = Color.White,
    background = Color(0xFFFFFFFF),      // White
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFFFF),         // White
    onSurface = Color(0xFF1C1B1F),
    error = Color(0xFFF44336),
    onError = Color.White
)

val DarkBlueColorScheme = darkColorScheme(
    primary = Color(0xFF64B5F6),         // Lighter Blue
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF1976D2),
    onPrimaryContainer = Color(0xFFBBDEFB),
    secondary = Color(0xFF03DAC5),       // Teal
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF004D40),
    onSecondaryContainer = Color(0xFFB2DFDB),
    tertiary = Color(0xFF81C784),        // Lighter Green
    onTertiary = Color.Black,
    background = Color(0xFF121212),      // Dark background
    onBackground = Color(0xFFE1E1E1),
    surface = Color(0xFF1E1E1E),         // Dark surface
    onSurface = Color(0xFFE1E1E1),
    error = Color(0xFFCF6679),
    onError = Color.Black
)

@Composable
fun PersonalLifeLoggerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkBlueColorScheme
        else -> LightBlueColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}