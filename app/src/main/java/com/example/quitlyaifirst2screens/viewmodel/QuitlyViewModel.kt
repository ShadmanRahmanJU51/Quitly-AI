package com.example.quitlyaifirst2screens.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.quitlyaifirst2screens.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuitlyViewModel(app: Application) : AndroidViewModel(app) {

    private val aiService = AiService()
    private val persistence = Persistence(app)
    private val scope = CoroutineScope(Dispatchers.Main)

    // ── Onboarding state ─────────────────────────────────────────────
    var reasons by mutableStateOf(
        SampleData.reasons.map { it.copy(isSelected = it.id in persistence.selectedReasonIds) }
    )
        private set

    var triggers by mutableStateOf(
        SampleData.triggers.map { it.copy(isSelected = it.id in persistence.selectedTriggerIds) }
    )
        private set

    var milestones by mutableStateOf(SampleData.milestones); private set

    var cigsPerDay by mutableStateOf(persistence.cigsPerDay)
        private set

    // ── User / progress state ────────────────────────────────────────
    var userName by mutableStateOf(persistence.userName ?: "Maya")
        private set

    var streakDays by mutableStateOf(persistence.streakDays)
        private set

    var quitStartDate by mutableStateOf(System.currentTimeMillis())
        private set

    // ── Logs ─────────────────────────────────────────────────────────
    var logs by mutableStateOf(persistence.loadLogs())
        private set

    // ── Chat ─────────────────────────────────────────────────────────
    var chatMessages by mutableStateOf<List<ChatMessage>>(emptyList())
        private set

    var isAiLoading by mutableStateOf(false)
        private set

    var aiError by mutableStateOf<String?>(null)
        private set

    // ── Derived stats ────────────────────────────────────────────────
    val cigarettesNotSmoked: Int
        get() = streakDays * cigsPerDay

    val moneySaved: Double
        get() = cigarettesNotSmoked * 0.40

    val hoursReclaimed: Int
        get() = (cigarettesNotSmoked * 5) / 60

    val selectedReasonCount: Int
        get() = reasons.count { it.isSelected }

    val selectedTriggerCount: Int
        get() = triggers.count { it.isSelected }

    // ── Onboarding mutations ─────────────────────────────────────────
    fun toggleReason(id: String) {
        reasons = reasons.map {
            if (it.id == id) it.copy(isSelected = !it.isSelected) else it
        }
        persistence.selectedReasonIds = reasons.filter { it.isSelected }.map { it.id }.toSet()
    }

    fun updateUserName(name: String) {
        val trimmed = name.trim()
        if (trimmed.isNotEmpty()) {
            userName = trimmed
            persistence.userName = trimmed
        }
    }

    fun toggleTrigger(id: String) {
        triggers = triggers.map {
            if (it.id == id) it.copy(isSelected = !it.isSelected) else it
        }
        persistence.selectedTriggerIds = triggers.filter { it.isSelected }.map { it.id }.toSet()
    }

    fun incrementCigs() {
        if (cigsPerDay < 60) {
            cigsPerDay++
            persistence.cigsPerDay = cigsPerDay
        }
    }

    fun decrementCigs() {
        if (cigsPerDay > 1) {
            cigsPerDay--
            persistence.cigsPerDay = cigsPerDay
        }
    }

    // ── Logging ──────────────────────────────────────────────────────
    fun addLog(entry: LogEntry) {
        logs = listOf(entry) + logs
        persistence.saveLogs(logs)
    }

    // ── Chat ─────────────────────────────────────────────────────────
    fun addChatMessage(message: ChatMessage) {
        chatMessages = chatMessages + message
    }

    fun sendChatMessage(text: String) {
        val userMsg = ChatMessage(isUser = true, text = text)
        chatMessages = chatMessages + userMsg
        isAiLoading = true
        aiError = null

        scope.launch {
            try {
                val reply = aiService.getCoachReply(
                    history = chatMessages.dropLast(1),
                    userMessage = text
                )
                chatMessages = chatMessages + ChatMessage(isUser = false, text = reply)
            } catch (e: Exception) {
                aiError = e.message
                chatMessages = chatMessages + ChatMessage(
                    isUser = false,
                    text = AiService.FALLBACK_REPLY
                )
            } finally {
                isAiLoading = false
            }
        }
    }

    fun updateAiLoading(loading: Boolean) { isAiLoading = loading }
    fun updateAiError(err: String?) { aiError = err }
}