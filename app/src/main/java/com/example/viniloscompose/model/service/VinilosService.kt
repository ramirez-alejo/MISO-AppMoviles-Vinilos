package com.example.viniloscompose.model.service

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.dto.TracksDto
import com.example.viniloscompose.model.serviceAdapter.VinilosServiceAdapter

class VinilosService: IAlbumService, IMusicianService, ICollectorService, ITrackService {
    private val vinilosServiceAdapter: VinilosServiceAdapter = VinilosServiceAdapter()
    override suspend fun getMusicians(): Result<List<MusicianDto>> = vinilosServiceAdapter.getMusicians()
    override suspend fun getAlbums(): Result<List<AlbumDto>> = vinilosServiceAdapter.getAlbums()
    override suspend fun getTracks(albumId: Int): Result<List<TracksDto>>  = vinilosServiceAdapter.getTracks(albumId)

    override suspend fun addTrackToAlbum(albumId: Int, track: CreateTrackDto): Result<TracksDto> = vinilosServiceAdapter.addTrackToAlbum(albumId, track)
    override suspend fun getCollectors(): Result<List<CollectorDto>> = vinilosServiceAdapter.getCollectors()
}