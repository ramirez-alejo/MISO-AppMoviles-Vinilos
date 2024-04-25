package com.example.viniloscompose.model.dto

data class AlbumDto (
    val id: Int,
    val name: String,
    val cover: String,
    var releaseDate: String,
    var description: String,
    var genre: String,
    var recordLabel: String
)