package com.angel.panini.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary          = GoldFIFA,
    onPrimary        = Color(0xFF1A1200),
    primaryContainer = GoldFIFADark,
    secondary        = GreenObtained,
    onSecondary      = Color(0xFF002918),
    tertiary         = BlueRepeated,
    background       = Surface0,
    surface          = Surface1,
    surfaceVariant   = Surface2,
    onBackground     = TextPrimary,
    onSurface        = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline          = BorderSubtle,
    error            = RedPending,
)

@Composable
fun PaniniAlbumTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography  = Typography,
        content     = content
    )
}