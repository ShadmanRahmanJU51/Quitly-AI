package com.example.quitlyaifirst2screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.ui.theme.AccentDeep
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.Neutral500
import com.example.quitlyaifirst2screens.ui.theme.TextDark

/**
 * Small stacked stat: big Caprasimo value + small Figtree label underneath.
 * Used on Home stats card, Progress summary, Profile quick-stats row.
 */
@Composable
fun QuitlyStat(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    valueColor: androidx.compose.ui.graphics.Color = TextDark,
    valueSize: androidx.compose.ui.unit.TextUnit = 22.sp,
    centered: Boolean = true
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start
    ) {
        Text(
            text = value,
            fontFamily = Caprasimo,
            fontWeight = FontWeight.Normal,
            fontSize = valueSize,
            color = valueColor
        )
        Text(
            text = label,
            fontFamily = Figtree,
            fontSize = 11.sp,
            color = Neutral500,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

/** Uppercase section label ("For you today", "Your body, healing"). */
@Composable
fun QuitlySectionLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text.uppercase(),
        modifier = modifier,
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        color = AccentDeep,
        letterSpacing = 1.2.sp
    )
}