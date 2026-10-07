package com.example.quitlyaifirst2screens

import com.example.quitlyaifirst2screens.components.TabItem
import com.example.quitlyaifirst2screens.screens.HomeDashboardScreen
import com.example.quitlyaifirst2screens.screens.PersonalizedResultsScreen
import com.example.quitlyaifirst2screens.screens.ResultVariant
import com.example.quitlyaifirst2screens.screens.HabitAssessmentScreen
import com.example.quitlyaifirst2screens.screens.PreferenceSurveyScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quitlyaifirst2screens.navigation.Routes
import com.example.quitlyaifirst2screens.screens.OnboardingIntroScreen
import com.example.quitlyaifirst2screens.screens.PlaceholderScreen
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
    val navController = rememberNavController()
    val onTabSelected: (TabItem) -> Unit = { tab ->
        when (tab) {
            TabItem.HOME -> navController.navigate(Routes.HOME) {
                launchSingleTop = true
                popUpTo(Routes.HOME) { inclusive = false }
            }
            TabItem.PROGRESS -> navController.navigate(Routes.PROGRESS) {
                launchSingleTop = true
            }
            TabItem.LOG -> navController.navigate(Routes.HABIT_LOG) {
                launchSingleTop = true
            }
            TabItem.COACH -> navController.navigate(Routes.COACH) {
                launchSingleTop = true
            }
            TabItem.YOU -> navController.navigate(Routes.PROFILE) {
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        // ---------- Onboarding ----------
        composable(Routes.SPLASH) {
            SplashScreen(
                onBeginQuit = {
                    navController.navigate(Routes.ONBOARDING_INTRO) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onSignIn = { /* not yet designed */ }
            )
        }

        composable(Routes.ONBOARDING_INTRO) {
            OnboardingIntroScreen(
                onContinue = { navController.navigate(Routes.SURVEY) }
            )
        }

        composable(Routes.SURVEY) {
            PreferenceSurveyScreen(
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(Routes.ASSESSMENT) }
            )
        }

        composable(Routes.ASSESSMENT) {
            HabitAssessmentScreen(
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(Routes.RESULTS) }
            )
        }

        composable(Routes.RESULTS) {
            PersonalizedResultsScreen(
                variant = ResultVariant.MAYA,
                onStart = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.RESULTS_DIEGO) {
            PersonalizedResultsScreen(
                variant = ResultVariant.DIEGO,
                onStart = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // ---------- Everyday ----------

        composable(Routes.HOME) {
            HomeDashboardScreen(
                onCraving = { navController.navigate(Routes.SOS) },
                onLog = { navController.navigate(Routes.HABIT_LOG) },
                onCoach = { navController.navigate(Routes.COACH) },
                onTabSelected = onTabSelected
            )
        }

        composable(Routes.SOS) {
            PlaceholderScreen(
                title = "Craving / SOS",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.HABIT_LOG) {
            PlaceholderScreen(
                title = "Habit Log",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.HISTORY) {
            PlaceholderScreen(
                title = "History & Analytics",
                onBack = { navController.popBackStack() }
            )
        }

        // ---------- Coaching ----------
        composable(Routes.COACH) {
            PlaceholderScreen(
                title = "AI Coach",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.FEED) {
            PlaceholderScreen(
                title = "Motivational Feed",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PROGRESS) {
            PlaceholderScreen(
                title = "Progress & Milestones",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SETBACK) {
            PlaceholderScreen(
                title = "Setback Check-in",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.COMMUNITY) {
            PlaceholderScreen(
                title = "Community",
                onBack = { navController.popBackStack() }
            )
        }

        // ---------- Settings ----------
        composable(Routes.NUDGES) {
            PlaceholderScreen(
                title = "Adaptive Nudges",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PROFILE) {
            PlaceholderScreen(
                title = "Profile & Settings",
                onBack = { navController.popBackStack() }
            )
        }
    }
}