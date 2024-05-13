package com.example.viniloscompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.repository.CollectorRepository
import com.example.viniloscompose.viewModel.state.CollectorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorViewModel(private val collectorRepository: CollectorRepository) : ViewModel() {
    var state by mutableStateOf(CollectorState())
        private set
    var response: List<CollectorDto> by mutableStateOf(listOf())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            try {
                var collectorList = collectorRepository.getCollectors()
                if (collectorList.isEmpty()) {
                    withContext(Dispatchers.IO) {
                        collectorRepository.refreshData()
                        collectorList = collectorRepository.getCollectors()
                    }
                }
                response = collectorList

                state = state.copy(
                    isLoading = false,
                    collectors = response,
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


    fun getFilteredCollectors(query: String): List<CollectorDto> {
        return response.filter { it.name.contains(query, true) }
    }
}