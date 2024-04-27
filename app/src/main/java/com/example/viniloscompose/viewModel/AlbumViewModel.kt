package com.example.viniloscompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.viewModel.state.AlbumState
import kotlinx.coroutines.launch

open class AlbumViewModel : ViewModel() {

    var state by mutableStateOf(AlbumState())
        private  set
    var response: List<AlbumDto> by mutableStateOf(listOf())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val albumRepository = AlbumRepository.getInstance()
            val albumList = albumRepository.getAlbums()
            response = albumList

            state = state.copy(
                isLoading = false,
                albums = response

            )
        }
    }


    fun getFilteredAlbums(query: String): List<AlbumDto> {
        return response.filter { it.name.contains(query, true) }
    }

    protected fun setState(albums: List<AlbumDto>, isLoading: Boolean) {
        state = AlbumState(
            albums = albums,
            isLoading = isLoading
        )
        response = albums
    }

}