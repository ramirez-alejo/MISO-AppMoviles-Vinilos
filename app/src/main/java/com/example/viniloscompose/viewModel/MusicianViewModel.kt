package com.example.viniloscompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.repository.MusicianRepository
import com.example.viniloscompose.viewModel.state.MucisianState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicianViewModel(private val musicianRepository: MusicianRepository) : ViewModel() {
    var state by mutableStateOf(MucisianState())
        private set
    var response: List<MusicianDto> by mutableStateOf(listOf())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            try {
                var musicianList = musicianRepository.getMusicians()
                if (musicianList.isEmpty()) {
                    withContext(Dispatchers.IO) {
                        musicianRepository.refreshData()
                        musicianList = musicianRepository.getMusicians()
                    }
                }
                response = musicianList

                state = state.copy(
                    isLoading = false,
                    musicians = response,
                    error = null
                )
            } catch (e: Exception) {
                response = emptyList()
                state = state.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun getFilteredMusicians(query: String): List<MusicianDto> {
        return response.filter { it.name.contains(query, true) }
    }

    fun getMusician(id:Int):MusicianDto{
        return response.first { it.id == id }
    }

}