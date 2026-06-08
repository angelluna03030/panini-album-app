package com.angel.panini.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PlayerSearchResponse(
    @SerializedName("players")  val players: List<PlayerDto>?,
    @SerializedName("message")  val message: String?
)

data class PlayerDto(
    @SerializedName("id")          val id: String,
    @SerializedName("name")        val name: String,
    @SerializedName("nationality") val nationality: String?,
    @SerializedName("team")        val team: String?,
    @SerializedName("position")    val position: String?,
    @SerializedName("birthDate")   val birthDate: String?,
    @SerializedName("birthPlace")  val birthPlace: String?,
    @SerializedName("photo")       val photo: String?,
    @SerializedName("height")      val height: String?,
    @SerializedName("weight")      val weight: String?,
    @SerializedName("description") val description: String?
)