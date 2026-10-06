package com.example.quitlyaifirst2screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.quitlyaifirst2screens.screens.OnboardingIntroScreen
import com.example.quitlyaifirst2screens.screens.SplashScreen
import com.example.quitlyaifirst2screens.ui.theme.QuitlyAIfirst2ScreensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuitlyAIfirst2ScreensTheme {
                QuitlyApp()
            }
        }
    }
}

@Composable
fun QuitlyApp() {
    // Simple 2-screen state switch. Will be replaced with proper
    // navigation once more screens are added.
    var currentScreen by remember { mutableStateOf("splash") }

    when (currentScreen) {
        "splash" -> SplashScreen(
            onBeginQuit = { currentScreen = "onboardingIntro" },
            onSignIn = { /* not yet designed */ }
        )
        "onboardingIntro" -> OnboardingIntroScreen(
            onContinue = { /* Screen 3 not yet built */ }
        )
    }
}