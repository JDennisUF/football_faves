package com.footballfaves.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    secondary = SecondaryGold,
    onSecondary = Color.Black,
    background = NeutralOnBackground,
    onBackground = NeutralBackground,
    surface = NeutralOnBackground,
    onSurface = NeutralBackground
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    secondary = SecondaryGold,
    onSecondary = Color.Black,
    background = NeutralBackground,
    onBackground = NeutralOnBackground,
    surface = Color.White,
    onSurface = NeutralOnBackground
)

@Composable
fun FootballFavesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
