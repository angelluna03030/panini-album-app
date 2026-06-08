package com.angel.panini.data.remote

import com.angel.panini.data.remote.dto.*
import retrofit2.http.*

interface PaniniApiService {
    @GET("stickers/pending")
    suspend fun getPending(): List<StickerDto>

    @GET("stickers/obtained")
    suspend fun getObtained(): List<StickerDto>

    @GET("stickers/repeated")
    suspend fun getRepeated(): List<StickerDto>

    @POST("stickers/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("stickers/exchange")
    suspend fun exchange(@Body request: ExchangeRequest): ExchangeResponse

    @GET("players/search")
    suspend fun searchPlayer(@Query("name") name: String): PlayerSearchResponse
}