package com.example.viniloscompose.model.service.mocks

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.PerformerDto
import com.example.viniloscompose.model.service.IAlbumService

class AlbumServiceMock : IAlbumService {
    override suspend fun getAlbums(): Result<List<AlbumDto>> {
        return Result.success(listOf(
            AlbumDto(
                id = 1,
                name = "Album 1",
                performers = listOf(
                    PerformerDto(
                        id = 1,
                        name = "Performer 1",
                        image = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                        description = "Description",
                        birthDate = "2021-01-01"
                    ),
                    PerformerDto(
                        id = 2,
                        name = "Performer 2",
                        image = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                        description = "Description",
                        birthDate = "2021-01-01"
                    )
                ),
                cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                releaseDate = "2021-01-01",
                description = "Description",
                genre = "Genre",
                recordLabel = "Record Label"
            ),
            AlbumDto(
                id = 2,
                name = "Album 2",
                performers = listOf(
                    PerformerDto(
                        id = 1,
                        name = "Performer 1",
                        image = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                        description = "Description",
                        birthDate = "2021-01-01"
                    ),
                    PerformerDto(
                        id = 2,
                        name = "Performer 2",
                        image = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                        description = "Description",
                        birthDate = "2021-01-01"
                    )
                ),
                cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                releaseDate = "2021-01-01",
                description = "Description",
                genre = "Genre",
                recordLabel = "Record Label"
            ),
            AlbumDto(
                id = 3,
                name = "Album 3",
                performers = listOf(
                    PerformerDto(
                        id = 1,
                        name = "Performer 1",
                        image = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                        description = "Description",
                        birthDate = "2021-01-01"
                    ),
                    PerformerDto(
                        id = 2,
                        name = "Performer 2",
                        image = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                        description = "Description",
                        birthDate = "2021-01-01"
                    )
                ),
                cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                releaseDate = "2021-01-01",
                description = "Description",
                genre = "Genre",
                recordLabel = "Record Label"
            )
        )
        )
    }
}