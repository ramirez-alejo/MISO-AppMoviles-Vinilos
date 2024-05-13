package com.example.viniloscompose.model.service.mocks

import com.example.viniloscompose.model.dto.MusicianAlbumDetailDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.service.IMusicianService

class MusicianServiceMock: IMusicianService {
    override suspend fun getMusicians(): Result<List<MusicianDto>> {
        return Result.success(
            listOf(
                MusicianDto(
                    id = 1,
                    name = "Musician 1",
                    image = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                    albums = listOf(
                        MusicianAlbumDetailDto(
                            id = 1,
                            name = "Musician 1 Album 1",
                            cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg"
                        ),
                        MusicianAlbumDetailDto(
                            id = 2,
                            name = "Musician 1 Album 2",
                            cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg"
                        )
                    ),
                    birthDate = "1944-03-01T00:00:00.000Z",
                    description = "Es un músico y actor británico, conocido por ser el vocalista y líder de la banda de rock The Who"
                ),
                MusicianDto(
                    id = 2,
                    name = "Musician 2",
                    image = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                    albums = listOf(
                        MusicianAlbumDetailDto(
                            id = 4,
                            name = "Musician 2 Album 1",
                            cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg"
                        ),
                        MusicianAlbumDetailDto(
                            id = 3,
                            name = "Musician 2 Album 2",
                            cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg"
                        )
                    ),
                    birthDate = "1948-08-20T00:00:00.000Z",
                    description = "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York."
                )
            )
        )
    }
}