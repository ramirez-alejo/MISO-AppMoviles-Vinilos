package com.example.viniloscompose.utils.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class NetworkValidator(application: Application) : INetworkValidator {
    private val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isNetworkAvailable(): Boolean {
        return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}

interface INetworkValidator {
    fun isNetworkAvailable(): Boolean
}

class FixedNetworkValidator(private val isNetworkAvailable: Boolean) : INetworkValidator {
    override fun isNetworkAvailable(): Boolean {
        return isNetworkAvailable
    }
}