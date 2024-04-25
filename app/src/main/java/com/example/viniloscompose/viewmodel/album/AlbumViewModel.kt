package com.example.viniloscompose.viewmodel.album

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viniloscompose.model.album.Album
import com.example.viniloscompose.model.album.AlbumRepository
import com.example.viniloscompose.model.album.GetAlbumsUseCase
import com.example.viniloscompose.model.data.AppDatabase
import com.example.viniloscompose.model.data.ExceptionHandler
import com.example.viniloscompose.model.data.album.AlbumCache
import com.example.viniloscompose.model.data.api.IVinilosApi
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import com.example.viniloscompose.utils.UseCase.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AlbumViewModel @Inject constructor(
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

    companion object {
        private val exceptionHandler = ExceptionHandler()
        fun provideAlbumViewModel(context: Context): AlbumViewModel {
            // Step 1: Create an instance of AppDatabase
            val appDatabase = AppDatabase.getInstance(context)
            println("AppDatabase instance created: $appDatabase")

            // Step 2: Create an instance of ExceptionHandler
            val exceptionHandler = ExceptionHandler()
            println("ExceptionHandler instance created: $exceptionHandler")

            // Step 3: Create an instance of AlbumCache
            val albumCache = AlbumCache(appDatabase.albumDao(), exceptionHandler)
            println("AlbumCache instance created: $albumCache")

            // Step 4: Create an instance of IVinilosApi
            val iVinilosApi = IVinilosApi.getInstance()
            println("IVinilosApi instance created: $iVinilosApi")

            // Step 5: Create an instance of AlbumRepository
            val albumRepository = AlbumRepository.getInstance(albumCache, iVinilosApi)
            println("AlbumRepository instance created: $albumRepository")

            // Step 6: Create an instance of GetAlbumsUseCase
            val getAlbumsUseCase = GetAlbumsUseCase(albumRepository, exceptionHandler)
            println("GetAlbumsUseCase instance created: $getAlbumsUseCase")

            // Step 7: Create an instance of AlbumViewModel
            val albumViewModel = AlbumViewModel(getAlbumsUseCase)
            println("AlbumViewModel instance created: $albumViewModel")

            return albumViewModel
        }
    }


}