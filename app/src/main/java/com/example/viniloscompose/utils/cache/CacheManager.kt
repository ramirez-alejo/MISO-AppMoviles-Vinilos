package com.example.viniloscompose.utils.cache

import android.content.Context
import android.content.SharedPreferences
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.dto.TracksDto
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CacheManager(private val context: Context) : ICacheManager{
    companion object {
        const val ALBUMS_SPREFS = "albums"
        const val MUSICIANS_SPREFS = "musicians"
        const val COLLECTORS_SPREFS = "collectors"
        const val TRACKS_SPREFS = "tracks"

        fun getPrefs(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
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
    private var tracks: ArrayMap<Int, List<TracksDto>> = arrayMapOf()
    override fun getTracks(albumId: Int): List<TracksDto> {
        if(tracks[albumId]?.isNotEmpty() == true) return tracks[albumId] ?: emptyList()
        val tracks = getPrefs(context, TRACKS_SPREFS).getString(TRACKS_SPREFS, null)
        return if(tracks != null){
            return Json.decodeFromString(tracks)
        } else emptyList()
    }

    override fun setTracks(albumId: Int, tracks: List<TracksDto>) {
        this.tracks[albumId] = tracks
        val jsonString: String  = Gson().toJson(this.tracks)
        getPrefs(context, TRACKS_SPREFS).edit().putString(TRACKS_SPREFS, Json.encodeToString(jsonString)).apply()
    }

    private var collectors: List<CollectorDto> = emptyList()
    override fun getCollectors() : List<CollectorDto> {
        if(collectors.isNotEmpty()) return collectors
        val collectors = getPrefs(context, COLLECTORS_SPREFS).getString(COLLECTORS_SPREFS, null)
        return if(collectors != null){
            return Json.decodeFromString(collectors)
        } else emptyList()
    }
    override fun setCollectors(collectors: List<CollectorDto>) {
        this.collectors = collectors
        getPrefs(context, COLLECTORS_SPREFS).edit().putString(COLLECTORS_SPREFS, Json.encodeToString(collectors)).apply()
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

    override fun hasEmptyTracks(albumId: Int): Boolean {
        return getPrefs(context, TRACKS_SPREFS).contains(TRACKS_SPREFS)
    }
}

interface ICacheManager {
    fun getAlbums(): List<AlbumDto>
    fun setAlbums(albums: List<AlbumDto>)
    fun getTracks(albumId: Int): List<TracksDto>
    fun setTracks(albumId: Int, tracks: List<TracksDto>)
    fun getCollectors(): List<CollectorDto>
    fun setCollectors(collectors: List<CollectorDto>)
    fun getMusicians(): List<MusicianDto>
    fun setMusicians(musicians: List<MusicianDto>)
    fun hasCollection(collection: String): Boolean
    fun hasEmptyTracks(albumId: Int): Boolean
}

class FixedCacheManager(
    private var albums: List<AlbumDto> = emptyList(),
    private var musicians: List<MusicianDto> = emptyList(),
    private var collectors: List<CollectorDto> = emptyList(),
    private var tracks: ArrayMap<Int, List<TracksDto>> = arrayMapOf()

) : ICacheManager {
    override fun getAlbums(): List<AlbumDto> {
        return albums
    }
    override fun setAlbums(albums: List<AlbumDto>) {
        this.albums = albums
    }
    override fun getTracks(albumId: Int): List<TracksDto> {
        return tracks[albumId] ?: emptyList()
    }
    override fun setTracks(albumId: Int, tracks: List<TracksDto>) {
        this.tracks[albumId] = tracks
    }
    override fun getCollectors(): List<CollectorDto> {
        return collectors
    }

    override fun setCollectors(collectors: List<CollectorDto>) {
        this.collectors = collectors
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
            CacheManager.COLLECTORS_SPREFS -> collectors.isNotEmpty()
            CacheManager.TRACKS_SPREFS -> tracks.isNotEmpty()
            else -> false
        }
    }
    override fun hasEmptyTracks(albumId: Int) : Boolean {
        return tracks[albumId]?.isNotEmpty() == true
    }
}