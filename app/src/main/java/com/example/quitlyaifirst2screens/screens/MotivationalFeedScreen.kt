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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitlyaifirst2screens.components.QuitlyKicker
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.data.FeedKind
import com.example.quitlyaifirst2screens.data.SampleData
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

@Composable
fun MotivationalFeedScreen(
    onBack: () -> Unit,
    onOpenCommunity: () -> Unit,
    vm: QuitlyViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream)
    ) {
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
            QuitlyKicker("Curated for ${vm.userName}")
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Your feed",
                fontFamily = Caprasimo,
                fontSize = 31.sp,
                color = TextDark
            )
            Spacer(Modifier.height(20.dp))

            SampleData.feedItems.forEach { item ->
                when (item.kind) {
                    FeedKind.HERO -> HeroCard(title = item.title, body = item.body, tag = item.tag)
                    FeedKind.FACT -> FactCard(title = item.title, body = item.body)
                    FeedKind.STORY -> StoryCard(title = item.title, body = item.body, tag = item.tag)
                    FeedKind.MONEY -> MoneyCard(title = item.title, body = item.body)
                }
                Spacer(Modifier.height(14.dp))
            }

            // Community link (Flow G branch)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Terracotta100)
                    .clickable { onOpenCommunity() }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "See what others are doing →",
                    fontFamily = Caprasimo,
                    fontSize = 14.sp,
                    color = AccentDeep
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HeroCard(title: String, body: String, tag: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(SandCard)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Terracotta)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = tag.uppercase(),
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp,
                        letterSpacing = 1.2.sp,
                        color = GroundCream
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = title,
                fontFamily = Caprasimo,
                fontSize = 22.sp,
                lineHeight = 26.sp,
                color = TextDark
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = body,
                fontFamily = Figtree,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = TextMuted
            )
            Spacer(Modifier.height(14.dp))
            IlloHero()
        }
    }
}

@Composable
private fun FactCard(title: String, body: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Terracotta)
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = "DID YOU KNOW",
                fontFamily = Figtree,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                letterSpacing = 1.2.sp,
                color = GroundCream.copy(alpha = 0.75f)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = title,
                fontFamily = Caprasimo,
                fontSize = 19.sp,
                lineHeight = 23.sp,
                color = GroundCream
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = body,
                fontFamily = Figtree,
                fontSize = 13.5.sp,
                lineHeight = 19.sp,
                color = GroundCream.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
private fun StoryCard(title: String, body: String, tag: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(SandCard)
            .padding(20.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Terracotta100),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌿", fontSize = 15.sp)
                }
                Spacer(Modifier.size(10.dp))
                Column {
                    Text(
                        text = tag,
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = TextDark
                    )
                    Text(
                        text = "Real story",
                        fontFamily = Figtree,
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = title,
                fontFamily = Caprasimo,
                fontSize = 18.sp,
                lineHeight = 22.sp,
                color = TextDark
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = body,
                fontFamily = Figtree,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = TextMuted
            )
        }
    }
}

@Composable
private fun MoneyCard(title: String, body: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Sage100)
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = "MONEY",
                fontFamily = Figtree,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                letterSpacing = 1.2.sp,
                color = Sage
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = title,
                fontFamily = Caprasimo,
                fontSize = 22.sp,
                color = Sage900
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = body,
                fontFamily = Figtree,
                fontSize = 13.5.sp,
                lineHeight = 19.sp,
                color = Sage900
            )
        }
    }
}

@Composable
private fun IlloHero() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        val w = size.width
        val h = size.height
        val stroke = 2.5.dp.toPx()
        // Rolling hills
        val pts = listOf(
            Offset(0f, h * 0.85f),
            Offset(w * 0.25f, h * 0.55f),
            Offset(w * 0.5f, h * 0.8f),
            Offset(w * 0.75f, h * 0.45f),
            Offset(w, h * 0.75f)
        )
        for (i in 0 until pts.size - 1) {
            drawLine(Terracotta, pts[i], pts[i + 1], stroke, StrokeCap.Round)
        }
        // Sun
        drawCircle(Terracotta, radius = 6.dp.toPx(), center = Offset(w * 0.88f, h * 0.18f))
    }
}