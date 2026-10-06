package com.example.quitlyaifirst2screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.ui.theme.AccentDeep
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Neutral400
import com.example.quitlyaifirst2screens.ui.theme.TextDark

enum class TopBarLead { BACK, CLOSE }

/**
 * Modal header: lead icon (back arrow or ✕) · centered title · optional trailing action.
 */
@Composable
fun QuitlyTopBar(
    title: String,
    onLeadClick: () -> Unit,
    modifier: Modifier = Modifier,
    lead: TopBarLead = TopBarLead.BACK,
    trailingText: String? = null,
    trailingEnabled: Boolean = true,
    onTrailingClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clickable { onLeadClick() },
            contentAlignment = Alignment.Center
        ) {
            when (lead) {
                TopBarLead.BACK -> BackArrowIcon()
                TopBarLead.CLOSE -> CloseIcon()
            }
        }

        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
            fontFamily = Caprasimo,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = TextDark,
            maxLines = 1
        )

        if (trailingText != null) {
            Box(
                modifier = Modifier
                    .height(44.dp)
                    .clickable(enabled = trailingEnabled) { onTrailingClick() }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = trailingText,
                    fontFamily = Caprasimo,
                    fontSize = 16.sp,
                    color = if (trailingEnabled) AccentDeep else Neutral400
                )
            }
        } else {
            Box(modifier = Modifier.size(44.dp))
        }
    }
}

@Composable
private fun BackArrowIcon() {
    Canvas(modifier = Modifier.size(22.dp)) {
        val w = size.width
        val h = size.height
        val stroke = 2.5.dp.toPx()
        drawLine(
            color = TextDark,
            start = Offset(w * 0.15f, h * 0.5f),
            end = Offset(w * 0.9f, h * 0.5f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawLine(
            color = TextDark,
            start = Offset(w * 0.15f, h * 0.5f),
            end = Offset(w * 0.45f, h * 0.2f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawLine(
            color = TextDark,
            start = Offset(w * 0.15f, h * 0.5f),
            end = Offset(w * 0.45f, h * 0.8f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun CloseIcon() {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height
        val stroke = 2.5.dp.toPx()
        drawLine(
            color = TextDark,
            start = Offset(w * 0.2f, h * 0.2f),
            end = Offset(w * 0.8f, h * 0.8f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawLine(
            color = TextDark,
            start = Offset(w * 0.8f, h * 0.2f),
            end = Offset(w * 0.2f, h * 0.8f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}