package com.example.viniloscompose.model.service

import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.TracksDto


interface ITrackService {
    suspend fun getTracks(albumId: Int): Result<List<TracksDto>>
    suspend fun addTrackToAlbum(albumId: Int, track: CreateTrackDto): Result<TracksDto>

}
