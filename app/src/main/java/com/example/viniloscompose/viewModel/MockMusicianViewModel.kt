package com.example.viniloscompose.viewModel

import com.example.viniloscompose.model.dto.MusicianDto

class MockMusicianViewModel: MusicianViewModel() {

        init {
            setState(
                isLoading = false,
                musicians = listOf(
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