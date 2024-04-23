package com.example.viniloscompose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.album.Album
import com.example.viniloscompose.model.album.GetAlbumsUseCase
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import com.example.viniloscompose.utils.UseCase.Result


class AlbumViewModel(
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {


    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    private val _refreshing = MutableLiveData<Boolean>(false)
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    fun onViewCreated() {
        refresh()
    }

    fun onRefresh() {
        refresh(true)
    }

    private fun refresh(force: Boolean = false) {
        viewModelScope.launch {
            getAlbumsUseCase(force)
                .onStart { _refreshing.value = true }
                .onCompletion { _refreshing.value = false }
                .collect { response ->
                    when (response) {
                        is Result.Success -> _albums.value = response.result
                        else -> Unit // TODO handle error
                    }
                }
        }
    }


}