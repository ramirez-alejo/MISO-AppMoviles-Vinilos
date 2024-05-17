package com.example.viniloscompose.viewModel.state

import com.example.viniloscompose.model.dto.TracksDto

data class TrackState (
    val tracks:List<TracksDto> = emptyList(),
    val isLoading: Boolean  = false,
    val error: String? = null
)