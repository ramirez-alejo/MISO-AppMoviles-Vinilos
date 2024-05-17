package com.example.viniloscompose.fakeservices

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.PerformerDto
import com.example.viniloscompose.model.dto.TracksDto
import com.example.viniloscompose.model.repository.TrackRepository
import com.example.viniloscompose.model.service.IAlbumService
import com.example.viniloscompose.model.service.ITrackService

class FakeTracksService(private val amount: Int) : ITrackService {

    var listOfTracks = listOf<TracksDto>()
    override suspend fun getTracks(albumId: Int): Result<List<TracksDto>> {
        for (i in 0 until amount) {
            listOfTracks = listOfTracks + TracksDto(
                id = i,
                name = "Track: $i",
                duration = "00:00"
            )
        }
        return Result.success(listOfTracks)
    }

    override suspend fun addTrackToAlbum(albumId: Int, track: CreateTrackDto): Result<TracksDto> {
        val newTrack = TracksDto(
            id = listOfTracks.size,
            duration = track.duration,
            name = track.name
        )
        listOfTracks += newTrack
        return Result.success(newTrack)
    }

}