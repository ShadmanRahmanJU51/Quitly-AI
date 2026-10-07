package com.example.quitlyaifirst2screens.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

/**
 * Tiny SharedPreferences wrapper. Saves only what's needed to reconstruct the user's state.
 */
class Persistence(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences("quitly_prefs", Context.MODE_PRIVATE)

    var userName: String?
        get() = prefs.getString("userName", null)
        set(v) = prefs.edit().putString("userName", v).apply()

    var streakDays: Int
        get() = prefs.getInt("streakDays", 12)
        set(v) = prefs.edit().putInt("streakDays", v).apply()

    var cigsPerDay: Int
        get() = prefs.getInt("cigsPerDay", 15)
        set(v) = prefs.edit().putInt("cigsPerDay", v).apply()

    var selectedReasonIds: Set<String>
        get() = prefs.getStringSet("reasonIds", emptySet()) ?: emptySet()
        set(v) = prefs.edit().putStringSet("reasonIds", v).apply()

    var selectedTriggerIds: Set<String>
        get() = prefs.getStringSet("triggerIds", emptySet()) ?: emptySet()
        set(v) = prefs.edit().putStringSet("triggerIds", v).apply()

    fun saveLogs(logs: List<LogEntry>) {
        val arr = JSONArray()
        logs.forEach { e ->
            arr.put(JSONObject().apply {
                put("id", e.id)
                put("ts", e.timestamp)
                put("outcome", e.outcome.name)
                put("trigger", e.trigger ?: JSONObject.NULL)
                put("intensity", e.intensity)
                put("note", e.note)
            })
        }
        prefs.edit().putString("logs", arr.toString()).apply()
    }

    fun loadLogs(): List<LogEntry> {
        val raw = prefs.getString("logs", null) ?: return emptyList()
        return try {
            val arr = JSONArray(raw)
            (0 until arr.length()).map { i ->
                val o = arr.getJSONObject(i)
                LogEntry(
                    id = o.getString("id"),
                    timestamp = o.getLong("ts"),
                    outcome = LogOutcome.valueOf(o.getString("outcome")),
                    trigger = if (o.isNull("trigger")) null else o.getString("trigger"),
                    intensity = o.getInt("intensity"),
                    note = o.optString("note", "")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}