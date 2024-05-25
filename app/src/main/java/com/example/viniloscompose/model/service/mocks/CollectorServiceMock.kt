package com.example.viniloscompose.model.service.mocks

import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.service.ICollectorService

class CollectorServiceMock : ICollectorService {
    override suspend fun getCollectors(): Result<List<CollectorDto>> {
        return Result.success(
            listOf(
                CollectorDto(
                    id = 1,
                    name = "Collector 1",
                    telephone = "123456789",
                    email = "first_collector@email.com",
                    comments = arrayListOf()
                ),
                CollectorDto(
                    id = 2,
                    name = "Collector 2",
                    telephone = "123456789",
                    email = "second_collector@email.com",
                    comments = arrayListOf()
                ),
                CollectorDto(
                    id = 3,
                    name = "Collector 3",
                    telephone = "123456789",
                    email = "third_collector@email.com",
                    comments = arrayListOf()
                )
            )
        )
    }

    override suspend fun addAlbumToCollector(collectorId: Int, albumId: Int) {
        // Do nothing
    }
}