package com.angel.panini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.angel.panini.ui.viewmodels.StickerViewModel

@Composable
fun HomeScreen(
    viewModel: StickerViewModel = viewModel(),
    onNavigateToRegister: () -> Unit = {},
    onNavigateToExchange: () -> Unit = {}
) {
    val obtainedCount by viewModel.obtainedCount.collectAsState()
    val pendingCount by viewModel.pendingCount.collectAsState()
    val repeatedCount by viewModel.repeatedCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "⚽ Panini 2026",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Contador de Obtenidas
        CounterCard(
            title = "Obtenidas",
            count = obtainedCount,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contador de Pendientes
        CounterCard(
            title = "Pendientes",
            count = pendingCount,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contador de Repetidas
        CounterCard(
            title = "Repetidas",
            count = repeatedCount,
            color = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botones de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onNavigateToRegister,
                modifier = Modifier.weight(1f)
            ) {
                Text("Registrar")
            }

            Button(
                onClick = onNavigateToExchange,
                modifier = Modifier.weight(1f)
            ) {
                Text("Intercambiar")
            }
        }
    }
}

@Composable
fun CounterCard(title: String, count: Int, color: androidx.compose.ui.graphics.Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}