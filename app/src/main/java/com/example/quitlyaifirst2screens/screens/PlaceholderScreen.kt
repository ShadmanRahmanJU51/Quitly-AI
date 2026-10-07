package com.example.quitlyaifirst2screens.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.ui.theme.Neutral500
import com.example.quitlyaifirst2screens.ui.theme.TextDark

/**
 * Temporary stub shown for routes whose real screen hasn't been built yet.
 * Every call site will be replaced one-by-one during Steps 12–24.
 */
@Composable
fun PlaceholderScreen(
    title: String,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        QuitlyTopBar(
            title = title,
            onLeadClick = onBack,
            lead = TopBarLead.BACK
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextDark
                )
                Text(
                    text = "Coming in a later step",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Neutral500,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}