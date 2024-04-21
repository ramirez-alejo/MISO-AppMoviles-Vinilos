package com.example.viniloscompose.data.album

import com.example.viniloscompose.model.album.Album

fun Album.toEntity() = AlbumEntity(
    id = id,
    name = name,
    cover = cover,
    releaseDate = releaseDate,
    description = description,
    genre = genre,
    recordLabel = recordLabel
)

fun AlbumEntity.toAlbum() = Album(
    id = id,
    name = name,
    cover = cover,
    releaseDate = releaseDate,
    description = description,
    genre = genre,
    recordLabel = recordLabel
)