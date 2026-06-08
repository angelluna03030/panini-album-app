package com.angel.panini.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StickerDto(
    @SerializedName("code")     val code: String,
    @SerializedName("name")     val name: String,
    @SerializedName("team")     val team: String,
    @SerializedName("obtained") val obtained: Boolean = false,
    @SerializedName("repeated") val repeated: Int = 0
)

data class RegisterRequest(
    @SerializedName("code") val code: String
)

data class RegisterResponse(
    @SerializedName("message") val message: String,
    @SerializedName("action")  val action: String   // "new" | "repeated"
)

data class ExchangeRequest(
    @SerializedName("repeatedCode") val repeatedCode: String,
    @SerializedName("newCode")      val newCode: String
)

data class ExchangeResponse(
    @SerializedName("message")   val message: String,
    @SerializedName("givenAway") val givenAway: String,
    @SerializedName("received")  val received: String
)