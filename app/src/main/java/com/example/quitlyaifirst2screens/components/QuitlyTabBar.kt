package com.example.quitlyaifirst2screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Neutral500
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.TextDark

enum class TabItem(val label: String) {
    HOME("Home"),
    PROGRESS("Progress"),
    LOG("Log"),
    COACH("Coach"),
    YOU("You")
}

/**
 * Persistent bottom tab bar with 5 slots and a raised centre FAB for LOG.
 *
 * @param active currently selected tab.
 * @param onTabSelected called for every tab, including LOG (host decides whether it's a full-screen modal).
 */
@Composable
fun QuitlyTabBar(
    active: TabItem,
    onTabSelected: (TabItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(SandCard)
            .padding(top = 12.dp, bottom = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TabSlot(TabItem.HOME, active, onTabSelected) { active -> HomeIcon(active) }
            TabSlot(TabItem.PROGRESS, active, onTabSelected) { active -> ProgressIcon(active) }
            // Centre slot is reserved space; the FAB overlays it.
            Box(modifier = Modifier.size(64.dp))
            TabSlot(TabItem.COACH, active, onTabSelected) { active -> CoachIcon(active) }
            TabSlot(TabItem.YOU, active, onTabSelected) { active -> YouIcon(active) }
        }

        // Raised centre FAB
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp)
                .size(56.dp)
                .clip(CircleShape)
                .background(Terracotta)
                .clickable { onTabSelected(TabItem.LOG) },
            contentAlignment = Alignment.Center
        ) {
            PlusIcon()
        }
    }
}

@Composable
private fun TabSlot(
    item: TabItem,
    active: TabItem,
    onTabSelected: (TabItem) -> Unit,
    icon: @Composable (Boolean) -> Unit
) {
    val isActive = item == active
    val color = if (isActive) Terracotta else Neutral500
    Column(
        modifier = Modifier
            .size(width = 64.dp, height = 56.dp)
            .clickable { onTabSelected(item) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon(isActive)
        Text(
            text = item.label,
            fontFamily = Figtree,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 9.5.sp,
            color = color,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// ---------- Icons (hand-drawn, stroke 2.5dp, rounded caps) ----------

private fun tabTint(active: Boolean) = if (active) Terracotta else Neutral500

@Composable
private fun HomeIcon(active: Boolean) {
    val color = tabTint(active)
    Canvas(modifier = Modifier.size(22.dp)) {
        val w = size.width; val h = size.height
        val stroke = 2.4.dp.toPx()
        // Roof
        drawLine(color, Offset(w * 0.1f, h * 0.5f), Offset(w * 0.5f, h * 0.15f), stroke, StrokeCap.Round)
        drawLine(color, Offset(w * 0.5f, h * 0.15f), Offset(w * 0.9f, h * 0.5f), stroke, StrokeCap.Round)
        // Body (sides + base)
        drawLine(color, Offset(w * 0.2f, h * 0.5f), Offset(w * 0.2f, h * 0.88f), stroke, StrokeCap.Round)
        drawLine(color, Offset(w * 0.8f, h * 0.5f), Offset(w * 0.8f, h * 0.88f), stroke, StrokeCap.Round)
        drawLine(color, Offset(w * 0.2f, h * 0.88f), Offset(w * 0.8f, h * 0.88f), stroke, StrokeCap.Round)
    }
}

@Composable
private fun ProgressIcon(active: Boolean) {
    val color = tabTint(active)
    Canvas(modifier = Modifier.size(22.dp)) {
        val w = size.width; val h = size.height
        val stroke = 2.4.dp.toPx()
        // Three ascending bars
        drawLine(color, Offset(w * 0.2f, h * 0.8f), Offset(w * 0.2f, h * 0.55f), stroke, StrokeCap.Round)
        drawLine(color, Offset(w * 0.5f, h * 0.8f), Offset(w * 0.5f, h * 0.35f), stroke, StrokeCap.Round)
        drawLine(color, Offset(w * 0.8f, h * 0.8f), Offset(w * 0.8f, h * 0.15f), stroke, StrokeCap.Round)
    }
}

@Composable
private fun CoachIcon(active: Boolean) {
    val color = tabTint(active)
    Canvas(modifier = Modifier.size(22.dp)) {
        val w = size.width; val h = size.height
        val stroke = 2.4.dp.toPx()
        // Rounded speech bubble
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.12f, h * 0.18f),
            size = androidx.compose.ui.geometry.Size(w * 0.76f, h * 0.54f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.18f),
            style = Stroke(width = stroke)
        )
        // Tail
        drawLine(color, Offset(w * 0.32f, h * 0.72f), Offset(w * 0.28f, h * 0.9f), stroke, StrokeCap.Round)
        drawLine(color, Offset(w * 0.28f, h * 0.9f), Offset(w * 0.46f, h * 0.72f), stroke, StrokeCap.Round)
    }
}

@Composable
private fun YouIcon(active: Boolean) {
    val color = tabTint(active)
    Canvas(modifier = Modifier.size(22.dp)) {
        val w = size.width; val h = size.height
        val stroke = 2.4.dp.toPx()
        // Head
        drawCircle(
            color = color,
            radius = w * 0.18f,
            center = Offset(w * 0.5f, h * 0.32f),
            style = Stroke(width = stroke)
        )
        // Shoulders (arc)
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(w * 0.18f, h * 0.55f),
            size = androidx.compose.ui.geometry.Size(w * 0.64f, h * 0.6f),
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun PlusIcon() {
    Canvas(modifier = Modifier.size(22.dp)) {
        val w = size.width; val h = size.height
        val stroke = 2.8.dp.toPx()
        drawLine(GroundCream, Offset(w * 0.5f, h * 0.2f), Offset(w * 0.5f, h * 0.8f), stroke, StrokeCap.Round)
        drawLine(GroundCream, Offset(w * 0.2f, h * 0.5f), Offset(w * 0.8f, h * 0.5f), stroke, StrokeCap.Round)
    }
}