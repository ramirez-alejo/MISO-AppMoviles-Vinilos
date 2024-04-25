package com.example.viniloscompose.model.data.api

import com.example.viniloscompose.model.album.Album
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface IVinilosApi {

    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{id}")
    suspend fun getAlbumById(id: Int): Album

    companion object{
        private var vinilosApi : IVinilosApi? = null
        fun getInstance(): IVinilosApi {
            if (vinilosApi == null) {
                vinilosApi = Retrofit.Builder()
                    .baseUrl("https://backendappmoviles.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IVinilosApi::class.java)
            }
            return vinilosApi!!
        }
    }
}