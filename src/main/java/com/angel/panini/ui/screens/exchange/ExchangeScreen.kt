package com.angel.panini.ui.screens.exchange

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.angel.panini.ui.theme.*

@Composable
fun ExchangeScreen(vm: ExchangeViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()

    var repeatedCode by remember { mutableStateOf("") }
    var newCode      by remember { mutableStateOf("") }

    val snackbarHost = remember { SnackbarHostState() }
    LaunchedEffect(state.message) {
        state.message?.let {
            snackbarHost.showSnackbar(it)
            if (state.isSuccess) {
                repeatedCode = ""
                newCode      = ""
            }
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))

            Text("Intercambio", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                "Da una repetida, recibe una que te falta",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(Modifier.height(40.dp))

            // Campo lámina que das
            Text("Lámina que entregas (repetida)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value         = repeatedCode,
                onValueChange = { repeatedCode = it.uppercase() },
                placeholder   = { Text("Ej: ARG-3", color = TextTertiary) },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(12.dp),
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = RedPending,
                    unfocusedBorderColor = BorderSubtle,
                    focusedTextColor     = TextPrimary,
                    unfocusedTextColor   = TextPrimary,
                    cursorColor          = RedPending,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction      = ImeAction.Next
                ),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            // Flecha visual
            Text("⬇", style = MaterialTheme.typography.headlineLarge,
                color = GoldFIFA)

            Spacer(Modifier.height(8.dp))

            // Campo lámina que recibes
            Text("Lámina que recibes (faltante)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value         = newCode,
                onValueChange = { newCode = it.uppercase() },
                placeholder   = { Text("Ej: ARG-4", color = TextTertiary) },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(12.dp),
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = GreenObtained,
                    unfocusedBorderColor = BorderSubtle,
                    focusedTextColor     = TextPrimary,
                    unfocusedTextColor   = TextPrimary,
                    cursorColor          = GreenObtained,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction      = ImeAction.Done
                ),
                singleLine = true
            )

            Spacer(Modifier.height(32.dp))

            // Botón
            Button(
                onClick  = { vm.exchange(repeatedCode, newCode) },
                enabled  = !state.isLoading,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = GoldFIFA),
                shape    = RoundedCornerShape(12.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color    = Surface0,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Confirmar intercambio", color = Surface0,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}