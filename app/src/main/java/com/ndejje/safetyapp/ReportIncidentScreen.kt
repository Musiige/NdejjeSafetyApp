package com.ndejje.safetyapp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.AddCircle
import com.ndejje.safetyapp.R
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ReportIncidentScreen(
    viewModel: SafetyViewModel,
    onReportSubmitted: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    // Existing States
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCampus by remember { mutableStateOf("Main Campus") }
    var expanded by remember { mutableStateOf(false) }

    // New States for Features
    var selectedCategory by remember { mutableStateOf("") }
    var isAnonymous by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val campuses = listOf("Main Campus", "Bombo Campus", "Kampala Campus")
    val categories = listOf(
        "Sexual Harassment", "Physical Assault", "Theft",
        "Security Harassment", "Drug-related",
        "Emergency", "Mental Health", "Others"
    )

    // Photo Picker Launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Incident") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(dimensionResource(R.dimen.screenPadding))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Prevents keyboard from hiding fields
        ) {
            // 1. Category Chips Section
            Text("Incident Category", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 3 // This forces the "Vertical" flow without using the red-line parameter
            ) {
                categories.forEach { category ->
                    FilterChip(
                        modifier = Modifier.padding(vertical = 4.dp), // This creates the vertical space manually!
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Title Input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Short Summary (Title)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Campus Dropdown
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Location: $selectedCampus")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    campuses.forEach { campus ->
                        DropdownMenuItem(
                            text = { Text(campus) },
                            onClick = {
                                selectedCampus = campus
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Description Input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Describe what happened...") },
                modifier = Modifier.fillMaxWidth().height(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Photo Evidence Button
            OutlinedButton(
                onClick = { photoPickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.AddCircle, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(if (imageUri == null) "ATTACH PHOTO (OPTIONAL)" else "PHOTO ATTACHED ✅")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 6. Anonymous Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Report Anonymously", style = MaterialTheme.typography.bodyLarge)
                    Text("Hide your name from other students", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
                Switch(checked = isAnonymous, onCheckedChange = { isAnonymous = it })
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 7. Submit Button
            Button(
                onClick = {
                    // Update your ViewModel call to include the new fields
                    viewModel.submitReport(
                        title = title,
                        description = description,
                        campus = selectedCampus,
                        category = selectedCategory,
                        imageUri = imageUri?.toString(),
                        context = context,
                        isAnonymous = isAnonymous
                    )
                    onReportSubmitted()
                },
                modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.buttonHeight)),
                enabled = title.isNotBlank() && description.isNotBlank() && selectedCategory.isNotEmpty()
            ) {
                Text("SUBMIT REPORT")
            }
        }
    }
}