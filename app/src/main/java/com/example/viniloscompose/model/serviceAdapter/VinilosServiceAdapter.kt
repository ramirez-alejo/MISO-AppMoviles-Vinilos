package com.example.viniloscompose.model.serviceAdapter

import com.example.viniloscompose.model.api.IVinilosApi
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.MusicianDto

class VinilosServiceAdapter constructor(
    private val vinilosApi: IVinilosApi = IVinilosApi.getInstace()
): IVinilosServiceAdapter {
    override suspend fun getMusicians(): Result<List<MusicianDto>>  =
    try{
        vinilosApi.getMusicians().let {
            Result.success(it)
        }
    } catch (e: Exception){
        Result.failure(e)
    }

    override suspend fun getAlbums() : Result<List<AlbumDto>> =
        try{
            vinilosApi.getAlbums().let {
                Result.success(it)
            }
        } catch (e: Exception){
            Result.failure(e)
        }

    override suspend fun getCollectors() : Result<List<CollectorDto>> =
        try{
            vinilosApi.getCollectors().let {
                Result.success(it)
            }
        } catch (e: Exception){
            Result.failure(e)
        }
}
