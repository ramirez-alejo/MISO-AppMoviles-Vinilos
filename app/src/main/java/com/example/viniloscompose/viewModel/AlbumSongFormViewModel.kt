package com.example.viniloscompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.repository.AlbumRepository
import kotlinx.coroutines.launch

class AlbumSongFormViewModel(private val albumRepository: AlbumRepository) : ViewModel() {
    var songName by mutableStateOf("")
        private set
    var songDuration by mutableStateOf("")
        private set

    fun stateOfName(): Boolean {
        return songName != ""
    }

    fun stateOfDuration(): Boolean {
        return Regex("""\d{2}:\d{2}""").containsMatchIn(songDuration)
    }

    fun setName(newName: String) {
        songName = newName
    }

    fun setDuration(newDuration: String) {
        songDuration = newDuration
    }

    fun addTrackToAlbum(id: Int, callback: () -> Unit) {
        viewModelScope.launch {
            println("Try to add track: $songName to album :$songDuration")
            val trackDto = albumRepository.addTrackToAlbum(
                id, CreateTrackDto(
                    name = songName,
                    duration = songDuration
                )
            )
            if (trackDto != null) {
                callback()
            }
        }
    }
}