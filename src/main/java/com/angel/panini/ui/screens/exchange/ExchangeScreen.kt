package com.angel.panini.ui.screens.exchange

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.angel.panini.ui.theme.GoldFIFA

@Composable
fun ExchangeScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("🔄 Intercambiar Lámina", color = GoldFIFA,
            style = MaterialTheme.typography.titleLarge)
    }
}