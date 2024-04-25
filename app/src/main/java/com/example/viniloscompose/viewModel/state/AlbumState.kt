package com.example.viniloscompose.viewModel.state

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.MusicianDto

data class AlbumState (
    val albums:List<AlbumDto> = emptyList(),
    val isLoading: Boolean  = false

)