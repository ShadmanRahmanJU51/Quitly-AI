package com.example.quitlyaifirst2screens.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
fun SplashScreen(
    onBeginQuit: () -> Unit = {},
    onSignIn: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(74.dp)
                    .background(Terracotta, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Q",
                    color = GroundCream,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Quitly",
                style = MaterialTheme.typography.displayLarge,
                color = TextDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Quitting isn't about willpower. It's about a plan built for you.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onBeginQuit,
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
                    text = "Begin your quit",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onSignIn) {
                Text(
                    text = "I already have an account",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextMuted
                )
            }
        }
    }
}