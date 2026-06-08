package com.angel.panini.ui.screens.pending

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.angel.panini.domain.model.Sticker
import com.angel.panini.ui.theme.*

@Composable
fun PendingScreen(vm: PendingViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()

    // Snackbar
    val snackbarHost = remember { SnackbarHostState() }
    LaunchedEffect(state.message) {
        state.message?.let {
            snackbarHost.showSnackbar(it)
            vm.clearMessage()
        }
    }

    Scaffold(
        containerColor = Surface0,
        snackbarHost   = { SnackbarHost(snackbarHost) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(20.dp))
            Text("Pendientes", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                "${state.stickers.size} láminas por conseguir",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Spacer(Modifier.height(16.dp))

            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = GoldFIFA)
                }
            } else if (state.stickers.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("🎉 ¡Álbum completo!", color = GreenObtained,
                        style = MaterialTheme.typography.titleLarge)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.stickers, key = { it.code }) { sticker ->
                        PendingRow(sticker, onRegister = { vm.register(sticker.code) })
                    }
                    item { Spacer(Modifier.height(8.dp)) }
                }
            }
        }
    }
}

@Composable
private fun PendingRow(sticker: Sticker, onRegister: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors   = CardDefaults.cardColors(containerColor = Surface1),
        shape    = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier          = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Surface2, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(sticker.code, style = MaterialTheme.typography.labelSmall,
                    color = RedPending, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(sticker.name, style = MaterialTheme.typography.titleMedium)
                Text(sticker.team, style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary)
            }

            Button(
                onClick  = onRegister,
                colors   = ButtonDefaults.buttonColors(containerColor = GoldFIFA),
                shape    = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("Registrar", color = Surface0,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}