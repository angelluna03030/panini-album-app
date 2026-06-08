package com.angel.panini.data.repository

import com.angel.panini.data.remote.RetrofitClient
import com.angel.panini.domain.model.Player

class PlayerRepository {

    suspend fun searchPlayer(name: String): Result<List<Player>> = runCatching {
        val response = RetrofitClient.paniniApi.searchPlayer(name)
        response.players?.map { dto ->
            Player(
                id          = dto.id,
                name        = dto.name,
                nationality = dto.nationality.orEmpty(),
                team        = dto.team.orEmpty(),
                position    = dto.position.orEmpty(),
                birthDate   = dto.birthDate.orEmpty(),
                birthPlace  = dto.birthPlace.orEmpty(),
                photo       = dto.photo.orEmpty(),
                height      = dto.height.orEmpty(),
                weight      = dto.weight.orEmpty(),
                description = dto.description.orEmpty()
            )
        } ?: emptyList()
    }
}