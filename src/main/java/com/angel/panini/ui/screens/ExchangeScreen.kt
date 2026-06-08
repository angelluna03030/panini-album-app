package com.angel.panini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.angel.panini.ui.viewmodels.StickerViewModel

@Composable
fun ExchangeScreen(
    viewModel: StickerViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val repeatedStickers by viewModel.repeatedStickers.collectAsState()
    var repeatedCode by remember { mutableStateOf("") }
    var newCode by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Intercambiar Lámina",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = repeatedCode,
            onValueChange = { repeatedCode = it.uppercase() },
            label = { Text("Lámina repetida a entregar") },
            placeholder = { Text("Ej: ARG-3") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newCode,
            onValueChange = { newCode = it.uppercase() },
            label = { Text("Lámina nueva a recibir") },
            placeholder = { Text("Ej: ARG-4") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (repeatedCode.isNotBlank() && newCode.isNotBlank()) {
                    viewModel.exchangeSticker(repeatedCode, newCode)
                    message = "✅ Intercambio exitoso: diste $repeatedCode y recibiste $newCode"
                    repeatedCode = ""
                    newCode = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Intercambiar")
        }

        if (message.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (repeatedStickers.isNotEmpty()) {
            Text(
                text = "Tus láminas repetidas:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            repeatedStickers.forEach { sticker ->
                Text(
                    text = "• ${sticker.code} (${sticker.playerName}) - x${sticker.repeatedCount}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Inicio")
        }
    }
}