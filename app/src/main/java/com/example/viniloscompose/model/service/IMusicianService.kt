package com.example.viniloscompose.model.service

import com.example.viniloscompose.model.dto.MusicianDto


interface IMusicianService {
    suspend fun getMusicians(): Result<List<MusicianDto>>
}