package com.ndejje.safetyapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ndejje.safetyapp.R

@Composable
fun HomeScreen(username: String, onLogout: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()
        .padding(dimensionResource(R.dimen.screenPadding)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(stringResource(R.string.label_welcome, username),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center)
        Spacer(Modifier.height(dimensionResource(R.dimen.spacingLarge)))

        Button(onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
                .height(dimensionResource(R.dimen.buttonHeight))) {
            Text(stringResource(R.string.btn_logout))
        }
    }
}