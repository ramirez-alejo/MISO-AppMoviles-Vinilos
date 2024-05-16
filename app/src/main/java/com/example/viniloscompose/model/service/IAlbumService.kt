package com.example.viniloscompose.model.service

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.TracksDto


interface IAlbumService {
    suspend fun getAlbums(): Result<List<AlbumDto>>
    suspend fun addTrackToAlbum(albumId: Int, track: CreateTrackDto): Result<TracksDto>

}
