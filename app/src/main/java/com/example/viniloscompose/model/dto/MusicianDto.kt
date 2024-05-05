package com.example.viniloscompose.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class MusicianDto(
    val id: Int,
    val name: String,
    val image: String
)
