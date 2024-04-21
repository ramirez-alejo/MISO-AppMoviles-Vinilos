package com.example.viniloscompose.model.album

data class Album (
    val id: Int,
    val name: String,
    val cover: String,
    var releaseDate: String,
    var description: String,
    var genre: String,
    var recordLabel: String
) : java.io.Serializable