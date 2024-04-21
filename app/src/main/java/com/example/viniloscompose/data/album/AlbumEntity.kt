package com.example.viniloscompose.data.album

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val cover: String,
    var releaseDate: String,
    var description: String,
    var genre: String,
    var recordLabel: String
)