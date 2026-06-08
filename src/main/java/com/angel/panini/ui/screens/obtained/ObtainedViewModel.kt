package com.angel.panini.ui.screens.obtained

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.angel.panini.data.local.AppDatabase
import com.angel.panini.data.repository.StickerRepository
import com.angel.panini.domain.model.Sticker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ObtainedViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = StickerRepository(
        AppDatabase.getInstance(app).stickerDao()
    )

    val stickers: StateFlow<List<Sticker>> = repo.obtained.stateIn(
        scope         = viewModelScope,
        started       = SharingStarted.WhileSubscribed(5000),
        initialValue  = emptyList()
    )

    val obtainedCount: StateFlow<Int> = repo.obtainedCount.stateIn(
        scope        = viewModelScope,
        started      = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    val totalCount: StateFlow<Int> = repo.totalCount.stateIn(
        scope        = viewModelScope,
        started      = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    init {
        viewModelScope.launch { repo.syncFromApi() }
    }
}