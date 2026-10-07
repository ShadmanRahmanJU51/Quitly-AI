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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitlyaifirst2screens.components.QuitlyStat
import com.example.quitlyaifirst2screens.components.QuitlyStreakRing
import com.example.quitlyaifirst2screens.components.QuitlyTabBar
import com.example.quitlyaifirst2screens.components.TabItem
import com.example.quitlyaifirst2screens.ui.theme.AccentDeep
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Sage
import com.example.quitlyaifirst2screens.ui.theme.Sage100
import com.example.quitlyaifirst2screens.ui.theme.Sage900
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.Terracotta100
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted
import com.example.quitlyaifirst2screens.viewmodel.QuitlyViewModel
import java.util.Calendar

@Composable
fun HomeDashboardScreen(
    onCraving: () -> Unit,
    onLog: () -> Unit,
    onCoach: () -> Unit,
    onTabSelected: (TabItem) -> Unit,
    vm: QuitlyViewModel = viewModel()
) {
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
            Spacer(Modifier.height(16.dp))

            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${greeting()},",
                        fontFamily = Figtree,
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                    Text(
                        text = vm.userName,
                        fontFamily = Caprasimo,
                        fontSize = 27.sp,
                        color = TextDark
                    )
                }
                BellIcon()
            }

            Spacer(Modifier.height(20.dp))

            // Streak + stats card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(SandCard)
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    QuitlyStreakRing(
                        progress = (vm.streakDays % 30) / 30f,
                        number = vm.streakDays.toString(),
                        size = 108.dp,
                        numberSize = 34.sp,
                        strokeWidth = 9.dp
                    )
                    Spacer(Modifier.size(16.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        QuitlyStat(
                            value = vm.cigarettesNotSmoked.toString(),
                            label = "cigarettes not smoked",
                            centered = false
                        )
                        QuitlyStat(
                            value = "$${formatMoney(vm.moneySaved)}",
                            label = "saved so far",
                            centered = false
                        )
                        QuitlyStat(
                            value = "${vm.hoursReclaimed}h",
                            label = "life reclaimed",
                            centered = false
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // CRAVING hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Terracotta)
                    .clickable { onCraving() }
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "I'm craving right now",
                            fontFamily = Caprasimo,
                            fontSize = 20.sp,
                            color = GroundCream
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Breathe with me for 60 seconds",
                            fontFamily = Figtree,
                            fontSize = 13.sp,
                            color = GroundCream.copy(alpha = 0.85f)
                        )
                    }
                    WaveGlyph()
                }
            }

            Spacer(Modifier.height(14.dp))

            // Quick action tiles
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                QuickTile(
                    title = "Log a moment",
                    sub = "It happened, note it",
                    bg = Terracotta100,
                    tint = AccentDeep,
                    modifier = Modifier.weight(1f),
                    onClick = onLog
                )
                QuickTile(
                    title = "Talk to coach",
                    sub = "No judgment, ever",
                    bg = Sage100,
                    tint = Sage900,
                    modifier = Modifier.weight(1f),
                    onClick = onCoach
                )
            }

            Spacer(Modifier.height(16.dp))

            // For you today
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Sage100)
                    .padding(18.dp)
            ) {
                Column {
                    Text(
                        text = "FOR YOU TODAY",
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        letterSpacing = 1.2.sp,
                        color = Sage
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Your 3pm coffee trigger usually peaks about now. Want a 2-minute reset?",
                        fontFamily = Figtree,
                        fontSize = 14.sp,
                        lineHeight = 19.sp,
                        color = Sage900
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        QuitlyTabBar(active = TabItem.HOME, onTabSelected = onTabSelected)
    }
}

@Composable
private fun QuickTile(
    title: String,
    sub: String,
    bg: androidx.compose.ui.graphics.Color,
    tint: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(110.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            Text(
                text = title,
                fontFamily = Caprasimo,
                fontSize = 15.sp,
                color = tint
            )
            Text(
                text = sub,
                fontFamily = Figtree,
                fontSize = 11.5.sp,
                color = tint.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun BellIcon() {
    Canvas(modifier = Modifier.size(24.dp)) {
        val w = size.width; val h = size.height
        val stroke = 2.3.dp.toPx()
        // Bell body
        drawArc(
            color = TextDark,
            startAngle = 180f, sweepAngle = 180f, useCenter = false,
            topLeft = Offset(w * 0.2f, h * 0.5f),
            size = androidx.compose.ui.geometry.Size(w * 0.6f, h * 0.35f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(stroke, cap = StrokeCap.Round)
        )
        drawLine(TextDark, Offset(w * 0.2f, h * 0.675f), Offset(w * 0.2f, h * 0.72f), stroke, StrokeCap.Round)
        drawLine(TextDark, Offset(w * 0.8f, h * 0.675f), Offset(w * 0.8f, h * 0.72f), stroke, StrokeCap.Round)
        drawLine(TextDark, Offset(w * 0.15f, h * 0.72f), Offset(w * 0.85f, h * 0.72f), stroke, StrokeCap.Round)
        // Clapper
        drawLine(TextDark, Offset(w * 0.42f, h * 0.85f), Offset(w * 0.58f, h * 0.85f), stroke, StrokeCap.Round)
    }
}

@Composable
private fun WaveGlyph() {
    Canvas(modifier = Modifier.size(28.dp)) {
        val w = size.width; val h = size.height
        val stroke = 2.5.dp.toPx()
        for (i in 0..2) {
            val x = w * (0.2f + i * 0.3f)
            drawLine(
                color = GroundCream,
                start = Offset(x, h * 0.3f),
                end = Offset(x, h * 0.7f),
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )
        }
    }
}

private fun greeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when {
        hour < 12 -> "Good morning"
        hour < 17 -> "Good afternoon"
        else -> "Good evening"
    }
}

private fun formatMoney(v: Double): String {
    val cents = (v * 100).toLong()
    val whole = cents / 100
    val frac = (cents % 100).toInt()
    return "$whole.${frac.toString().padStart(2, '0')}"
}