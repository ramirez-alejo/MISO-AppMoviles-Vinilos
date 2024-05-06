package com.example.viniloscompose.utils.cache

import android.content.Context
import android.content.SharedPreferences
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.MusicianDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CacheManager(context: Context) : ICacheManager{
    private val context = context
    companion object {
        const val ALBUMS_SPREFS = "albums"
        const val MUSICIANS_SPREFS = "musicians"

        fun getPrefs(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

        private var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    private var albums: List<AlbumDto> = emptyList()
    override fun getAlbums() : List<AlbumDto> {
        if(albums.isNotEmpty()) return albums
        val albums = getPrefs(context, ALBUMS_SPREFS).getString(ALBUMS_SPREFS, null)
        return if(albums != null){
            return Json.decodeFromString(albums)
        } else emptyList()
    }
    override fun setAlbums(albums: List<AlbumDto>) {
        this.albums = albums
        getPrefs(context, ALBUMS_SPREFS).edit().putString(ALBUMS_SPREFS, Json.encodeToString(albums)).apply()
    }

    private var musicians: List<MusicianDto> = emptyList()
    override fun getMusicians() : List<MusicianDto> {
        if(musicians.isNotEmpty()) return musicians
        val musicians = getPrefs(context, MUSICIANS_SPREFS).getString(MUSICIANS_SPREFS, null)
        return if(musicians != null){
            return Json.decodeFromString(musicians)
        } else emptyList()
    }
    override fun setMusicians(musicians: List<MusicianDto>) {
        this.musicians = musicians
        getPrefs(context, MUSICIANS_SPREFS).edit().putString(MUSICIANS_SPREFS, Json.encodeToString(musicians)).apply()
    }
    override fun hasCollection(collection: String): Boolean {
        return getPrefs(context, collection).contains(collection)
    }
}

interface ICacheManager {
    fun getAlbums(): List<AlbumDto>
    fun setAlbums(albums: List<AlbumDto>)
    fun getMusicians(): List<MusicianDto>
    fun setMusicians(musicians: List<MusicianDto>)
    fun hasCollection(collection: String): Boolean
}

class FixedCacheManager(
    private var albums: List<AlbumDto> = emptyList(),
    private var musicians: List<MusicianDto> = emptyList()

) : ICacheManager {
    override fun getAlbums(): List<AlbumDto> {
        return albums
    }

    override fun setAlbums(albums: List<AlbumDto>) {
        this.albums = albums
    }

    override fun getMusicians(): List<MusicianDto> {
        return musicians
    }

    override fun setMusicians(musicians: List<MusicianDto>) {
        this.musicians = musicians
    }

    override fun hasCollection(collection: String): Boolean {
        return when(collection){
            CacheManager.ALBUMS_SPREFS -> albums.isNotEmpty()
            CacheManager.MUSICIANS_SPREFS -> musicians.isNotEmpty()
            else -> false
        }
    }
}