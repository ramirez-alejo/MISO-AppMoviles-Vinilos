package com.example.viniloscompose

import android.app.Application
import android.content.Context
import com.example.viniloscompose.model.album.AlbumRepository
import com.example.viniloscompose.model.album.GetAlbumsUseCase
import com.example.viniloscompose.model.data.AppDatabase
import com.example.viniloscompose.model.data.ExceptionHandler
import com.example.viniloscompose.model.data.album.AlbumCache
import com.example.viniloscompose.model.data.api.IVinilosApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidApp
class VinilosApp : Application() {
    @Inject
    lateinit var database: AppDatabase
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideExceptionHandler(): ExceptionHandler {
        return ExceptionHandler()
    }

    @Provides
    @Singleton
    fun provideAlbumCache(database: AppDatabase, exceptionHandler: ExceptionHandler): AlbumCache {
        return AlbumCache(database.albumDao(), exceptionHandler)
    }

    @Provides
    @Singleton
    fun provideIVinilosApi(): IVinilosApi {
        return IVinilosApi.getInstance()
    }

    @Provides
    @Singleton
    fun provideAlbumRepository(albumCache: AlbumCache, iVinilosApi: IVinilosApi): AlbumRepository {
        return AlbumRepository.getInstance(albumCache, iVinilosApi)
    }

    @Provides
    @Singleton
    fun provideGetAlbumsUseCase(albumRepository: AlbumRepository, exceptionHandler: ExceptionHandler): GetAlbumsUseCase {
        return GetAlbumsUseCase(albumRepository, exceptionHandler)
    }
}