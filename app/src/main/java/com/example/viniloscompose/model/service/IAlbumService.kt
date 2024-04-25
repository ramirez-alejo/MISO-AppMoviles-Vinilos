package com.example.viniloscompose.model.service

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.MusicianDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface IAlbumService {

    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>
    companion object{
        private var albumService: IAlbumService? = null
        fun getInstace():IAlbumService{
            if(albumService == null)
                albumService = Retrofit.Builder()
                    .baseUrl("https://backendappmoviles.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IAlbumService::class.java)
            return  albumService!!
        }
    }
}