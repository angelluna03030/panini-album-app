package com.angel.panini.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [StickerEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stickerDao(): StickerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "panini_database"
                )
                    .addCallback(PrepopulateCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

private class PrepopulateCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(context)
            val dao = database.stickerDao()

            // Argentina
            val argentina = listOf(
                StickerEntity("ARG-1", "Lionel Messi", "Argentina"),
                StickerEntity("ARG-2", "Julián Álvarez", "Argentina"),
                StickerEntity("ARG-3", "Emiliano Martínez", "Argentina"),
                StickerEntity("ARG-4", "Rodrigo De Paul", "Argentina"),
                StickerEntity("ARG-5", "Lautaro Martínez", "Argentina"),
                StickerEntity("ARG-6", "Nicolás Otamendi", "Argentina"),
                StickerEntity("ARG-7", "Alexis Mac Allister", "Argentina"),
                StickerEntity("ARG-8", "Enzo Fernández", "Argentina"),
                StickerEntity("ARG-9", "Cristian Romero", "Argentina"),
                StickerEntity("ARG-10", "Ángel Di María", "Argentina"),
                StickerEntity("ARG-11", "Nahuel Molina", "Argentina"),
                StickerEntity("ARG-12", "Marcos Acuña", "Argentina"),
                StickerEntity("ARG-13", "Nicolás Tagliafico", "Argentina"),
                StickerEntity("ARG-14", "Giovani Lo Celso", "Argentina"),
                StickerEntity("ARG-15", "Leandro Paredes", "Argentina"),
                StickerEntity("ARG-16", "Paulo Dybala", "Argentina")
            )

            // Brasil
            val brasil = listOf(
                StickerEntity("BRA-1", "Neymar Jr", "Brasil"),
                StickerEntity("BRA-2", "Vinícius Jr", "Brasil"),
                StickerEntity("BRA-3", "Casemiro", "Brasil"),
                StickerEntity("BRA-4", "Alisson Becker", "Brasil"),
                StickerEntity("BRA-5", "Marquinhos", "Brasil"),
                StickerEntity("BRA-6", "Raphinha", "Brasil"),
                StickerEntity("BRA-7", "Rodrygo", "Brasil"),
                StickerEntity("BRA-8", "Bruno Guimarães", "Brasil"),
                StickerEntity("BRA-9", "Antony", "Brasil"),
                StickerEntity("BRA-10", "Richarlison", "Brasil"),
                StickerEntity("BRA-11", "Éder Militão", "Brasil"),
                StickerEntity("BRA-12", "Gabriel Jesus", "Brasil"),
                StickerEntity("BRA-13", "Lucas Paquetá", "Brasil"),
                StickerEntity("BRA-14", "Fabinho", "Brasil"),
                StickerEntity("BRA-15", "Thiago Silva", "Brasil"),
                StickerEntity("BRA-16", "Ederson", "Brasil")
            )

            // Portugal - Cristiano Ronaldo PRIMERO
            val portugal = listOf(
                StickerEntity("POR-1", "Cristiano Ronaldo", "Portugal"),
                StickerEntity("POR-2", "Bruno Fernandes", "Portugal"),
                StickerEntity("POR-3", "Bernardo Silva", "Portugal"),
                StickerEntity("POR-4", "João Félix", "Portugal"),
                StickerEntity("POR-5", "Rúben Dias", "Portugal"),
                StickerEntity("POR-6", "Diogo Jota", "Portugal"),
                StickerEntity("POR-7", "Rafael Leão", "Portugal"),
                StickerEntity("POR-8", "Pepe", "Portugal"),
                StickerEntity("POR-9", "João Cancelo", "Portugal"),
                StickerEntity("POR-10", "Diogo Costa", "Portugal"),
                StickerEntity("POR-11", "Vitinha", "Portugal"),
                StickerEntity("POR-12", "Gonçalo Ramos", "Portugal"),
                StickerEntity("POR-13", "Rúben Neves", "Portugal"),
                StickerEntity("POR-14", "João Mário", "Portugal"),
                StickerEntity("POR-15", "Danilo Pereira", "Portugal")
            )

            // España
            val espana = listOf(
                StickerEntity("ESP-1", "Pedri", "España"),
                StickerEntity("ESP-2", "Gavi", "España"),
                StickerEntity("ESP-3", "Rodri", "España"),
                StickerEntity("ESP-4", "Álvaro Morata", "España"),
                StickerEntity("ESP-5", "Dani Carvajal", "España"),
                StickerEntity("ESP-6", "Unai Simón", "España"),
                StickerEntity("ESP-7", "Ferran Torres", "España"),
                StickerEntity("ESP-8", "Mikel Oyarzabal", "España"),
                StickerEntity("ESP-9", "Aymeric Laporte", "España"),
                StickerEntity("ESP-10", "Pau Torres", "España"),
                StickerEntity("ESP-11", "Nico Williams", "España"),
                StickerEntity("ESP-12", "Dani Olmo", "España"),
                StickerEntity("ESP-13", "Alejandro Balde", "España"),
                StickerEntity("ESP-14", "Fabián Ruiz", "España"),
                StickerEntity("ESP-15", "Kepa Arrizabalaga", "España")
            )

            // Francia
            val francia = listOf(
                StickerEntity("FRA-1", "Kylian Mbappé", "Francia"),
                StickerEntity("FRA-2", "Antoine Griezmann", "Francia"),
                StickerEntity("FRA-3", "Aurélien Tchouaméni", "Francia"),
                StickerEntity("FRA-4", "Theo Hernández", "Francia"),
                StickerEntity("FRA-5", "Mike Maignan", "Francia"),
                StickerEntity("FRA-6", "Raphaël Varane", "Francia"),
                StickerEntity("FRA-7", "Ousmane Dembélé", "Francia"),
                StickerEntity("FRA-8", "Eduardo Camavinga", "Francia"),
                StickerEntity("FRA-9", "Jules Koundé", "Francia"),
                StickerEntity("FRA-10", "Adrien Rabiot", "Francia"),
                StickerEntity("FRA-11", "Olivier Giroud", "Francia"),
                StickerEntity("FRA-12", "Dayot Upamecano", "Francia"),
                StickerEntity("FRA-13", "Ibrahima Konaté", "Francia"),
                StickerEntity("FRA-14", "Kingsley Coman", "Francia"),
                StickerEntity("FRA-15", "Hugo Lloris", "Francia")
            )

            // Alemania
            val alemania = listOf(
                StickerEntity("ALE-1", "Jamal Musiala", "Alemania"),
                StickerEntity("ALE-2", "Joshua Kimmich", "Alemania"),
                StickerEntity("ALE-3", "Kai Havertz", "Alemania"),
                StickerEntity("ALE-4", "Serge Gnabry", "Alemania"),
                StickerEntity("ALE-5", "Manuel Neuer", "Alemania"),
                StickerEntity("ALE-6", "Timo Werner", "Alemania"),
                StickerEntity("ALE-7", "Leroy Sané", "Alemania"),
                StickerEntity("ALE-8", "Antonio Rüdiger", "Alemania"),
                StickerEntity("ALE-9", "Florian Wirtz", "Alemania"),
                StickerEntity("ALE-10", "Ilkay Gündogan", "Alemania"),
                StickerEntity("ALE-11", "Leon Goretzka", "Alemania"),
                StickerEntity("ALE-12", "David Raum", "Alemania"),
                StickerEntity("ALE-13", "Niklas Süle", "Alemania"),
                StickerEntity("ALE-14", "Marc-André ter Stegen", "Alemania")
            )

            // Inglaterra
            val inglaterra = listOf(
                StickerEntity("ING-1", "Jude Bellingham", "Inglaterra"),
                StickerEntity("ING-2", "Harry Kane", "Inglaterra"),
                StickerEntity("ING-3", "Bukayo Saka", "Inglaterra"),
                StickerEntity("ING-4", "Declan Rice", "Inglaterra"),
                StickerEntity("ING-5", "Phil Foden", "Inglaterra"),
                StickerEntity("ING-6", "Jordan Pickford", "Inglaterra"),
                StickerEntity("ING-7", "John Stones", "Inglaterra"),
                StickerEntity("ING-8", "Marcus Rashford", "Inglaterra"),
                StickerEntity("ING-9", "Kyle Walker", "Inglaterra"),
                StickerEntity("ING-10", "Luke Shaw", "Inglaterra"),
                StickerEntity("ING-11", "Mason Mount", "Inglaterra"),
                StickerEntity("ING-12", "Raheem Sterling", "Inglaterra"),
                StickerEntity("ING-13", "Kalvin Phillips", "Inglaterra"),
                StickerEntity("ING-14", "Trent Alexander-Arnold", "Inglaterra"),
                StickerEntity("ING-15", "Jack Grealish", "Inglaterra")
            )

            val allStickers = argentina + brasil + portugal + espana + francia + alemania + inglaterra
            dao.insertAll(allStickers)
        }
    }
}