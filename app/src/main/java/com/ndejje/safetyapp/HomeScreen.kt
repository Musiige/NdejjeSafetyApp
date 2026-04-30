package com.ndejje.safetyapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    username: String,
    userRole: String, // "admin" or "student"
    onLogout: () -> Unit,
    onNavigateToReport: () -> Unit,
    onNavigateToAlerts: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToResources: () -> Unit,
) {
    val context = LocalContext.current

    // Admin Color logic for background and top bar
    val isAdmin = userRole == "admin"
    val containerColor = if (isAdmin) Color(0xFF263238) else MaterialTheme.colorScheme.background

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = containerColor // Admin gets the Charcoal Navy, User gets standard theme background
    ) {
        Scaffold(
            containerColor = Color.Transparent, // Let Surface handle background
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = if (isAdmin) "ADMIN DASHBOARD" else stringResource(R.string.app_name).uppercase(),
                            fontWeight = FontWeight.ExtraBold
                        )
                    },
                    actions = {
                        IconButton(onClick = onLogout) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = stringResource(R.string.desc_logout),
                                tint = MaterialTheme.colorScheme.onPrimary // Theme-aware tint
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = if (isAdmin) Color(0xFF263238) else MaterialTheme.colorScheme.primary,
                        titleContentColor = if (isAdmin) Color.White else MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_welcome_back),
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isAdmin) Color.LightGray else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$username ($userRole)",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isAdmin) Color.White else MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (isAdmin) {
                    AdminGrid(onNavigateToAnalytics, onNavigateToAlerts, onNavigateToReport)
                } else {
                    UserGrid(onNavigateToReport, onNavigateToAlerts, onNavigateToResources)
                }

                Spacer(modifier = Modifier.weight(1f))

                if (!isAdmin) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0700123456"))
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(R.string.btn_emergency_call),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserGrid(
    onNavigateToReport: () -> Unit,
    onNavigateToAlerts: () -> Unit,
    onNavigateToResources: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { MenuCard(stringResource(R.string.menu_report), Icons.Default.Notifications, Color(0xFFE53935), onNavigateToReport) }
        item { MenuCard(stringResource(R.string.menu_alerts), Icons.Default.Warning, Color(0xFFFBC02D), onNavigateToAlerts) }
        item { MenuCard(stringResource(R.string.menu_resources), Icons.Default.Info, Color(0xFF43A047), onNavigateToResources) }
    }
}

@Composable
fun AdminGrid(
    onNavigateToAnalytics: () -> Unit,
    onNavigateToAlerts: () -> Unit,
    onNavigateToReport: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item { MenuCard(stringResource(R.string.menu_report), Icons.Default.Notifications, Color(0xFFE53935), onNavigateToReport) }
        item { MenuCard(stringResource(R.string.menu_analytics), Icons.Default.List, Color(0xFF1E88E5), onNavigateToAnalytics) }
        item { MenuCard("Review Reports", Icons.Default.Edit, Color(0xFF607D8B), onNavigateToAlerts) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCard(title: String, icon: ImageVector, iconColor: Color, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.elevatedCardColors(
            // surfaceVariant provides a nice slight contrast against the background
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = iconColor.copy(alpha = 0.2f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, fontWeight = FontWeight.SemiBold)
        }
    }
}