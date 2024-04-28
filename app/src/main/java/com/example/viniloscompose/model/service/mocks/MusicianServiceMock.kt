package com.example.viniloscompose.model.service.mocks

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
                ),
                MusicianDto(
                    id = 2,
                    name = "Musician 2",
                    image = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                )
            )
        )
    }
}