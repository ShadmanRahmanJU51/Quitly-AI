package com.example.quitlyaifirst2screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.Neutral300
import com.example.quitlyaifirst2screens.ui.theme.Sage
import com.example.quitlyaifirst2screens.ui.theme.Sage100
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.Terracotta100
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted

/**
 * Multi-select pill chip.
 *
 * @param accent which palette family to use when selected:
 *   "terracotta" (default) or "sage" (used on SOS / Setback screens).
 */
@Composable
fun QuitlyChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accent: String = "terracotta"
) {
    val selectedBg = if (accent == "sage") Sage100 else Terracotta100
    val selectedBorder = if (accent == "sage") Sage else Terracotta

    val bg = if (selected) selectedBg else androidx.compose.ui.graphics.Color.Transparent
    val border = if (selected) selectedBorder else Neutral300
    val textColor = if (selected) TextDark else TextMuted

    Text(
        text = label,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(bg)
            .border(width = 1.5.dp, color = border, shape = RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        fontFamily = Figtree,
        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
        fontSize = 14.sp,
        color = textColor
    )
}