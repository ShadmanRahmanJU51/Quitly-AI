package com.example.quitlyaifirst2screens.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import com.example.quitlyaifirst2screens.components.QuitlyButton
import com.example.quitlyaifirst2screens.components.QuitlyKicker
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

enum class ResultVariant { MAYA, DIEGO }

private data class ResultContent(
    val name: String,
    val heroAccent: Color,
    val heroSoft: Color,
    val heroLabel: String,
    val heroValue: String,
    val heroSub: String,
    val impactTitle: String,
    val impactBody: String
)

private fun contentFor(v: ResultVariant) = when (v) {
    ResultVariant.MAYA -> ResultContent(
        name = "Maya",
        heroAccent = Terracotta,
        heroSoft = Terracotta100,
        heroLabel = "Money you'll keep",
        heroValue = "$2,190",
        heroSub = "over your first year smoke-free",
        impactTitle = "Your family notices first",
        impactBody = "Kids of parents who quit are 3× less likely to start. Your choice echoes."
    )
    ResultVariant.DIEGO -> ResultContent(
        name = "Diego",
        heroAccent = Sage,
        heroSoft = Sage100,
        heroLabel = "Fitness you'll gain",
        heroValue = "+30%",
        heroSub = "lung capacity within 90 days",
        impactTitle = "Your circle feels it too",
        impactBody = "Quitting is contagious. Friends are 36% more likely to quit when you do."
    )
}

@Composable
fun PersonalizedResultsScreen(
    variant: ResultVariant,
    onStart: () -> Unit
) {
    val c = contentFor(variant)
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
            QuitlyKicker("Your personalized plan")
            Spacer(Modifier.height(12.dp))
            Text(
                text = "${c.name}, this is what day one\nstarts.",
                fontFamily = Caprasimo,
                fontSize = 31.sp,
                lineHeight = 36.sp,
                color = TextDark
            )
            Spacer(Modifier.height(20.dp))

            // Recovery curve card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(SandCard)
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Your body, healing",
                        fontFamily = Caprasimo,
                        fontSize = 17.sp,
                        color = TextDark
                    )
                    Spacer(Modifier.height(14.dp))
                    RecoveryCurve(accent = c.heroAccent)
                    Spacer(Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Marker("72h", "lungs clear", TextMuted)
                        Marker("2w", "circulation", TextMuted)
                        Marker("1m", "taste back", TextMuted)
                        Marker("1y", "risk halved", c.heroAccent)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Hero accent card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(c.heroSoft)
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = c.heroLabel.uppercase(),
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        letterSpacing = 1.2.sp,
                        color = c.heroAccent
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = c.heroValue,
                        fontFamily = Caprasimo,
                        fontSize = 46.sp,
                        color = c.heroAccent
                    )
                    Text(
                        text = c.heroSub,
                        fontFamily = Figtree,
                        fontSize = 13.5.sp,
                        color = TextDark
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Impact card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Sage100)
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = c.impactTitle,
                        fontFamily = Caprasimo,
                        fontSize = 17.sp,
                        color = Sage900
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = c.impactBody,
                        fontFamily = Figtree,
                        fontSize = 14.sp,
                        lineHeight = 19.sp,
                        color = Sage900
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            QuitlyButton(text = "Start day one", onClick = onStart)
        }
    }
}

@Composable
private fun RecoveryCurve(accent: Color) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        val w = size.width
        val h = size.height
        val stroke = 3.dp.toPx()
        // Baseline track
        drawLine(
            color = Color(0xFFD4CFC5),
            start = Offset(0f, h * 0.85f),
            end = Offset(w, h * 0.85f),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
        // Rising curve (approximated with segments)
        val pts = listOf(
            Offset(0f, h * 0.85f),
            Offset(w * 0.2f, h * 0.7f),
            Offset(w * 0.45f, h * 0.5f),
            Offset(w * 0.7f, h * 0.3f),
            Offset(w, h * 0.1f)
        )
        for (i in 0 until pts.size - 1) {
            drawLine(
                color = accent,
                start = pts[i],
                end = pts[i + 1],
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )
        }
        // Endpoint dot
        drawCircle(color = accent, radius = 5.dp.toPx(), center = pts.last())
    }
}

@Composable
private fun Marker(top: String, bottom: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = top,
            fontFamily = Caprasimo,
            fontSize = 13.sp,
            color = color
        )
        Text(
            text = bottom,
            fontFamily = Figtree,
            fontSize = 10.sp,
            color = TextMuted
        )
    }
}