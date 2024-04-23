package com.example.viniloscompose.data.api

import com.example.viniloscompose.model.album.Album

interface IVinilosApi {
    suspend fun getAlbums() : List<Album>?

    suspend fun getAlbumById(id: Int) : Album?
}