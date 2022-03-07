package com.geekbrains.pictureoftheday.model.mars

sealed class MarsData {
    data class Success(val serverResponseData: MarsServerResponseData) : MarsData()
    data class Error(val error: Throwable) : MarsData()
    data class Loading(val progress: Int?) : MarsData()
}