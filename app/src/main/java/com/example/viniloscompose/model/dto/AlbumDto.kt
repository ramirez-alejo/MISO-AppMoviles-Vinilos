package com.example.viniloscompose.model.dto

data class AlbumDto (
    val id: Int,
    val name: String,
    val cover: String,
    var releaseDate: String,
    var description: String,
    var genre: String,
    var recordLabel: String,
    var performers: List<PerformerDto>
)

data class PerformerDto(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
)