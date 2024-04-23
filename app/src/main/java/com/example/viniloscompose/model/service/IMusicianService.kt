package com.example.viniloscompose.model.service

import com.example.viniloscompose.model.dto.MusicianDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface IMusicianService {

    @GET("musicians")
    suspend fun getMusicians(): List<MusicianDto>
    companion object{
        private var musicianService: IMusicianService? = null
        fun getInstace():IMusicianService{
            if(musicianService == null)
                musicianService = Retrofit.Builder()
                    .baseUrl("https://backendappmoviles.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IMusicianService::class.java)
            return  musicianService!!
        }
    }
}