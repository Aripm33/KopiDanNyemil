package com.example.kopidannyemil.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Navy = Color(0xFF0B2545)
val NavyLight = Color(0xFF13315C)
val Blue = Color(0xFF1E5AA8)
val SoftBlue = Color(0xFFE8F0FB)
val BorderBlue = Color(0xFFC5D5EA)
val Ink = Color(0xFF0F1B2D)
val Muted = Color(0xFF5B6B82)
val ErrorRed = Color(0xFFC62828)

private val Colors = lightColorScheme(
    primary = Navy,
    onPrimary = Color.White,
    secondary = Blue,
    background = Color.White,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
    surfaceVariant = SoftBlue,
    error = ErrorRed
)

private val AppTypography = Typography(
    headlineLarge = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 32.sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 26.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    bodyMedium = TextStyle(fontSize = 14.sp),
    bodySmall = TextStyle(fontSize = 12.sp)
)

@Composable
fun KopiDanNyemilTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colors, typography = AppTypography, content = content)
}
