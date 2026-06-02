package com.angel.panini.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.angel.panini.ui.theme.TextSecondary

@Composable
fun SearchScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("🔍 Buscar Jugador", color = TextSecondary,
            style = MaterialTheme.typography.titleLarge)
    }
}