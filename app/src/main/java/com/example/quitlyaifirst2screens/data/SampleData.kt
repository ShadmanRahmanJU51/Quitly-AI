package com.example.quitlyaifirst2screens.data

object SampleData {

    // Screen 3 — Preference survey
    val reasons = listOf(
        Reason("lungs",  "Lung health",     "\uD83E\uDEC1"),
        Reason("family", "Family & kids",   "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67"),
        Reason("money",  "Money saved",     "\uD83D\uDCB0"),
        Reason("focus",  "Focus & mood",    "\uD83E\uDDE0"),
        Reason("skin",   "Skin & teeth",    "\u2728"),
        Reason("smell",  "Smell & social",  "\uD83C\uDF38")
    )

    // Screen 4 — Habit assessment
    val triggers = listOf(
        Trigger("coffee",  "Morning coffee"),
        Trigger("stress",  "Stress at work"),
        Trigger("meals",   "After meals"),
        Trigger("driving", "Driving"),
        Trigger("drinks",  "With drinks"),
        Trigger("boredom", "Boredom")
    )

    // Screen 13 — Progress & milestones
    val milestones = listOf(
        Milestone("72h", "72 hours", 72,    isEarned = true),
        Milestone("1w",  "1 week",   168,   isEarned = true),
        Milestone("2w",  "2 weeks",  336,   isEarned = false),
        Milestone("1m",  "1 month",  720,   isEarned = false),
        Milestone("3m",  "3 months", 2160,  isEarned = false),
        Milestone("1y",  "1 year",   8760,  isEarned = false)
    )

    // Screen 12 — Motivational feed (Maya's default ordering)
    val feedItems = listOf(
        FeedItem(
            id = "f1",
            title = "Why your lungs start healing in 48 hours",
            body = "The first two days are the most dramatic. Cilia begin to wake up and clear the buildup.",
            tag = "LUNG HEALTH",
            kind = FeedKind.HERO
        ),
        FeedItem(
            id = "f2",
            title = "By tomorrow, carbon monoxide is back to a non-smoker's level.",
            body = "That single change is why your energy shifts first.",
            tag = "DID YOU KNOW",
            kind = FeedKind.FACT
        ),
        FeedItem(
            id = "f3",
            title = "\"Day 90. My daughter said I smell like Dad again.\"",
            body = "Priya, quit 3 months ago",
            tag = "REAL STORY",
            kind = FeedKind.STORY
        ),
        FeedItem(
            id = "f4",
            title = "$2,190 a year \u2014 Leo's swim class, four times over.",
            body = "What your quit adds up to.",
            tag = "MONEY",
            kind = FeedKind.MONEY
        )
    )

    // Screen 15 — Community
    val communityPosts = listOf(
        CommunityPost(
            id = "c1",
            authorName = "Anika",
            dayBadge = "DAY 30",
            body = "Made it a month. The 3pm cravings finally stopped yelling at me.",
            likes = 24,
            comments = 6
        ),
        CommunityPost(
            id = "c2",
            authorName = "Sam",
            dayBadge = "DAY 4",
            body = "Slipped last night but I'm back. Logging it felt weirdly freeing.",
            likes = 41,
            comments = 12
        )
    )
}