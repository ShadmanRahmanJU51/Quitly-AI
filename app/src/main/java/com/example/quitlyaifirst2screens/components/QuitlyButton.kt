package com.example.quitlyaifirst2screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.Terracotta100
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.Figtree

@Composable
fun QuitlyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = enabled,
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Terracotta,
            contentColor = GroundCream,
            disabledContainerColor = Terracotta100,
            disabledContentColor = TextDark
        )
    ) {
        Text(
            text = text,
            fontFamily = Figtree
        )
    }
}