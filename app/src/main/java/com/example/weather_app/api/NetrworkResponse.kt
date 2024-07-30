package com.example.weather_app.api

sealed class NetrworkResponse<out T> {
    data class Success<out T>(val data: T) : NetrworkResponse<T>()
    data class Error(val message: String) : NetrworkResponse<Nothing>()
    object loading : NetrworkResponse<Nothing>()

}