package com.angel.panini.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.angel.panini.data.AppDatabase
import com.angel.panini.data.NetworkModule
import com.angel.panini.data.PlayerDto
import com.angel.panini.data.StickerEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StickerViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).stickerDao()

    val obtainedCount: StateFlow<Int> = dao.countObtained()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val pendingCount: StateFlow<Int> = dao.countPending()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val repeatedCount: StateFlow<Int> = dao.countRepeated()
        .map { it ?: 0 }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val obtainedStickers = dao.getObtainedStickers()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val pendingStickers = dao.getPendingStickers()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val repeatedStickers = dao.getRepeatedStickers()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun registerSticker(code: String) {
        viewModelScope.launch {
            val sticker = dao.getStickerByCode(code)
            if (sticker != null) {
                if (!sticker.isObtained) {
                    dao.markAsObtained(code)
                } else {
                    dao.increaseRepeated(code)
                }
            }
        }
    }

    fun exchangeSticker(repeatedCode: String, newCode: String) {
        viewModelScope.launch {
            val repeatedSticker = dao.getStickerByCode(repeatedCode)
            val newSticker = dao.getStickerByCode(newCode)

            if (repeatedSticker != null && repeatedSticker.repeatedCount > 0) {
                dao.decreaseRepeated(repeatedCode)
                if (newSticker != null && !newSticker.isObtained) {
                    dao.markAsObtained(newCode)
                }
            }
        }
    }

    suspend fun searchPlayer(name: String): PlayerDto? {
        return try {
            val response = NetworkModule.theSportsDbApi.searchPlayer(name)
            response.player?.firstOrNull()
        } catch (e: Exception) {
            null
        }
    }
}