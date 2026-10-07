package com.example.quitlyaifirst2screens.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitlyaifirst2screens.components.QuitlyTabBar
import com.example.quitlyaifirst2screens.components.TabItem
import com.example.quitlyaifirst2screens.data.LogOutcome
import com.example.quitlyaifirst2screens.ui.theme.AccentDeep
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Neutral200
import com.example.quitlyaifirst2screens.ui.theme.Neutral500
import com.example.quitlyaifirst2screens.ui.theme.Sage
import com.example.quitlyaifirst2screens.ui.theme.Sage100
import com.example.quitlyaifirst2screens.ui.theme.Sage900
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.Terracotta100
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted
import com.example.quitlyaifirst2screens.viewmodel.QuitlyViewModel

@Composable
fun HistoryAnalyticsScreen(
    onOpenNudges: () -> Unit,
    onTabSelected: (TabItem) -> Unit,
    vm: QuitlyViewModel = viewModel()
) {
    var range by remember { mutableStateOf("Week") }

    // Fake 7-day series for the chart (real data comes from logs + persistence later)
    val week = listOf(3f, 5f, 4f, 7f, 6f, 8f, 4f)
    val maxVal = week.maxOrNull() ?: 10f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                text = "History & Analytics",
                fontFamily = Caprasimo,
                fontSize = 31.sp,
                color = TextDark
            )
            Spacer(Modifier.height(16.dp))

            // Segmented control
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Neutral200)
                    .padding(4.dp)
            ) {
                SegPill("Week", range == "Week", Modifier.weight(1f)) { range = "Week" }
                SegPill("Month", range == "Month", Modifier.weight(1f)) { range = "Month" }
            }

            Spacer(Modifier.height(20.dp))

            // Chart card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(SandCard)
                    .padding(18.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Craving intensity",
                            fontFamily = Caprasimo,
                            fontSize = 16.sp,
                            color = TextDark
                        )
                        Spacer(Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(Sage100)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "▼ 28% vs last week",
                                fontFamily = Figtree,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp,
                                color = Sage
                            )
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    Bars(values = week, maxValue = maxVal)
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("M", "T", "W", "T", "F", "S", "S").forEach {
                            Text(
                                text = it,
                                fontFamily = Figtree,
                                fontSize = 11.sp,
                                color = Neutral500
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // AI insight card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Sage100)
                    .padding(18.dp)
            ) {
                Column {
                    Text(
                        text = "AI INSIGHT",
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        letterSpacing = 1.2.sp,
                        color = Sage
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Your cravings spike Wed ~3pm, right after your coffee break. That's a pattern we can work with.",
                        fontFamily = Figtree,
                        fontSize = 14.sp,
                        lineHeight = 19.sp,
                        color = Sage900
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Want a nudge before then?",
                        fontFamily = Caprasimo,
                        fontSize = 14.sp,
                        color = AccentDeep,
                        modifier = Modifier.clickable { onOpenNudges() }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Recent moments",
                fontFamily = Caprasimo,
                fontSize = 18.sp,
                color = TextDark
            )
            Spacer(Modifier.height(10.dp))

            if (vm.logs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(SandCard)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Nothing logged yet. When you do, it'll show up here.",
                        fontFamily = Figtree,
                        fontSize = 13.5.sp,
                        color = TextMuted
                    )
                }
            } else {
                vm.logs.take(10).forEach { entry ->
                    LogRow(
                        outcome = entry.outcome,
                        trigger = entry.trigger ?: "—",
                        intensity = entry.intensity,
                        note = entry.note
                    )
                    Spacer(Modifier.height(10.dp))
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        QuitlyTabBar(active = TabItem.PROGRESS, onTabSelected = onTabSelected)
    }
}

@Composable
private fun SegPill(
    label: String,
    active: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(50))
            .background(if (active) GroundCream else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = Figtree,
            fontWeight = if (active) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 13.5.sp,
            color = if (active) TextDark else TextMuted
        )
    }
}

@Composable
private fun Bars(values: List<Float>, maxValue: Float) {
    val brush = Brush.verticalGradient(listOf(Terracotta, Sage))
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val n = values.size
        val gap = 8.dp.toPx()
        val barW = (size.width - gap * (n - 1)) / n
        values.forEachIndexed { i, v ->
            val h = (v / maxValue) * (size.height - 8.dp.toPx())
            val x = i * (barW + gap)
            drawRoundRect(
                brush = brush,
                topLeft = androidx.compose.ui.geometry.Offset(x, size.height - h),
                size = androidx.compose.ui.geometry.Size(barW, h),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(barW / 2f)
            )
        }
    }
}

@Composable
private fun LogRow(
    outcome: LogOutcome,
    trigger: String,
    intensity: Int,
    note: String
) {
    val accent = if (outcome == LogOutcome.RESISTED) Sage else Terracotta
    val soft = if (outcome == LogOutcome.RESISTED) Sage100 else Terracotta100
    val label = if (outcome == LogOutcome.RESISTED) "Resisted" else "Smoked"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SandCard)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(50))
                    .background(soft),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (outcome == LogOutcome.RESISTED) "🌿" else "💨",
                    fontSize = 16.sp
                )
            }
            Spacer(Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = label,
                        fontFamily = Caprasimo,
                        fontSize = 14.sp,
                        color = accent
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(
                        text = "· $intensity/10",
                        fontFamily = Figtree,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
                Spacer(Modifier.height(2.dp))
                Text(
                    text = trigger,
                    fontFamily = Figtree,
                    fontSize = 12.5.sp,
                    color = TextDark
                )
                if (note.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "“$note”",
                        fontFamily = Figtree,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }
        }
    }
}