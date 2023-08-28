package com.example.mydiary.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mydiary.R

val WindSongFont = FontFamily(
    Font(resId = R.font.windsong_medium, weight = FontWeight.Medium),
    Font(resId = R.font.windsong_regular, weight = FontWeight.Medium),
)

val Akayate = FontFamily(
    Font(resId = R.font.akayatelivigala_regular, weight = FontWeight.Medium)
)

val Alison = FontFamily(
    Font(resId = R.font.allison_regular, weight = FontWeight.Medium)
)

val Vibur = FontFamily(
    Font(resId = R.font.vibur_regular, weight = FontWeight.Medium)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)