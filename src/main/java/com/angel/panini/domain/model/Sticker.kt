package com.angel.panini.domain.model

data class Sticker(
    val code: String,
    val name: String,
    val team: String,
    val obtained: Boolean,
    val repeated: Int
)