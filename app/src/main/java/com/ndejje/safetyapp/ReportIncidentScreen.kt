package com.ndejje.safetyapp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ReportIncidentScreen(
    viewModel: SafetyViewModel,
    onReportSubmitted: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isAnonymous by remember { mutableStateOf(false) }

    // --- CAMPUS DROPDOWN STATE ---
    val campusOptions = listOf("Main Campus", "Kampala Campus", "Luweero Campus")
    var campusExpanded by remember { mutableStateOf(false) }
    var campus by remember { mutableStateOf(campusOptions[0]) }

    // --- CATEGORY CHIPS STATE ---
    val categories = listOf(
        "Physical Assault",
        "Theft / Robbery",
        "Sexual Harassment",
        "Security Guard Issue",
        "Mental Health Concern",
        "Other Emergency"
    )
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    // --- PHOTO PICKER STATE ---
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Report", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text("Incident Category", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            // FlowRow wraps chips to next line automatically
            FlowRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = (selectedCategory == cat),
                        onClick = { selectedCategory = cat },
                        label = { Text(cat) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ANONYMITY TOGGLE
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Report Anonymously", modifier = Modifier.weight(1f))
                Switch(
                    checked = isAnonymous,
                    onCheckedChange = { isAnonymous = it }
                )
            }

            if (isAnonymous) {
                Text(
                    "Your name will be hidden from other students.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TITLE
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CAMPUS DROPDOWN
            ExposedDropdownMenuBox(
                expanded = campusExpanded,
                onExpandedChange = { campusExpanded = !campusExpanded }
            ) {
                OutlinedTextField(
                    value = campus,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Campus") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = campusExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = campusExpanded,
                    onDismissRequest = { campusExpanded = false }
                ) {
                    campusOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                campus = selectionOption
                                campusExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // DESCRIPTION
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().height(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // PHOTO SECTION
            Text("Attachment", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Selected Image",
                    modifier = Modifier.size(150.dp).padding(bottom = 8.dp)
                )
            }

            OutlinedButton(
                onClick = { photoLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (imageUri == null) "Add Photo" else "Change Photo")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SUBMIT BUTTON
            SafetyButton(
                text = "SUBMIT INCIDENT",
                onClick = {
                    val imagePath: String? = imageUri?.toString()

                    viewModel.submitReport(
                        context = context,
                        title = title,
                        description = description,
                        campus = campus,
                        category = selectedCategory, // Passing the chip value
                        imageUri = imagePath,
                        isAnonymous = isAnonymous // Passing the switch value
                    )
                    onReportSubmitted()
                }
            )
        }
    }
}