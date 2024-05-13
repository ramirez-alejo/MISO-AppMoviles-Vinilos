package com.example.viniloscompose.viewModel.state

import com.example.viniloscompose.model.dto.CollectorDto

data class CollectorState (
    val collectors:List<CollectorDto> = emptyList(),
    val isLoading: Boolean  = false,
    val error: String? = null
)