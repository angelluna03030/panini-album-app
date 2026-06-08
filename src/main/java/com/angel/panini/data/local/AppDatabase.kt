package com.angel.panini.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.angel.panini.data.local.dao.StickerDao
import com.angel.panini.data.local.entity.StickerEntity

@Database(entities = [StickerEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stickerDao(): StickerDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "panini_db"
                ).build().also { INSTANCE = it }
            }
    }
}