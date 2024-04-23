package com.example.viniloscompose.model.album

import com.example.viniloscompose.utils.FlowUseCase
import com.example.viniloscompose.utils.IExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import com.example.viniloscompose.utils.UseCase.Result
import kotlinx.coroutines.flow.map

class GetAlbumsUseCase (
    private val albumRepository: AlbumRepository,
    handler: IExceptionHandler,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Boolean, List<Album>>(handler, dispatcher) {
    override suspend fun performAction(param: Boolean): Flow<Result<List<Album>>> =
        albumRepository.getAlbums(param).map { Result.fromNullable(it) }
}
