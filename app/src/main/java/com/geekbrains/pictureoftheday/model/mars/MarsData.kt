package com.geekbrains.pictureoftheday.model.mars

import com.geekbrains.pictureoftheday.model.mars.serverResponseData.MarsServerResponseData

sealed class MarsData {
    data class Success(val serverResponseData: MarsServerResponseData) : MarsData()
    data class Error(val error: Throwable) : MarsData()
    data class Loading(val progress: Int?) : MarsData()
}