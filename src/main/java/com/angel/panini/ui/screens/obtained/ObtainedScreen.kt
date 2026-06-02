package com.angel.panini.ui.screens.obtained

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.angel.panini.ui.theme.GreenObtained

@Composable
fun ObtainedScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("✅ Láminas Obtenidas", color = GreenObtained,
            style = MaterialTheme.typography.titleLarge)
    }
}