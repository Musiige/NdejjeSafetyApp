package com.ndejje.safetyapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsDashboard(
    viewModel: SafetyViewModel,
    userRole: String,
    currentUsername: String,
    onBack: () -> Unit
) {
    // 1. Fixed: Ensure viewModel has a StateFlow named 'incidents'
    // This is where most "Unresolved reference" errors come from
    val allAlerts by viewModel.incidents.collectAsStateWithLifecycle(initialValue = emptyList())

    var showOnlyMine by remember { mutableStateOf(false) }

    // 2. Logic: Filtering the list
    val displayList = remember(allAlerts, showOnlyMine, userRole, currentUsername) {
        allAlerts.filter { alert ->
            if (showOnlyMine) {
                alert.reporterName == currentUsername
            } else {
                userRole == "admin" || alert.status == "Approved" || alert.reporterName == currentUsername
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Safety Dashboard") },
                    navigationIcon = {
                        TextButton(onClick = onBack) { Text("Back") }
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    FilterChip(
                        selected = !showOnlyMine,
                        onClick = { showOnlyMine = false },
                        label = { Text("Public Alerts") },
                        leadingIcon = {
                            Icon(Icons.Default.Share, null, Modifier.size(FilterChipDefaults.IconSize))
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    FilterChip(
                        selected = showOnlyMine,
                        onClick = { showOnlyMine = true },
                        label = { Text("My Reports") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, null, Modifier.size(FilterChipDefaults.IconSize))
                        }
                    )
                }
            }
        }
    ) { padding ->
        if (displayList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(
                    text = if (showOnlyMine) "You haven't reported anything yet." else "No active alerts for Ndejje.",
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Fixed: Ensure IncidentEntity is recognized
                items(displayList, key = { it.id }) { alert ->
                    AlertItem(
                        incident = alert,
                        isAdmin = userRole == "admin",
                        onApprove = { viewModel.approveIncident(alert.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun AlertItem(
    incident: IncidentEntity,
    isAdmin: Boolean,
    onApprove: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(if (isExpanded) 6.dp else 2.dp),
        border = if (incident.status == "Pending") BorderStroke(1.dp, Color(0xFFFBC02D)) else null
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = incident.category.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                StatusBadge(incident.status)
            }

            Text(text = incident.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = incident.description, style = MaterialTheme.typography.bodyMedium)

                if (!incident.imagePath.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = incident.imagePath,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                if (incident.status == "Pending" && !isAdmin) {
                    Text(
                        text = "Awaiting admin verification...",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFFBC02D),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(modifier = Modifier.padding(vertical = 8.dp))

                Text("Campus: ${incident.campus}", style = MaterialTheme.typography.bodySmall)
                Text("Reporter: ${if (incident.isAnonymous) "Anonymous" else incident.reporterName}", style = MaterialTheme.typography.bodySmall)

                if (isAdmin && incident.status == "Pending") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onApprove,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43A047))
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Verify & Approve Report")
                    }
                }
            } else {
                Text("Tap to view details...", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val color = when (status) {
        "Pending" -> Color(0xFFFBC02D)
        "Approved" -> Color(0xFF43A047)
        else -> Color.Gray
    }
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, color)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}