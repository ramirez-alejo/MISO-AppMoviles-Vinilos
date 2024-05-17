package com.example.viniloscompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.TracksDto
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.model.repository.TrackRepository
import com.example.viniloscompose.viewModel.state.TrackState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumTrackListViewModel(private val trackRepository: TrackRepository, albumId: Int) : ViewModel() {
    var tracks by mutableStateOf(listOf<TracksDto>())
        private set
    var state by mutableStateOf(TrackState())
        private set

    init {
        viewModelScope.launch {
            refreshState(albumId)
        }
    }

    suspend fun refreshState(albumId: Int) {
        state = state.copy(
            isLoading = true
        )
        try {
            tracks = trackRepository.refreshData(albumId)
            val trackList = trackRepository.getTracks(albumId)
            if (trackList.isEmpty()) {
                withContext(Dispatchers.IO) {
                    tracks = trackRepository.getTracks(albumId)
                    println("Update tracks")
                    println(tracks)
                }
            }
            tracks = trackList
            state = state.copy(
                isLoading = false,
                tracks = tracks,
                error = null
            )
        } catch (e: Exception) {
            tracks = emptyList()
            state = state.copy(
                isLoading = false,
                error = e.message
            )
            println(e)
        }
    }
}