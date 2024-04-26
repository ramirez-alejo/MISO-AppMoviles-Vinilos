package com.example.viniloscompose.model.repository

import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.service.IMusicianService

class MusicianRepository (private val service: IMusicianService){
    // Método para obtener la lista de músicos
    suspend fun getMusicians(): List<MusicianDto> {
        return service.getMusicians()
    }

    companion object {
        // Función para obtener una instancia del repositorio
        fun getInstance(): MusicianRepository {
            // Obtener una instancia del servicio
            val service = IMusicianService.getInstace()
            // Retornar una instancia del repositorio con el servicio como parámetro
            return MusicianRepository(service)
        }
    }
}