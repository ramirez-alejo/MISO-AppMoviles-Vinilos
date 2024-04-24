package com.example.viniloscompose

import android.app.Application
import com.example.viniloscompose.model.data.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class VinilosApp : Application() {
    @Inject
    lateinit var database: AppDatabase
}