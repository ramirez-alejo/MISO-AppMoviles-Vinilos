package com.example.viniloscompose.model.repository

import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.service.IMusicianService
import com.example.viniloscompose.model.service.VinilosService

class MusicianRepository (private val service: IMusicianService){
    // Método para obtener la lista de músicos
    suspend fun getMusicians(): List<MusicianDto> {
        return service.getMusicians().getOrThrow()
    }

    companion object {
        // Función para obtener una instancia del repositorio
        fun getInstance(): MusicianRepository {
            // Obtener una instancia del servicio
            val service = VinilosService()
            // Retornar una instancia del repositorio con el servicio como parámetro
            return MusicianRepository(service)
        }
    }
}