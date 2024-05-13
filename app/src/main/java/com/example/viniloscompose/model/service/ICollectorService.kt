package com.example.viniloscompose.model.service

import com.example.viniloscompose.model.dto.CollectorDto

interface ICollectorService {
    suspend fun getCollectors(): Result<List<CollectorDto>>
}