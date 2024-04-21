package com.example.viniloscompose.data.album

import androidx.room.Dao
import androidx.room.Query
import com.example.viniloscompose.model.album.Album

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums")
    suspend fun getAlbums(): List<Album>

    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: Int): Album
}