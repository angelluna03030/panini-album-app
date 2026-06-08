package com.angel.panini.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stickers")
data class StickerEntity(
    @PrimaryKey val code: String,
    val name: String,
    val team: String,
    val obtained: Boolean = false,
    val repeated: Int = 0
)