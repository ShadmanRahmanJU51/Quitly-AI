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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.data.ChatMessage
import com.example.quitlyaifirst2screens.ui.theme.AccentDeep
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Neutral200
import com.example.quitlyaifirst2screens.ui.theme.Neutral500
import com.example.quitlyaifirst2screens.ui.theme.Sage
import com.example.quitlyaifirst2screens.ui.theme.Sage100
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted
import com.example.quitlyaifirst2screens.viewmodel.QuitlyViewModel

@Composable
fun CoachChatScreen(
    onBack: () -> Unit,
    vm: QuitlyViewModel = viewModel()
) {
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Auto-scroll to newest message
    LaunchedEffect(vm.chatMessages.size, vm.isAiLoading) {
        if (vm.chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(vm.chatMessages.size + if (vm.isAiLoading) 1 else 0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream)
            .imePadding()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuitlyTopBar(
                title = "",
                onLeadClick = onBack,
                lead = TopBarLead.BACK,
                modifier = Modifier.widthIn(max = 56.dp)
            )
            CoachAvatar()
            Spacer(Modifier.size(10.dp))
            Column {
                Text(
                    text = "Quitly Coach",
                    fontFamily = Caprasimo,
                    fontSize = 16.sp,
                    color = TextDark
                )
                Text(
                    text = "Always here",
                    fontFamily = Figtree,
                    fontSize = 11.5.sp,
                    color = Sage
                )
            }
            Spacer(Modifier.weight(1f))
            Spacer(Modifier.size(12.dp))
        }

        // Messages
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (vm.chatMessages.isEmpty()) {
                item { IntroCard(onPick = { vm.sendChatMessage(it) }) }
            }
            items(vm.chatMessages, key = { it.id }) { msg ->
                MessageBubble(msg)
            }
            if (vm.isAiLoading) {
                item { TypingBubble() }
            }
        }

        // Suggested replies (only when idle)
        if (!vm.isAiLoading && vm.chatMessages.isNotEmpty()) {
            val suggestions = listOf(
                "I'm having a rough day",
                "Give me a 2-minute reset",
                "I slipped last night"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                suggestions.take(2).forEach { s ->
                    SuggestionChip(text = s, onClick = { vm.sendChatMessage(s) })
                }
            }
        }

        // Composer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .background(SandCard)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                BasicTextField(
                    value = input,
                    onValueChange = { input = it },
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = Figtree,
                        fontSize = 14.sp,
                        color = TextDark
                    ),
                    cursorBrush = SolidColor(Terracotta),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { inner ->
                        if (input.isEmpty()) {
                            Text(
                                text = "Say anything...",
                                fontFamily = Figtree,
                                fontSize = 14.sp,
                                color = Neutral500
                            )
                        }
                        inner()
                    }
                )
            }
            Spacer(Modifier.size(10.dp))
            SendButton(
                enabled = input.isNotBlank() && !vm.isAiLoading,
                onClick = {
                    vm.sendChatMessage(input.trim())
                    input = ""
                }
            )
        }
    }
}

@Composable
private fun IntroCard(onPick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Sage100)
            .padding(18.dp)
    ) {
        Column {
            Text(
                text = "Hey — I'm your coach.",
                fontFamily = Caprasimo,
                fontSize = 17.sp,
                color = TextDark
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "No lectures, no judgment. Tell me what's going on, or pick one below to start.",
                fontFamily = Figtree,
                fontSize = 13.5.sp,
                lineHeight = 19.sp,
                color = TextDark
            )
            Spacer(Modifier.height(12.dp))
            onPick("I'm craving right now")
        }
    }
}

@Composable
private fun MessageBubble(msg: ChatMessage) {
    val isUser = msg.isUser
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = if (isUser) 20.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 20.dp
                    )
                )
                .background(if (isUser) Terracotta else SandCard)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = msg.text,
                fontFamily = Figtree,
                fontSize = 14.sp,
                lineHeight = 19.sp,
                color = if (isUser) GroundCream else TextDark
            )
        }
    }
}

@Composable
private fun TypingBubble() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 4.dp, bottomEnd = 20.dp))
                .background(SandCard)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "● ● ●",
                fontFamily = Figtree,
                fontSize = 12.sp,
                color = Neutral500
            )
        }
    }
}

@Composable
private fun SuggestionChip(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(SandCard)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            fontFamily = Figtree,
            fontSize = 12.sp,
            color = AccentDeep
        )
    }
}

@Composable
private fun CoachAvatar() {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(Sage100),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(18.dp)) {
            val w = size.width; val h = size.height
            val stroke = 2.dp.toPx()
            drawLine(Sage, Offset(w * 0.5f, h * 0.85f), Offset(w * 0.5f, h * 0.35f), stroke, StrokeCap.Round)
            drawArc(
                color = Sage, startAngle = 180f, sweepAngle = 180f, useCenter = false,
                topLeft = Offset(w * 0.1f, h * 0.15f),
                size = androidx.compose.ui.geometry.Size(w * 0.4f, h * 0.4f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(stroke)
            )
            drawArc(
                color = Sage, startAngle = 0f, sweepAngle = 180f, useCenter = false,
                topLeft = Offset(w * 0.5f, h * 0.15f),
                size = androidx.compose.ui.geometry.Size(w * 0.4f, h * 0.4f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(stroke)
            )
        }
    }
}

@Composable
private fun SendButton(enabled: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (enabled) Terracotta else Neutral200)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(20.dp)) {
            val w = size.width; val h = size.height
            val stroke = 2.4.dp.toPx()
            // Up-arrow / send glyph
            drawLine(GroundCream, Offset(w * 0.5f, h * 0.8f), Offset(w * 0.5f, h * 0.2f), stroke, StrokeCap.Round)
            drawLine(GroundCream, Offset(w * 0.25f, h * 0.45f), Offset(w * 0.5f, h * 0.2f), stroke, StrokeCap.Round)
            drawLine(GroundCream, Offset(w * 0.75f, h * 0.45f), Offset(w * 0.5f, h * 0.2f), stroke, StrokeCap.Round)
        }
    }
}