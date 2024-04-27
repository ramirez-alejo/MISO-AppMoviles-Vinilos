package com.example.viniloscompose.model.service

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.serviceAdapter.VinilosServiceAdapter

class VinilosService (
    private val vinilosServiceAdapter: VinilosServiceAdapter = VinilosServiceAdapter()
): IAlbumService, IMusicianService {
    override suspend fun getMusicians(): Result<List<MusicianDto>> = vinilosServiceAdapter.getMusicians()
    override suspend fun getAlbums(): Result<List<AlbumDto>> = vinilosServiceAdapter.getAlbums()
}