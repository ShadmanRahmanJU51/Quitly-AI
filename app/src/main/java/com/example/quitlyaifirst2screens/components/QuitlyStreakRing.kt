package com.example.quitlyaifirst2screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Neutral300
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.TextDark

/**
 * Circular streak ring. [progress] is 0f..1f.
 *
 * @param size overall diameter (e.g. 120.dp on Home, 160.dp on Progress).
 * @param numberSize font size of the big number inside (e.g. 34.sp / 46.sp).
 * @param label small "day streak" label under the number.
 */
@Composable
fun QuitlyStreakRing(
    progress: Float,
    number: String,
    label: String = "day streak",
    size: Dp = 140.dp,
    numberSize: androidx.compose.ui.unit.TextUnit = 34.sp,
    strokeWidth: Dp = 10.dp,
    modifier: Modifier = Modifier
) {
    val clamped = progress.coerceIn(0f, 1f)
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val sw = strokeWidth.toPx()
            val inset = sw / 2f
            val arcSize = Size(this.size.width - sw, this.size.height - sw)
            val topLeft = Offset(inset, inset)

            // Track
            drawArc(
                color = Neutral300,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = sw, cap = StrokeCap.Round)
            )
            // Progress (starts at 12 o'clock, clockwise)
            drawArc(
                color = Terracotta,
                startAngle = -90f,
                sweepAngle = 360f * clamped,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = sw, cap = StrokeCap.Round)
            )
        }
        androidx.compose.foundation.layout.Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = number,
                fontFamily = Caprasimo,
                fontWeight = FontWeight.Normal,
                fontSize = numberSize,
                color = TextDark
            )
            Text(
                text = label,
                fontFamily = Caprasimo,
                fontSize = 11.sp,
                color = TextDark
            )
        }
    }
}