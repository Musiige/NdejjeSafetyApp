package com.ndejje.safetyapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsDashboard(
    viewModel: SafetyViewModel,
    onBack: () -> Unit
) {
    // This collects the Flow from our Room database
    val alerts by viewModel.incidents.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Live Safety Alerts") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        if (alerts.isEmpty()) {
            // Show a message if no one has reported anything yet
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No active alerts for Ndejje University.", color = Color.Gray)
            }
        } else {
            // DYNAMIC LIST REQUIREMENT
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(dimensionResource(R.dimen.spacingMedium)),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(alerts) { alert ->
                    AlertItem(alert)
                }
            }
        }
    }
}

@Composable
fun AlertItem(incident: IncidentEntity) {
    // Local state to track if THIS specific card is open
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { isExpanded = !isExpanded }, // Toggle on tap
        elevation = CardDefaults.cardElevation(if (isExpanded) 8.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Category and Anonymity
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = incident.category.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                if (incident.isAnonymous) {
                    Text("Anonymous", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
            }

            Text(text = incident.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            // --- EXPANDABLE SECTION ---
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = incident.description, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(12.dp))

                // PHOTO ATTACHMENT
                if (!incident.imagePath.isNullOrEmpty()) {
                    Card(
                        modifier = Modifier.padding(top = 8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        AsyncImage(
                            model = incident.imagePath,
                            contentDescription = "Incident Evidence",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop // This makes the image fit neatly
                        )
                    }
                } else {
                    Text("No photo attached", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Location and Reporter Footer
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Location: ${incident.campus}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Reported by: ${if (incident.isAnonymous) "Private" else incident.reporterName}",
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                // Show a small "Tap for more" hint when closed
                Text(
                    text = "Tap to view details...",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}