package com.example.viniloscompose.model.repository

import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.service.IMusicianService
import com.example.viniloscompose.utils.cache.CacheManager
import com.example.viniloscompose.utils.cache.ICacheManager
import com.example.viniloscompose.utils.network.INetworkValidator

class MusicianRepository (
    private val cacheManager : ICacheManager,
    private val networkValidator : INetworkValidator,
    private val service : IMusicianService
){
    // Método para obtener la lista de músicos
    fun getMusicians(): List<MusicianDto> {
        return if(cacheManager.hasCollection(CacheManager.MUSICIANS_SPREFS)){
            return cacheManager.getMusicians()
        } else emptyList()
    }

    // Método para refrescar la lista de músicos
    suspend fun refreshData(): List<MusicianDto>{
        var musicians = getMusicians()
        return if(musicians.isNullOrEmpty()){
            if(!networkValidator.isNetworkAvailable()){
                emptyList()
            } else {
                musicians = service.getMusicians().getOrThrow()
                setMusicians(musicians)
                musicians
            }
        } else musicians
    }

    // Método para guardar la lista de músicos en el SharedPreferences
    private fun setMusicians(musicians: List<MusicianDto>){
        if(!cacheManager.hasCollection(CacheManager.MUSICIANS_SPREFS)){
            cacheManager.setMusicians(musicians)
        }
    }
}