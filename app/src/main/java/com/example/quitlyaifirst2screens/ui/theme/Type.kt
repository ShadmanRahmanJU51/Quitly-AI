package com.example.quitlyaifirst2screens.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.quitlyaifirst2screens.R

val Caprasimo = FontFamily(
    Font(R.font.caprasimo_regular, FontWeight.Normal)
)

val Figtree = FontFamily(
    Font(R.font.figtree_regular, FontWeight.Normal),
    Font(R.font.figtree_medium, FontWeight.Medium),
    Font(R.font.figtree_semibold, FontWeight.SemiBold)
)

val Typography = Typography(
    // Wordmark / big headline style — Caprasimo
    displayLarge = TextStyle(
        fontFamily = Caprasimo,
        fontWeight = FontWeight.Normal,
        fontSize = 52.sp
    ),
    // Screen headline style — Caprasimo, smaller
    headlineMedium = TextStyle(
        fontFamily = Caprasimo,
        fontWeight = FontWeight.Normal,
        fontSize = 31.sp
    ),
    // Body text — Figtree
    bodyLarge = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 14.5.sp
    ),
    // Button label — Caprasimo
    labelLarge = TextStyle(
        fontFamily = Caprasimo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    // Small link text — Figtree
    labelMedium = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    )
)