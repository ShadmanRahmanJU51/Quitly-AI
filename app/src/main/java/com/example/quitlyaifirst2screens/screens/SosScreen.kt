package com.example.quitlyaifirst2screens.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitlyaifirst2screens.components.QuitlyKicker
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Sage
import com.example.quitlyaifirst2screens.ui.theme.Sage100
import com.example.quitlyaifirst2screens.ui.theme.Sage900
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted
import com.example.quitlyaifirst2screens.viewmodel.QuitlyViewModel

@Composable
fun SosScreen(
    onClose: () -> Unit,
    onResisted: () -> Unit,
    onSlipped: () -> Unit,
    vm: QuitlyViewModel = viewModel()
) {
    var breathing by remember { mutableStateOf(false) }

    val selectedReasons = vm.reasons.filter { it.isSelected }
        .ifEmpty { vm.reasons.take(3) } // fallback if user skipped survey

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Sage100, GroundCream, GroundCream)
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
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
                QuitlyKicker("Craving rescue")
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "This will pass.\nStay with me.",
                    fontFamily = Caprasimo,
                    fontSize = 31.sp,
                    lineHeight = 36.sp,
                    color = TextDark,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Cravings peak in 3–5 minutes and fade whether you smoke or not.",
                    fontFamily = Figtree,
                    fontSize = 13.5.sp,
                    lineHeight = 18.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(28.dp))

                BreathingOrb(active = breathing)

                Spacer(Modifier.height(20.dp))

                if (!breathing) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Sage)
                            .clickable { breathing = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Guide me · start breathing",
                            fontFamily = Caprasimo,
                            fontSize = 16.sp,
                            color = GroundCream
                        )
                    }
                } else {
                    Text(
                        text = "Breathe in... and out...",
                        fontFamily = Caprasimo,
                        fontSize = 16.sp,
                        color = Sage900
                    )
                }

                Spacer(Modifier.height(28.dp))

                // Remember why panel
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Sage100)
                        .padding(18.dp)
                ) {
                    Column {
                        Text(
                            text = "REMEMBER WHY",
                            fontFamily = Figtree,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            letterSpacing = 1.2.sp,
                            color = Sage
                        )
                        Spacer(Modifier.height(8.dp))
                        selectedReasons.forEach { r ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = r.emoji, fontSize = 16.sp)
                                Spacer(Modifier.size(10.dp))
                                Text(
                                    text = r.label,
                                    fontFamily = Figtree,
                                    fontSize = 14.sp,
                                    color = Sage900
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
            }

            // Outcome pills row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutcomePill(
                    label = "🎉 It passed",
                    modifier = Modifier.weight(1f),
                    bg = Sage,
                    textColor = GroundCream,
                    onClick = onResisted
                )
                OutcomePill(
                    label = "I slipped",
                    modifier = Modifier.weight(1f),
                    bg = Sage100,
                    textColor = Sage900,
                    onClick = onSlipped
                )
            }
        }
    }
}

@Composable
private fun BreathingOrb(active: Boolean) {
    val transition = rememberInfiniteTransition(label = "breath")
    val scale by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = if (active) 1.15f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    val baseSize = 200.dp

    Box(
        modifier = Modifier.size(baseSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(baseSize)
                .scale(scale)
        ) {
            drawCircle(color = Sage100, radius = size.minDimension / 2f)
        }
        Canvas(
            modifier = Modifier
                .size(baseSize * 0.78f)
                .scale(scale)
        ) {
            drawCircle(color = Sage.copy(alpha = 0.85f), radius = size.minDimension / 2f)
        }
        Box(
            modifier = Modifier
                .size(baseSize * 0.42f)
                .scale(scale)
                .clip(CircleShape)
                .background(GroundCream),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (active) "🌿" else "🫁",
                fontSize = 34.sp
            )
        }
    }
}

@Composable
private fun OutcomePill(
    label: String,
    bg: androidx.compose.ui.graphics.Color,
    textColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(50))
            .background(bg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = Caprasimo,
            fontSize = 15.sp,
            color = textColor
        )
    }
}