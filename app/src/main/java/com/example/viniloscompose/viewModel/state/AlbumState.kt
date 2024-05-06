package com.example.viniloscompose.viewModel.state

import com.example.viniloscompose.model.dto.AlbumDto

data class AlbumState (
    val albums:List<AlbumDto> = emptyList(),
    val isLoading: Boolean  = false,
    val error: String? = null

)