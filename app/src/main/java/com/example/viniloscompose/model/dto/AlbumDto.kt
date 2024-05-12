package com.example.viniloscompose.model.dto
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto (
    val id: Int,
    val name: String,
    val cover: String,
    var releaseDate: String?,
    var description: String,
    var genre: String,
    var recordLabel: String,
    var performers: List<PerformerDto>,
    var tracks: List<TracksDto>? = null
)

@Serializable
data class PerformerDto(
    val id: Int,
    val name: String,
    val image: String,
    val description: String?,
    val birthDate: String?,
)

@Serializable
data class TracksDto(
    val id: Int,
    val name: String,
    val duration: String
)