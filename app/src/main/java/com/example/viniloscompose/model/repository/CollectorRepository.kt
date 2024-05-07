package com.example.viniloscompose.model.repository

import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.service.ICollectorService
import com.example.viniloscompose.utils.cache.CacheManager
import com.example.viniloscompose.utils.cache.ICacheManager
import com.example.viniloscompose.utils.network.INetworkValidator

class CollectorRepository(
    private val cacheManager : ICacheManager,
    private val networkValidator : INetworkValidator,
    private val service : ICollectorService
){

    fun getCollectors(): List<CollectorDto> {
        return if(cacheManager.hasCollection(CacheManager.COLLECTORS_SPREFS)){
            return cacheManager.getCollectors()
        } else emptyList()
    }

    suspend fun refreshData(): List<CollectorDto>{
        var collectors = getCollectors()
        return if(collectors.isNullOrEmpty()){
            if(!networkValidator.isNetworkAvailable()){
                emptyList()
            } else {
                collectors = service.getCollectors().getOrThrow()
                setCollectors(collectors)
                collectors
            }
        } else collectors
    }

    private fun setCollectors(collectors: List<CollectorDto>){
        if(!cacheManager.hasCollection(CacheManager.COLLECTORS_SPREFS)){
            cacheManager.setCollectors(collectors)
        }
    }
}