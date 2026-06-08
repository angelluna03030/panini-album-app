package com.angel.panini.data.repository

import com.angel.panini.data.local.dao.StickerDao
import com.angel.panini.data.local.entity.StickerEntity
import com.angel.panini.data.remote.RetrofitClient
import com.angel.panini.data.remote.dto.ExchangeRequest
import com.angel.panini.data.remote.dto.RegisterRequest
import com.angel.panini.domain.model.Sticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StickerRepository(private val dao: StickerDao) {

    // ── Flujos locales (Room) ──────────────────────────────────────────────

    val obtained: Flow<List<Sticker>> = dao.getObtained().map { list ->
        list.map { it.toDomain() }
    }

    val pending: Flow<List<Sticker>> = dao.getPending().map { list ->
        list.map { it.toDomain() }
    }

    val repeated: Flow<List<Sticker>> = dao.getRepeated().map { list ->
        list.map { it.toDomain() }
    }

    val obtainedCount: Flow<Int> = dao.countObtained()
    val totalCount: Flow<Int>    = dao.countTotal()

    // ── Sync con API → guarda en Room ─────────────────────────────────────

    suspend fun syncFromApi() {
        try {
            val allStickers = mutableMapOf<String, StickerEntity>()

            // Pendientes primero (base de todas las láminas)
            RetrofitClient.paniniApi.getPending().forEach { dto ->
                allStickers[dto.code] = StickerEntity(
                    code     = dto.code,
                    name     = dto.name,
                    team     = dto.team,
                    obtained = false,
                    repeated = 0
                )
            }

            // Obtenidas
            RetrofitClient.paniniApi.getObtained().forEach { dto ->
                allStickers[dto.code] = StickerEntity(
                    code     = dto.code,
                    name     = dto.name,
                    team     = dto.team,
                    obtained = true,
                    repeated = 0
                )
            }

            // Repetidas — actualiza el contador
            RetrofitClient.paniniApi.getRepeated().forEach { dto ->
                allStickers[dto.code] = allStickers[dto.code]?.copy(repeated = dto.repeated)
                    ?: StickerEntity(
                        code     = dto.code,
                        name     = dto.name,
                        team     = dto.team,
                        obtained = true,
                        repeated = dto.repeated
                    )
            }

            dao.upsertAll(allStickers.values.toList())
        } catch (e: Exception) {
            // Si falla la red, usamos los datos locales que ya tenemos
        }
    }

    // ── Acciones ──────────────────────────────────────────────────────────

    suspend fun register(code: String): Result<String> = runCatching {
        val response = RetrofitClient.paniniApi.register(RegisterRequest(code))

        // Actualizar Room según la respuesta
        val existing = dao.getByCode(code)
        if (existing != null) {
            if (response.action == "repeated") {
                dao.upsert(existing.copy(repeated = existing.repeated + 1))
            } else {
                dao.upsert(existing.copy(obtained = true))
            }
        }
        response.message
    }

    suspend fun exchange(repeatedCode: String, newCode: String): Result<String> = runCatching {
        val response = RetrofitClient.paniniApi.exchange(
            ExchangeRequest(repeatedCode, newCode)
        )

        // Actualizar Room: bajar repetida
        dao.getByCode(repeatedCode)?.let { sticker ->
            dao.upsert(sticker.copy(repeated = maxOf(0, sticker.repeated - 1)))
        }

        // Actualizar Room: marcar nueva como obtenida
        dao.getByCode(newCode)?.let { sticker ->
            dao.upsert(sticker.copy(obtained = true))
        }

        response.message
    }

    // ── Mapper ────────────────────────────────────────────────────────────

    private fun StickerEntity.toDomain() = Sticker(
        code     = code,
        name     = name,
        team     = team,
        obtained = obtained,
        repeated = repeated
    )
}