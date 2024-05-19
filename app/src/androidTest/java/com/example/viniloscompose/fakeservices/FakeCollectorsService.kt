package com.example.viniloscompose.fakeservices

import com.example.viniloscompose.model.dto.CollectorDto
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
                comments = listOf()
            )
        }
        return Result.success(listOfCollectors)
    }

}