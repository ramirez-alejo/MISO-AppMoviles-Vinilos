package com.example.viniloscompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.model.repository.CollectorRepository
import com.example.viniloscompose.model.repository.MusicianRepository
import com.example.viniloscompose.viewModel.state.CollectorDetailState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorDetailViewModel(
    private val collectorRepository: CollectorRepository,
    private val albumRepository: AlbumRepository,
    private val musicianRepository: MusicianRepository,
    collectorId: Int
) : ViewModel() {

    var state by mutableStateOf<CollectorDetailState>(CollectorDetailState.Loading)
        private set

    init {
        viewModelScope.launch {
            loadCollectorData(collectorId)
        }
    }

    private suspend fun loadCollectorData(collectorId: Int) {
        state = CollectorDetailState.Loading
        try {
            val collector = getCollector(collectorId)
            val musiciansIds = this.getPerformersCollectorIds(collector)
            val albumsIds = this.getCollectorAlbumsIds(collector)
            val albums = getCollectorAlbumDtos(albumsIds)
            val favoriteMusicians = getFavoriteMusicians(musiciansIds)
            state = CollectorDetailState.Success(collector, albums, favoriteMusicians)
        } catch (e: Exception) {
            state = CollectorDetailState.Error(e.message)
        }
    }

    private suspend fun getFavoriteMusicians(musiciansIds: List<Int>): List<MusicianDto> {
        var musicians: List<MusicianDto> = emptyList()
        if (musiciansIds.isNotEmpty())
            musicians = withContext(Dispatchers.IO) {
                musicianRepository.refreshData().filter { it.id in musiciansIds }
            }
        return musicians
    }

    private suspend fun getCollectorAlbumDtos(albumsIds: List<Int>): List<AlbumDto> {
        var albums: List<AlbumDto> = emptyList()
        if (albumsIds.isNotEmpty())
            albums = withContext(Dispatchers.IO) {
                albumRepository.refreshData().filter { it.id in albumsIds }
            }
        return albums
    }

    private fun getCollectorAlbumsIds(collector: CollectorDto) =
        collector.collectorAlbums.map { it.id }

    private fun getPerformersCollectorIds(collector: CollectorDto): List<Int> {
        return collector.favoritePerformers.map { it.id }
    }

    private suspend fun getCollector(collectorId: Int): CollectorDto {
        val collector = withContext(Dispatchers.IO) {
            collectorRepository.refreshData().first { it.id == collectorId }
        }
        return collector
    }

}