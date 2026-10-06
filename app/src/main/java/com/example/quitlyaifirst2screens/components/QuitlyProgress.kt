package com.example.quitlyaifirst2screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.quitlyaifirst2screens.ui.theme.Neutral300
import com.example.quitlyaifirst2screens.ui.theme.Terracotta

/** Thin linear progress bar used across onboarding (e.g. "2/5"). */
@Composable
fun QuitlyProgressBar(
    current: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    val progress = (current.toFloat() / total.toFloat()).coerceIn(0f, 1f)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(50))
            .background(Neutral300)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(6.dp)
                .clip(RoundedCornerShape(50))
                .background(Terracotta)
        )
    }
}

/** Row of dot indicators. Filled = Terracotta, unfilled = Neutral300. */
@Composable
fun QuitlyProgressDots(
    current: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        repeat(total) { i ->
            val filled = i < current
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (filled) Terracotta else Neutral300)
            )
        }
    }
}