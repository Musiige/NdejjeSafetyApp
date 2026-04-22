package com.ndejje.safetyapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ndejje.safetyapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportIncidentScreen(
    viewModel: SafetyViewModel,
    onReportSubmitted: () -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCampus by remember { mutableStateOf("Main Campus") }
    var expanded by remember { mutableStateOf(false) }

    val campuses = listOf("Main Campus", "Bombo Campus", "Kampala Campus")

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
        ) {
            // Title Input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Incident Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacingMedium)))

            // Campus Dropdown
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Campus: $selectedCampus")
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

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacingMedium)))

            // Description Input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description Details") },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Submit Button
            Button(
                onClick = {
                    if (title.isNotBlank() && description.isNotBlank()) {
                        viewModel.submitReport(title, description, selectedCampus)
                        onReportSubmitted()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.buttonHeight)),
                enabled = title.isNotBlank() && description.isNotBlank()
            ) {
                Text("SUBMIT REPORT")
            }
        }
    }
}

