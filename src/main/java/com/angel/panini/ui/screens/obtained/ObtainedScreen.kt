package com.angel.panini.ui.screens.obtained

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.angel.panini.domain.model.Sticker
import com.angel.panini.ui.theme.*

@Composable
fun ObtainedScreen(vm: ObtainedViewModel = viewModel()) {
    val stickers     by vm.stickers.collectAsState()
    val obtained     by vm.obtainedCount.collectAsState()
    val total        by vm.totalCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface0)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(20.dp))

        // Header
        Text("Obtenidas", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(4.dp))

        // Barra de progreso
        LinearProgressIndicator(
            progress        = { if (total > 0) obtained / total.toFloat() else 0f },
            modifier        = Modifier.fillMaxWidth().height(6.dp),
            color           = GreenObtained,
            trackColor      = Surface2,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "$obtained / $total láminas",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(Modifier.height(16.dp))

        if (stickers.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aún no tienes láminas obtenidas", color = TextTertiary)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(stickers, key = { it.code }) { sticker ->
                    StickerRow(sticker)
                }
                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}

@Composable
private fun StickerRow(sticker: Sticker) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors   = CardDefaults.cardColors(containerColor = Surface1),
        shape    = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier            = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment   = Alignment.CenterVertically
        ) {
            // Badge código
            Box(
                modifier          = Modifier
                    .background(Surface2, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(sticker.code, style = MaterialTheme.typography.labelSmall,
                    color = GoldFIFA, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(sticker.name, style = MaterialTheme.typography.titleMedium)
                Text(sticker.team, style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary)
            }

            // Badge obtenida
            Box(
                modifier = Modifier
                    .background(GreenObtained.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("✓", color = GreenObtained,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}