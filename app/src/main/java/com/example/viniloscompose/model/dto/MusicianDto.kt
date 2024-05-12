package com.example.viniloscompose.model.dto

import kotlinx.serialization.Serializable


@Serializable
data class MusicianDto(
    val id: Int,
    val name: String,
    val image: String,
    var description: String,
    var birthDate: String,
    var albums: List<MusicianAlbumDetailDto>
)

@Serializable
data class MusicianAlbumDetailDto(
    val id: Int,
    val name: String,
    val cover: String,
    var releaseDate: String = "",
    var description: String = "",
    var genre: String = "",
    var recordLabel: String= ""
)


