package com.angel.panini.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerResponseDto(
    val player: List<PlayerDto>?
)

@Serializable
data class PlayerDto(
    @SerialName("idPlayer") val idPlayer: String = "",
    @SerialName("strPlayer") val strPlayer: String = "",
    @SerialName("strTeam") val strTeam: String = "",
    @SerialName("strSport") val strSport: String = "",
    @SerialName("strThumb") val strThumb: String = "",
    @SerialName("strCutout") val strCutout: String = "",
    @SerialName("strNationality") val strNationality: String = "",
    @SerialName("dateBorn") val dateBorn: String = "",
    @SerialName("strStatus") val strStatus: String = "",
    @SerialName("strGender") val strGender: String = "",
    @SerialName("strPosition") val strPosition: String = "",
    @SerialName("strDescriptionEN") val strDescriptionEN: String? = null
)