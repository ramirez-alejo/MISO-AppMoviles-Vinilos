package com.example.viniloscompose.data.api

import com.example.viniloscompose.model.album.Album

interface IVinilosApi {
    suspend fun getAlbums() : List<Album>? {
        NotImplementedError("This method should be implemented by the subclass")
        return null
    }

    suspend fun getAlbumById(id: Int) : Album? {
        NotImplementedError("This method should be implemented by the subclass")
        return null
    }
}