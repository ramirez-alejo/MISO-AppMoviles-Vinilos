package com.example.viniloscompose.model.api

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.MusicianDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface IVinilosApi {

    @GET("musicians")
    suspend fun getMusicians(): List<MusicianDto>

    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>
    @GET("collectors")
    suspend fun getCollectors(): List<CollectorDto>
    companion object{
        private var vinilosApi: IVinilosApi? = null
        fun getInstace(): IVinilosApi {
            if(vinilosApi == null)
                vinilosApi = Retrofit.Builder()
                    .baseUrl("https://backendappmoviles.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IVinilosApi::class.java)
            return  vinilosApi!!
        }
    }



}