package com.example.viniloscompose.model.service.mocks

import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.TracksDto
import com.example.viniloscompose.model.service.ITrackService

class TrackServiceMock : ITrackService {
    val trackList: MutableList<TracksDto> = mutableListOf(
        TracksDto(
            id = 1,
            name = "Track 1",
            duration = "00:00"
        ),
        TracksDto(
            id = 2,
            name = "Track 2",
            duration = "00:00"
        ),
        TracksDto(
            id = 3,
            name = "Track 1",
            duration = "00:00"
        ),
        TracksDto(
            id = 4,
            name = "Track 2",
            duration = "00:00"
        ),
        TracksDto(
            id = 5,
            name = "Track 1",
            duration = "00:00"
        ),
        TracksDto(
            id = 6,
            name = "Track 2",
            duration = "00:00"
        )
    )

    override suspend fun getTracks(albumId: Int): Result<List<TracksDto>> {
        return Result.success(trackList)
    }

    override suspend fun addTrackToAlbum(albumId: Int, track: CreateTrackDto): Result<TracksDto> {
        val newTrack = TracksDto(
            id = trackList.size,
            name = track.name,
            duration = track.duration
        )
        trackList.add(newTrack)
        return Result.success(
            newTrack
        )
    }
}