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
import com.example.quitlyaifirst2screens.components.QuitlyTabBar
import com.example.quitlyaifirst2screens.components.QuitlyStat
import com.example.quitlyaifirst2screens.components.TabItem
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
fun ProfileSettingsScreen(
    onOpenNudges: () -> Unit,
    onOpenSurvey: () -> Unit,
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
            Spacer(Modifier.height(24.dp))

            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Terracotta100),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = vm.userName.first().toString(),
                        fontFamily = Caprasimo,
                        fontSize = 24.sp,
                        color = Terracotta
                    )
                }
                Spacer(Modifier.size(14.dp))
                Column {
                    Text(
                        text = vm.userName,
                        fontFamily = Caprasimo,
                        fontSize = 22.sp,
                        color = TextDark
                    )
                    Text(
                        text = "Smoke-free since Jul 11, 2026",
                        fontFamily = Figtree,
                        fontSize = 12.5.sp,
                        color = TextMuted
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Quick stats
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(SandCard)
                    .padding(vertical = 18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    QuitlyStat(
                        value = "${vm.streakDays}",
                        label = "day streak"
                    )
                    QuitlyStat(
                        value = "$${fmt(vm.moneySaved)}",
                        label = "saved"
                    )
                    QuitlyStat(
                        value = "${vm.milestones.count { it.isEarned }}",
                        label = "badges"
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // My reasons
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Sage100)
                    .padding(18.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "MY REASONS",
                            fontFamily = Figtree,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            letterSpacing = 1.2.sp,
                            color = Sage,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Edit",
                            fontFamily = Caprasimo,
                            fontSize = 13.sp,
                            color = AccentDeep,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .clickable { onOpenSurvey() }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    val picked = vm.reasons.filter { it.isSelected }
                    if (picked.isEmpty()) {
                        Text(
                            text = "No reasons picked yet — tap Edit to choose.",
                            fontFamily = Figtree,
                            fontSize = 13.sp,
                            color = Sage900
                        )
                    } else {
                        picked.forEach { r ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = r.emoji, fontSize = 15.sp)
                                Spacer(Modifier.size(8.dp))
                                Text(
                                    text = r.label,
                                    fontFamily = Figtree,
                                    fontSize = 13.5.sp,
                                    color = Sage900
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            SettingsRow(
                title = "Quit plan",
                subtitle = "Start date, cigarettes/day, reasons",
                onClick = onOpenSurvey
            )
            SettingsRow(
                title = "Coach personality",
                subtitle = "Gentle, direct, or a mix",
                onClick = { /* deferred */ }
            )
            SettingsRow(
                title = "Nudges & reminders",
                subtitle = "Craving alerts, check-ins, quiet hours",
                onClick = onOpenNudges
            )
            SettingsRow(
                title = "Privacy & health data",
                subtitle = "What Quitly stores and where",
                onClick = { /* deferred */ }
            )

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable { /* sign out deferred */ },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sign out",
                    fontFamily = Caprasimo,
                    fontSize = 15.sp,
                    color = Terracotta
                )
            }

            Spacer(Modifier.height(24.dp))
        }

        QuitlyTabBar(active = TabItem.YOU, onTabSelected = onTabSelected)
    }
}

@Composable
private fun SettingsRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(SandCard)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = Caprasimo,
                fontSize = 15.sp,
                color = TextDark
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontFamily = Figtree,
                fontSize = 12.sp,
                color = Neutral500
            )
        }
        ChevronGlyph()
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun ChevronGlyph() {
    Canvas(modifier = Modifier.size(16.dp)) {
        val w = size.width; val h = size.height
        val stroke = 2.dp.toPx()
        drawLine(Neutral500, Offset(w * 0.35f, h * 0.2f), Offset(w * 0.7f, h * 0.5f), stroke, StrokeCap.Round)
        drawLine(Neutral500, Offset(w * 0.7f, h * 0.5f), Offset(w * 0.35f, h * 0.8f), stroke, StrokeCap.Round)
    }
}

private fun fmt(v: Double): String {
    val cents = (v * 100).toLong()
    val whole = cents / 100
    val frac = (cents % 100).toInt()
    return "$whole.${frac.toString().padStart(2, '0')}"
}