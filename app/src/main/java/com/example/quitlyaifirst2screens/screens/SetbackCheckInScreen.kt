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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitlyaifirst2screens.components.QuitlyButton
import com.example.quitlyaifirst2screens.components.QuitlyChip
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Sage
import com.example.quitlyaifirst2screens.ui.theme.Sage100
import com.example.quitlyaifirst2screens.ui.theme.Sage900
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted
import com.example.quitlyaifirst2screens.viewmodel.QuitlyViewModel

@Composable
fun SetbackCheckInScreen(
    onClose: () -> Unit,
    onKeepStreak: () -> Unit,
    onTalkToCoach: () -> Unit,
    vm: QuitlyViewModel = viewModel()
) {
    val selectedCause = remember { mutableStateOf<Set<String>>(emptySet()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream)
    ) {
        QuitlyTopBar(
            title = "",
            onLeadClick = onClose,
            lead = TopBarLead.CLOSE
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            SproutBadge()
            Spacer(Modifier.height(20.dp))

            Text(
                text = "Welcome back, ${vm.userName}.\nYou're not starting over.",
                fontFamily = Caprasimo,
                fontSize = 27.sp,
                lineHeight = 32.sp,
                color = TextDark,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "One moment doesn't erase the work you've done. Let's look at what was happening, so it's easier next time.",
                fontFamily = Figtree,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = TextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            // Reframe card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Sage100)
                    .padding(20.dp)
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "REFRAME",
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        letterSpacing = 1.2.sp,
                        color = Sage
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "You still chose not to smoke\n${vm.cigarettesNotSmoked} times.",
                        fontFamily = Caprasimo,
                        fontSize = 20.sp,
                        lineHeight = 25.sp,
                        color = Sage900
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "One slip is a single data point — not the whole graph.",
                        fontFamily = Figtree,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = Sage900
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Cause chips
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "WHAT SET IT OFF?",
                    fontFamily = Figtree,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    letterSpacing = 1.2.sp,
                    color = Sage
                )
                Spacer(Modifier.height(10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    vm.triggers.forEach { trigger ->
                        val sel = trigger.label in selectedCause.value
                        QuitlyChip(
                            label = trigger.label,
                            selected = sel,
                            accent = "sage",
                            onClick = {
                                selectedCause.value = if (sel)
                                    selectedCause.value - trigger.label
                                else selectedCause.value + trigger.label
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        // Actions
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuitlyButton(
                text = "Keep my streak & carry on",
                onClick = onKeepStreak
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable { onTalkToCoach() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Talk it through with my coach",
                    fontFamily = Caprasimo,
                    fontSize = 15.sp,
                    color = Sage
                )
            }
        }
    }
}

@Composable
private fun SproutBadge() {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(Sage100),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(40.dp)) {
            val w = size.width; val h = size.height
            val stroke = 2.8.dp.toPx()
            // Stem
            drawLine(Sage, Offset(w * 0.5f, h * 0.9f), Offset(w * 0.5f, h * 0.35f), stroke, StrokeCap.Round)
            // Left leaf
            drawArc(
                color = Sage,
                startAngle = 180f, sweepAngle = 180f, useCenter = false,
                topLeft = Offset(w * 0.06f, h * 0.14f),
                size = androidx.compose.ui.geometry.Size(w * 0.44f, h * 0.42f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(stroke, cap = StrokeCap.Round)
            )
            // Right leaf
            drawArc(
                color = Sage,
                startAngle = 0f, sweepAngle = 180f, useCenter = false,
                topLeft = Offset(w * 0.5f, h * 0.14f),
                size = androidx.compose.ui.geometry.Size(w * 0.44f, h * 0.42f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(stroke, cap = StrokeCap.Round)
            )
        }
    }
}