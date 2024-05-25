package com.example.viniloscompose.model.api

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.dto.TracksDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface IVinilosApi {

    @GET("musicians")
    suspend fun getMusicians(): List<MusicianDto>

    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("collectors")
    suspend fun getCollectors(): List<CollectorDto>

    @GET("albums/{albumId}/tracks")
    suspend fun getTracks(@Path("albumId") albumId: Int): List<TracksDto>

    @POST("albums/{albumId}/tracks")
    @Headers(
        "Accept-Encoding: gzip,deflate",
        "Content-Type: Application/Json;charset=UTF-8",
        "Accept: Application/Json",
        "User-Agent: Retrofit 2.3.0"
     )
    suspend fun addTrackToAlbum(@Path("albumId") albumId: Int, @Body track: CreateTrackDto): TracksDto

    @POST("collectors/{collectorId}/albums/{albumId}")
    suspend fun addAlbumToCollector(@Path("collectorId") collectorId: Int, @Path("albumId") albumId: Int)

    companion object {
        private var vinilosApi: IVinilosApi? = null
        fun getInstace(): IVinilosApi {
            if (vinilosApi == null) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
                vinilosApi = Retrofit.Builder()
                    .baseUrl("https://backendappmoviles.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(IVinilosApi::class.java)
            }
            return vinilosApi!!
        }
    }


}