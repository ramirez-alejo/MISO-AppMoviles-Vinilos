package com.example.viniloscompose.viewModel.state

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.MusicianDto

sealed class CollectorDetailState{
    object Loading : CollectorDetailState()
    data class   Success(val collector: CollectorDto, val albums: List<AlbumDto>, val favoriteMusicians: List<MusicianDto>) : CollectorDetailState()
    data class Error(val message: String?) : CollectorDetailState()
    }