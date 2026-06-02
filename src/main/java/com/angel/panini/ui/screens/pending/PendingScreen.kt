package com.angel.panini.ui.screens.pending
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.angel.panini.ui.theme.RedPending

@Composable
fun PendingScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("⏳ Láminas Pendientes", color = RedPending,
            style = MaterialTheme.typography.titleLarge)
    }
}