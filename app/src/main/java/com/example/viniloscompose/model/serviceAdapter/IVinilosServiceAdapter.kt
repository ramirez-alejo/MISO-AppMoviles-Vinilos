package com.example.viniloscompose.model.serviceAdapter

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.dto.TracksDto

interface IVinilosServiceAdapter {
    suspend fun getMusicians() : Result<List<MusicianDto>>
    suspend fun getAlbums() : Result<List<AlbumDto>>
    suspend fun getCollectors() : Result<List<CollectorDto>>
    suspend fun getTracks(albumId: Int): Result<List<TracksDto>>
    suspend fun addTrackToAlbum(albumId: Int, track: CreateTrackDto): Result<TracksDto>
}