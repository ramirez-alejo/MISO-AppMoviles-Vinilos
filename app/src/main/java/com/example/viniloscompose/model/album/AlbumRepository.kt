package com.example.viniloscompose.model.album

import com.example.viniloscompose.data.api.IVinilosApi
import com.example.viniloscompose.utils.ResourceGroup
import kotlinx.coroutines.flow.Flow

class AlbumRepository (
    private val cache: IAlbumCache,
    private val remote: IVinilosApi
) {
    private val resourceGroup = ResourceGroup<Unit, Int, Album>(
        { remote.getAlbums() },
        { cache.getAlbums() },
        cache::storeAlbums,
        { id, _ -> remote.getAlbumById(id) },
        { id, _ -> cache.getAlbumById(id) },
        cache::storeAlbum,
        cache::deleteAllAlbums
    )
    suspend fun getAlbums(force: Boolean) : Flow<List<Album>?> = resourceGroup.query(Unit, force)
    suspend fun getAlbumById(id: Int, force: Boolean) : Flow<Album?> = resourceGroup.queryByKey(id, Unit, force)
}