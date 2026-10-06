package com.example.quitlyaifirst2screens.data

import java.util.UUID

data class Reason(
    val id: String,
    val label: String,
    val emoji: String,
    val isSelected: Boolean = false
)

data class Trigger(
    val id: String,
    val label: String,
    val isSelected: Boolean = false
)

enum class LogOutcome { RESISTED, SMOKED }

data class LogEntry(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val outcome: LogOutcome,
    val trigger: String?,
    val intensity: Int,
    val note: String = ""
)

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val isUser: Boolean,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class Milestone(
    val id: String,
    val label: String,
    val hoursRequired: Int,
    val isEarned: Boolean = false
)

enum class FeedKind { HERO, FACT, STORY, MONEY }

data class FeedItem(
    val id: String,
    val title: String,
    val body: String,
    val tag: String,
    val kind: FeedKind
)

data class CommunityPost(
    val id: String,
    val authorName: String,
    val dayBadge: String,
    val body: String,
    val likes: Int,
    val comments: Int
)

