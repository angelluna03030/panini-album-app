package com.angel.panini.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.angel.panini.data.NetworkModule
import com.angel.panini.data.PlayerDto

// Paleta de colores estilo Panini
private val GoldGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFD700),
        Color(0xFFFFA500),
        Color(0xFFFFD700),
        Color(0xFFFFEC8B),
        Color(0xFFFFD700)
    ),
    start = Offset(0f, 0f),
    end = Offset(1000f, 1000f)
)

private val SilverGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFC0C0C0),
        Color(0xFFE8E8E8),
        Color(0xFFC0C0C0)
    )
)

private val DarkGold = Color(0xFFB8860B)
private val PaniniRed = Color(0xFFC41E3A)
private val PaniniBlue = Color(0xFF002868)

// Mapa de banderas por país
private val countryFlags = mapOf(
    "Argentina" to "🇦🇷",
    "Brasil" to "🇧🇷",
    "Brazil" to "🇧🇷",
    "España" to "🇪🇸",
    "Spain" to "🇪🇸",
    "Francia" to "🇫🇷",
    "France" to "🇫🇷",
    "Portugal" to "🇵🇹",
    "Alemania" to "🇩🇪",
    "Germany" to "🇩🇪",
    "Inglaterra" to "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
    "England" to "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
    "Portugual" to "🇵🇹"
)

@Composable
fun PlayerDetailScreen(
    playerName: String,
    onNavigateBack: () -> Unit
) {
    var player by remember { mutableStateOf<PlayerDto?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(playerName) {
        try {
            val response = NetworkModule.theSportsDbApi.searchPlayer(playerName)
            player = response.player?.firstOrNull()
            isLoading = false
            if (player == null) {
                errorMessage = "No se encontró información del jugador"
            }
        } catch (e: Exception) {
            errorMessage = "Error al cargar: ${e.message}"
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F3460)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header con botón volver
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
                Text(
                    text = "FIFA World Cup 2026™",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFFFD700),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Spacer(modifier = Modifier.height(100.dp))
                CircularProgressIndicator(color = Color(0xFFFFD700))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Cargando ficha...", color = Color.White)
            } else if (errorMessage != null) {
                Spacer(modifier = Modifier.height(100.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A3E)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("😕", style = MaterialTheme.typography.displayLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = errorMessage!!,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else if (player != null) {
                // ===== FICHA PANINI =====
                PaniniCard(player!!)
            }
        }
    }
}

@Composable
fun PaniniCard(player: PlayerDto) {
    val flag = countryFlags[player.strNationality] ?: "⚽"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(16.dp, RoundedCornerShape(20.dp))
            .border(
                width = 3.dp,
                brush = GoldGradient,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF8DC),
                        Color(0xFFFFFACD),
                        Color(0xFFFFF8DC)
                    )
                )
            )
    ) {
        Column {
            // === HEADER DE LA FICHA ===
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PaniniRed)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⭐ WORLD CUP 2026 ⭐",
                        color = Color(0xFFFFD700),
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = flag,
                        fontSize = 20.sp
                    )
                }
            }

            // === FOTO DEL JUGADOR ===
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                PaniniBlue.copy(alpha = 0.8f),
                                PaniniBlue.copy(alpha = 0.4f),
                                Color(0xFFE0E0E0)
                            )
                        )
                    )
            ) {
                // Foto
                if (player.strCutout.isNotBlank()) {
                    AsyncImage(
                        model = player.strCutout,
                        contentDescription = player.strPlayer,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.BottomCenter
                    )
                } else if (player.strThumb.isNotBlank()) {
                    AsyncImage(
                        model = player.strThumb,
                        contentDescription = player.strPlayer,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.BottomCenter
                    )
                }

                // Badge de posición
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(56.dp)
                        .shadow(8.dp, CircleShape)
                        .background(
                            brush = GoldGradient,
                            shape = CircleShape
                        )
                        .border(2.dp, DarkGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = getPositionAbbreviation(player.strPosition),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                }

                // Badge de país
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .shadow(8.dp, RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.95f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "${flag} ${player.strNationality}",
                        fontWeight = FontWeight.Bold,
                        color = PaniniBlue,
                        fontSize = 14.sp
                    )
                }
            }

            // === NOMBRE DEL JUGADOR ===
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(PaniniBlue, PaniniBlue.copy(alpha = 0.8f), PaniniBlue)
                        )
                    )
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = player.strPlayer.uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = player.strTeam,
                        color = Color(0xFFFFD700),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            // === ESTADÍSTICAS ===
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                // Título de estadísticas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .weight(1f)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color.Transparent, DarkGold, Color.Transparent)
                                )
                            )
                    )
                    Text(
                        text = "  STATS  ",
                        color = DarkGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 2.sp
                    )
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .weight(1f)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color.Transparent, DarkGold, Color.Transparent)
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Grid de estadísticas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        icon = Icons.Default.Work,
                        label = "Posición",
                        value = player.strPosition
                    )
                    StatItem(
                        icon = Icons.Default.CalendarMonth,
                        label = "Nacimiento",
                        value = formatDate(player.dateBorn)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        icon = Icons.Default.SportsSoccer,
                        label = "Deporte",
                        value = player.strSport
                    )
                    StatItem(
                        icon = Icons.Default.Person,
                        label = "Estado",
                        value = player.strStatus
                    )
                }

                if (player.strGender.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        StatItem(
                            icon = Icons.Default.LocationOn,
                            label = "Género",
                            value = player.strGender
                        )
                    }
                }
            }

            // === FOOTER DORADO ===
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = GoldGradient
                    )
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "© PANINI FIFA WORLD CUP 2026™",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}

@Composable
fun StatItem(icon: ImageVector, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = PaniniBlue.copy(alpha = 0.1f),
                    shape = CircleShape
                )
                .border(1.dp, PaniniBlue.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = PaniniBlue,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Funciones auxiliares
private fun getPositionAbbreviation(position: String): String {
    return when {
        position.contains("Forward", ignoreCase = true) ||
                position.contains("Striker", ignoreCase = true) -> "FW"
        position.contains("Midfield", ignoreCase = true) -> "MF"
        position.contains("Defend", ignoreCase = true) ||
                position.contains("Back", ignoreCase = true) -> "DF"
        position.contains("Goalkeeper", ignoreCase = true) ||
                position.contains("Keeper", ignoreCase = true) -> "GK"
        else -> position.take(2).uppercase()
    }
}

private fun formatDate(date: String): String {
    return try {
        if (date.length >= 4) {
            val parts = date.split("-")
            if (parts.size == 3) "${parts[2]}/${parts[1]}/${parts[0].takeLast(2)}"
            else date
        } else date
    } catch (e: Exception) {
        date
    }
}