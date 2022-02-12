package com.geekbrains.pictureoftheday.Model.spaceweather

sealed class SpaceWeatherData {
    data class Success(val serverResponseData: List<SpaceWeatherServerResponseData>) : SpaceWeatherData()
    data class Error(val error: Throwable) : SpaceWeatherData()
    data class Loading(val progress: Int?) : SpaceWeatherData()

}