package com.example.viniloscompose.model.serviceAdapter

import com.example.viniloscompose.model.api.IVinilosApi
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.dto.TracksDto

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

    override suspend fun getTracks(albumId: Int): Result<List<TracksDto>> =
        try{
            vinilosApi.getTracks(albumId).let {
                Result.success(it)
            }
        } catch (e: Exception){
            Result.failure(e)
        }


    override suspend fun addTrackToAlbum(albumId: Int, track: CreateTrackDto): Result<TracksDto> =
        try{
            vinilosApi.addTrackToAlbum(albumId, track).let {
                Result.success(it)
            }
        } catch (e: Exception){
            Result.failure(e)
        }

}
