package com.example.quitlyaifirst2screens.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.ui.theme.AccentDeep
import com.example.quitlyaifirst2screens.ui.theme.Figtree

@Composable
fun QuitlyKicker(
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