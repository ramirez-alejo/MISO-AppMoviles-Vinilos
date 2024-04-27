package com.example.viniloscompose.model.repository

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.service.IAlbumService
import com.example.viniloscompose.model.service.VinilosService

class AlbumRepository  (private val service: IAlbumService){
    suspend fun getAlbums(): List<AlbumDto> {
        return service.getAlbums().getOrThrow()
    }

    companion object {
        fun getInstance(): AlbumRepository {
            val service = VinilosService()
            return AlbumRepository(service)
        }
    }
}