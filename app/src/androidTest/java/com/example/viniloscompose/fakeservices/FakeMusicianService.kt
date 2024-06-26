package com.example.viniloscompose.fakeservices

import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.service.IMusicianService

class FakeMusicianService(private val amount: Int) : IMusicianService {
    override suspend fun getMusicians(): Result<List<MusicianDto>> {
        var listOfMusicians = emptyList<MusicianDto>()
        for (i in 0 until amount) {
            listOfMusicians = listOfMusicians + MusicianDto(
                id = i,
                image = "${i}.com",
                name = "Musician: $i",
                albums =  emptyList(),
                description = "",
                birthDate = "1948-07-16T05:00:00.000Z"
            )
        }
        return Result.success(listOfMusicians)
    }
}