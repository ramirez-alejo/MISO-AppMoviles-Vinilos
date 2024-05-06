package com.example.viniloscompose.model.repository

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.service.IAlbumService
import com.example.viniloscompose.utils.cache.CacheManager
import com.example.viniloscompose.utils.cache.ICacheManager
import com.example.viniloscompose.utils.network.INetworkValidator

class AlbumRepository  (
    private val cacheManager : ICacheManager,
    private val networkValidator : INetworkValidator,
    private val service : IAlbumService
    ){

    fun getAlbums(): List<AlbumDto> {
        return if(cacheManager.hasCollection(CacheManager.ALBUMS_SPREFS)){
            return cacheManager.getAlbums()
        } else emptyList()
    }

    suspend fun refreshData(): List<AlbumDto>{
        var albums = getAlbums()
        return if(albums.isNullOrEmpty()){
            if(!networkValidator.isNetworkAvailable()){
                emptyList()
            } else {
                albums = service.getAlbums().getOrThrow()
                setAlbums(albums)
                albums
            }
        } else albums
    }
    private fun setAlbums(albums: List<AlbumDto>){
        if(!cacheManager.hasCollection(CacheManager.ALBUMS_SPREFS)){
            cacheManager.setAlbums(albums)
        }
    }
}