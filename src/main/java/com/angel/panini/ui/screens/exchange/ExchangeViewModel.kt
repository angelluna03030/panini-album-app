package com.angel.panini.ui.screens.exchange

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.angel.panini.data.local.AppDatabase
import com.angel.panini.data.repository.StickerRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ExchangeUiState(
    val isLoading: Boolean = false,
    val message: String?   = null,
    val isSuccess: Boolean = false
)

class ExchangeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = StickerRepository(
        AppDatabase.getInstance(app).stickerDao()
    )

    private val _uiState = MutableStateFlow(ExchangeUiState())
    val uiState: StateFlow<ExchangeUiState> = _uiState.asStateFlow()

    fun exchange(repeatedCode: String, newCode: String) {
        if (repeatedCode.isBlank() || newCode.isBlank()) {
            _uiState.update { it.copy(message = "Completa ambos campos") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = null, isSuccess = false) }
            val result = repo.exchange(repeatedCode.trim().uppercase(), newCode.trim().uppercase())
            _uiState.update { it.copy(
                isLoading = false,
                message   = result.getOrElse { e -> e.message ?: "Error al intercambiar" },
                isSuccess = result.isSuccess
            )}
        }
    }

    fun clearMessage() = _uiState.update { it.copy(message = null, isSuccess = false) }
}