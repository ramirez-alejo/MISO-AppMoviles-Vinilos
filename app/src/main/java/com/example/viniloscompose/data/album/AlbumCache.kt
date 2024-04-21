package com.example.viniloscompose.data.album

import com.example.viniloscompose.model.album.Album
import com.example.viniloscompose.model.album.IAlbumCache
import com.example.viniloscompose.utils.Cache
import com.example.viniloscompose.utils.IExceptionHandler

class AlbumCache(
    private val dao: AlbumDao,
    exceptionHandler: IExceptionHandler
) : Cache(exceptionHandler), IAlbumCache {
    override suspend fun getAlbums(): List<Album>? {
        return runQuery { dao.getAlbums() }
    }

    override suspend fun getAlbumById(id: Int): Album? {
        return runQuery { dao.getAlbumById(id) }
    }
}