package com.example.viniloscompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.viewModel.state.AlbumState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Logger

class AlbumViewModel(private val albumRepository: AlbumRepository) : ViewModel() {
    var state by mutableStateOf(AlbumState())
        private set
    private var response: List<AlbumDto> by mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            refreshState()
        }
    }

    private suspend fun refreshState() {
        state = state.copy(
            isLoading = true
        )
        try {
            albumRepository.refreshData()
            val albumList = albumRepository.getAlbums()
            if (albumList.isEmpty()) {
                withContext(Dispatchers.IO) {
                    response = albumRepository.getAlbums()
                }
            }
            response = albumList
            state = state.copy(
                isLoading = false,
                albums = response,
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

    fun getFilteredAlbums(query: String): List<AlbumDto> {
        return response.filter { it.name.contains(query, true) }
    }

    fun getAlbum(id: Int): AlbumDto {
        return response.first { it.id == id }
    }

    fun selectAlbum(album: AlbumDto?) {
        Logger.getLogger("AlbumViewModel").info("Selected album: $album")
        state = state.copy(
            selectedAlbum = album
        )
        Logger.getLogger("AlbumViewModel").info("Selected album: ${state.selectedAlbum}")
    }
}