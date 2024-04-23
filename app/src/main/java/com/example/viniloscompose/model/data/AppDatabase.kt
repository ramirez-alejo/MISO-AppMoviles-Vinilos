package com.example.viniloscompose.model.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.viniloscompose.model.data.album.AlbumDao
import com.example.viniloscompose.model.album.Album

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "vinilos-database"
@Database(entities = [Album::class/*, Track::class*/], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    class Builder(private val application: Application) {
        private val builder: RoomDatabase.Builder<AppDatabase>
            get() = Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()

        fun build(): AppDatabase = builder.build()
    }

    abstract fun albumDao(): AlbumDao
}