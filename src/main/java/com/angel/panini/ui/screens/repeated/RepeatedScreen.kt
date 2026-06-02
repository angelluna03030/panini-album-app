package com.angel.panini.ui.screens.repeated

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.angel.panini.ui.theme.BlueRepeated

@Composable
fun RepeatedScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("🔁 Láminas Repetidas", color = BlueRepeated,
            style = MaterialTheme.typography.titleLarge)
    }
}