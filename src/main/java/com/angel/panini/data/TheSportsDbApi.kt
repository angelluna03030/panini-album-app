package com.angel.panini.data

import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportsDbApi {
    @GET("searchplayers.php")
    suspend fun searchPlayer(@Query("p") playerName: String): PlayerResponseDto
}