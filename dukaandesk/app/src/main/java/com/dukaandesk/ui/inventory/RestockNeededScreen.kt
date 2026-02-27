package com.dukaandesk.ui.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dukaandesk.ui.theme.*

// Dummy Data Model
data class InventoryItem(val id: Int, val name: String, val currentStock: Int, val reorderLevel: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestockNeededScreen() {
    
    // Mocking real-world Indian neighborhood economy products
    val lowStockItems = remember {
        mutableStateListOf(
            InventoryItem(1, "Parle-G Biscuit 800g", 2, 10),
            InventoryItem(2, "Lux Beauty Soap", 0, 15),
            InventoryItem(3, "Tata Tea Gold 500g", 1, 5),
            InventoryItem(4, "Aashirvaad Atta 5kg", 3, 20),
            InventoryItem(5, "Amul Butter 100g", 2, 12)
        )
    }

    Scaffold(
        containerColor = OffWhite,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Restock Needed", 
                        style = DukaanDeskTypography.titleLarge,
                        color = AlmostBlack
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OffWhite
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(OffWhite)
        ) {
            // Vertically scrolling list of low-stock items
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = DukaanDimensions.spacingMd),
                contentPadding = PaddingValues(vertical = DukaanDimensions.spacingMd),
                verticalArrangement = Arrangement.spacedBy(DukaanDimensions.spacingMd)
            ) {
                items(lowStockItems, key = { it.id }) { item ->
                    RestockItemCard(
                        item = item,
                        onReorderClick = {
                            // In real app: Trigger WhatsApp Supplier integration, then remove from list
                            lowStockItems.remove(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RestockItemCard(
    item: InventoryItem,
    onReorderClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SoftBlueGrey),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // Utilitarian and spacious padding
                .padding(DukaanDimensions.spacingMd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Clear, highly legible sans-serif product name
                Text(
                    text = item.name,
                    style = DukaanDeskTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = AlmostBlack
                )
                
                Spacer(modifier = Modifier.height(DukaanDimensions.spacingSm))
                
                // Stock count highlighted in a warning color
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WarningAmber,
                        contentDescription = "Low Stock Icon",
                        tint = BrickRed,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(DukaanDimensions.spacingXs))
                    Text(
                        text = "Only ${item.currentStock} left (Reorder mark: ${item.reorderLevel})",
                        style = DukaanDeskTypography.bodyLarge,
                        color = BrickRed
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(DukaanDimensions.spacingMd))
            
            // Distinct, filled "1-Tap Reorder" button
            Button(
                onClick = onReorderClick,
                colors = ButtonDefaults.buttonColors(containerColor = DeepBlue),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    // Ensuring the touch target is at least 48dp tall
                    .heightIn(min = DukaanDimensions.minTouchTarget)
            ) {
                Text(
                    text = "1-Tap Reorder",
                    style = DukaanDeskTypography.labelLarge,
                    color = OffWhite
                )
            }
        }
    }
}
