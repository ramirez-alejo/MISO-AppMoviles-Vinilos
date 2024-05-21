package com.example.viniloscompose.fakeservices

import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.TracksDto
import com.example.viniloscompose.model.service.ITrackService

class FakeTracksService(private val amount: Int) : ITrackService {

    private var listOfTracks = listOf<TracksDto>()
    override suspend fun getTracks(albumId: Int): Result<List<TracksDto>> {
        val mutableList = listOfTracks.toMutableList()
        for (i in 0 until amount) {
            val newTrack = TracksDto(
                id = i,
                name = "Track: $i",
                duration = "00:00"
            )
            mutableList.add(newTrack)
            //listOfTracks = listOfTracks +
        }
        return Result.success(mutableList.toList())
    }

    override suspend fun addTrackToAlbum(albumId: Int, track: CreateTrackDto): Result<TracksDto> {
        val newTrack = TracksDto(
            id = listOfTracks.size,
            duration = track.duration,
            name = track.name
        )
        val mutableList = listOfTracks.toMutableList()
        mutableList.add(newTrack)
        listOfTracks = mutableList
        return Result.success(newTrack)
    }

}