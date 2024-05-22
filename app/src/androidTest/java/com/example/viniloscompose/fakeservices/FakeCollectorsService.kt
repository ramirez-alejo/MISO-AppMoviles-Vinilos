package com.example.viniloscompose.fakeservices

import com.example.viniloscompose.model.dto.CollectorAlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.FavoritePerformerDto
import com.example.viniloscompose.model.service.ICollectorService

class FakeCollectorsService(private val amount: Int): ICollectorService {
    override suspend fun getCollectors(): Result<List<CollectorDto>> {
        var listOfCollectors = emptyList<CollectorDto>()
        for (i in 0 until amount) {
            listOfCollectors = listOfCollectors + CollectorDto(
                id = i,
                name = "Collector: $i",
                email = "Email: $i",
                telephone = "Telephone: $i",
                comments = listOf(),
                favoritePerformers = getFavoritePerformers(),
                collectorAlbums = getCollectorAlbums()
            )
        }
        return Result.success(listOfCollectors)
    }

    private  fun  getFavoritePerformers() : List<FavoritePerformerDto>{
        var listFavoritePerformers = emptyList<FavoritePerformerDto>()
        for (i in 0 until   amount){
            listFavoritePerformers = listFavoritePerformers + FavoritePerformerDto(i)
        }
        return  listFavoritePerformers
    }

    private  fun getCollectorAlbums(): List<CollectorAlbumDto>{
        var listCollectorAlbumDto = emptyList<CollectorAlbumDto>()
        for (i in 0 until amount ){
            listCollectorAlbumDto = listCollectorAlbumDto + CollectorAlbumDto(i)
        }
        return listCollectorAlbumDto
    }
}