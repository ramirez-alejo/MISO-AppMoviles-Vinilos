package com.example.viniloscompose.model.album

interface IAlbumCache {
    suspend fun getAlbums(): List<Album>?
    suspend fun getAlbumById(id: Int): Album?
}