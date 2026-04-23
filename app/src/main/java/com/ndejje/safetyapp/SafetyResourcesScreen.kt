package com.ndejje.safetyapp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ndejje.safetyapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafetyResourcesScreen(onBack: () -> Unit) {
    // Using stringResource to get data from strings.xml
    val resources = listOf(
        SafetyResource(
            title = stringResource(R.string.res_night_title),
            content = stringResource(R.string.res_night_content),
            contact = stringResource(R.string.res_night_contact)
        ),
        SafetyResource(
            title = stringResource(R.string.res_harassment_title),
            content = stringResource(R.string.res_harassment_content),
            contact = stringResource(R.string.res_harassment_contact)
        ),
        SafetyResource(
            title = stringResource(R.string.res_mental_title),
            content = stringResource(R.string.res_mental_content),
            contact = stringResource(R.string.res_mental_contact)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Safety Guides & Tips") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(resources) { resource ->
                ResourceCard(resource)
            }
        }
    }
}

@Composable
fun ResourceCard(resource: SafetyResource) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize() // Smooth opening/closing animation
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = if (expanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = resource.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = resource.content, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = Color.Gray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = resource.contact,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Text(
                    text = "Tap to read more...",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

// 2. The Data Model (Put this at the bottom of the file or in a separate file)
data class SafetyResource(
    val title: String,
    val content: String,
    val contact: String
)