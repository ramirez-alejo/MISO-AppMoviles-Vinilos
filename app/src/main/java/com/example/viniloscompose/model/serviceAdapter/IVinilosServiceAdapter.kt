package com.example.viniloscompose.model.serviceAdapter

import com.example.viniloscompose.model.dto.*

interface IVinilosServiceAdapter {
    suspend fun getMusicians() : Result<List<MusicianDto>>
    suspend fun getAlbums() : Result<List<AlbumDto>>
    suspend fun getCollectors() : Result<List<CollectorDto>>

    suspend fun addTrackToAlbum(albumId: Int, track: CreateTrackDto): Result<TracksDto>
}