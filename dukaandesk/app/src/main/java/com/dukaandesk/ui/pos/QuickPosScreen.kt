package com.dukaandesk.ui.pos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dukaandesk.ui.theme.*

// Dummy data model
data class OrderItem(val id: Int, val name: String, val price: Double, var quantity: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickPosScreen() {
    var searchQuery by remember { mutableStateOf("") }
    
    // Seed some interactive mock data
    val orderItems = remember { 
        mutableStateListOf(
            OrderItem(1, "Basmati Rice 5kg", 450.0, 1),
            OrderItem(2, "Lifebuoy Soap", 40.0, 5),
            OrderItem(3, "Tata Salt 1kg", 25.0, 2)
        )
    }
    
    val orderTotal = orderItems.sumOf { it.price * it.quantity }

    Scaffold(
        containerColor = OffWhite,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "New Sale", 
                        style = DukaanDeskTypography.titleLarge
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OffWhite,
                    titleContentColor = AlmostBlack
                )
            )
        },
        bottomBar = {
            // The massive persistent Bottom Sheet / FAB equivalent anchored at the bottom
            Surface(
                color = OffWhite,
                shadowElevation = 8.dp // Level 3 Shadow mapping
            ) {
                Button(
                    onClick = { /* Trigger WhatsApp API */ },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlue),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(DukaanDimensions.spacingMd)
                        // Oversized primary action target for the 'rush'
                        .height(80.dp) 
                ) {
                    Text(
                        text = "Complete & Send WhatsApp Receipt - ₹${orderTotal.toInt()}",
                        style = DukaanDeskTypography.titleMedium,
                        color = OffWhite,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(OffWhite)
        ) {
            // Large Search Bar featuring prominent Voice-to-Text Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = DukaanDimensions.spacingMd, 
                        vertical = DukaanDimensions.spacingSm
                    )
                    // Ensuring height is at least the primary target height
                    .heightIn(min = DukaanDimensions.primaryActionTarget),
                placeholder = { 
                    Text(
                        text = "Search or Voice Input...", 
                        style = DukaanDeskTypography.bodyLarge,
                        color = DarkGrey
                    ) 
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search, 
                        contentDescription = "Search", 
                        tint = DeepBlue
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { /* Trigger speech to text layer */ },
                        modifier = Modifier.size(DukaanDimensions.primaryActionTarget)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic, 
                            contentDescription = "Voice Input", 
                            tint = DeepBlue,
                            // Extra large mic icon for visibility
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DeepBlue,
                    unfocusedBorderColor = DarkGrey,
                    focusedContainerColor = OffWhite,
                    unfocusedContainerColor = OffWhite
                ),
                singleLine = true
            )

            // Simple, highly legible list view of added items
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        horizontal = DukaanDimensions.spacingMd, 
                        vertical = DukaanDimensions.spacingSm
                    ),
                verticalArrangement = Arrangement.spacedBy(DukaanDimensions.spacingSm)
            ) {
                items(orderItems, key = { it.id }) { item ->
                    PosItemCard(
                        item = item,
                        onIncrease = { 
                            val idx = orderItems.indexOf(item)
                            if(idx != -1) orderItems[idx] = item.copy(quantity = item.quantity + 1)
                        },
                        onDecrease = {
                            val idx = orderItems.indexOf(item)
                            if(idx != -1) {
                                if (item.quantity > 1) {
                                    orderItems[idx] = item.copy(quantity = item.quantity - 1)
                                } else {
                                    orderItems.removeAt(idx) // Drop to 0 removes it
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PosItemCard(
    item: OrderItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    // Level 1 Tonal Elevation (Surface Elevated) mapped here
    Card(
        colors = CardDefaults.cardColors(containerColor = SoftBlueGrey),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // Standard internal padding inside cards
                .padding(DukaanDimensions.spacingMd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = DukaanDeskTypography.titleMedium,
                    color = AlmostBlack
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₹${item.price.toInt()} / unit",
                    style = DukaanDeskTypography.bodyLarge,
                    color = DarkGrey
                )
            }
            
            // Large '+' and '-' Stepper Buttons with generous touch targets
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(DukaanDimensions.spacingXs)
            ) {
                // Minimum 48dp target STRICTLY enforced
                IconButton(
                    onClick = onDecrease,
                    modifier = Modifier.size(DukaanDimensions.minTouchTarget)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove, 
                        contentDescription = "Decrease Quantity", 
                        tint = DeepBlue
                    )
                }
                
                Text(
                    text = item.quantity.toString(),
                    style = DukaanDeskTypography.titleLarge,
                    color = AlmostBlack,
                    modifier = Modifier.padding(horizontal = DukaanDimensions.spacingSm)
                )
                
                // Minimum 48dp target STRICTLY enforced
                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier.size(DukaanDimensions.minTouchTarget)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add, 
                        contentDescription = "Increase Quantity", 
                        tint = DeepBlue
                    )
                }
            }
        }
    }
}
