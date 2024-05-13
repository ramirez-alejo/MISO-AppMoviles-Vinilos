package com.example.viniloscompose.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class CollectorDto(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<CommentDto>
)

@Serializable
data class CommentDto(
    val id: Int,
    val description: String,
    val rating: Int
)