package com.dukaandesk.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DukaanLightColorScheme = lightColorScheme(
    primary = DeepBlue,
    primaryContainer = DeepBlue,
    onPrimaryContainer = OffWhite,
    
    background = OffWhite,
    surface = OffWhite,
    
    surfaceVariant = SoftBlueGrey, // Tonal elevation 1
    
    onSurface = AlmostBlack,
    onSurfaceVariant = DarkGrey,
    
    error = BrickRed,
    errorContainer = BrickRed,
    onError = OffWhite
    
    // Custom colors (Success, Offline) would be implemented as CompositionLocals
    // or extension properties on ColorScheme since they aren't default M3 roles.
)

@Composable
fun DukaanDeskTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DukaanLightColorScheme,
        typography = DukaanDeskTypography,
        content = content
    )
}
