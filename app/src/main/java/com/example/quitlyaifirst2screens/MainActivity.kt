package com.example.quitlyaifirst2screens

import com.example.quitlyaifirst2screens.screens.MotivationalFeedScreen
import com.example.quitlyaifirst2screens.screens.CoachChatScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quitlyaifirst2screens.components.TabItem
import com.example.quitlyaifirst2screens.data.LogOutcome
import com.example.quitlyaifirst2screens.navigation.Routes
import com.example.quitlyaifirst2screens.screens.HabitAssessmentScreen
import com.example.quitlyaifirst2screens.screens.HabitLogScreen
import com.example.quitlyaifirst2screens.screens.HistoryAnalyticsScreen
import com.example.quitlyaifirst2screens.screens.HomeDashboardScreen
import com.example.quitlyaifirst2screens.screens.OnboardingIntroScreen
import com.example.quitlyaifirst2screens.screens.PersonalizedResultsScreen
import com.example.quitlyaifirst2screens.screens.PlaceholderScreen
import com.example.quitlyaifirst2screens.screens.PreferenceSurveyScreen
import com.example.quitlyaifirst2screens.screens.ProgressMilestonesScreen
import com.example.quitlyaifirst2screens.screens.ResultVariant
import com.example.quitlyaifirst2screens.screens.SosScreen
import com.example.quitlyaifirst2screens.screens.SplashScreen
import com.example.quitlyaifirst2screens.ui.theme.QuitlyAIfirst2ScreensTheme
import com.example.quitlyaifirst2screens.viewmodel.QuitlyViewModel

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

    val vm: QuitlyViewModel = viewModel()

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
                onContinue = { navController.navigate(Routes.ASSESSMENT) },
                vm = vm
            )
        }

        composable(Routes.ASSESSMENT) {
            HabitAssessmentScreen(
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(Routes.RESULTS) },
                vm = vm
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
                onTabSelected = onTabSelected,
                vm = vm
            )
        }

        composable(Routes.SOS) {
            SosScreen(
                onClose = { navController.popBackStack() },
                onResisted = {
                    navController.navigate(Routes.habitLog("resisted")) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onSlipped = {
                    navController.navigate(Routes.SETBACK) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                vm = vm
            )
        }

        composable(
            route = "${Routes.HABIT_LOG}?${Routes.HABIT_LOG_ARG}={${Routes.HABIT_LOG_ARG}}",
            arguments = listOf(
                navArgument(Routes.HABIT_LOG_ARG) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val raw = backStackEntry.arguments?.getString(Routes.HABIT_LOG_ARG)
            val prefill = when (raw) {
                "resisted" -> LogOutcome.RESISTED
                "smoked" -> LogOutcome.SMOKED
                else -> null
            }
            HabitLogScreen(
                onClose = { navController.popBackStack() },
                onSaved = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                prefilledOutcome = prefill,
                vm = vm
            )
        }

        composable(Routes.HISTORY) {
            HistoryAnalyticsScreen(
                onOpenNudges = { navController.navigate(Routes.NUDGES) },
                onTabSelected = onTabSelected,
                vm = vm
            )
        }

        // ---------- Coaching ----------
        composable(Routes.COACH) {
            CoachChatScreen(
                onBack = { navController.popBackStack() },
                vm = vm
            )
        }

        composable(Routes.FEED) {
            MotivationalFeedScreen(
                onBack = { navController.popBackStack() },
                onOpenCommunity = { navController.navigate(Routes.COMMUNITY) },
                vm = vm
            )
        }

        composable(Routes.PROGRESS) {
            ProgressMilestonesScreen(
                onTabSelected = onTabSelected,
                onOpenHistory = { navController.navigate(Routes.HISTORY) },
                onOpenFeed = { navController.navigate(Routes.FEED) },
                vm = vm
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