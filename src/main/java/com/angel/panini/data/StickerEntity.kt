package com.angel.panini.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stickers")
data class StickerEntity(
    @PrimaryKey
    val code: String,           // Ej: "ARG-1", "BRA-5"
    val playerName: String,     // Nombre del jugador
    val team: String,           // País/equipo
    val isObtained: Boolean = false,
    val repeatedCount: Int = 0
)