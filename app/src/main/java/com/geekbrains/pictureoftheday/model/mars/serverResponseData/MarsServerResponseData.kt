package com.geekbrains.pictureoftheday.model.mars.serverResponseData

import com.google.gson.annotations.SerializedName

class MarsServerResponseData(
    @SerializedName("photos") val photos: List<Photo>
)