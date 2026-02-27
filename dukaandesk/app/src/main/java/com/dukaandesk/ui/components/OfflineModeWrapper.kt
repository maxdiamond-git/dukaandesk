package com.dukaandesk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dukaandesk.ui.theme.*

/**
 * A wrapper component that automatically injects the "Offline Mode" banner 
 * below the status bar whenever [isOffline] is true.
 * 
 * This ensures the rest of the screen ([content]) appears fully enabled and active.
 */
@Composable
fun OfflineModeWrapper(
    isOffline: Boolean,
    pendingSyncItems: Int = 0,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        
        // The persistent, full-width warning banner
        if (isOffline) {
            Surface(
                color = WarmAmber, // Muted, non-alarming color from design tokens
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Keep padding minimal but readable for a banner
                        .padding(horizontal = DukaanDimensions.spacingMd, vertical = DukaanDimensions.spacingSm),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudOff,
                        contentDescription = "Offline icon",
                        tint = OffWhite,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(DukaanDimensions.spacingSm))
                    
                    Text(
                        text = "Offline Mode - Saving Locally",
                        style = DukaanDeskTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = OffWhite
                    )
                    
                    // Subtle sync queue indicator displayed directly in the banner, 
                    // or it can be passed down to the FAB as requested.
                    if (pendingSyncItems > 0) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "$pendingSyncItems queued",
                            style = DukaanDeskTypography.labelLarge,
                            color = OffWhite.copy(alpha = 0.8f) // Slightly faded
                        )
                    }
                }
            }
        }
        
        // The main screen content remains fully active below
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}

// Example usage demonstrating it wrapping the POS screen we built earlier
@Composable
fun DemoOfflinePosWrapper() {
    OfflineModeWrapper(
        isOffline = true, 
        pendingSyncItems = 3
    ) {
        // Drop in the QuickPosScreen here
        // QuickPosScreen() 
        
        // Dummy placeholder to visualize
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(OffWhite),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Active POS UI remains fully interactive here.",
                style = DukaanDeskTypography.bodyLarge,
                color = AlmostBlack
            )
        }
    }
}
