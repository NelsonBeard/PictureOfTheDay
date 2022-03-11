package com.geekbrains.pictureoftheday.model.spaceweather.serverResponseData

import com.google.gson.annotations.SerializedName

class SpaceWeatherServerResponseData(
    @SerializedName("allKpIndex") val allKpIndex: List<KpIndex>
)