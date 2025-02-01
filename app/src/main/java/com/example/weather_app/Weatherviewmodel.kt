package com.example.weather_app


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.api.NetrworkResponse
import com.example.weather_app.api.RetrofitInstance
import com.example.weather_app.api.WeatherModel
import com.example.weather_app.api.constant
import kotlinx.coroutines.launch


class Weatherviewmodel : ViewModel() {

    private val weatherApi = RetrofitInstance.apiService
    private val weatherData = MutableLiveData<NetrworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetrworkResponse<WeatherModel>> = weatherData

    fun getData(city: String) {

        weatherData.value = NetrworkResponse.loading

        viewModelScope.launch {

            try {
                val response = weatherApi.getCurrentWeather(constant.apikey, city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        weatherData.value = NetrworkResponse.Success(it)
                    }

                } else {

                    weatherData.value = NetrworkResponse.Error("Something went wrong")
                }
            } catch (e: Exception) {
                weatherData.value = NetrworkResponse.Error("Something went wrong")
            }

        }

    }

}