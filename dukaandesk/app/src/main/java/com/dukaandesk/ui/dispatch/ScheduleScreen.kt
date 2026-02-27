package com.dukaandesk.ui.dispatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dukaandesk.ui.theme.*

// Dummy Data Model
data class JobAppointment(
    val id: Int, 
    val customerName: String, 
    val neighborhood: String, 
    val scheduledTime: String,
    var status: JobStatus
)

enum class JobStatus { PENDING, IN_PROGRESS, DONE }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen() {
    // Mocking field service jobs
    val todaysJobs = remember {
        mutableStateListOf(
            JobAppointment(1, "Ravi Kumar", "Koramangala Block 5", "10:30 AM", JobStatus.PENDING),
            JobAppointment(2, "Meera Singh", "Indiranagar 100ft Rd", "1:00 PM", JobStatus.PENDING),
            JobAppointment(3, "Arjun Patel", "Jayanagar 4th Block", "3:45 PM", JobStatus.PENDING)
        )
    }

    Scaffold(
        containerColor = OffWhite,
        topBar = {
            TopAppBar(
                title = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Calendar",
                            tint = DeepBlue
                        )
                        Spacer(modifier = Modifier.width(DukaanDimensions.spacingSm))
                        Text(
                            text = "Today's Jobs", 
                            style = DukaanDeskTypography.titleLarge,
                            color = AlmostBlack
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OffWhite
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(OffWhite)
                .padding(horizontal = DukaanDimensions.spacingMd),
            contentPadding = PaddingValues(top = DukaanDimensions.spacingSm, bottom = DukaanDimensions.spacingXl),
            verticalArrangement = Arrangement.spacedBy(DukaanDimensions.spacingLg)
        ) {
            items(todaysJobs, key = { it.id }) { job ->
                JobCard(
                    job = job,
                    onStatusChange = { newStatus ->
                        val index = todaysJobs.indexOf(job)
                        if (index != -1) {
                            todaysJobs[index] = job.copy(status = newStatus)
                        }
                    },
                    onNavigateClick = { /* Launch Google Maps */ },
                    onMessageClick = { /* Launch WhatsApp Intent */ }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCard(
    job: JobAppointment,
    onStatusChange: (JobStatus) -> Unit,
    onNavigateClick: () -> Unit,
    onMessageClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SoftBlueGrey),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DukaanDimensions.spacingMd)
        ) {
            // Header Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = job.customerName,
                        style = DukaanDeskTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = AlmostBlack
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = job.neighborhood,
                        style = DukaanDeskTypography.bodyLarge,
                        color = DarkGrey
                    )
                }
                
                // Time prominently displayed
                Surface(
                    color = DeepBlue.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = job.scheduledTime,
                        style = DukaanDeskTypography.labelLarge,
                        color = DeepBlue,
                        modifier = Modifier.padding(
                            horizontal = DukaanDimensions.spacingSm,
                            vertical = DukaanDimensions.spacingXs
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(DukaanDimensions.spacingLg))

            // Large, interactive SingleChoiceSegmentedButton (M3 standard)
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                SegmentedButton(
                    selected = job.status == JobStatus.PENDING,
                    onClick = { onStatusChange(JobStatus.PENDING) },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = WarmAmber.copy(alpha = 0.2f),
                        activeContentColor = AlmostBlack
                    )
                ) {
                    Text("Pending", style = DukaanDeskTypography.labelLarge)
                }
                
                SegmentedButton(
                    selected = job.status == JobStatus.IN_PROGRESS,
                    onClick = { onStatusChange(JobStatus.IN_PROGRESS) },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = DeepBlue.copy(alpha = 0.2f),
                        activeContentColor = DeepBlue
                    )
                ) {
                    Text("In Progress", style = DukaanDeskTypography.labelLarge)
                }
                
                SegmentedButton(
                    selected = job.status == JobStatus.DONE,
                    onClick = { onStatusChange(JobStatus.DONE) },
                    shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = ForestGreen.copy(alpha = 0.2f),
                        activeContentColor = ForestGreen
                    )
                ) {
                    Text("Done", style = DukaanDeskTypography.labelLarge)
                }
            }

            Spacer(modifier = Modifier.height(DukaanDimensions.spacingMd))

            // Massive high-contrast action buttons for outdoor readability
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DukaanDimensions.spacingSm)
            ) {
                // Primary Action: Navigate
                Button(
                    onClick = onNavigateClick,
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlue),
                    modifier = Modifier
                        .weight(1f)
                        .height(DukaanDimensions.primaryActionTarget),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = "Navigate"
                    )
                    Spacer(modifier = Modifier.width(DukaanDimensions.spacingXs))
                    Text(
                        text = "Navigate",
                        style = DukaanDeskTypography.labelLarge
                    )
                }

                // Secondary Action: Message Customer
                OutlinedButton(
                    onClick = onMessageClick,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = ForestGreen // Using Success green to mimic WhatsApp color broadly
                    ),
                    border = androidx.compose.foundation.BorderStroke(2.dp, ForestGreen),
                    modifier = Modifier
                        .weight(1f)
                        .height(DukaanDimensions.primaryActionTarget),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Message"
                    )
                    Spacer(modifier = Modifier.width(DukaanDimensions.spacingXs))
                    Text(
                        text = "Message",
                        style = DukaanDeskTypography.labelLarge
                    )
                }
            }
        }
    }
}
