package com.example.quitlyaifirst2screens.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.ui.theme.AccentDeep
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Neutral200
import com.example.quitlyaifirst2screens.ui.theme.Neutral400
import com.example.quitlyaifirst2screens.ui.theme.Neutral500
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted

@Composable
fun AdaptiveNudgesScreen(onBack: () -> Unit) {
    var master by remember { mutableStateOf(true) }
    var craving by remember { mutableStateOf(true) }
    var daily by remember { mutableStateOf(true) }
    var milestones by remember { mutableStateOf(true) }
    var money by remember { mutableStateOf(false) }
    var tone by remember { mutableStateOf("Gentle & warm") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream)
    ) {
        QuitlyTopBar(
            title = "Nudges",
            onLeadClick = onBack,
            lead = TopBarLead.BACK
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Smart nudges",
                fontFamily = Caprasimo,
                fontSize = 31.sp,
                color = TextDark
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Quitly learns when you're most at risk and steps in gently — never preachy.",
                fontFamily = Figtree,
                fontSize = 14.sp,
                lineHeight = 19.sp,
                color = TextMuted
            )
            Spacer(Modifier.height(20.dp))

            // Master toggle card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Terracotta)
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Smart nudges",
                            fontFamily = Caprasimo,
                            fontSize = 18.sp,
                            color = GroundCream
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = if (master) "On — learning your patterns" else "Off — no nudges will be sent",
                            fontFamily = Figtree,
                            fontSize = 12.sp,
                            color = GroundCream.copy(alpha = 0.85f)
                        )
                    }
                    Switch(
                        checked = master,
                        onCheckedChange = { master = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = GroundCream,
                            checkedTrackColor = Terracotta.copy(alpha = 0.35f),
                            uncheckedThumbColor = GroundCream,
                            uncheckedTrackColor = Terracotta.copy(alpha = 0.35f)
                        )
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            SectionLabel("NUDGE TYPES")
            Spacer(Modifier.height(10.dp))

            NudgeToggleRow(
                title = "Craving-risk alerts",
                subtitle = "before your Wed 3pm spike",
                checked = craving && master,
                enabled = master,
                onChange = { craving = it }
            )
            NudgeToggleRow(
                title = "Daily check-in",
                subtitle = "one warm nudge each morning",
                checked = daily && master,
                enabled = master,
                onChange = { daily = it }
            )
            NudgeToggleRow(
                title = "Milestones",
                subtitle = "when you hit a new badge",
                checked = milestones && master,
                enabled = master,
                onChange = { milestones = it }
            )
            NudgeToggleRow(
                title = "Money updates",
                subtitle = "your savings, in real numbers",
                checked = money && master,
                enabled = master,
                onChange = { money = it }
            )

            Spacer(Modifier.height(24.dp))
            SectionLabel("TONE")
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Neutral200)
                    .padding(4.dp)
            ) {
                TonePill("Gentle & warm", tone, Modifier.weight(1f)) { tone = "Gentle & warm" }
                TonePill("Direct", tone, Modifier.weight(1f)) { tone = "Direct" }
            }

            Spacer(Modifier.height(24.dp))
            SectionLabel("QUIET HOURS")
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(SandCard)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Don't nudge me between",
                        fontFamily = Figtree,
                        fontSize = 14.sp,
                        color = TextDark
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "22:00 – 07:00",
                        fontFamily = Caprasimo,
                        fontSize = 16.sp,
                        color = AccentDeep
                    )
                }
                Text(
                    text = "Change",
                    fontFamily = Caprasimo,
                    fontSize = 13.sp,
                    color = AccentDeep,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .clickable { /* time picker deferred */ }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        letterSpacing = 1.2.sp,
        color = AccentDeep
    )
}

@Composable
private fun NudgeToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    enabled: Boolean,
    onChange: (Boolean) -> Unit
) {
    val rowBg = if (checked) SandCard else GroundCream
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(rowBg)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontFamily = Caprasimo,
                    fontSize = 15.sp,
                    color = if (enabled) TextDark else Neutral500
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontFamily = Figtree,
                    fontSize = 12.sp,
                    color = Neutral500
                )
            }
            Switch(
                checked = checked,
                enabled = enabled,
                onCheckedChange = onChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = GroundCream,
                    checkedTrackColor = Terracotta,
                    uncheckedThumbColor = GroundCream,
                    uncheckedTrackColor = Neutral400,
                    disabledCheckedTrackColor = Terracotta.copy(alpha = 0.4f),
                    disabledUncheckedTrackColor = Neutral400.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Composable
private fun TonePill(
    label: String,
    active: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isActive = active == label
    Box(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(50))
            .background(if (isActive) GroundCream else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = Figtree,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 13.sp,
            color = if (isActive) TextDark else TextMuted
        )
    }
}