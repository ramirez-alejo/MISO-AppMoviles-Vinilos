package com.example.viniloscompose.model.data.album

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.viniloscompose.model.data.BaseDao

@Dao
interface AlbumDao : BaseDao<AlbumEntity> {
    @Query("SELECT * FROM albums")
    fun getAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM albums WHERE id = :id")
    fun getAlbumById(id: Int): AlbumEntity

    @Query("DELETE FROM albums")
    fun deleteAllAlbums()

}

@Transaction
suspend fun AlbumDao.replaceAll(vararg albums: AlbumEntity) {
    deleteAllAlbums()
    insert(*albums)
}