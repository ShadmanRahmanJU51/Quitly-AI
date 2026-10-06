package com.example.quitlyaifirst2screens.data

import com.example.quitlyaifirst2screens.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AiService {

    private val systemPrompt = """
        You are Quitly, an empathetic smoking-cessation coach.
        Speak in warm, non-judgmental language. Never shame the user for slipping.
        Reframe setbacks as data, not failure. Keep replies short (2-4 sentences).
        Sound human, not clinical. Never use bullet points or headings.
    """.trimIndent()

    private val model: GenerativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY,
            systemInstruction = content { text(systemPrompt) }
        )
    }

    suspend fun getCoachReply(
        history: List<ChatMessage>,
        userMessage: String
    ): String = withContext(Dispatchers.IO) {
        try {
            val contents = history.map { msg ->
                content(role = if (msg.isUser) "user" else "model") {
                    text(msg.text)
                }
            } + content(role = "user") { text(userMessage) }

            val response = model.generateContent(*contents.toTypedArray())
            response.text ?: FALLBACK_REPLY
        } catch (e: Exception) {
            FALLBACK_REPLY
        }
    }

    companion object {
        const val FALLBACK_REPLY =
            "I hear you — and I'm still here. Slips are information, not failure. " +
                    "Let's look at what was happening right before, and build from there."
    }
}