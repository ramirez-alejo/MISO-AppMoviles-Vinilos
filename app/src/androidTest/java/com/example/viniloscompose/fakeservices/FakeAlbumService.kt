package com.example.viniloscompose.fakeservices

import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.PerformerDto
import com.example.viniloscompose.model.service.IAlbumService

class FakeAlbumService(private val amount: Int): IAlbumService {
    override suspend fun getAlbums(): Result<List<CollectorDto>> {
        var listOfMusicians = emptyList<CollectorDto>()
        for (i in 0 until amount) {
            listOfMusicians = listOfMusicians + CollectorDto(
                id = i,
                name = "Album: $i",
                cover = "Cover: $i",
                description = "description: $i",
                genre = "genre: $i",
                performers = listOf(PerformerDto(
                    id = i,
                    description = "performer description",
                    name = "Performer: $i",
                    image = "$i.com",
                    birthDate = "$i"
                )),
                recordLabel = "Record label: $i",
                releaseDate = "Release date: $i"
            )
        }
        return Result.success(listOfMusicians)
    }

}