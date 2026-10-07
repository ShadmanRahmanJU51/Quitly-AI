package com.example.quitlyaifirst2screens.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.quitlyaifirst2screens.data.*

class QuitlyViewModel : ViewModel() {

    private val aiService = AiService()
    private val scope = CoroutineScope(Dispatchers.Main)

    // ── Onboarding state ─────────────────────────────────────────────
    var reasons by mutableStateOf(SampleData.reasons)
        private set

    var triggers by mutableStateOf(SampleData.triggers)
        private set

    var milestones by mutableStateOf(SampleData.milestones); private set

    var cigsPerDay by mutableStateOf(15)
        private set

    // ── User / progress state ────────────────────────────────────────
    var userName by mutableStateOf("Maya")
        private set

    var streakDays by mutableStateOf(12)
        private set

    var quitStartDate by mutableStateOf(System.currentTimeMillis())
        private set

    // ── Logs ─────────────────────────────────────────────────────────
    var logs by mutableStateOf<List<LogEntry>>(emptyList())
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
        get() = cigarettesNotSmoked * 0.40    // $0.40 per cigarette

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
    }

    fun toggleTrigger(id: String) {
        triggers = triggers.map {
            if (it.id == id) it.copy(isSelected = !it.isSelected) else it
        }
    }

    fun incrementCigs() { if (cigsPerDay < 60) cigsPerDay++ }
    fun decrementCigs() { if (cigsPerDay > 1) cigsPerDay-- }

    // ── Logging ──────────────────────────────────────────────────────
    fun addLog(entry: LogEntry) {
        logs = listOf(entry) + logs
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