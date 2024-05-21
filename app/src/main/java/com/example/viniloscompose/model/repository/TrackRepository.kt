package com.example.viniloscompose.model.repository

import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.TracksDto
import com.example.viniloscompose.model.service.ITrackService
import com.example.viniloscompose.utils.cache.CacheManager
import com.example.viniloscompose.utils.cache.ICacheManager
import com.example.viniloscompose.utils.network.INetworkValidator

class TrackRepository(
    private val cacheManager: ICacheManager,
    private val networkValidator: INetworkValidator,
    private val service: ITrackService
) {

    fun getTracks(albumId: Int): List<TracksDto> {
        return if (cacheManager.hasCollection(CacheManager.TRACKS_SPREFS)) {
            return cacheManager.getTracks(albumId)
        } else emptyList()
    }

    suspend fun refreshData(albumId: Int): List<TracksDto> {
        return if (!networkValidator.isNetworkAvailable()) {
            emptyList()
        } else {
            val tracks = service.getTracks(albumId).getOrThrow()
            setTracks(albumId, tracks)
            tracks
        }
    }

    private fun setTracks(albumId: Int, tracks: List<TracksDto>) {
        if (!networkValidator.isNetworkAvailable() && tracks.isNotEmpty()) {
            cacheManager.setTracks(albumId, tracks)
        }
        cacheManager.setTracks(albumId, tracks)
    }

    suspend fun addTrackToAlbum(albumId: Int, track: CreateTrackDto): TracksDto? {
        return if (!networkValidator.isNetworkAvailable()) {
            return null
        } else {
            val newTrack = service.addTrackToAlbum(albumId, track).getOrThrow()
            val newTracks = getTracks(albumId).toMutableList()
            newTracks.add(newTrack)
            setTracks(albumId, newTracks)
            newTrack
        }
    }
}