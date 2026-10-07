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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.components.QuitlyChip
import com.example.quitlyaifirst2screens.components.QuitlyKicker
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.data.CommunityPost
import com.example.quitlyaifirst2screens.data.SampleData
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Neutral500
import com.example.quitlyaifirst2screens.ui.theme.Sage100
import com.example.quitlyaifirst2screens.ui.theme.Sage900
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.Terracotta100
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted

private val groups = listOf("All", "Day 1–7", "Week 2+", "Month 3+", "Long-term")

@Composable
fun CommunityScreen(onBack: () -> Unit) {
    var activeGroup by remember { mutableStateOf("All") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            QuitlyTopBar(
                title = "",
                onLeadClick = onBack,
                lead = TopBarLead.BACK
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                QuitlyKicker("You're not alone")
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Your circle",
                    fontFamily = Caprasimo,
                    fontSize = 31.sp,
                    color = TextDark
                )
                Spacer(Modifier.height(16.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    groups.forEach { g ->
                        QuitlyChip(
                            label = g,
                            selected = activeGroup == g,
                            onClick = { activeGroup = g }
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                SampleData.communityPosts.forEach { post ->
                    PostCard(post)
                    Spacer(Modifier.height(12.dp))
                }

                Spacer(Modifier.height(80.dp))
            }
        }

        // Compose FAB
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 24.dp)
                .size(52.dp)
                .clip(CircleShape)
                .background(Terracotta)
                .clickable { /* compose not yet implemented */ },
            contentAlignment = Alignment.Center
        ) {
            PlusGlyph()
        }
    }
}

@Composable
private fun PostCard(post: CommunityPost) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(SandCard)
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Avatar(initial = post.authorName.first().toString())
                Spacer(Modifier.size(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = post.authorName,
                        fontFamily = Caprasimo,
                        fontSize = 14.sp,
                        color = TextDark
                    )
                    Text(
                        text = post.dayBadge,
                        fontFamily = Figtree,
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Sage100)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Supportive",
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp,
                        color = Sage900
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = post.body,
                fontFamily = Figtree,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = TextDark
            )
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                ActionIcon(label = post.likes.toString(), glyph = "♥")
                Spacer(Modifier.size(16.dp))
                ActionIcon(label = post.comments.toString(), glyph = "💬")
            }
        }
    }
}

@Composable
private fun Avatar(initial: String) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(Terracotta100),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            fontFamily = Caprasimo,
            fontSize = 14.sp,
            color = Terracotta
        )
    }
}

@Composable
private fun ActionIcon(label: String, glyph: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = glyph, fontSize = 13.sp)
        Spacer(Modifier.size(4.dp))
        Text(
            text = label,
            fontFamily = Figtree,
            fontSize = 12.sp,
            color = TextMuted
        )
    }
}

@Composable
private fun PlusGlyph() {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width; val h = size.height
        val stroke = 2.8.dp.toPx()
        drawLine(GroundCream, Offset(w * 0.5f, h * 0.15f), Offset(w * 0.5f, h * 0.85f), stroke, StrokeCap.Round)
        drawLine(GroundCream, Offset(w * 0.15f, h * 0.5f), Offset(w * 0.85f, h * 0.5f), stroke, StrokeCap.Round)
    }
}

