package com.example.viniloscompose.viewModel.state

import com.example.viniloscompose.model.dto.MusicianDto

data class MucisianState (
    val musicians:List<MusicianDto> = emptyList(),
    val isLoading: Boolean  = false

)