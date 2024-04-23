package com.example.viniloscompose.model.data.album

import com.example.viniloscompose.model.album.Album
import com.example.viniloscompose.model.album.IAlbumCache
import com.example.viniloscompose.utils.Cache
import com.example.viniloscompose.utils.IExceptionHandler

class AlbumCache(
    private val dao: AlbumDao,
    exceptionHandler: IExceptionHandler
) : Cache(exceptionHandler), IAlbumCache {
    override suspend fun getAlbums() = runQuery {
        dao.getAlbums().map { it.toAlbum() }
    }

    override suspend fun getAlbumById(id: Int) = runQuery {
        dao.getAlbumById(id)?.toAlbum()
    }

    override suspend fun storeAlbums(albums: List<Album>) {
        runQuery {
            albums.map(Album::toEntity).let {
                dao.replaceAll(*it.toTypedArray())
            }

        }
    }

    override suspend fun storeAlbum(album: Album) {
        runQuery {
            dao.insert(album.toEntity())
        }
    }

    override suspend fun deleteAllAlbums() {
        runQuery {
            dao.deleteAllAlbums()
        }
    }
}