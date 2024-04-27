package com.example.viniloscompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.repository.MusicianRepository
import com.example.viniloscompose.viewModel.state.MucisianState
import kotlinx.coroutines.launch

open class MusicianViewModel: ViewModel() {

    var state by mutableStateOf(MucisianState())
        private  set
    var response: List<MusicianDto> by mutableStateOf(listOf())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val musicianRepositor = MusicianRepository.getInstance()
            val musicianList = musicianRepositor.getMusicians()
            response = musicianList

            state = state.copy(
                isLoading = false,
                musicians = response

            )
        }
    }

    fun getFilteredMusicians(query: String): List<MusicianDto> {
        return response.filter { it.name.contains(query, true) }
    }
    protected fun setState(musicians: List<MusicianDto>, isLoading: Boolean) {
        state = MucisianState(
            musicians = musicians,
            isLoading = isLoading
        )
        response = musicians
    }

}