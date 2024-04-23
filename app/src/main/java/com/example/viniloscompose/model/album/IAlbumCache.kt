package com.example.viniloscompose.model.album

interface IAlbumCache {
    suspend fun getAlbums(): List<Album>?
    suspend fun getAlbumById(id: Int): Album?

    suspend fun storeAlbums(albums: List<Album>)
    suspend fun storeAlbum(album: Album)
    suspend fun deleteAllAlbums()
}