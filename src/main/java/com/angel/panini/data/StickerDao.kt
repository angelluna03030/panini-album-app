package com.angel.panini.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StickerDao {
    @Query("SELECT * FROM stickers WHERE isObtained = 1 ORDER BY code ASC")
    fun getObtainedStickers(): Flow<List<StickerEntity>>

    @Query("SELECT * FROM stickers WHERE isObtained = 0 ORDER BY code ASC")
    fun getPendingStickers(): Flow<List<StickerEntity>>

    @Query("SELECT * FROM stickers WHERE repeatedCount > 0 ORDER BY code ASC")
    fun getRepeatedStickers(): Flow<List<StickerEntity>>

    @Query("SELECT COUNT(*) FROM stickers WHERE isObtained = 1")
    fun countObtained(): Flow<Int>

    @Query("SELECT COUNT(*) FROM stickers WHERE isObtained = 0")
    fun countPending(): Flow<Int>

    @Query("SELECT SUM(repeatedCount) FROM stickers")
    fun countRepeated(): Flow<Int?>

    @Query("SELECT * FROM stickers WHERE code = :code")
    suspend fun getStickerByCode(code: String): StickerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSticker(sticker: StickerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stickers: List<StickerEntity>)

    @Update
    suspend fun updateSticker(sticker: StickerEntity)

    @Query("UPDATE stickers SET isObtained = 1 WHERE code = :code")
    suspend fun markAsObtained(code: String)

    @Query("UPDATE stickers SET repeatedCount = repeatedCount + 1 WHERE code = :code")
    suspend fun increaseRepeated(code: String)

    @Query("UPDATE stickers SET repeatedCount = repeatedCount - 1 WHERE code = :code")
    suspend fun decreaseRepeated(code: String)
}