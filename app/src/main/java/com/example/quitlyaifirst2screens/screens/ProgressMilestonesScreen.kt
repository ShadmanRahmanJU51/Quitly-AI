package com.example.quitlyaifirst2screens.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitlyaifirst2screens.components.QuitlyStreakRing
import com.example.quitlyaifirst2screens.components.QuitlyTabBar
import com.example.quitlyaifirst2screens.components.TabItem
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Neutral200
import com.example.quitlyaifirst2screens.ui.theme.Neutral400
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
fun ProgressMilestonesScreen(
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
            Text(
                text = "Your journey",
                fontFamily = Caprasimo,
                fontSize = 31.sp,
                color = TextDark
            )
            Spacer(Modifier.height(20.dp))

            // Summary card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(SandCard)
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    QuitlyStreakRing(
                        progress = (vm.streakDays % 30) / 30f,
                        number = vm.streakDays.toString(),
                        size = 130.dp,
                        numberSize = 46.sp,
                        strokeWidth = 11.dp
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatLine("${vm.cigarettesNotSmoked}", "not smoked")
                        StatLine("$${fmt(vm.moneySaved)}", "saved")
                        StatLine("${vm.hoursReclaimed}h", "reclaimed")
                    }
                }
            }

            Spacer(Modifier.height(28.dp))
            Text(
                text = "Your body, healing",
                fontFamily = Caprasimo,
                fontSize = 18.sp,
                color = TextDark
            )
            Spacer(Modifier.height(14.dp))
            HealingBar(label = "Heart rate & BP", progress = 1.0f)
            Spacer(Modifier.height(12.dp))
            HealingBar(label = "Lung function", progress = 0.64f)
            Spacer(Modifier.height(12.dp))
            HealingBar(label = "Taste & smell", progress = 0.48f)

            Spacer(Modifier.height(28.dp))
            Text(
                text = "Milestones",
                fontFamily = Caprasimo,
                fontSize = 18.sp,
                color = TextDark
            )
            Spacer(Modifier.height(14.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                vm.milestones.forEach { m ->
                    MilestoneBadge(label = m.label, earned = m.isEarned)
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        QuitlyTabBar(active = TabItem.PROGRESS, onTabSelected = onTabSelected)
    }
}

@Composable
private fun StatLine(value: String, label: String) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = value,
            fontFamily = Caprasimo,
            fontSize = 20.sp,
            color = TextDark
        )
        Spacer(Modifier.size(6.dp))
        Text(
            text = label,
            fontFamily = Figtree,
            fontSize = 12.sp,
            color = TextMuted,
            modifier = Modifier.padding(bottom = 2.dp)
        )
    }
}

@Composable
private fun HealingBar(label: String, progress: Float) {
    val pct = (progress * 100).toInt()
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label,
                fontFamily = Figtree,
                fontSize = 13.5.sp,
                color = TextDark,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (pct >= 100) "Done" else "$pct%",
                fontFamily = Figtree,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = if (pct >= 100) Sage else Terracotta
            )
        }
        Spacer(Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(Neutral200)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (pct >= 100) Sage else Terracotta)
            )
        }
    }
}

@Composable
private fun MilestoneBadge(label: String, earned: Boolean) {
    Column(
        modifier = Modifier.size(width = 84.dp, height = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(64.dp)) {
                val stroke = 2.5.dp.toPx()
                if (earned) {
                    drawCircle(color = Terracotta100, radius = size.minDimension / 2f)
                    drawCircle(
                        color = Terracotta,
                        radius = size.minDimension / 2f - stroke,
                        style = Stroke(width = stroke)
                    )
                } else {
                    drawCircle(
                        color = Neutral400,
                        radius = size.minDimension / 2f - stroke,
                        style = Stroke(
                            width = stroke,
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(10f, 10f), 0f
                            )
                        )
                    )
                }
                // Star / check inside
                if (earned) {
                    val w = size.width; val h = size.height
                    val s = 2.8.dp.toPx()
                    drawLine(Terracotta, Offset(w * 0.32f, h * 0.5f), Offset(w * 0.45f, h * 0.64f), s, StrokeCap.Round)
                    drawLine(Terracotta, Offset(w * 0.45f, h * 0.64f), Offset(w * 0.7f, h * 0.34f), s, StrokeCap.Round)
                } else {
                    // small dot
                    drawCircle(
                        color = Neutral400,
                        radius = 4.dp.toPx(),
                        center = Offset(size.width / 2f, size.height / 2f)
                    )
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = label,
            fontFamily = Figtree,
            fontWeight = if (earned) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 11.sp,
            color = if (earned) TextDark else Neutral500,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

private fun fmt(v: Double): String {
    val cents = (v * 100).toLong()
    val whole = cents / 100
    val frac = (cents % 100).toInt()
    return "$whole.${frac.toString().padStart(2, '0')}"
}