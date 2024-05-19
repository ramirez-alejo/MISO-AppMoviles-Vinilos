package com.example.viniloscompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.repository.TrackRepository
import kotlinx.coroutines.launch

class AlbumSongFormViewModel(private val trackRepository: TrackRepository) : ViewModel() {
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
            val trackDto = trackRepository.addTrackToAlbum(
                id, CreateTrackDto(
                    name = songName,
                    duration = songDuration
                )
            )
            if (trackDto != null) {
                println("Added succesfully")
                callback()
            }
        }
    }
}