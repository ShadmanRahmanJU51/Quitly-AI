package com.example.quitlyaifirst2screens.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitlyaifirst2screens.components.QuitlyButton
import com.example.quitlyaifirst2screens.components.QuitlyChip
import com.example.quitlyaifirst2screens.components.QuitlyKicker
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.data.LogEntry
import com.example.quitlyaifirst2screens.data.LogOutcome
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Neutral200
import com.example.quitlyaifirst2screens.ui.theme.Sage
import com.example.quitlyaifirst2screens.ui.theme.Sage100
import com.example.quitlyaifirst2screens.ui.theme.Sage900
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted
import com.example.quitlyaifirst2screens.viewmodel.QuitlyViewModel

@Composable
fun HabitLogScreen(
    onClose: () -> Unit,
    onSaved: () -> Unit,
    prefilledOutcome: LogOutcome? = null,
    vm: QuitlyViewModel = viewModel()
) {
    var outcome by remember {
        mutableStateOf(prefilledOutcome ?: LogOutcome.RESISTED)
    }
    var intensity by remember { mutableIntStateOf(5) }
    var note by remember { mutableStateOf("") }
    val selectedTriggers = remember { mutableStateOf(setOf<String>()) }

    // If SOS passed in an outcome, apply it once
    LaunchedEffect(prefilledOutcome) {
        prefilledOutcome?.let { outcome = it }
    }

    val valid = selectedTriggers.value.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream)
    ) {
        QuitlyTopBar(
            title = "Log a moment",
            onLeadClick = onClose,
            lead = TopBarLead.CLOSE,
            trailingText = "Save",
            trailingEnabled = valid,
            onTrailingClick = {
                vm.addLog(
                    LogEntry(
                        outcome = outcome,
                        trigger = selectedTriggers.value.joinToString(", "),
                        intensity = intensity,
                        note = note
                    )
                )
                onSaved()
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // Outcome toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Neutral200)
                    .padding(4.dp)
            ) {
                OutcomePill(
                    label = "Resisted",
                    active = outcome == LogOutcome.RESISTED,
                    accentSage = true,
                    modifier = Modifier.weight(1f),
                    onClick = { outcome = LogOutcome.RESISTED }
                )
                OutcomePill(
                    label = "Smoked",
                    active = outcome == LogOutcome.SMOKED,
                    accentSage = false,
                    modifier = Modifier.weight(1f),
                    onClick = { outcome = LogOutcome.SMOKED }
                )
            }

            Spacer(Modifier.height(12.dp))
            Text(
                text = if (outcome == LogOutcome.RESISTED)
                    "You chose you. Let's note what helped."
                else
                    "It's just data. Let's see what was happening.",
                fontFamily = Figtree,
                fontSize = 13.5.sp,
                color = TextMuted
            )

            Spacer(Modifier.height(24.dp))
            QuitlyKicker("What was happening?")
            Spacer(Modifier.height(10.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                vm.triggers.forEach { trigger ->
                    val sel = trigger.label in selectedTriggers.value
                    QuitlyChip(
                        label = trigger.label,
                        selected = sel,
                        onClick = {
                            selectedTriggers.value = if (sel)
                                selectedTriggers.value - trigger.label
                            else selectedTriggers.value + trigger.label
                        }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                QuitlyKicker("Craving intensity")
                Spacer(Modifier.weight(1f))
                Text(
                    text = "$intensity / 10",
                    fontFamily = Caprasimo,
                    fontSize = 15.sp,
                    color = Terracotta
                )
            }
            Spacer(Modifier.height(6.dp))
            Slider(
                value = intensity.toFloat(),
                onValueChange = { intensity = it.toInt() },
                valueRange = 0f..10f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = Terracotta,
                    activeTrackColor = Terracotta,
                    inactiveTrackColor = Neutral200
                )
            )

            Spacer(Modifier.height(20.dp))
            QuitlyKicker("A note to yourself (optional)")
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                placeholder = {
                    Text(
                        "What helped, what didn't...",
                        fontFamily = Figtree,
                        fontSize = 13.5.sp,
                        color = TextMuted
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Terracotta,
                    unfocusedBorderColor = Neutral200,
                    focusedContainerColor = SandCard,
                    unfocusedContainerColor = SandCard
                )
            )

            Spacer(Modifier.height(20.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            QuitlyButton(
                text = "Save this moment",
                onClick = {
                    vm.addLog(
                        LogEntry(
                            outcome = outcome,
                            trigger = selectedTriggers.value.joinToString(", "),
                            intensity = intensity,
                            note = note
                        )
                    )
                    onSaved()
                },
                enabled = valid
            )
        }
    }
}

@Composable
private fun OutcomePill(
    label: String,
    active: Boolean,
    accentSage: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = when {
        active && accentSage -> Sage
        active && !accentSage -> Terracotta
        else -> androidx.compose.ui.graphics.Color.Transparent
    }
    val txtColor = when {
        active -> GroundCream
        else -> TextMuted
    }
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(50))
            .background(bg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = Figtree,
            fontWeight = if (active) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 14.sp,
            color = txtColor
        )
    }
}