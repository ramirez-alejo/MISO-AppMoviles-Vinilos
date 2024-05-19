package com.example.viniloscompose.fakeservices

import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.PerformerDto
import com.example.viniloscompose.model.dto.TracksDto
import com.example.viniloscompose.model.service.IAlbumService
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FakeAlbumService(private val amount: Int): IAlbumService {
    override suspend fun getAlbums(): Result<List<AlbumDto>> {
        var listOfAlbums = emptyList<AlbumDto>()
        val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = Date()
        val formattedDate = formatoEntrada.format(Timestamp(date.time))
        for (i in 0 until amount) {
            listOfAlbums = listOfAlbums + AlbumDto(
                id = i,
                name = "Album: $i",
                cover = "Cover: $i",
                description = "description: $i",
                genre = "genre: $i",
                performers = listOf(PerformerDto(
                    id = i,
                    description = "performer description",
                    name = "Performer: $i",
                    image = "$i.com",
                    birthDate = "$i"
                )),
                recordLabel = "Record label: $i",
                releaseDate = formattedDate,
                tracks = listOf(
                    TracksDto(
                        id = i,
                        name = "Track: $i",
                        duration = "00:00"
                    ),
                    TracksDto(
                        id = i+1,
                        name = "Track: $i",
                        duration = "00:02"
                    )
                )
            )
        }
        return Result.success(listOfAlbums)
    }

}