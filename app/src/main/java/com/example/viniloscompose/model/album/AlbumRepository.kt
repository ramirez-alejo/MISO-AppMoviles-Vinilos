package com.example.viniloscompose.model.album

import com.example.viniloscompose.data.api.IVinilosApi

class AlbumRepository (
    private val cache: IAlbumCache,
    private val remote: IVinilosApi
) {
    suspend fun getAlbums() = cache.getAlbums() ?: remote.getAlbums()
    suspend fun getAlbumById(id: Int) = cache.getAlbumById(id) ?: remote.getAlbumById(id)
}