package com.example.viniloscompose.model.data.api

import com.example.viniloscompose.model.album.Album

interface IVinilosApi {
    suspend fun getAlbums() : List<Album>?

    suspend fun getAlbumById(id: Int) : Album?
}