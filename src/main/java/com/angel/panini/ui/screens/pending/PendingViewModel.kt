package com.angel.panini.ui.screens.pending

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.angel.panini.data.local.AppDatabase
import com.angel.panini.data.repository.StickerRepository
import com.angel.panini.domain.model.Sticker
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PendingUiState(
    val stickers: List<Sticker> = emptyList(),
    val isLoading: Boolean      = false,
    val message: String?        = null
)

class PendingViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = StickerRepository(
        AppDatabase.getInstance(app).stickerDao()
    )

    private val _uiState = MutableStateFlow(PendingUiState())
    val uiState: StateFlow<PendingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.syncFromApi()
            repo.pending.collect { list ->
                _uiState.update { it.copy(stickers = list) }
            }
        }
    }

    fun register(code: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = null) }
            val result = repo.register(code)
            _uiState.update { it.copy(
                isLoading = false,
                message   = result.getOrElse { "Error al registrar" }
            )}
        }
    }

    fun clearMessage() = _uiState.update { it.copy(message = null) }
}