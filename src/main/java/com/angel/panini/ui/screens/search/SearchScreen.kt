package com.angel.panini.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.angel.panini.domain.model.Player
import com.angel.panini.ui.theme.*

@Composable
fun SearchScreen(vm: SearchViewModel = viewModel()) {
    val state       by vm.uiState.collectAsState()
    var query       by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface0)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(20.dp))
        Text("Buscar Jugador", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        // Barra de búsqueda
        OutlinedTextField(
            value         = query,
            onValueChange = { query = it },
            placeholder   = { Text("Ej: Cristiano Ronaldo", color = TextTertiary) },
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(12.dp),
            colors        = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = GoldFIFA,
                unfocusedBorderColor = BorderSubtle,
                focusedTextColor     = TextPrimary,
                unfocusedTextColor   = TextPrimary,
                cursorColor          = GoldFIFA,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                vm.search(query)
                focusManager.clearFocus()
            }),
            trailingIcon = {
                TextButton(onClick = {
                    vm.search(query)
                    focusManager.clearFocus()
                }) {
                    Text("Buscar", color = GoldFIFA, fontWeight = FontWeight.Bold)
                }
            },
            singleLine = true
        )

        Spacer(Modifier.height(24.dp))

        when (val s = state) {
            is SearchUiState.Idle -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Busca un jugador por su nombre",
                        color = TextTertiary,
                        style = MaterialTheme.typography.bodyMedium)
                }
            }
            is SearchUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = GoldFIFA)
                }
            }
            is SearchUiState.NotFound -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("😕", style = MaterialTheme.typography.headlineLarge)
                        Spacer(Modifier.height(8.dp))
                        Text("Jugador no encontrado",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextSecondary)
                    }
                }
            }
            is SearchUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(s.message, color = RedPending)
                }
            }
            is SearchUiState.Success -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(s.players, key = { it.id }) { player ->
                        PlayerCard(player)
                    }
                    item { Spacer(Modifier.height(8.dp)) }
                }
            }
        }
    }
}

@Composable
private fun PlayerCard(player: Player) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors   = CardDefaults.cardColors(containerColor = Surface1),
        shape    = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Foto
                if (player.photo.isNotEmpty()) {
                    AsyncImage(
                        model             = player.photo,
                        contentDescription = player.name,
                        modifier          = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Surface2),
                        contentScale      = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier          = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Surface2),
                        contentAlignment  = Alignment.Center
                    ) {
                        Text("?", style = MaterialTheme.typography.headlineMedium,
                            color = TextTertiary)
                    }
                }

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(player.name,
                        style      = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold)
                    Text(player.team,
                        style = MaterialTheme.typography.bodyMedium,
                        color = GoldFIFA)
                    Text(player.nationality,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary)
                }
            }

            if (player.position.isNotEmpty() || player.birthDate.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = BorderSubtle)
                Spacer(Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    if (player.position.isNotEmpty())
                        InfoChip("⚽", player.position)
                    if (player.birthDate.isNotEmpty())
                        InfoChip("🎂", player.birthDate)
                    if (player.height.isNotEmpty())
                        InfoChip("📏", player.height)
                }
            }
        }
    }
}

@Composable
private fun InfoChip(icon: String, text: String) {
    Row(
        modifier          = Modifier
            .background(Surface2, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(icon, style = MaterialTheme.typography.labelSmall)
        Text(text, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
    }
}