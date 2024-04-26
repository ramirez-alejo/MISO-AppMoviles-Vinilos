package com.example.viniloscompose.model.repository

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.service.IAlbumService
import com.example.viniloscompose.model.service.IMusicianService

class AlbumRepository  (private val service: IAlbumService){
    suspend fun getAlbums(): List<AlbumDto> {
        return service.getAlbums()
    }

    companion object {
        fun getInstance(): AlbumRepository {
            val service = IAlbumService.getInstace()
            return AlbumRepository(service)
        }
    }
}