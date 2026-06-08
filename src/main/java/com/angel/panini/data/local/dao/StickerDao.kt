package com.angel.panini.data.local.dao

import androidx.room.*
import com.angel.panini.data.local.entity.StickerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StickerDao {

    @Query("SELECT * FROM stickers WHERE obtained = 1 ORDER BY team, code")
    fun getObtained(): Flow<List<StickerEntity>>

    @Query("SELECT * FROM stickers WHERE obtained = 0 ORDER BY team, code")
    fun getPending(): Flow<List<StickerEntity>>

    @Query("SELECT * FROM stickers WHERE repeated > 0 ORDER BY team, code")
    fun getRepeated(): Flow<List<StickerEntity>>

    @Query("SELECT * FROM stickers WHERE code = :code LIMIT 1")
    suspend fun getByCode(code: String): StickerEntity?

    @Upsert
    suspend fun upsert(sticker: StickerEntity)

    @Upsert
    suspend fun upsertAll(stickers: List<StickerEntity>)

    @Query("SELECT COUNT(*) FROM stickers WHERE obtained = 1")
    fun countObtained(): Flow<Int>

    @Query("SELECT COUNT(*) FROM stickers")
    fun countTotal(): Flow<Int>
}