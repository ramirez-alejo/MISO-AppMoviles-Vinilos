package com.example.viniloscompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.service.IMusicianService
import com.example.viniloscompose.viewModel.state.MucisianState
import kotlinx.coroutines.launch

class MusicianViewModel: ViewModel() {

    var state by mutableStateOf(MucisianState())
        private  set
    var response: List<MusicianDto> by mutableStateOf(listOf())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val musicianService = IMusicianService.getInstace()
            val musicianList = musicianService.getMusicians()
            response = musicianList

            state = state.copy(
                isLoading = false,
                musicians = response

            )
        }
    }

}