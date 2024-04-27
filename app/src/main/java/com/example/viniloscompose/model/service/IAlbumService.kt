package com.example.viniloscompose.model.service

import com.example.viniloscompose.model.dto.AlbumDto


interface IAlbumService {
    suspend fun getAlbums(): Result<List<AlbumDto>>


}