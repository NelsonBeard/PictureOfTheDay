package com.geekbrains.pictureoftheday.model.spaceweather

import com.google.gson.annotations.SerializedName

class SpaceWeatherServerResponseData(
    @SerializedName("allKpIndex") val allKpIndex: List<KpIndex>
) {
    data class KpIndex(
        @SerializedName("kpIndex") val kpIndex: Int,
    )
}