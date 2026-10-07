package com.example.quitlyaifirst2screens.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitlyaifirst2screens.components.QuitlyButton
import com.example.quitlyaifirst2screens.components.QuitlyKicker
import com.example.quitlyaifirst2screens.components.QuitlyProgressBar
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.Terracotta100
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted
import com.example.quitlyaifirst2screens.viewmodel.QuitlyViewModel

@Composable
fun PreferenceSurveyScreen(
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
                QuitlyKicker("Step 2 of 5")
                Spacer(Modifier.weight(1f))
                Text(
                    text = "2/5",
                    fontFamily = Figtree,
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
            Spacer(Modifier.height(10.dp))
            QuitlyProgressBar(current = 2, total = 5)
            Spacer(Modifier.height(24.dp))

            Text(
                text = "What are you quitting for?",
                fontFamily = Caprasimo,
                fontSize = 31.sp,
                lineHeight = 36.sp,
                color = TextDark
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Pick the ones that matter most. You can change these later.",
                fontFamily = Figtree,
                fontSize = 14.5.sp,
                color = TextMuted
            )
            Spacer(Modifier.height(20.dp))

            // 2-column grid (manual, 6 items)
            vm.reasons.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { reason ->
                        ReasonCard(
                            label = reason.label,
                            emoji = reason.emoji,
                            selected = reason.isSelected,
                            modifier = Modifier.weight(1f),
                            onClick = { vm.toggleReason(reason.id) }
                        )
                    }
                    if (rowItems.size < 2) Spacer(Modifier.weight(1f))
                }
            }
        }

        // Sticky CTA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            QuitlyButton(
                text = if (vm.selectedReasonCount == 0) "Continue"
                else "Continue · ${vm.selectedReasonCount} picked",
                onClick = onContinue,
                enabled = vm.selectedReasonCount >= 1
            )
        }
    }
}

@Composable
private fun ReasonCard(
    label: String,
    emoji: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1.15f)
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) Terracotta100 else SandCard)
            .border(
                width = 2.dp,
                color = if (selected) Terracotta else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, fontSize = 30.sp)
            Spacer(Modifier.height(6.dp))
            Text(
                text = label,
                fontFamily = Figtree,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                fontSize = 13.sp,
                lineHeight = 16.sp,
                color = TextDark,
                textAlign = TextAlign.Center
            )
        }
        if (selected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(Terracotta),
                contentAlignment = Alignment.Center
            ) {
                CheckGlyph()
            }
        }
    }
}

@Composable
private fun CheckGlyph() {
    Canvas(modifier = Modifier.size(12.dp)) {
        val w = size.width
        val h = size.height
        val s = 2.2.dp.toPx()
        drawLine(GroundCream, Offset(w * 0.15f, h * 0.55f), Offset(w * 0.42f, h * 0.8f), s, StrokeCap.Round)
        drawLine(GroundCream, Offset(w * 0.42f, h * 0.8f), Offset(w * 0.85f, h * 0.2f), s, StrokeCap.Round)
    }
}