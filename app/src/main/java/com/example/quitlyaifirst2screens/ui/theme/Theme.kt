package com.example.quitlyaifirst2screens.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val QuitlyColorScheme = lightColorScheme(
    primary = Terracotta,
    onPrimary = GroundCream,
    primaryContainer = Terracotta100,
    onPrimaryContainer = TextDark,
    secondary = Sage,
    onSecondary = GroundCream,
    secondaryContainer = Sage100,
    onSecondaryContainer = TextDark,
    background = GroundCream,
    onBackground = TextDark,
    surface = SandCard,
    onSurface = TextDark,
    surfaceVariant = SandCard,
    onSurfaceVariant = TextMuted
)

@Composable
fun QuitlyAIfirst2ScreensTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = QuitlyColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}