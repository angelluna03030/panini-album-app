package com.angel.panini.ui.screens.repeated

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.angel.panini.data.local.AppDatabase
import com.angel.panini.data.repository.StickerRepository
import com.angel.panini.domain.model.Sticker
import kotlinx.coroutines.flow.*

class RepeatedViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = StickerRepository(
        AppDatabase.getInstance(app).stickerDao()
    )

    val stickers: StateFlow<List<Sticker>> = repo.repeated.stateIn(
        scope        = viewModelScope,
        started      = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}