package com.example.quitlyaifirst2screens.navigation

/** Central route constants. Add new routes here, never inline. */
object Routes {
    // Onboarding
    const val SPLASH = "splash"
    const val ONBOARDING_INTRO = "onboarding_intro"
    const val SURVEY = "survey"
    const val ASSESSMENT = "assessment"
    const val RESULTS = "results"           // Maya variant (default)
    const val RESULTS_DIEGO = "results_diego"

    // Everyday
    const val HOME = "home"
    const val SOS = "sos"
    const val HABIT_LOG = "habit_log"       // optional arg: prefill=resisted|smoked
    const val HISTORY = "history"

    // Coaching
    const val COACH = "coach"
    const val FEED = "feed"
    const val PROGRESS = "progress"
    const val SETBACK = "setback"
    const val COMMUNITY = "community"

    // Settings
    const val NUDGES = "nudges"
    const val PROFILE = "profile"

    const val HABIT_LOG_ARG = "outcome"
    /** Build a Habit Log route with an optional prefill: "resisted" | "smoked" | null. */
    fun habitLog(prefill: String? = null): String =
        if (prefill == null) HABIT_LOG else "$HABIT_LOG?$HABIT_LOG_ARG=$prefill"
}