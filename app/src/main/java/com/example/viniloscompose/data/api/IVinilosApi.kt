package com.example.viniloscompose.data.api

interface IVinilosApi {
    suspend fun getAlbums() {
        // Some code
    }

    suspend fun getAlbumById(id: Int) {
        // Some code
    }
}