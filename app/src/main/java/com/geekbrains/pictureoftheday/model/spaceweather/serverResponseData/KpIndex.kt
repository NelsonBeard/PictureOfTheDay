package com.geekbrains.pictureoftheday.model.spaceweather.serverResponseData

import com.google.gson.annotations.SerializedName

data class KpIndex(
    @SerializedName("kpIndex") val kpIndex: Int,
)
