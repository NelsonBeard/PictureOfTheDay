package com.geekbrains.pictureoftheday.Model.meteorite

sealed class MeteoriteData {
    data class Success(val serverResponseData: MeteoriteServerResponseData) : MeteoriteData()
    data class Error(val error: Throwable) : MeteoriteData()
    data class Loading(val progress: Int?) : MeteoriteData()
}