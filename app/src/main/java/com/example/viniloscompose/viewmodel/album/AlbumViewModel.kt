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
import com.example.viniloscompose.model.data.api.VinilosApi
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import com.example.viniloscompose.utils.UseCase.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
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

    companion object {
        private val exceptionHandler = ExceptionHandler()
        fun provideAlbumViewModel(context: Context,
                                  getAlbumsUseCase: GetAlbumsUseCase = GetAlbumsUseCase(
                                        AlbumRepository.getInstance(
                                            AlbumCache(AppDatabase.getInstance(context).albumDao()
                                                , exceptionHandler),
                                            VinilosApi(context)
                                        ),
                                      exceptionHandler
                                  )
        ) = AlbumViewModel(getAlbumsUseCase)
    }


}