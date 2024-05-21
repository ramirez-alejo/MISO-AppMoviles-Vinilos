package com.example.viniloscompose.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class CollectorDto(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<CommentDto>,
    var favoritePerformers: List<FavoritePerformerDto>  = emptyList(),
    var collectorAlbums: List<CollectorAlbumDto>  = emptyList()
)

@Serializable
data class CommentDto(
    val id: Int,
    val description: String,
    val rating: Int
)

@Serializable
data class  FavoritePerformerDto(
    val id: Int
)

@Serializable
data class  CollectorAlbumDto(
    val id: Int
)