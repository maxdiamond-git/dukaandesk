package com.dukaandesk.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dukaandesk.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    userName: String = "Anand",
    isOffline: Boolean = true,
    todaysSales: Double = 12450.0,
    lowStockItemCount: Int = 5,
    pendingAppointments: Int = 2
) {
    Scaffold(
        containerColor = OffWhite,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Good morning, $userName!",
                        style = DukaanDeskTypography.titleLarge,
                        color = AlmostBlack
                    )
                },
                actions = {
                    // Offline/Online Status Chip
                    Surface(
                        color = if (isOffline) WarmAmber else ForestGreen,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(end = DukaanDimensions.spacingMd)
                    ) {
                        Text(
                            text = if (isOffline) "Offline" else "Online",
                            style = DukaanDeskTypography.labelLarge,
                            color = OffWhite,
                            modifier = Modifier.padding(
                                horizontal = DukaanDimensions.spacingMd,
                                vertical = DukaanDimensions.spacingXs
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OffWhite
                )
            )
        },
        bottomBar = {
            // Standard Bottom Navigation Bar
            NavigationBar(
                containerColor = OffWhite,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home", style = DukaanDeskTypography.labelLarge) },
                    selected = true,
                    onClick = { /* Navigate to Home */ },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DeepBlue,
                        selectedTextColor = DeepBlue,
                        indicatorColor = SoftBlueGrey,
                        unselectedIconColor = DarkGrey,
                        unselectedTextColor = DarkGrey
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Inventory, contentDescription = "Inventory") },
                    label = { Text("Inventory", style = DukaanDeskTypography.labelLarge) },
                    selected = false,
                    onClick = { /* Navigate to Inventory */ },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DeepBlue,
                        selectedTextColor = DeepBlue,
                        indicatorColor = SoftBlueGrey,
                        unselectedIconColor = DarkGrey,
                        unselectedTextColor = DarkGrey
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings", style = DukaanDeskTypography.labelLarge) },
                    selected = false,
                    onClick = { /* Navigate to Settings */ },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DeepBlue,
                        selectedTextColor = DeepBlue,
                        indicatorColor = SoftBlueGrey,
                        unselectedIconColor = DarkGrey,
                        unselectedTextColor = DarkGrey
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(OffWhite)
                .padding(DukaanDimensions.spacingMd),
            verticalArrangement = Arrangement.spacedBy(DukaanDimensions.spacingLg)
        ) {
            
            // 1. "Today's Sales" showing a large Rupee (₹) amount.
            DashboardCard(
                title = "Today's Sales",
                content = {
                    Text(
                        text = "₹${todaysSales.toInt()}",
                        style = DukaanDeskTypography.displaySmall,
                        color = DeepBlue
                    )
                }
            )

            // 2. "Low Stock Alerts" showing a red warning icon and a count
            DashboardCard(
                title = "Low Stock Alerts",
                content = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Warning",
                            tint = BrickRed,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(DukaanDimensions.spacingSm))
                        Text(
                            text = "$lowStockItemCount Items",
                            style = DukaanDeskTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = BrickRed
                        )
                    }
                }
            )

            // 3. "Today's Appointments" showing pending jobs
            DashboardCard(
                title = "Today's Appointments",
                content = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Appointments",
                            tint = DeepBlue,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(DukaanDimensions.spacingSm))
                        Text(
                            text = "$pendingAppointments Pending Jobs",
                            style = DukaanDeskTypography.titleLarge,
                            color = AlmostBlack
                        )
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(DukaanDimensions.spacingXl))
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    content: @Composable () -> Unit
) {
    // Large, distinct elevated Cards using 'Surface Elevated' token (SoftBlueGrey)
    Card(
        colors = CardDefaults.cardColors(containerColor = SoftBlueGrey),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DukaanDimensions.spacingLg)
        ) {
            Text(
                text = title,
                style = DukaanDeskTypography.titleMedium,
                color = DarkGrey
            )
            Spacer(modifier = Modifier.height(DukaanDimensions.spacingMd))
            content()
        }
    }
}
