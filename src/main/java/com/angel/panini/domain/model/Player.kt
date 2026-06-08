package com.angel.panini.domain.model

data class Player(
    val id: String,
    val name: String,
    val nationality: String,
    val team: String,
    val position: String,
    val birthDate: String,
    val birthPlace: String,
    val photo: String,
    val height: String,
    val weight: String,
    val description: String
)