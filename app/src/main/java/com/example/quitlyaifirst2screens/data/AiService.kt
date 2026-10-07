package com.example.quitlyaifirst2screens.data

import android.util.Log
import com.example.quitlyaifirst2screens.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class AiService {

    private val systemPrompt = """
        You are Quitly, an empathetic smoking-cessation coach.
        Speak in warm, non-judgmental language. Never shame the user for slipping.
        Reframe setbacks as data, not failure. Keep replies short (2-4 sentences).
        Sound human, not clinical. Never use bullet points or headings.
    """.trimIndent()

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val json = "application/json; charset=utf-8".toMediaType()

    suspend fun getCoachReply(history: List<ChatMessage>, userMessage: String): String =
        withContext(Dispatchers.IO) {
            try {
                val contents = JSONArray()
                history.forEach { msg ->
                    contents.put(
                        JSONObject().apply {
                            put("role", if (msg.isUser) "user" else "model")
                            put("parts", JSONArray().put(JSONObject().put("text", msg.text)))
                        }
                    )
                }
                contents.put(
                    JSONObject().apply {
                        put("role", "user")
                        put("parts", JSONArray().put(JSONObject().put("text", userMessage)))
                    }
                )

                val body = JSONObject().apply {
                    put(
                        "system_instruction",
                        JSONObject().put(
                            "parts",
                            JSONArray().put(JSONObject().put("text", systemPrompt))
                        )
                    )
                    put("contents", contents)
                    put(
                        "generationConfig",
                        JSONObject().apply {
                            put("temperature", 0.8)
                            put("maxOutputTokens", 512)
                            put(
                                "thinkingConfig",
                                JSONObject().apply {
                                    put("thinkingBudget", 0)
                                }
                            )
                        }
                    )
                }

                val url = "https://generativelanguage.googleapis.com/v1beta/models/" +
                        "gemini-3.5-flash:generateContent"

                val request = Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-goog-api-key", BuildConfig.GEMINI_API_KEY)
                    .post(body.toString().toRequestBody(json))
                    .build()

                client.newCall(request).execute().use { resp ->
                    val raw = resp.body?.string().orEmpty()
                    if (!resp.isSuccessful) {
                        Log.e("AiService", "HTTP ${resp.code}: $raw")
                        return@withContext FALLBACK_REPLY
                    }
                    val text = parseReply(raw)
                    if (text.isNullOrBlank()) {
                        Log.e("AiService", "Empty reply. Raw: $raw")
                        FALLBACK_REPLY
                    } else text
                }
            } catch (e: Exception) {
                Log.e("AiService", "Request failed: ${e.message}", e)
                FALLBACK_REPLY
            }
        }

    private fun parseReply(raw: String): String? {
        return try {
            val root = JSONObject(raw)
            val candidates = root.optJSONArray("candidates") ?: return null
            if (candidates.length() == 0) return null
            val first = candidates.getJSONObject(0)
            val content = first.optJSONObject("content") ?: return null
            val parts = content.optJSONArray("parts") ?: return null
            if (parts.length() == 0) return null
            parts.getJSONObject(0).optString("text").trim()
        } catch (e: Exception) {
            Log.e("AiService", "Parse failed: ${e.message}", e)
            null
        }
    }

    companion object {
        const val FALLBACK_REPLY =
            "I hear you — and I'm still here. Slips are information, not failure. " +
                    "Let's look at what was happening right before, and build from there."
    }
}