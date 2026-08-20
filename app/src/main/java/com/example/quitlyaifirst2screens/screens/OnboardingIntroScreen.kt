package com.example.quitlyaifirst2screens.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.ui.theme.*

@Composable
fun OnboardingIntroScreen(
    onContinue: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Text(
                text = "WELCOME TO QUITLY",
                style = MaterialTheme.typography.labelMedium,
                color = AccentDeep,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "A quit plan that's actually yours.",
                style = MaterialTheme.typography.headlineMedium,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "No shame, no rigid rules \u2014 just a plan shaped by your own reasons and your own pace.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(28.dp))

            ValueCard(
                title = "Built on your reasons",
                body = "Everything here is tailored to why quitting matters to you."
            )
            Spacer(modifier = Modifier.height(14.dp))
            ValueCard(
                title = "A coach, not a rulebook",
                body = "Support that responds to you, not a checklist to follow."
            )
            Spacer(modifier = Modifier.height(14.dp))
            ValueCard(
                title = "There for the hard moments",
                body = "One tap away when a craving hits."
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ProgressDot(filled = true)
                Spacer(modifier = Modifier.width(6.dp))
                ProgressDot(filled = false)
                Spacer(modifier = Modifier.width(6.dp))
                ProgressDot(filled = false)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onContinue,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Terracotta,
                    contentColor = GroundCream
                ),
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Continue",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun ValueCard(title: String, body: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SandCard, RoundedCornerShape(22.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )
        }
    }
}

@Composable
private fun ProgressDot(filled: Boolean) {
    Box(
        modifier = Modifier
            .size(7.dp)
            .background(
                if (filled) Terracotta else TextMuted.copy(alpha = 0.25f),
                CircleShape
            )
    )
}