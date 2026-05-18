package com.gramavasathi.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Warm, earthy, welcoming palette
val Saffron = Color(0xFFE8751A)
val SaffronLight = Color(0xFFF4A35A)
val SaffronDark = Color(0xFFB85510)

val Clay = Color(0xFF8B4513)
val ClayLight = Color(0xFFCD853F)

val Leaf = Color(0xFF4A7C59)
val LeafLight = Color(0xFF7DB48F)
val LeafDark = Color(0xFF2D5A3D)

val Cream = Color(0xFFFDF6EC)
val WarmWhite = Color(0xFFFFFAF3)
val LightCream = Color(0xFFF5E6CC)
val DarkBrown = Color(0xFF3E2010)
val MediumBrown = Color(0xFF6B3A1F)
val TextBrown = Color(0xFF4A2800)

private val WarmColorScheme = lightColorScheme(
    primary = Saffron,
    onPrimary = Color.White,
    primaryContainer = SaffronLight,
    onPrimaryContainer = DarkBrown,

    secondary = Leaf,
    onSecondary = Color.White,
    secondaryContainer = LeafLight,
    onSecondaryContainer = LeafDark,

    tertiary = Clay,
    onTertiary = Color.White,
    tertiaryContainer = ClayLight,
    onTertiaryContainer = DarkBrown,

    background = Cream,
    onBackground = TextBrown,

    surface = WarmWhite,
    onSurface = TextBrown,
    surfaceVariant = LightCream,
    onSurfaceVariant = MediumBrown,

    error = Color(0xFFB00020),
    onError = Color.White,
)

@Composable
fun GramaVasathiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WarmColorScheme,
        content = content
    )
}
