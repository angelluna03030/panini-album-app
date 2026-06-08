package com.angel.panini.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angel.panini.data.repository.PlayerRepository
import com.angel.panini.domain.model.Player
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class SearchUiState {
    object Idle      : SearchUiState()
    object Loading   : SearchUiState()
    object NotFound  : SearchUiState()
    data class Success(val players: List<Player>) : SearchUiState()
    data class Error(val message: String)         : SearchUiState()
}

class SearchViewModel : ViewModel() {

    private val repo = PlayerRepository()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun search(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            val result = repo.searchPlayer(name)
            _uiState.value = result.fold(
                onSuccess = { players ->
                    if (players.isEmpty()) SearchUiState.NotFound
                    else SearchUiState.Success(players)
                },
                onFailure = { SearchUiState.Error(it.message ?: "Error de conexión") }
            )
        }
    }
}