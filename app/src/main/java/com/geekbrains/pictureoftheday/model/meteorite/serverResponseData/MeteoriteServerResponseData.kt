package com.geekbrains.pictureoftheday.model.meteorite.serverResponseData

import com.google.gson.annotations.SerializedName


class MeteoriteServerResponseData(
    @SerializedName("near_earth_objects") val near_earth_objects: NearEarthObjects
)

