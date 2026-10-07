package com.example.quitlyaifirst2screens.screens

import androidx.compose.foundation.Canvas
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
import com.example.quitlyaifirst2screens.components.QuitlyButton
import com.example.quitlyaifirst2screens.components.QuitlyChip
import com.example.quitlyaifirst2screens.components.QuitlyKicker
import com.example.quitlyaifirst2screens.components.QuitlyProgressBar
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.ui.theme.AccentDeep
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Sage
import com.example.quitlyaifirst2screens.ui.theme.Sage100
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.Terracotta100
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted
import com.example.quitlyaifirst2screens.viewmodel.QuitlyViewModel

@Composable
fun HabitAssessmentScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit,
    vm: QuitlyViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream)
    ) {
        QuitlyTopBar(title = "", onLeadClick = onBack, lead = TopBarLead.BACK)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                QuitlyKicker("Step 4 of 5")
                Spacer(Modifier.weight(1f))
                Text("4/5", fontFamily = Figtree, fontSize = 12.sp, color = TextMuted)
            }
            Spacer(Modifier.height(10.dp))
            QuitlyProgressBar(current = 4, total = 5)
            Spacer(Modifier.height(24.dp))

            // Coach avatar + bubble
            Row(verticalAlignment = Alignment.Top) {
                CoachAvatar()
                Spacer(Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp))
                        .background(SandCard)
                        .padding(14.dp)
                ) {
                    Text(
                        text = "No judgment here — this just helps me shape your plan. What usually sets off a craving?",
                        fontFamily = Figtree,
                        fontSize = 14.sp,
                        lineHeight = 19.sp,
                        color = TextDark
                    )
                }
            }
            Spacer(Modifier.height(24.dp))

            QuitlyKicker("Triggers")
            Spacer(Modifier.height(10.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                vm.triggers.forEach { trigger ->
                    QuitlyChip(
                        label = trigger.label,
                        selected = trigger.isSelected,
                        onClick = { vm.toggleTrigger(trigger.id) }
                    )
                }
            }
            Spacer(Modifier.height(28.dp))

            QuitlyKicker("Cigarettes per day")
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(SandCard)
                    .padding(vertical = 18.dp, horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StepperButton(symbol = "−", onClick = { vm.decrementCigs() })
                    Text(
                        text = vm.cigsPerDay.toString(),
                        fontFamily = Caprasimo,
                        fontSize = 40.sp,
                        color = AccentDeep
                    )
                    StepperButton(symbol = "+", onClick = { vm.incrementCigs() })
                }
            }
            Spacer(Modifier.height(20.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            QuitlyButton(
                text = "Next question",
                onClick = onContinue,
                enabled = vm.selectedTriggerCount >= 1
            )
        }
    }
}

@Composable
private fun CoachAvatar() {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Sage100),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(20.dp)) {
            val w = size.width; val h = size.height
            val stroke = 2.dp.toPx()
            // Simple leaf/sprout glyph
            drawLine(Sage, Offset(w * 0.5f, h * 0.85f), Offset(w * 0.5f, h * 0.35f), stroke, StrokeCap.Round)
            drawArc(
                color = Sage,
                startAngle = 180f, sweepAngle = 180f, useCenter = false,
                topLeft = Offset(w * 0.1f, h * 0.15f),
                size = androidx.compose.ui.geometry.Size(w * 0.4f, h * 0.4f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(stroke)
            )
            drawArc(
                color = Sage,
                startAngle = 0f, sweepAngle = 180f, useCenter = false,
                topLeft = Offset(w * 0.5f, h * 0.15f),
                size = androidx.compose.ui.geometry.Size(w * 0.4f, h * 0.4f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(stroke)
            )
        }
    }
}

@Composable
private fun StepperButton(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Terracotta100)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            fontFamily = Caprasimo,
            fontSize = 22.sp,
            color = Terracotta
        )
    }
}